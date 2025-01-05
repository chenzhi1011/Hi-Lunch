package com.hiLunch.vo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.time.LocalDateTime;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuVO implements Serializable {


    private Long id;
    private String name;
    private Integer price;
    private String description;
    private String image;
    private Integer category;
    private Integer isSale;
    private Integer weekday;
    private LocalDateTime updateTime;

}
