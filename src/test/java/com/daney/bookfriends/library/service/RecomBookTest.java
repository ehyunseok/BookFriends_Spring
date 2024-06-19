//package com.daney.bookfriends;
//
//import com.daney.bookfriends.library.RecomBook;
//import com.fasterxml.jackson.dataformat.xml.XmlMapper;
//import org.junit.jupiter.api.Test;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//public class RecomBookTest {
//
//    @Test
//    public void testDeserializeFromXml() throws Exception {
//        String xml = "<RecomBook>\n" +
//                "    <recom_no>1</recom_no>\n" +
//                "    <recom_title>Sample Book Title</recom_title>\n" +
//                "    <recom_author>John Doe</recom_author>\n" +
//                "    <recom_publisher>Sample Publisher</recom_publisher>\n" +
//                "    <recom_isbn>1234567890</recom_isbn>\n" +
//                "    <publish_Year>2021</publish_Year>\n" +
//                "    <recom_mokcha>Sample Table of Contents</recom_mokcha>\n" +
//                "    <recom_contents>Sample Content Description</recom_contents>\n" +
//                "    <recom_file_path>/path/to/image.jpg</recom_file_path>\n" +
//                "    <recom_year>2023</recom_year>\n" +
//                "    <recom_month>06</recom_month>\n" +
//                "    <mokchaFilePath>/path/to/mokcha.jpg</mokchaFilePath>\n" +
//                "</RecomBook>";
//
//        XmlMapper xmlMapper = new XmlMapper();
//        RecomBook recomBook = xmlMapper.readValue(xml, RecomBook.class);
//
////        assertEquals(1, recomBook.getRecomNo());
////        assertEquals("Sample Book Title", recomBook.getRecomTitle());
////        assertEquals("John Doe", recomBook.getRecomAuthor());
////        assertEquals("Sample Publisher", recomBook.getRecomPublisher());
////        assertEquals("1234567890", recomBook.getRecomIsbn());
////        assertEquals("2021", recomBook.getPublishYear());
////        assertEquals("Sample Table of Contents", recomBook.getRecomMocha());
////        assertEquals("Sample Content Description", recomBook.getRecomContents());
////        assertEquals("/path/to/image.jpg", recomBook.getRecomFilePath());
////        assertEquals("2023", recomBook.getRecomYear());
////        assertEquals("06", recomBook.getRecomMonth());
////        assertEquals("/path/to/mokcha.jpg", recomBook.getMokchaFilePath());
//    }
//}
