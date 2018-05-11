package com.project.manager.data;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * This is the class which is responsible for taking avatar from IMG file contain that in byte[] array.
 * This class also converting image from byte[] array into javaFX image.
 */
@Component
public class InjectAvatar {

    /**
     * This method perform taking avatar from IMG file and return that in byte[] array.
     *
     * @return method return image converted into byte[] array.
     */
    public byte[] getAvatar() {

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            InputStream in = this.getClass().getResourceAsStream("/images/default-avatar.png");
            int length;
            byte[] buffer = new byte[1024];
            while ((length = in.read(buffer)) != -1) out.write(buffer, 0, length);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return out.toByteArray();
    }
}
