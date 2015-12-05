package com.raspbot;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.raspbot.botapi.client.TelegramClient;
import com.raspbot.botapi.models.Update;
import com.raspbot.capture.WebcamGrabber;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Main {

    static final String botName = "152958668";
    static final String botToken = "AAGOo8wMRtFCArMJ90RGjD6pmyh3R2glKLg";


    public static void main(String[] args) throws UnirestException, InterruptedException, IOException {
        TelegramClient client = new TelegramClient(botName, botToken);

        WebcamGrabber.grab();

        while (true) {

            for (Update update : client.getUpdates()) {
                System.out.println(update.Message.From.Id + " : " + update.Message.Text);

                if (update.Message != null && update.Message.Text.toLowerCase().equals("/wazzup")) {
                    BufferedImage img = WebcamGrabber.grab();
                    client.sendNewPhoto(update.Message.From.Id, convertToBytes(img));
                }
            }

            Thread.sleep(200);
        }
    }

    private static byte[] convertToBytes(BufferedImage img) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(img, "jpg", baos);
        return baos.toByteArray();
    }
}
