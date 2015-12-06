package com.raspbot;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ImageUtils {
    public static byte[] convertToBytes(BufferedImage img) throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        ImageIO.write(img, "jpg", stream);
        return stream.toByteArray();
    }
}
