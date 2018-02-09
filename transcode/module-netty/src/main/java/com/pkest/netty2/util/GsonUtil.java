package com.pkest.netty2.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pkest.netty2.gson.LowercaseEnumTypeAdapterFactory;

/**
 * Helper class with commonly used methods
 */
public final class GsonUtil {

    final static Gson gson = GsonUtil.setupGson();


    GsonUtil() {
        throw new AssertionError("No instances for you!");
    }

    public static Gson getGson() {
        return gson;
    }

    private static Gson setupGson() {
        GsonBuilder builder = new GsonBuilder();

        builder.registerTypeAdapterFactory(new LowercaseEnumTypeAdapterFactory());

        return builder.create();
    }
}

