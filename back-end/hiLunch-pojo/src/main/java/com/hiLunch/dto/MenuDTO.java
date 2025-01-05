package com.hiLunch.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;


@Data
public class MenuDTO implements Serializable {

    private Long id;
    private String name;
    private Integer price;
    private String description;
    private String image;
    private Integer category;
    private Integer isSale;
    private Integer weekday;


}
