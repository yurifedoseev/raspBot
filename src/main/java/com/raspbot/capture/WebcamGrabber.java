package com.raspbot.capture;

import com.github.sarxos.webcam.Webcam;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class WebcamGrabber {

    public static BufferedImage grab() throws IOException {
        Webcam webcam = Webcam.getDefault();
        webcam.open();

        return webcam.getImage();
    }
}
