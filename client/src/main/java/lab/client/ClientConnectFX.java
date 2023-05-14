package lab.client;

import command.CommandName;
import command.parsing.ClientLineParser;
import command.parsing.Command;
import command.tasksCommands.with_arguments.*;
import command.tasksCommands.without_arguments.*;
import javafx.scene.control.Alert;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class ClientConnectFX {
    private static final String EXECUTE_SCRIPT_STRING = "execute_script";
    private ClientLineParser clientLineParser;
    private String login;
    private String password;
    private Selector sel = null;


    public String getLogin() {
        return login;
    }
    public String getPassword(){
        return password;
    }

    public void connectToServer(String host, int port) throws UnresolvedAddressException {
        boolean okay = false;
        Authorization authorization = new Authorization();
        while (!okay){
            try{
                Selector selector = Selector.open();
                SocketChannel clientSocket = SocketChannel.open();
                clientSocket.configureBlocking(false);
                clientSocket.connect(new InetSocketAddress(host, port));
//                System.out.println("подключение успешно");
                okay = true;
                clientSocket.register(selector, SelectionKey.OP_CONNECT);
                //тут переделать на write, чтобы заработало на хелиосе
                while (true){
                    selector.select();
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()){
                        SelectionKey key = iterator.next();
                        iterator.remove();
                        SocketChannel client = (SocketChannel) key.channel();
//                        System.out.println("Добро пожаловать, новый пользователь! Вам необходимо войти в существующий аккаунт или же зарегестрироваться\\nДля регистрации введите 'register', для входа в сущ. аккуаунт - 'login'");
                        if (key.isConnectable()){
                            if (client.isConnectionPending()){
                                try{
                                    client.finishConnect();
                                    System.out.println("Добро пожаловать, новый пользователь! Вам необходимо войти в существующий аккаунт или же зарегестрироваться\nДля регистрации введите 'register', для входа в сущ. аккуаунт - 'login'");
//                                    System.out.println();
                                    client.register(selector, SelectionKey.OP_WRITE);
                                    continue;
                                }catch (IOException e){
                                    System.out.println("что-то пошло не так");

                                }
                            }else {
                                System.out.println("как?");
                            }
                        }
                        if (key.isWritable()){
                            if (!authorization.isLog_or_reg()) {
                                try {
                                    authorization.autorize(client);
                                    login = authorization.getLogin();
                                    password = authorization.getPassword();
                                    clientSocket.register(selector, SelectionKey.OP_READ);
                                } catch (IOException e) {
                                    System.out.println("сервер не отвечает");
                                    continue;
                                }
                            }
                            else {
                                System.out.print(">>> ");
                                clientLineParser = new ClientLineParser(new BufferedReader(new InputStreamReader(System.in)), initCommands());
                                String str = (String) clientLineParser.readLine();
                                if (!str.isEmpty()) {
                                    String[] cmdWithArgs = str.replaceAll("\\s+", " ").split(" ");
                                    try {
                                        GetCommand getCommand = new GetCommand();
                                        getCommand.getCmdAndSendToTheServer(client, cmdWithArgs, login, password);
                                        if (getCommand.isEmpty() == true) {
                                            System.out.println("файл пустой");
                                            continue;
                                        }
                                        clientSocket.register(selector, SelectionKey.OP_READ);
                                    } catch (IOException e) {
                                        System.out.println("сервер не отвечает");
                                        continue;
                                    }
                                }
                                else {
                                    clientSocket.register(selector, SelectionKey.OP_WRITE);
                                }
                            }
                        }
                        if (key.isReadable()){
                            client = (SocketChannel) key.channel();
                            ByteBuffer data = ByteBuffer.allocate(16384);
                            try{
                                client.read(data);
                                ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data.array());
                                ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
                                Object o = objectInputStream.readObject();
                                if (o.equals("Вы успешно зарегестрировались!")){
                                    System.out.println(o);
                                    assert authorization != null;
                                    authorization.setLog_or_reg(true);
                                }
                                else if(o.equals("такой пользователь уже существует")){
                                    Alert alert = new Alert(Alert.AlertType.ERROR);
                                    alert.setContentText("такой пользователь уже существует");
                                    System.out.println(o);
                                    assert authorization != null;
                                    authorization.setLog_or_reg(false);
                                    System.out.println("введите 'login' или 'register'");
                                }
                                else if (o instanceof ArrayList){
                                    login = (String) ((ArrayList<?>) o).get(1);
                                    password = (String) ((ArrayList<?>) o).get(2);

                                    System.out.println(((ArrayList<?>) o).get(0));
                                    authorization.setLog_or_reg(true);
                                }
                                else if (o.equals("такой учетной записи не было найдено")){
                                    Alert alert = new Alert(Alert.AlertType.ERROR);
                                    alert.setContentText("такой учетной записи не было найдено");
                                    alert.show();
                                    System.out.println(o);
                                    authorization.setLog_or_reg(false);
                                    System.out.println("введите команду 'login' или 'register'");
                                }
                                else {
                                    System.out.println("server's answer - \n" + o);
                                    authorization.setLog_or_reg(true);
                                }
                            }
                            catch (IOException e){
                                System.out.println("сервер отлетел");
                                key.cancel();
                                clientSocket.register(selector, SelectionKey.OP_WRITE);
                            }catch (ClassNotFoundException e){
                                System.out.println("ClassNotFound");

                            }catch (NullPointerException e){
                                System.out.println("сервер не прислал ответ");
                                continue;
                            }

                            finally {
                                clientSocket.register(selector, SelectionKey.OP_WRITE);
                            }
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("сервер нет/хост занят");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("сервер нет/хост занят");
                alert.show();
            }catch (CancelledKeyException e){
                System.out.println("-сервер");
            }
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
