//package lab.server;
//
//import cmd.Cmd;
//import collection.CollectionReader;
//import collection.LinkedCollection;
//import command.parsing.Command;
//import command.parsing.CommandLineParser;
//import command.tasksCommands.with_arguments.*;
//import command.tasksCommands.without_arguments.*;
//import org.postgresql.util.OSUtil;
//import visitor.*;
//import entity.*;
//import command.*;
//
//
//
//import java.io.*;
//import java.net.BindException;
//import java.net.InetSocketAddress;
//import java.nio.ByteBuffer;
//import java.nio.channels.*;
//import java.sql.*;
//import java.util.*;
//import java.util.concurrent.Executor;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
//public class Server {
//    private static final String url = "jdbc:postgresql://localhost:63333/studs";
//    private static final String username = "s336805";
//    private static final String password = "ipb588";
//    private static final String driver = "org.postgresql.Driver";
//    Service service;
//    ExecutorService executorService = Executors.newCachedThreadPool();
//    public static void main(String[] args) throws IOException, ClassNotFoundException{
//        PreparedStatement ps;
//        Connection connection;
//        ResultSet resultSet;
//        System.out.println("Сервер запущен!");
//        LinkedCollection collection = new LinkedCollection();
//        HashMap<CommandName, Command> commands = initCommands();
//        boolean correctPath = true;
//        Selector selector = null;
//        VisitorImpl visitor = null;
//        ServerSocketChannel server = null;
//        boolean okay = true;
//        if (args.length == 0){
//            System.out.println("не введен путь к файлу");
//        }
//        else {
//            while (correctPath) {
//                String filePath = args[0];
//                File file = new File(filePath);
//                if (!file.exists()) {
//                    System.out.println("указанный файл не существует");
//                    return;
//                } else if (!file.canWrite() || !file.canRead()) {
//                    System.out.println("файл не доступен для чтения/записи");
//                    return;
//                } else if (file.isDirectory()) {
//                    System.out.println("введен не файл, а директория");
//                    return;
//                } else {
//                    if (file.length() == 0) {
//                        collection = new LinkedCollection();
//                        correctPath = false;
//                    } else {
//                        while (okay) {
//                            CollectionReader collectionReader = new CollectionReader(collection);
//                            collection = collectionReader.read(filePath);
//                            System.out.println("Загружена коллекция \n" + collection);
//                            visitor = new VisitorImpl(collection, initCommands());
//                            selector = Selector.open();
//                            server = ServerSocketChannel.open();
//                            server.configureBlocking(false);
//                            try {
//                                server.socket().bind(new InetSocketAddress("localhost", 9000));
//                                okay = false;
//                            } catch (UnresolvedAddressException e) {
//                                System.out.print("");
//                                okay = true;
//                                continue;
//                            } catch (BindException e) {
//                                System.out.println("адрес уже кем-то заюзан");
//                                okay = true;
//                                }
//                            }
//                            server.register(selector, SelectionKey.OP_ACCEPT);
//                            while (true) {
//                                SocketChannel client;
//                                selector.select();
//                                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
//                                while (iterator.hasNext()) {
//                                    SocketChannel channel = null;
//                                    SelectionKey key = iterator.next();
//                                    iterator.remove();
//                                    if (key.isAcceptable()) {
//                                        client = server.accept();
//                                        System.out.println("подключился пользователь " + client.toString().substring(42));
//                                        client.configureBlocking(false);
//                                        client.register(selector, SelectionKey.OP_READ);
//                                    }
//                                    if (key.isReadable()) {
//                                        channel = (SocketChannel) key.channel();
//                                        ByteBuffer data = ByteBuffer.allocate(16384);
//                                        try {
//                                            channel.read(data);
//                                        } catch (IOException e) {
//                                            System.out.println("пользователь отключился");
//                                            key.cancel();
//                                            continue;
//                                        }
//                                        try {
//                                            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data.array());
//                                            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
//
//                                            //CachedThreadPool
//
//                                            //чтение запросов с помощью пула потоков
//
//                                            Cmd cmd = (Cmd) objectInputStream.readObject();
//                                            //пришел норм объект
//                                            if (cmd.getArguments() == null) {
//                                                if (cmd.getMovie() != null) {
//                                                    System.out.println("client's message - " + cmd.getName() + " " + cmd.getMovie()+ " " + cmd.getLogin() + " " + cmd.getPassword());
//                                                } else System.out.println("client's message - " + cmd.getName() + " " + cmd.getLogin() + " " + cmd.getPassword());
//                                            } else {
//                                                System.out.println("client's message - " + cmd);
//                                            }
//
//                                            if (channel != null) {
//                                                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//                                                ObjectOutputStream outputStream = new ObjectOutputStream(byteArrayOutputStream);
//
//                                                //отправить мы должны ответ команды, это может быть как список, объект Movie, еще чот
//                                                //здесь опять идет проверка на валидность
//                                                //далее мы запускаем какое-то исполнение команды и выхватываем результат
//
//                                                if (cmd.getName().equals("register")){
//                                                    try {
//                                                        int count = -1;
//                                                        System.out.println(cmd.getName());
//                                                        System.out.println(cmd.getLogin());
//                                                        System.out.println(cmd.getPassword());
//                                                        Class.forName(driver);
//                                                        connection = DriverManager.getConnection(url, username, password);
//                                                        System.out.println("connected");
//                                                        String sql = "select count(*) from \"Users\" where user_log = ?";
//                                                        ps = connection.prepareStatement(sql);
//                                                        ps.setString(1, cmd.getLogin());
//                                                        ps.execute();
//                                                        resultSet = ps.getResultSet();
//                                                        if (resultSet.next()) {
//                                                            count = resultSet.getInt(1);
//                                                            System.out.println(count);
//                                                            if (count == 0) {
//                                                                String add = "insert into \"Users\" (user_log, password) VALUES (?, ?)";
//                                                                ps = connection.prepareStatement(add);
//                                                                ps.setString(1, cmd.getLogin());
//                                                                ps.setString(2, cmd.getPassword());
//                                                                ps.execute();
//                                                                outputStream.writeObject("Вы успешно зарегестрировались!");
//                                                                outputStream.flush();
//                                                                channel.write(ByteBuffer.wrap(byteArrayOutputStream.toByteArray()));
//                                                            } else {
//                                                                outputStream.writeObject("такой пользователь уже существует");
//                                                                outputStream.flush();
//                                                                channel.write(ByteBuffer.wrap(byteArrayOutputStream.toByteArray()));
//                                                            }
//                                                        }
//                                                    }catch (ClassNotFoundException | SQLException e){
//                                                        System.out.print("");
//                                                    }
//                                                }
//                                                else if (cmd.getName().equals("login")){
//                                                    try {
//                                                        int count = -1;
//                                                        System.out.println(cmd.getName());
//                                                        Class.forName(driver);
//                                                        connection = DriverManager.getConnection(url, username, password);
//                                                        System.out.println("connected");
//                                                        String sql = "select * from \"Users\" where user_log = ? and password = ?";
//                                                        ps = connection.prepareStatement(sql);
//                                                        ps.setString(1, cmd.getLogin());
//                                                        ps.setString(2, cmd.getPassword());
//                                                        ps.executeQuery();
//                                                        resultSet = ps.getResultSet();
//                                                        if (resultSet.next()) {
//                                                                String log = resultSet.getString(1);
//                                                                System.out.println(log);
//                                                                String pass = resultSet.getString(2);
//                                                                System.out.println(pass);
//                                                                System.out.println(count);
//                                                                System.out.println(log + " " + pass);
//                                                                ArrayList<String> list = new ArrayList<>();
//                                                                list.add("Вы успешно вошли!");
//                                                                list.add(log);
//                                                                list.add(pass);
//                                                                outputStream.writeObject(list);
//                                                                outputStream.flush();
//                                                                channel.write(ByteBuffer.wrap(byteArrayOutputStream.toByteArray()));
//                                                        }
//                                                            else {
//                                                                outputStream.writeObject("такой учетной записи не было найдено");
//                                                                outputStream.flush();
//                                                                channel.write(ByteBuffer.wrap(byteArrayOutputStream.toByteArray()));
//                                                        }
//                                                    }catch (ClassNotFoundException | SQLException e){
//                                                        System.out.print("");
//                                                    }
//                                                }
////                                                else{
////                                                    Service service = new Service(collection, cmd.getName());
////                                                    Object o = service.doCommand();
////                                                    outputStream.writeObject(o);
////                                                    outputStream.flush();
////                                                    channel.write(ByteBuffer.wrap(byteArrayOutputStream.toByteArray()));
////                                                }
//
//
//                                                /*вот здесь уже мы вызываем ебучий Service;
//                                                то есть у нас в аргументах Service(cmd, collection);
//                                                на выходе должен быть Object(результат работы команды);
//                                                */
//
//
//                                                else if (cmd.getArguments() == null) {
//                                                    if (cmd.getName().equals("exit")) {
//                                                        collection.save("laba.txt");
//                                                        System.out.println("коллекция была сохранена в файл 'laba.txt'");
//                                                    } else if (cmd.getName().equals("add") || cmd.getName().equals("remove_lower") || cmd.getName().equals("remove_greater")) {
//                                                        //тоже норм
//                                                        Movie movie = cmd.getMovie();
//                                                        String[] temp = cmd.toString().split(" ");
//                                                        if (temp[2] != null) {
//                                                            CommandLineParser parser = new CommandLineParser(temp[0] + " " + movie.allInfo(), initCommands());
//                                                            Object o = parser.exe(visitor);
//                                                            outputStream.writeObject(o);
//                                                            outputStream.flush();
//                                                            channel.write(ByteBuffer.wrap(byteArrayOutputStream.toByteArray()));
//
//                                                        }
//                                                    } else {
//                                                        CommandLineParser parser = new CommandLineParser(cmd.getName(), initCommands());
//                                                        Object o = parser.exe(visitor);
//                                                        outputStream.writeObject(o);
//                                                        outputStream.flush();
//                                                        channel.write(ByteBuffer.wrap(byteArrayOutputStream.toByteArray()));
//                                                        System.out.println("sending answer to the client");
//                                                    }
//                                                } else {
//                                                    if (cmd.getMovie() != null) {
//                                                        CommandLineParser parser = new CommandLineParser(cmd.getName() + " " + cmd.getArguments() + " " + cmd.getMovie().allInfo(), initCommands());
//                                                        Object o = parser.exe(visitor);
//                                                        outputStream.writeObject(o);
//                                                        outputStream.flush();
//                                                        channel.write(ByteBuffer.wrap(byteArrayOutputStream.toByteArray()));
//                                                        System.out.println("sending answer to the client");
//                                                    } else {
//                                                        CommandLineParser parser = new CommandLineParser(cmd.getName() + " " + cmd.getArguments() + " " + cmd.getFile(), initCommands());
//                                                        Object o = parser.exe(visitor);
//                                                        outputStream.writeObject(o);
//                                                        outputStream.flush();
//                                                        channel.write(ByteBuffer.wrap(byteArrayOutputStream.toByteArray()));
//                                                        System.out.println("sending answer to the client");
//                                                    }
//                                                }
//                                                outputStream.close();
//                                            }
//                                        } catch (IOException e) {
//                                            key.cancel();
//                                            System.out.println("пользователь отключился");
//                                        }
//                                    }
//
//                                    //получили команду, в зависимости от нее
//                                    //отправляем дальше на исполнение
//                                    //получаем какой-то результат
//                                    //получаем его
//                                    //а дальше отправялем клиенту
//                                }
//                                System.out.println("сервер ожидает дальнейших действий.");
//                            }
//                        }
//                    }
//                }
//            }
//        }
//
//    public static HashMap<CommandName, Command> initCommands() {
//        HashMap<CommandName, Command> commands = new HashMap<>();
//        commands.put(CommandName.HELP, new HelpCommand());
//        commands.put(CommandName.EXIT, new ExitCommand());
//        commands.put(CommandName.ADD, new AddCommand());
//        commands.put(CommandName.SHOW, new ShowCommand());
//        commands.put(CommandName.HEAD, new HeadCommand());
//        commands.put(CommandName.INFO, new InfoCommand());
//        commands.put(CommandName.AVERAGE_OF_LENGTH, new AverageOfLength());
//        commands.put(CommandName.PRINT_UNIQUE_OSCARS_COUNT, new PrintUniqueOscarsCount());
//        commands.put(CommandName.UPDATE_ID, new UpdateCommand());
//        commands.put(CommandName.REMOVE_BY_ID, new RemoveCommand());
//        commands.put(CommandName.COUNT_BY_GENRE, new CountByGenreCommand());
//        commands.put(CommandName.REMOVE_GREATER, new RemoveGreaterCommand());
//        commands.put(CommandName.CLEAR, new ClearCommand());
//        commands.put(CommandName.REMOVE_LOWER, new RemoveLowerCommand());
//        commands.put(CommandName.EXECUTE_SCRIPT, new ExecuteScriptCommand());
//        return commands;
//    }
//
//    // getName у команд
//}
