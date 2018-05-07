package com.toskey.framework.core.util;

import com.baomidou.mybatisplus.toolkit.IdWorker;

import java.util.UUID;

/**
 * ID生成器
 */
public class IdGen {

    public static String getId() {
        return String.valueOf(IdWorker.getId());
    }

    public static long getIdLong() {
        return IdWorker.getId();
    }

    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-","");
    }
}
