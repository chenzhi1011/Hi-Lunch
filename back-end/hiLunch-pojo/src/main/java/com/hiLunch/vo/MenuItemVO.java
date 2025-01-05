package com.hiLunch.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuItemVO implements Serializable {

    private String name;

    //注文数量
    private Integer copies;

    private String image;

    private String description;
}
