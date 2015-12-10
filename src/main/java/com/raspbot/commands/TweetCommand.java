package com.raspbot.commands;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.raspbot.ImageUtils;
import com.raspbot.botapi.client.TelegramClient;
import com.raspbot.botapi.models.Message;
import com.raspbot.capture.WebcamGrabber;
import com.raspbot.twitter.TwitterApi;
import twitter4j.Status;
import twitter4j.TwitterException;

import java.awt.image.BufferedImage;
import java.io.IOException;

@Command("/tweet")
public class TweetCommand implements BotCommand {

    private WebcamGrabber webcamGrabber;
    private TelegramClient client;

    public TweetCommand(WebcamGrabber webcamGrabber, TelegramClient client) {
        this.webcamGrabber = webcamGrabber;
        this.client = client;
    }

    @Override
    public void exec(Message message) throws UnirestException, IOException, TwitterException {
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
        BufferedImage img = webcamGrabber.grab();
        Status response = twitter.send(img);
        client.sendText(message.Chat.Id, "https://twitter.com/inno_cave/status/" + response.getId());
    }
}
