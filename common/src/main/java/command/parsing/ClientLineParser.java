package command.parsing;


import command.CommandName;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;

public class ClientLineParser {
    private final BufferedReader in;
    private final HashMap<CommandName, Command> commands;
    private boolean shouldClose = false;
    private boolean regok;
    public ClientLineParser(BufferedReader in, HashMap<CommandName, Command> c) {
        this.in = in;
        this.commands = c;
    }

    public boolean shouldClose() {
        return shouldClose;
    }

    public void setReg(boolean ok){
        this.regok = ok;
    }
    public boolean getReg(){
        return regok;
    }

    ///////////
    public Object readLine() throws IOException {
        String line = in.readLine();
        if (line == null) {
            shouldClose = true;
            return "";
        }
        else {
            ///
            ////////////////
            final String[] cmds = line.trim().replaceAll("\\s+", " ").toLowerCase().split(" ");
            final String[] commandArguments = new String[cmds.length - 1];
            System.arraycopy(cmds, 1, commandArguments, 0, cmds.length - 1);

            Command c = commands.get(CommandName.fromString(cmds[0]));
            if (c == null) {
                System.out.println("Такой команды нет");
            } else {
                if (cmds[0].equals("exit")){
                    return "exit";
                }
                if (c.valid(commandArguments)) {
//                    if (cmds[0].equals("add") || cmds[0].equals("update") || cmds[0].equals("remove_lower") || cmds[0].equals("remove_greater")) {
//                        return c.accept()
//                    }
                    if (c.execute(commandArguments, in)) {
                        if (commandArguments.length!=0) {
                            return cmds[0] + " " + commandArguments[0];
                        }
                        else{
                            System.out.println(cmds[0]);
                            return cmds[0];
                        }
                        //создаем команду тута
                        //сериализация и отправка на сервак идет
                    } else {
                        shouldClose = true;
                    }
                } else return "";
            }
        }
        ///////////////////

        return "";
    }
}
