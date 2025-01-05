package com.hiLunch.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;
    private Long id;
    private String name;
    private Integer price;
    private String description;
    private String image;
    private Integer category;
    private Integer isSale;
    //TODO weekday新增的项目
    private Integer weekday;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Long createUser;
    private Long updateUser;
}
