package com.sogogo.bo.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GlobalUtils {
    public static boolean validateUsername(String username){
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9._-]{3,}$");
        Matcher matcher = pattern.matcher(username);
        return matcher.matches();
    }
}
