package com.daney.bookfriends.library;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Book {

    @JacksonXmlProperty(localName = "title_info")
    private String title;

    @JacksonXmlProperty(localName = "type_name")
    private String typeName;

    @JacksonXmlProperty(localName = "place_info")
    private String placeInfo;

    @JacksonXmlProperty(localName = "author_info")
    private String author;

    @JacksonXmlProperty(localName = "pub_info")
    private String publisher;

    @JacksonXmlProperty(localName = "menu_name")
    private String menuName;

    @JacksonXmlProperty(localName = "media_name")
    private String mediaName;

    @JacksonXmlProperty(localName = "manage_name")
    private String manageName;

    @JacksonXmlProperty(localName = "pub_year_info")
    private String pubYear;

    @JacksonXmlProperty(localName = "control_no")
    private String controlNo;

    @JacksonXmlProperty(localName = "doc_yn")
    private String documentAvailable;

    @JacksonXmlProperty(localName = "org_link")
    private String originalLink;

    @JacksonXmlProperty(localName = "id")
    private String id;

    @JacksonXmlProperty(localName = "type_code")
    private String typeCode;

    @JacksonXmlProperty(localName = "lic_yn")
    private String licAvailable;

    @JacksonXmlProperty(localName = "lic_text")
    private String licText;

    @JacksonXmlProperty(localName = "reg_date")
    private String regDate;

    @JacksonXmlProperty(localName = "detail_link")
    private String detailLink;

    @JacksonXmlProperty(localName = "isbn")
    private String isbn;

    @JacksonXmlProperty(localName = "call_no")
    private String callNumber;

    @JacksonXmlProperty(localName = "kdc_code_1s")
    private String kdcCode1s;

    @JacksonXmlProperty(localName = "kdc_name_1s")
    private String kdcName1s;

    @JacksonXmlProperty(localName = "image_url")
    private String imageUrl;
}