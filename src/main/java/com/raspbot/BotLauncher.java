package com.raspbot;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.raspbot.botapi.client.TelegramClient;
import com.raspbot.botapi.models.Message;
import com.raspbot.botapi.models.Update;
import com.raspbot.capture.WebcamGrabber;
import com.raspbot.twitter.TwitterApi;
import twitter4j.Status;
import twitter4j.TwitterException;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BotLauncher {

    private final static int THREAD_COUNT = 10;

    public void run() throws InterruptedException, IOException, UnirestException, TwitterException {
        TelegramClient client = new TelegramClient(Config.getBotToken());
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);

        while (true) {
            for (Update update : client.getUpdates()) {

                executor.submit((Runnable) () -> {
                    try {
                        processMessage(client, update.Message);
                    } catch (IOException | UnirestException | TwitterException e) {
                        e.printStackTrace();
                    }
                });
            }

            Thread.sleep(200);
        }
    }

    private void processMessage(TelegramClient client, Message message) throws IOException, UnirestException, TwitterException {
        LogMessage(message);
        if (isCommand(message, "/чекак") || isCommand(message, "/wazzup")) {

            if (message.Chat.Id == -30979178) {
                client.sendText(message.Chat.Id, "Иди делай UML, " + message.From.FirstName);
                return;
            }

            BufferedImage img = WebcamGrabber.grab();
            client.sendNewPhoto(message.Chat.Id, ImageUtils.convertToBytes(img));

        }

        if (isCommand(message, "/tweet")) {

            if (message.Chat.Id == -30979178) {
                client.sendText(message.Chat.Id, "Иди делай UML, " + message.From.FirstName);
                return;
            }

            if (message.From.Id == 102160912) {
                client.sendText(message.Chat.Id, "Азамат, успокойся!");
                return;
            }

            if (message.From.Id == 119475929) {
                client.sendText(message.Chat.Id, "Саша - в бан!");
                return;
            }


            TwitterApi twitter = new TwitterApi();
            BufferedImage img = WebcamGrabber.grab();
            Status response = twitter.send(img);
            client.sendText(message.Chat.Id, "https://twitter.com/inno_cave/status/" + response.getId());
        }
    }

    private void LogMessage(Message message) {


        System.out.println(message.From.FirstName + " " + message.From.LastName + " " + message.From.Username + " "
                + message.From.Id + " "
                +
                "chat:" + message.Chat.Id
                +
                " : " + message.Text
                +
                " thread:" + Thread.currentThread().getId()
                );
    }

    private boolean isCommand(Message message, String commandText) {
        return message != null && message.Text != null && message.Text.toLowerCase().equals(commandText);
    }
}
