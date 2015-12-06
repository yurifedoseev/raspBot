package com.raspbot;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.raspbot.botapi.client.TelegramClient;
import com.raspbot.botapi.models.Message;
import com.raspbot.botapi.models.Update;
import com.raspbot.capture.WebcamGrabber;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class BotLauncher {

    static final String botName = "152958668";
    static final String botToken = "AAGOo8wMRtFCArMJ90RGjD6pmyh3R2glKLg";

    public void run() throws InterruptedException, IOException, UnirestException {
        TelegramClient client = new TelegramClient(Config.getBotToken());

        while (true) {

            for (Update update : client.getUpdates()) {
                processMessage(client, update.Message);
            }

            Thread.sleep(200);
        }
    }

    private void processMessage(TelegramClient client, Message message) throws IOException, UnirestException {
        System.out.println(message.From.Id + " : " + message.Text);
        if (isCommand(message, "/чекак")) {
            BufferedImage img = WebcamGrabber.grab();
            client.sendNewPhoto(message.Chat.Id, ImageUtils.convertToBytes(img));
        }
    }

    private boolean isCommand(Message message, String commandText) {
        return message != null && message.Text != null && message.Text.toLowerCase().equals(commandText);
    }
}
