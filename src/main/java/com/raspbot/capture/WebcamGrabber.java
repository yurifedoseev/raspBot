package com.raspbot.capture;

import java.awt.image.BufferedImage;
import java.io.IOException;

public interface WebcamGrabber {
    BufferedImage grab() throws IOException;
}
