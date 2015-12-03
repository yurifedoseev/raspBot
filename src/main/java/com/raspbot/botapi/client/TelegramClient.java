package com.raspbot.botapi.client;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.raspbot.botapi.models.Update;

import java.util.ArrayList;
import java.util.List;

public class TelegramClient {

    private final String baseUrl;

    public TelegramClient(String botName, String botToken) {

        this.baseUrl = "https://api.telegram.org/bot" + botName + ":" + botToken + "/";
    }

    public List<Update> getUpdates() throws UnirestException {
        HttpResponse<String> response = Unirest.get(baseUrl + "getUpdates").asString();

        System.out.println(response.getBody());

        return new ArrayList<Update>();
    }
}
