package com.hiLunch.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class OrderNumberGenUtil {
    public static String generateOrderNumber() {
        String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        int random = new Random().nextInt(9999);
        return timestamp + String.format("%04d", random);
    }
}
