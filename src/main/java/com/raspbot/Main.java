package com.raspbot;

import com.mashape.unirest.http.exceptions.UnirestException;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws UnirestException, InterruptedException, IOException {
        BotLauncher botLauncher = new BotLauncher();
        botLauncher.run();
    }
}
