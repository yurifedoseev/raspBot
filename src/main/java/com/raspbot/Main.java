package com.raspbot;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.raspbot.capture.WebcamGrabber;
import com.raspbot.twitter.TwitterApi;
import twitter4j.TwitterException;

import java.awt.image.BufferedImage;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws UnirestException, InterruptedException, IOException, TwitterException {
        BotLauncher botLauncher = new BotLauncher();
        botLauncher.run();
    }
}
