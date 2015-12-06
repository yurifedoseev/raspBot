package com.raspbot.botapi.client;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.raspbot.botapi.models.Update;
import com.raspbot.botapi.models.UpdateResult;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.mashape.unirest.http.Unirest.get;
import static com.mashape.unirest.http.Unirest.post;

public class TelegramClient {

    private final String baseUrl;

    private int offset;

    static {
        RestConfig.init();
    }


    public TelegramClient(String botName, String botToken) {

        this.baseUrl = "https://api.telegram.org/bot" + botName + ":" + botToken + "/";
        offset = 0;
    }

    public List<Update> getUpdates() throws UnirestException {

        HttpResponse<UpdateResult> response = get("getUpdates?offset=" + offset).asObject(UpdateResult.class);
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

    public void sendText(int userId, String text) throws UnirestException {
        post(baseUrl + "sendMessage")
                .field("chat_id", userId)
                .field("text", text)
                .asJson();
    }

    public void sendNewPhoto(int userId, byte[] photo) throws UnirestException, IOException {
        File tempFile = File.createTempFile(userId+"ds", "jpg");
        FileOutputStream fos = new FileOutputStream(tempFile);
        fos.write(photo);

        post(baseUrl + "sendPhoto?chat_id="+userId)
                .field("chat_id", userId)
                .field("photo", tempFile)
                .asJson();
    }

    public void sendExistingPhoto(int userId, String photoId) throws UnirestException, IOException {
        post(baseUrl + "sendPhoto?chat_id=" + userId)
                .field("chat_id", userId)
                .field("photo", photoId)
                .asJson();
    }
}
