package com.raspbot.commands;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.raspbot.ImageUtils;
import com.raspbot.botapi.client.TelegramClient;
import com.raspbot.botapi.models.Message;
import com.raspbot.capture.WebcamGrabber;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Command("/wazzup")
public class ScreenshotCommand implements BotCommand {

    private WebcamGrabber webcamGrabber;
    private TelegramClient client;

    public ScreenshotCommand(WebcamGrabber webcamGrabber, TelegramClient client) {
        this.webcamGrabber = webcamGrabber;
        this.client = client;
    }

    @Override
    public void exec(Message message) throws UnirestException, IOException {
        if (message.Chat.Id == -30979178) {
            BufferedImage img = ImageIO.read(new File("/home/yuri/Downloads/ginnes.jpg"));
            client.sendNewPhoto(message.Chat.Id, ImageUtils.convertToBytes(img));
            return;
           // client.sendText(message.Chat.Id, "Иди делай UML, " + message.From.FirstName);
          //  return;
        }

        BufferedImage img = webcamGrabber.grab();
        client.sendNewPhoto(message.Chat.Id, ImageUtils.convertToBytes(img));
    }
}
