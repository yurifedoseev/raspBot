package com.raspbot.capture;

import com.github.sarxos.webcam.Webcam;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Camera controlled based on github.org/sarxos solution
 */
public class WebcamSarxosGrabber implements WebcamGrabber {

    public BufferedImage grab() throws IOException {
        Webcam webcam = Webcam.getDefault();
        webcam.open();

        BufferedImage image = webcam.getImage();
        if (webcam.isOpen())
            webcam.close();
        return image;
    }
}
