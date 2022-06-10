package command.tasksCommands.with_arguments;

import command.parsing.CommandWithFile;
import visitor.Visitor;

import java.io.BufferedReader;
import java.io.File;

public class ExecuteScriptCommand implements CommandWithFile {

    private String[] args = null;

    public String[] getArgs(){
        return args;
    }

    public String toString(){
        return "execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.";
    }

    
    public Object accept(Visitor v, String f) {
        return v.visit(this, f);
    }

    
    public boolean execute(String[] arguments, BufferedReader in) {
        args = arguments;
            String path = arguments[0];
            File file = new File(path);
            if (!file.exists()) {
                System.out.println("файл не существует");
            } else if (!file.canRead() || !file.canWrite()) {
                System.out.println("файл не доступен для чтения/записи");
            } else if (file.isDirectory()) {
                System.out.println("это не файл, а директория");
            }
        return true;
    }
    public boolean valid(String[] args) {
        this.args = args;
        if (args.length==0){
            System.out.println("не введен путь к файлу");
            return false;
        }
        else if (args.length > 1){
            System.out.println("вы ввели слишком много аругментов");
            return false;
        }
        else return true;
    }
}
