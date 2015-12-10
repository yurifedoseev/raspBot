package com.raspbot;

import com.mashape.unirest.http.exceptions.UnirestException;
import com.raspbot.botapi.client.TelegramClient;
import com.raspbot.botapi.models.Message;
import com.raspbot.botapi.models.Update;
import com.raspbot.capture.WebcamGrabber;
import com.raspbot.capture.WebcamSarxosGrabber;
import com.raspbot.commands.BotCommand;
import com.raspbot.commands.ScreenshotCommand;
import com.raspbot.commands.TweetCommand;
import com.raspbot.twitter.CommandLoader;
import twitter4j.TwitterException;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BotLauncher {

    private final static int THREAD_COUNT = 10;

    private TelegramClient client;
    private final Map<String, BotCommand> commandMap;

    public BotLauncher() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        WebcamGrabber webcamGrabber = new WebcamSarxosGrabber();
        client = new TelegramClient(Config.getBotToken());

        commandMap = CommandLoader.load(webcamGrabber, client);

//        commandMap = new HashMap<>();
//        commandMap.put("/wazzup", new ScreenshotCommand(webcamGrabber, client));
//        commandMap.put("/tweet", new TweetCommand(webcamGrabber, client));

    }

    public void run() throws InterruptedException, IOException, UnirestException, TwitterException {
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);

        while (true) {
            for (Update update : client.getUpdates()) {
                executor.submit((Runnable) () -> {
                    try {
                        processMessage(update.Message);
                    } catch (IOException | UnirestException | TwitterException e) {
                        e.printStackTrace();
                    }
                });
            }

            Thread.sleep(200);
        }
    }

    private void processMessage(Message message) throws IOException, UnirestException, TwitterException {
        LogMessage(message);

        String commandText = getCommandText(message);

        BotCommand command = commandMap.getOrDefault(commandText, null);

        if (command != null){
            command.exec(message);
        }
    }

    private void LogMessage(Message message) {

        String log = message.From.FirstName +
                " " + message.From.LastName +
                " " + message.From.Username +
                " " + message.From.Id +
                " " + message.Chat.Id +
                " " + message.Text;

        System.out.println(log);
    }

    private String getCommandText(Message message) {
        if (message == null || message.Text == null) {
            return "";
        }

        return message.Text.toLowerCase();
    }
}
