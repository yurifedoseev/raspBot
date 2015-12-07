package com.raspbot.twitter;

import twitter4j.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class TwitterApi {

    private final Twitter twitter;

    public TwitterApi() {
        twitter = TwitterFactory.getSingleton();
    }

    public Status send(BufferedImage img) throws TwitterException, IOException {
        StatusUpdate statusUpdate = new StatusUpdate("Че там в кейве?");
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(img, "jpg", os);
        InputStream is = new ByteArrayInputStream(os.toByteArray());

        statusUpdate.setMedia("file.jpg", is);
        Status responseStatus = twitter.updateStatus(statusUpdate);
        is.close();
        return responseStatus;
    }
}
