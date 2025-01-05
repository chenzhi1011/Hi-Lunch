package com.hiLunch.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class MenuPageQueryDTO implements Serializable {

    private int page;

    private int pageSize;

    private String name;

    private Integer category;

    private Integer isSale;

}
