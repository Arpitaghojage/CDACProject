package com.cdac.dto;

import com.cdac.entities.Category;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BookRespDTO {
    private Long id;
    private String title;
    private String author;
    private double price;
    private int stock;
    private String imageUrl; // base64 string
    private Category category;
}
