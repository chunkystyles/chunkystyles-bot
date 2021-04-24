package org.allenarcher.discord.bot.command;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class CommandManager {
    private static CommandManager instance;

    static {
        CommandManager.instance = new CommandManager();
    }

    private final Map<String, Command> commandsMap;
    private final Command unrecognizedCommand;

    private CommandManager() {
        this.commandsMap = CommandManager.initialize(
                new HelpCommand(),
                new RankCommand()
        );
        this.unrecognizedCommand = new UnrecognizedCommand();
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

    public static CommandManager getInstance(){
        return CommandManager.instance;
    }
}
