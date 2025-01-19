package com.hiLunch.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.bytebuddy.asm.Advice;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Stocks {
    private Long id;
    private Long menuId;
    private Integer stock;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
