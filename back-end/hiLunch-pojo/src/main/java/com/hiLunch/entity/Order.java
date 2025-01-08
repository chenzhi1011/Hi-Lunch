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
public class Order {
    private Integer id;
    private Long orderNo;
    private Long userId;
    private Integer menuId;
    private Integer num;
    private LocalDateTime createTime;

}
