package com.epam.rd.java.basic.repairagency.factory;

import com.epam.rd.java.basic.repairagency.web.command.Command;
import com.epam.rd.java.basic.repairagency.web.command.Method;
import com.epam.rd.java.basic.repairagency.web.command.UrlPatternAndMethod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

public final class CommandFactory {

    private static final Logger log = LogManager.getLogger(CommandFactory.class);
    private final Map<UrlPatternAndMethod, Command> commandsByUrlPatternAndMethod;

    public CommandFactory(Map<UrlPatternAndMethod, Command> commandsByUrlPatternAndMethod) {
        this.commandsByUrlPatternAndMethod = commandsByUrlPatternAndMethod;
    }

    public static String getName() {
        return "CommandFactory";
    }

    public void printAll() {
        commandsByUrlPatternAndMethod.forEach((urlPatternAndMethod, command) ->
                log.info("{} ==> {}", urlPatternAndMethod, command));
    }

    public Command getCommand(UrlPatternAndMethod urlPatternAndMethod) {
        return commandsByUrlPatternAndMethod.get(urlPatternAndMethod);
    }

    public Command getCommand(String urlPattern, Method method) {
        return commandsByUrlPatternAndMethod.get(new UrlPatternAndMethod(urlPattern, method));
    }

}
