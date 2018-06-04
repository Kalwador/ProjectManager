package com.project.manager.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class contain method to validate email adress
 */
public class EmailValidator {
    /**
     *
     * @param email e-mail adress of String type
     * @return true if email is correct, false otherwise
     */
    public static boolean isEmailValid(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        Pattern p = Pattern.compile(ePattern);
        Matcher m = p.matcher(email);
        return m.matches();
    }
}
