//package com.daney.bookfriends;
//
//import com.daney.bookfriends.config.ApiConfig;
//import com.daney.bookfriends.library.service.LibraryService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.util.Assert;
//
//@SpringBootTest
//public class LibraryServiceTest {
//
//    @Autowired
//    private ApiConfig apiConfig;
//    @Autowired
//    private LibraryService libraryService;
//
////    @Test
////    public void testSearchBooks(){
////        String query = "Java";
////        String result = libraryService.searchBooks(query);
////        System.out.println(result);
////        Assert.notNull(result, "Result should not be null");
////    }
////
////    @Test
////    public void testGetRecommendedBooks(){
////        String result = libraryService.getRecommendedBooks();
////        System.out.println(result);
////        Assert.notNull(result, "Result should not be null");
////    }
//
//    @Test
//    public void testApiKey() {
//        String apiKey = apiConfig.getKey();
//        System.out.println(apiKey);
//        Assert.notNull(apiKey, "API Key should not be null");
//    }
//
//
//}
