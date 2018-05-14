package com.project.manager.utils;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.WritableImage;
import lombok.extern.log4j.Log4j;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

/**
 * Class contains methods to convert javaafx image back and forth.
 */
@Log4j
public class ImageConverter {
    /**
     * This method perform converting image from byte[] array into JavaFX image.
     *
     * @return method return image converted into JavaFX image.
     */
    public static javafx.scene.image.Image convertToImage(byte[] raw, final int width, final int height) {
        WritableImage image = new WritableImage(width, height);
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(raw);
            BufferedImage read = ImageIO.read(bis);
            image = SwingFXUtils.toFXImage(read, null);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return image;
    }

    /**
     * Method convert InputStream to byte array
     * @param in insputed stream to convert
     * @return byte array of inputed stream
     */
    public static byte[] convertToBytes(InputStream in) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            int length;
            byte[] buffer = new byte[1024];
            while ((length = in.read(buffer)) != -1) out.write(buffer, 0, length);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return out.toByteArray();
    }

    /**
     * Method convert File to byte array
     * @param file file to convert
     * @return array of inputed stream
     */
    public static byte[] convertToBytes(File file) {
        try {
            return convertToBytes(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            log.error("File not found at path " + file.getAbsolutePath());
            return null;
        }
    }
}
