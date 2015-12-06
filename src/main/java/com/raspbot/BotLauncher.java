package com.raspbot;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.raspbot.botapi.client.TelegramClient;
import com.raspbot.botapi.models.Update;
import com.raspbot.capture.WebcamGrabber;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class BotLauncher {

    static final String botName = "152958668";
    static final String botToken = "AAGOo8wMRtFCArMJ90RGjD6pmyh3R2glKLg";

    public void run() throws InterruptedException, IOException, UnirestException {
        TelegramClient client = new TelegramClient(botName, botToken);

        while (true) {

            for (Update update : client.getUpdates()) {
                System.out.println(update.Message.From.Id + " : " + update.Message.Text);

                if (update.Message != null && update.Message.Text != null && update.Message.Text.toLowerCase().equals("/чекак")) {
                    BufferedImage img = WebcamGrabber.grab();

                    int sendToId = update.Message.From.Id;

                    if (update.Message.Chat != null) {
                        sendToId = update.Message.Chat.Id;
                    }

                    client.sendNewPhoto(sendToId, convertToBytes(img));
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
