package com.raspbot.botapi.client;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.raspbot.botapi.models.UpdateResult;
import com.raspbot.botapi.models.Update;

import java.io.IOException;
import java.util.Arrays;
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

            {
                mapper.setPropertyNamingStrategy(PropertyNamingStrategy.CAMEL_CASE_TO_LOWER_CASE_WITH_UNDERSCORES);
            }

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

        HttpResponse<UpdateResult> response = Unirest.get(baseUrl + "getUpdates").asObject(UpdateResult.class);
        UpdateResult apiResponse = response.getBody();

        return Arrays.asList(apiResponse.Result);
    }
}
