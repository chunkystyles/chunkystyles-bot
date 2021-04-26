package org.allenarcher.discord.bot.command;

import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

@Component
public class CommandManager {
    private final Map<String, Command> commandsMap;
    private final Command unrecognizedCommand;

    public CommandManager(UnrecognizedCommand unrecognizedCommand, HelpCommand helpCommand, RankCommand rankCommand) {
        this.commandsMap = initialize(helpCommand, rankCommand);
        this.unrecognizedCommand = unrecognizedCommand;
    }

    private static Map<String, Command> initialize(Command... commands){
        final Map<String, Command> map = new LinkedHashMap<>();
        for (final Command command : commands){
            for (final String alias : command.getAliases()){
                if (map.putIfAbsent(alias, command) != null){
                    //Command collision need to log
                }
            }
        }
        return Collections.unmodifiableMap(map);
    }

    public Command getCommand(String alias){
        return this.commandsMap.getOrDefault(alias, this.unrecognizedCommand);
    }
}
