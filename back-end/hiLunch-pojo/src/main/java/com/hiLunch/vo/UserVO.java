package com.hiLunch.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserVO {
    private Long id;
    private String pwd;
    private String department;
    private String email;
    private String token;
    private String image;

}
