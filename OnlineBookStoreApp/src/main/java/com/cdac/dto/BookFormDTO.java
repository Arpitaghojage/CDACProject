package com.cdac.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class BookFormDTO {

    private String title;
    private String author;
    private double price;
    private int stock;
    private String categoryName;

    private MultipartFile image; // Not byte[], because we're receiving the file from form
}
