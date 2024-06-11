package com.daney.bookfriends.library.controller;

import com.daney.bookfriends.library.Book;
import com.daney.bookfriends.library.RecomBook;
import com.daney.bookfriends.library.service.LibraryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/library")
public class LibraryController {

    private final LibraryService libraryService;

    @Autowired
    public LibraryController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    // 국립중앙도서관 소장자료 검색
    @GetMapping("/search")
    public String librarySearchPage(@RequestParam(name = "query", required = false) String query,
                                    @RequestParam(name = "page", required = false, defaultValue = "1") int page,
                                    Model model) {

        log.debug("Received search query: {}", query);

        if (query == null || query.isEmpty()) {
            model.addAttribute("books", null);
            model.addAttribute("message", "검색어를 입력해주세요.");
            return "library/search";
        }

        try {
            int pageSize = 10;
            List<Book> books = libraryService.searchBooks(query, page, pageSize);
            int totalBooks = libraryService.getTotalBooks(query);
            int totalPages = (int) Math.ceil((double) totalBooks / pageSize);
            int startPage = ((page - 1) / 10) * 10 + 1;
            int endPage = Math.min(startPage + 9, totalPages);

            if (books == null || books.isEmpty()) {
                log.debug("No books found for query: {}", query);
                model.addAttribute("message", "검색 결과가 없습니다.");
            } else {
                log.debug("Books found: {}", books);
                model.addAttribute("books", books);
                model.addAttribute("totalBooks", totalBooks);
                model.addAttribute("totalPages", totalPages);
                model.addAttribute("page", page);
                model.addAttribute("query", query);
                model.addAttribute("startPage", startPage);
                model.addAttribute("endPage", endPage);
                model.addAttribute("pageSize", pageSize);
            }
        } catch (Exception e) {
            log.error("Error occurred while searching for books: ", e);
            model.addAttribute("error", "도서 검색 중 오류가 발생했습니다. 잠시 후 다시 시도해주세요.");
        }

        return "library/search";
    }


    // 사서 추천도서
    @GetMapping("/recommended")
    public String getRecommendedBooks(
            @RequestParam(value = "year", required = false) String year,
            @RequestParam(value = "kdcName", required = false) String kdcName,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
            Model model) {

        log.debug("Received request for recommended books with year: {}, kdcName: {}, page: {}, pageSize: {}", year, kdcName, page, pageSize);

        List<RecomBook> recommendedBooks = libraryService.fetchRecommendedBooksByYearAndKdcName(year, kdcName, page, pageSize);
        int totalCount = libraryService.getTotalRecommendedBooksCount(year, kdcName);

        int totalPages = (int) Math.ceil((double) totalCount / pageSize);
        int startPage = ((page - 1) / 10) * 10 + 1;
        int endPage = Math.min(startPage + 9, totalPages);

        log.debug("Total recommended books: {}", totalCount);
        log.debug("Recommended books: {}", recommendedBooks);

        model.addAttribute("recommendedBooks", recommendedBooks);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("year", year);
        model.addAttribute("kdcName", kdcName);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "library/recommended"; // View name
    }
}
