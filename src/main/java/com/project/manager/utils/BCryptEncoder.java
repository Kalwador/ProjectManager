package com.project.manager.utils;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Class which is using to cypher passwords and equals them
 */
public class BCryptEncoder {
    private static final BCrypt bcrypt = new BCrypt();

    /**
     * Method to cypher password by BCrypt algorithm
     *
     * @param password this is parameter where is stored password
     * @return method return encrypted password as as String
     */
    public static String encode(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt(12));
    }

    /**
     * This method is use to equals encrypted passwords
     *
     * @param setPassword     this is password which user give
     * @param correctPassword this is password which is original user password
     * @return method return true if passwords are the same
     */
    public static boolean check(String setPassword, String correctPassword) {
        return bcrypt.checkpw(setPassword, correctPassword);
    }
}
