package com.daney.bookfriends.library;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RecomBooksResponse {

    @JacksonXmlProperty(localName = "totalCount")
    private int totalCount;

    @JacksonXmlElementWrapper(localName = "list")
    @JacksonXmlProperty(localName = "item")
    private List<RecomBook> recomBookList;

}
