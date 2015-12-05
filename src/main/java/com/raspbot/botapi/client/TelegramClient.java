package com.raspbot.botapi.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;
import com.raspbot.botapi.models.UpdateResult;
import com.raspbot.botapi.models.Update;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class TelegramClient {

    private final String baseUrl;

    private int offset;

    public TelegramClient(String botName, String botToken) {

        this.baseUrl = "https://api.telegram.org/bot" + botName + ":" + botToken + "/";
        offset = 0;
    }

    public List<Update> getUpdates() throws UnirestException {

        HttpResponse<UpdateResult> response = get("getUpdates", "offset=" + offset).asObject(UpdateResult.class);
        UpdateResult apiResponse = response.getBody();

        List<Update> updates = Arrays.asList(apiResponse.Result);

        updateOffset(updates);

        return updates;
    }

    private void updateOffset(List<Update> updates) {
        if (updates.size() > 0) {
            offset = updates.stream().map(item -> item.UpdateId).max(Integer::max).get() + 1;
        }
    }

    static {
        initializeMapper();
    }

    public void sendText(int userId, String text) throws UnirestException {
        Unirest.post(baseUrl + "sendMessage")
                .field("chat_id", userId)
                .field("text", text)
                .asJson();
    }

    private GetRequest get(String apiMethod, String queryString) {

        String query = baseUrl + apiMethod;
        if (queryString != null && !queryString.isEmpty()) {
            query += "?" + queryString;
        }

        return Unirest.get(query);
    }


    private static void initializeMapper() {
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
    }
}
