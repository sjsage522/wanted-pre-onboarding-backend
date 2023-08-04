package com.junseok.wantedpreonboardingbackend.global.util;

import org.springframework.stereotype.Component;

@Component
public class ByteUtils {

    public static String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes) {
            result.append(String.format("%02x", b));
        }
        return result.toString();
    }
}
