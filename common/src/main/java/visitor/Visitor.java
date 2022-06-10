package visitor;

import cmd.Cmd;
import command.tasksCommands.with_arguments.*;
import command.tasksCommands.without_arguments.*;
import entity.Movie;

public interface Visitor {

    Object visit(AddCommand c, Movie movie, Cmd cmd);
    Object visit(HelpCommand c);
    Object visit(HelpCommand c, Cmd cmd);
    Object visit(ShowCommand c);
    Object visit(HeadCommand c);
    Object visit(HeadCommand c, Cmd cmd);
    Object visit(ClearCommand c, Cmd cmd);
    Object visit(InfoCommand c);
    Object visit(InfoCommand c, Cmd cmd);
    Object visit(AverageOfLength c);
    Object visit(AverageOfLength c, Cmd cmd);
    Object visit(PrintUniqueOscarsCount c);
    Object visit(PrintUniqueOscarsCount c, Cmd cmd);
    Object visit(UpdateCommand c, Movie movie, Cmd cmd);
    Object visit(RemoveCommand c, Cmd cmd);
    Object visit(CountByGenreCommand c);
    Object visit(CountByGenreCommand c, Cmd cmd);
    Object visit(RemoveGreaterCommand c, Movie movie, Cmd cmd);
    Object visit(RemoveLowerCommand c, Movie movie, Cmd cmd);
    Object visit(ExecuteScriptCommand c);
    Object visit(ExecuteScriptCommand c, String str);

    Object visit(ShowCommand showCommand, Cmd cmd);
    //todo у других команд тоже сделать Cmd
}


