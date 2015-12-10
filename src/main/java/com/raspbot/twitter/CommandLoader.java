package com.raspbot.twitter;

import com.raspbot.commands.Command;
import org.reflections.Reflections;

public class CommandLoader {
    public static void load(){
        Reflections reflections = new Reflections("com.raspbot");
        System.out.println(reflections.getTypesAnnotatedWith(Command.class).size());
    }
}
