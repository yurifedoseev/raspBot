package com.raspbot.botapi.client;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.raspbot.botapi.models.Update;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TelegramClient {

    private final String baseUrl;

    public TelegramClient(String botName, String botToken) {

        this.baseUrl = "https://api.telegram.org/bot" + botName + ":" + botToken + "/";
    }

    public List<Update> getUpdates() throws UnirestException {

        Unirest.setObjectMapper(new ObjectMapper() {

            private com.fasterxml.jackson.databind.ObjectMapper mapper
                    = new com.fasterxml.jackson.databind.ObjectMapper();

            public <T> T readValue(String value, Class<T> valueType) {

                try {
                    return mapper.readValue(value, valueType);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            public String writeValue(Object o) {
                try {
                    return mapper.writeValueAsString(o);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        HttpResponse<String> response = Unirest.get(baseUrl + "getUpdates").asString();

        System.out.println(response.getBody());

        return new ArrayList<Update>();
    }
}
