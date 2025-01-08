package com.hiLunch.dto;

import com.hiLunch.entity.Order;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDTO {
    private Long orderNo;
    private Integer menuId;
    private Integer num;
    private LocalDateTime createTime;
}