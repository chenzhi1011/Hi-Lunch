package com.hiLunch.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    private Long id;
    private String pwd;
    private String department;
    private String email;
    private String image;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

}
