package com.raspbot.twitter;

import com.raspbot.botapi.client.TelegramClient;
import com.raspbot.capture.WebcamGrabber;
import com.raspbot.commands.BotCommand;
import com.raspbot.commands.Command;
import org.reflections.Reflections;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class CommandLoader {
    public static Map<String, BotCommand> load(WebcamGrabber webcamGrabber, TelegramClient client) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Reflections reflections = new Reflections("com.raspbot");

        Map<String, BotCommand> commandMap = new HashMap<>();

        for (Class<? extends BotCommand> commandClass : reflections.getSubTypesOf(BotCommand.class)) {

            Command annotation = commandClass.getAnnotation(Command.class);
            if (annotation == null){
                continue;
            }

            BotCommand cmdInstance = commandClass
                    .getConstructor(WebcamGrabber.class, TelegramClient.class)
                    .newInstance(webcamGrabber, client);

            commandMap.put(annotation.value(), cmdInstance);
        }

        return commandMap;
    }
}
