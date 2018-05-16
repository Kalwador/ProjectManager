package com.project.manager.data;

import com.project.manager.utils.ImageConverter;
import org.springframework.stereotype.Component;

@Component
public class InjectAvatar {

    /**
     * This method perform taking avatar from IMG file and return that in byte[] array.
     *
     * @return method return image converted into byte[] array.
     */
    public byte[] getAvatar() {
            return ImageConverter.convertToBytes(this.getClass()
                    .getResourceAsStream("/images/default-avatar.png"));
    }
}
