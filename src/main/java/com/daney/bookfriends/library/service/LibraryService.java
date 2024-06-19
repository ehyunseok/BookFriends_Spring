package com.daney.bookfriends.library.service;

import com.daney.bookfriends.config.ApiConfig;
import com.daney.bookfriends.library.Book;
import com.daney.bookfriends.library.BooksResponse;
import com.daney.bookfriends.library.RecomBook;
import com.daney.bookfriends.library.RecomBooksResponse;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
public class LibraryService {

    private final RestTemplate restTemplate;
    private final ApiConfig apiConfig;
    private final XmlMapper xmlMapper;

    @Autowired
    public LibraryService(RestTemplate restTemplate, ApiConfig apiConfig, XmlMapper xmlMapper) {
        this.restTemplate = restTemplate;
        this.apiConfig = apiConfig;
        this.xmlMapper = xmlMapper;
    }

    // 국중 소장 자료 검색
    // Redis 캐시 적용-도서 검색 결과를 캐싱함으로써 자주 조회되는 데이터에 대해 빠른 응답을 제공함
    @Cacheable(value = "books", key = "#query + '-' + #page + '-' + #pageSize")
    public List<Book> searchBooks(String query, int page, int pageSize) {
        try {
            String apiKey = apiConfig.getKey();
            int startRow = (page - 1) * pageSize + 1;

            String url = "https://www.nl.go.kr/NL/search/openApi/search.do?key=" + apiKey +
                    "&kwd=" + query +
                    "&startRowNumApi=" + startRow +
                    "&endRowNumApi=" + (startRow + pageSize - 1);

            log.debug("Request URL: {}", url);
            String response = restTemplate.getForObject(url, String.class);
            log.debug("XML Response: {}", response);

            BooksResponse booksResponse = xmlMapper.readValue(response, BooksResponse.class);
            log.debug("BooksResponse: {}", booksResponse);

            if (booksResponse != null && booksResponse.getBooks() != null) {
                return booksResponse.getBooks();
            } else {
                log.debug("No books found for query: {}", query);
                return Collections.emptyList();
            }
        } catch (Exception e) {
            log.error("Error occurred while searching for books: ", e);
            return Collections.emptyList();
        }
    }

    @Cacheable(value = "totalBooks", key = "#query") // Redis 캐시 적용
    public int getTotalBooks(String query) {
        try {
            String apiKey = apiConfig.getKey();


            String url = "https://www.nl.go.kr/NL/search/openApi/search.do?key=" + apiKey + "&kwd=" + query;

            log.debug("Request URL for total count: {}", url);
            String response = restTemplate.getForObject(url, String.class);
            log.debug("XML Response: {}", response);

            BooksResponse booksResponse = xmlMapper.readValue(response, BooksResponse.class);
            log.debug("BooksResponse: {}", booksResponse);

            if (booksResponse != null && booksResponse.getParamData() != null) {
                return Integer.parseInt(booksResponse.getParamData().getTotal());
            } else {
                return 0;
            }
        } catch (Exception e) {
            log.error("Error occurred while getting total books count: ", e);
            return 0;
        }
    }

    // 사서 추천 도서
    // Redis 캐시 적용-추천 도서 조회 결과를 캐싱함으로써 자주 조회되는 데이터에 대해 빠른 응답을 제공함
    @Cacheable(value = "recommendedBooks", key = "#year + '-' + #kdcName + '-' + #page + '-' + #pageSize")
    public List<RecomBook> fetchRecommendedBooksByYearAndKdcName(String year, String kdcName, int page, int pageSize) {
        String apiKey = apiConfig.getKey();
        int startRow = (page - 1) * pageSize + 1;
        int endRow = startRow + pageSize - 1;

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("https://nl.go.kr/NL/search/openApi/saseoApi.do")
                .queryParam("key", apiKey)
                .queryParam("startRowNumApi", startRow)
                .queryParam("endRowNumApi", endRow);

        if (year != null && !year.isEmpty()) {
            builder.queryParam("start_date", year + "0101")
                    .queryParam("end_date", year + "1231");
        }

        if (kdcName != null && !kdcName.equals("전체")) {
            int drCode = mapKdcNameToDrCode(kdcName);
            builder.queryParam("drCode", drCode);
        }

        String url = builder.encode().toUriString();

        try {
            log.debug("Request URL for recommended books: {}", url);
            String response = restTemplate.getForObject(url, String.class);
            log.debug("XML Response for recommended books: {}", response);

            RecomBooksResponse booksResponse = xmlMapper.readValue(response, RecomBooksResponse.class);
            return booksResponse != null ? booksResponse.getRecomBookList() : Collections.emptyList();
        } catch (Exception e) {
            log.error("Error occurred while fetching recommended books: ", e);
            return Collections.emptyList();
        }
    }

    @Cacheable(value = "totalRecommendedBooks", key = "#year + '-' + #kdcName") // Redis 캐시 적용
    public int getTotalRecommendedBooksCount(String year, String kdcName) {
        String apiKey = apiConfig.getKey();

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("https://nl.go.kr/NL/search/openApi/saseoApi.do")
                .queryParam("key", apiKey);

        if (year != null && !year.isEmpty()) {
            builder.queryParam("start_date", year + "0101")
                    .queryParam("end_date", year + "1231");
        }

        if (kdcName != null && !kdcName.equals("전체")) {
            int drCode = mapKdcNameToDrCode(kdcName);
            builder.queryParam("drCode", drCode);
        }

        String url = builder.encode().toUriString();

        try {
            log.debug("Request URL for total count of recommended books: {}", url);
            String response = restTemplate.getForObject(url, String.class);
            log.debug("XML Response for total count of recommended books: {}", response);

            RecomBooksResponse booksResponse = xmlMapper.readValue(response, RecomBooksResponse.class);
            return booksResponse != null ? booksResponse.getTotalCount() : 0;
        } catch (Exception e) {
            log.error("Error occurred while getting total recommended books count: ", e);
            return 0;
        }
    }

    private int mapKdcNameToDrCode(String kdcName) {
        switch (kdcName) {
            case "문학":
                return 11;
            case "인문과학":
                return 6;
            case "사회과학":
                return 5;
            case "자연과학":
                return 4;
            case "기타":
                return 3;
            default:
                return 0;
        }
    }
}
