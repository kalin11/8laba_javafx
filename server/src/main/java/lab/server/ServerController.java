package lab.server;

import cmd.Cmd;
import collection.LinkedCollection;
import command.CommandName;
import command.parsing.Command;
import command.tasksCommands.with_arguments.*;
import command.tasksCommands.without_arguments.*;
import DB.DataBase.ConnectToDB;
import visitor.VisitorImpl;

import java.io.IOException;
import java.net.BindException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerController {
    private ExecutorService poolReader = Executors.newCachedThreadPool();
    private Cmd cmd = null;


    public void acceptConnection(LinkedCollection collection, String host, int port) throws UnresolvedAddressException, BindException {
        try {
            VisitorImpl visitor = new VisitorImpl(collection, initCommands());
            Selector selector = Selector.open();
            ServerSocketChannel server = ServerSocketChannel.open();
            server.configureBlocking(false);
            server.socket().bind(new InetSocketAddress(host, port));
            server.register(selector, SelectionKey.OP_ACCEPT);
            while (true) {
                SocketChannel client;
                selector.select();
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SocketChannel channel = null;
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    if (key.isAcceptable()) {
                        client = server.accept();
                        System.out.println("Подкючился пользователь " + client.toString().substring(42));
                        client.configureBlocking(false);
                        client.register(selector, SelectionKey.OP_READ);
                    }
                    if (key.isReadable()) {
                        channel = (SocketChannel) key.channel();
                        ByteBuffer data = ByteBuffer.allocate(16384);
                        try {
                            channel.read(data);
                        } catch (IOException e) {
                            System.out.println("Пользователь отключился");
                            key.cancel();
                            continue;
                        }
                        try {
                            GetDataFromClient getData = new GetDataFromClient(data, channel, key);
                            poolReader.execute(getData);

                            if (channel != null) {
                                //new Thread
                                Thread.sleep(300);
                                ConnectToDB connectToDB = new ConnectToDB();
                                String autorize = connectToDB.autorize(channel, getData.getCmd());
                                if (autorize.isEmpty()) {
                                    GetCommand getCommand = new GetCommand(collection, getData.getCmd(), visitor, channel, key);
                                    Thread thread = new Thread(getCommand);
                                    thread.start();
                                    thread.join();
                                    System.out.println(collection);
                                    SendDataToTheClient sendData = new SendDataToTheClient(channel, getCommand.getResult(), key);
                                    Thread sender = new Thread(sendData);
                                    sender.start();
                                    sender.join();
                                }
                                else {
                                    SendDataToTheClient sendData = new SendDataToTheClient(channel, autorize, key);
                                    Thread Sender = new Thread(sendData);
                                    Sender.start();
                                    Sender.join();
                                }
                            }
                        } catch (IOException e) {
                            key.cancel();
                            System.out.println("Пользователь отключился");
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }catch (NullPointerException e){
                            continue;
                        }

                    }
                }
                System.out.println("сервер ожидает дальнейших действий");
            }
        }catch (IOException e){
            System.out.println("я пока хз че это");
            e.printStackTrace();
        }

    }
    public static HashMap<CommandName, Command> initCommands() {
        HashMap<CommandName, Command> commands = new HashMap<>();
        commands.put(CommandName.HELP, new HelpCommand());
        commands.put(CommandName.EXIT, new ExitCommand());
        commands.put(CommandName.ADD, new AddCommand());
        commands.put(CommandName.SHOW, new ShowCommand());
        commands.put(CommandName.HEAD, new HeadCommand());
        commands.put(CommandName.INFO, new InfoCommand());
        commands.put(CommandName.AVERAGE_OF_LENGTH, new AverageOfLength());
        commands.put(CommandName.PRINT_UNIQUE_OSCARS_COUNT, new PrintUniqueOscarsCount());
        commands.put(CommandName.UPDATE_ID, new UpdateCommand());
        commands.put(CommandName.REMOVE_BY_ID, new RemoveCommand());
        commands.put(CommandName.COUNT_BY_GENRE, new CountByGenreCommand());
        commands.put(CommandName.REMOVE_GREATER, new RemoveGreaterCommand());
        commands.put(CommandName.CLEAR, new ClearCommand());
        commands.put(CommandName.REMOVE_LOWER, new RemoveLowerCommand());
        commands.put(CommandName.EXECUTE_SCRIPT, new ExecuteScriptCommand());
        return commands;
    }

}
