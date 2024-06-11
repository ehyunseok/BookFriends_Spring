package com.daney.bookfriends.library;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RecomBook {
    private String recomNo;
    private String drCode;
    private String drCodeName;
    private String recomtitle;
    private String recomauthor;
    private String recompublisher;
    private String recomcallno;
    private String recomisbn;
    private String recomfilepath;
    private String recommokcha;
    private String recomcontents;
    private String regdate;
    private String controlNo;
    private String publishYear;
    private String recomYear;
    private String recomMonth;
    private String mokchFilePath;
    private String kdcName;
}
