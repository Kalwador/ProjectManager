package com.project.manager.utils;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;

/**
 * Class contain method to validate files for image type
 */
public class ImageValidator {
    /**
     * Check if an file is type of image
     * @param file file to check
     * @return true if it is image, false otherwise
     */
    public static boolean isFileAnImage(File file) {
        String mimetype = new MimetypesFileTypeMap().getContentType(file);
        String type = mimetype.split("/")[0];
        if (type.equals("image")) {
            return true;
        } else {
            return false;
        }
    }
}
