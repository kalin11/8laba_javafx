package command.parsing;

import visitor.Visitor;

public interface CommandWithFile extends Command{
    Object accept(Visitor visitor, String file);
    //todo тут тоже она принимать пользователя должна
}
