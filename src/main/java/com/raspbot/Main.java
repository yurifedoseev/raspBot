package com.raspbot;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.raspbot.capture.WebcamGrabber;
import com.raspbot.twitter.TwitterApi;
import twitter4j.TwitterException;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class Main {

    public static void main(String[] args) throws UnirestException, InterruptedException, IOException, TwitterException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        BotLauncher botLauncher = new BotLauncher();
        botLauncher.run();
    }
}
