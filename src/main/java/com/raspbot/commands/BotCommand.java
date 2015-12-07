package com.raspbot.commands;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.raspbot.botapi.models.Message;
import twitter4j.TwitterException;

import java.io.IOException;

public interface BotCommand {
    void exec(Message message) throws UnirestException, IOException, TwitterException;
}
