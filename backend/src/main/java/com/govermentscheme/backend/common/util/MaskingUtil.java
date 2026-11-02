package com.govermentscheme.backend.common.util;

public final class MaskingUtil {
    private MaskingUtil() {}

    public static String maskPhone(String phone) {
        if (phone == null || phone.length() < 4) return "****";
        return "******" + phone.substring(phone.length() - 4);
    }

    public static String maskEmail(String email) {
        if (email == null || !email.contains("@")) return "****";
        int at = email.indexOf("@");
        String user = email.substring(0, at);
        if (user.length() <= 2) return "**" + email.substring(at);
        return user.charAt(0) + "***" + user.charAt(user.length() - 1) + email.substring(at);
    }
}