package com.daney.bookfriends.library;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

import java.util.List;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BooksResponse {

    @JacksonXmlProperty(localName = "paramData")
    private ParamData paramData;

    @JacksonXmlElementWrapper(localName = "result")
    @JacksonXmlProperty(localName = "record")
    private List<Book> books;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ParamData {
        @JacksonXmlProperty(localName = "kwd")
        private String kwd;

        @JacksonXmlProperty(localName = "category")
        private String category;

        @JacksonXmlProperty(localName = "pageNum")
        private String pageNum;

        @JacksonXmlProperty(localName = "pageSize")
        private String pageSize;

        @JacksonXmlProperty(localName = "sort")
        private String sort;

        @JacksonXmlProperty(localName = "total")
        private String total;
    }
}
