package com.raspbot;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.raspbot.botapi.client.TelegramClient;

public class Main {

    static final String botName = "152958668";
    static final String botToken = "AAGOo8wMRtFCArMJ90RGjD6pmyh3R2glKLg";


    public static void main(String[] args) throws UnirestException, InterruptedException {
        TelegramClient client = new TelegramClient(botName, botToken);

        while (true) {
            client.getUpdates();

            Thread.sleep(200);
        }
    }
}
