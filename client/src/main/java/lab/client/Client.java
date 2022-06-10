//package lab.client;
//
//import cmd.Cmd;
//import com.sun.security.ntlm.Server;
//import command.CommandName;
//import command.parsing.ClientLineParser;
//import command.parsing.Command;
//import command.parsing.ObjectParser;
//import command.tasksCommands.with_arguments.*;
//import command.tasksCommands.without_arguments.*;
//
//import java.io.*;
//import java.net.InetSocketAddress;
//import java.nio.ByteBuffer;
//import java.nio.channels.SelectionKey;
//import java.nio.channels.Selector;
//import java.nio.channels.SocketChannel;
//import java.nio.channels.UnresolvedAddressException;
//import java.nio.file.Files;
//import java.nio.file.NoSuchFileException;
//import java.nio.file.Paths;
//import java.security.spec.RSAOtherPrimeInfo;
//import java.util.*;
//
//
//public class Client {
//    private static final String EXECUTE_SCRIPT_STRING = "execute_script";
//    static String login;
//    static String password;
//    String host;
//    int port;
//    //вот на клиенте должен быть только логин, пароль, хост и порт сервера
//
//    public static void main(String[] args) {
//        try {
//            ClientLineParser clientLineParser;
////            boolean reg;
//            Scanner scanner = new Scanner(System.in);
//            boolean log_or_reg = false;
////            String login_reg = "";
////            String login = "";
////            String password = "";
//            boolean okay = false;
////            boolean goodLog = false;
////            boolean goodReg = false;
////            boolean DBanswer = true;
//            while (!okay)
//                try {
//                    Selector selector = Selector.open();
//                    SocketChannel clientSocket = SocketChannel.open();
//                    clientSocket.configureBlocking(false);
//                    clientSocket.connect(new InetSocketAddress("localhost", 9000));
//                    System.out.println("подключение успешно");
//                    okay = true;
//                    clientSocket.register(selector, SelectionKey.OP_CONNECT);
//                    while (true) {
//                        selector.select();
//                        Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
//                        while (iterator.hasNext()) {
//                            SelectionKey key = iterator.next();
//                            iterator.remove();
//                            SocketChannel client = (SocketChannel) key.channel();
//                            if (key.isConnectable()) {
//                                if (client.isConnectionPending()) {
//                                    try {
//                                        client.finishConnect();
//                                        System.out.println("Добро пожаловать, новый пользователь!\nВам необходимо войти в существующий аккаунт или же зарегестрироваться\n" +
//                                                "Для регистрации введите 'register', для входа в сущ. аккуаунт - 'login'");
//
//                                        client.register(selector, SelectionKey.OP_WRITE);
//                                        continue;
//                                    } catch (IOException e) {
//                                        System.out.println("что-то пошло не так");
//                                    }
//                                }
//                                else {
//                                    System.out.println("хуйня");
//                                }
//                            }
//                            if (key.isWritable()) {
//                                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//                                ObjectOutputStream outputStream = new ObjectOutputStream(byteArrayOutputStream);
////                                    while (!log_or_reg) {
////                                        String user = scanner.nextLine();
////                                        if (user.equals("login".trim().toLowerCase(Locale.ROOT)) || user.equals("register".trim().toLowerCase(Locale.ROOT))) {
////                                            System.out.println("введите логин");
////                                            login = scanner.nextLine();
////                                            System.out.println("введите пароль");
////                                            password = scanner.nextLine();
////                                            Cmd userData = new Cmd(user, login, password);
////                                            outputStream.writeObject(userData);
////                                            client.write(ByteBuffer.wrap(byteArrayOutputStream.toByteArray()));
////                                            log_or_reg = true;
////
////                                        } else {
////                                            System.out.println("Вам необходимо войти в существующий аккаунт или же зарегестрироваться");
////                                        }
////                                        clientSocket.register(selector, SelectionKey.OP_READ);
////                                }
//                                //вот ввели какую-то команду здесь
//                                //дальше мы должны проверить ее валидность, если она не валидная, сказать что дебик клиент
//                                //далее если она норм делаем объект комманды и отправляем уже на сервак
//
//                                if (!log_or_reg) {
//                                    while (!log_or_reg) {
//                                        String user = scanner.nextLine();
//                                        if (user.equals("register")) {
//                                            System.out.println("введите логин");
//                                            login = scanner.nextLine();
//                                            System.out.println("введите пароль");
//                                            password = scanner.nextLine();
//                                            Cmd userData = new Cmd(user, login, password);
//                                            outputStream.writeObject(userData);
//                                            client.write(ByteBuffer.wrap(byteArrayOutputStream.toByteArray()));
//                                            log_or_reg = true;
//                                            clientSocket.register(selector, SelectionKey.OP_READ);
//                                        } if (user.equals("login")){
//                                            System.out.println("введите логин");
//                                            login = scanner.nextLine();
//                                            System.out.println("введите пароль");
//                                            password = scanner.nextLine();
//                                            Cmd userData = new Cmd(user, login, password);
//                                            outputStream.writeObject(userData);
//                                            client.write(ByteBuffer.wrap(byteArrayOutputStream.toByteArray()));
//                                            log_or_reg = true;
//                                            clientSocket.register(selector, SelectionKey.OP_READ);
//                                        }
//                                        else System.out.println("сначала надо авторизоваться");
//                                    }
//
//                                }
//                                else {
//
//
//                                    System.out.print(">>> ");
//                                    clientLineParser = new ClientLineParser(new BufferedReader(new InputStreamReader(System.in)), initCommands());
//                                    String str = (String) clientLineParser.readLine();
//                                    String[] cmdWithArgs = str.replaceAll("\\s+", " ").split(" ");
//
//
//                                    if (cmdWithArgs[0].equals("exit")) {
//                                        Cmd cmd = new Cmd(cmdWithArgs[0]);
//                                        try {
//                                            outputStream.writeObject(cmd);
//                                            client.write(ByteBuffer.wrap(byteArrayOutputStream.toByteArray()));
//                                        } catch (IOException e) {
//                                            System.out.println("сервер не отвечает");
//                                            continue;
//                                        }
//                                        System.exit(0);
//                                    } else if (cmdWithArgs[0].equals("")) {
//
//                                    } else if (cmdWithArgs.length == 1) {
//                                        if (cmdWithArgs[0].equals("add") || cmdWithArgs[0].equals("remove_lower") || cmdWithArgs[0].equals("remove_greater")) {
//                                            Cmd cmd = new Cmd(cmdWithArgs[0], new ObjectParser().parseObject(new BufferedReader(new InputStreamReader(System.in))), login, password);
//                                            try {
//                                                outputStream.writeObject(cmd);
//                                                outputStream.flush();
//                                                client.write(ByteBuffer.wrap(byteArrayOutputStream.toByteArray()));
//                                            } catch (IOException e) {
//                                                System.out.println("сервер не отвечает");
//                                                continue;
//                                            }
//                                        } else {
//                                            Cmd command = new Cmd(cmdWithArgs[0], login, password);
//                                            try {
//                                                outputStream.writeObject(command);
//                                                outputStream.flush();
//                                                client.write(ByteBuffer.wrap(byteArrayOutputStream.toByteArray()));
//                                            } catch (IOException e) {
//                                                System.out.println("сервер не отвечает");
//                                                continue;
//                                            }
//                                        }
//                                        clientSocket.register(selector, SelectionKey.OP_READ);
//                                    } else if (cmdWithArgs.length == 2) {
//                                        if (cmdWithArgs[0].equals("update")) {
//                                            Cmd cmd = new Cmd(cmdWithArgs[0], cmdWithArgs[1], new ObjectParser().parseObject(new BufferedReader(new InputStreamReader(System.in))), login, password);
//                                            cmd.getMovie().setId(Long.parseLong(cmdWithArgs[1]));
//                                            try {
//                                                outputStream.writeObject(cmd);
//                                                outputStream.flush();
//                                                client.write(ByteBuffer.wrap(byteArrayOutputStream.toByteArray()));
//                                            } catch (IOException e) {
//                                                System.out.println("сервер не отвечает");
//                                                continue;
//                                            }
//                                            clientSocket.register(selector, SelectionKey.OP_READ);
//                                        } else if (cmdWithArgs[0].equals("execute_script")) {
//                                            try {
//                                                String commands = "";
//                                                String rec = "";
//                                                HashSet<String> pathes = new HashSet<>();
//                                                pathes.add(cmdWithArgs[1]);
//                                                List<String> f = Files.readAllLines(Paths.get(cmdWithArgs[1]));
//                                                for (int i = 0; i < f.size(); i++) {
//                                                    String line = f.get(i);
//                                                    if (line.toLowerCase(Locale.ROOT).startsWith(EXECUTE_SCRIPT_STRING)) {
//                                                        String filePath = line.substring(EXECUTE_SCRIPT_STRING.length()).trim();
//                                                        if (pathes.contains(filePath)) {
//                                                            f.remove(i);
//                                                            rec = "цикл";
//                                                            System.out.println("вы пытаетесь зациклить скрипт");
//                                                            continue;
//                                                        } else {
//                                                            pathes.add(filePath);
//                                                            List<String> ftmp = Files.readAllLines(Paths.get(filePath));
//                                                            f.addAll(i + 1, ftmp);
//                                                            //noinspection SuspiciousListRemoveInLoop
//                                                            f.remove(i);
//                                                        }
//                                                    }
//                                                }
//                                                try {
//                                                    f.removeIf(s -> s.equals(""));
//                                                    for (String s : f) {
//                                                        commands += s + "!";
//                                                    }
//                                                } catch (StringIndexOutOfBoundsException e) {
//                                                    System.out.print("");
//                                                }
//                                                if (rec.equals("цикл")) {
//                                                    continue;
//                                                } else {
//                                                    System.out.println(commands);
//                                                    Cmd cmd = new Cmd(cmdWithArgs[0], cmdWithArgs[1], commands, login, password);
//                                                    outputStream.writeObject(cmd);
//                                                    outputStream.flush();
//                                                }
//                                            } catch (NoSuchFileException e) {
//                                                System.out.print("");
//                                                continue;
//                                            }
//                                            try {
//                                                client.write(ByteBuffer.wrap(byteArrayOutputStream.toByteArray()));
//                                            } catch (IOException e) {
//                                                System.out.println("сервер не отвечает");
//                                                continue;
//                                            }
//                                            clientSocket.register(selector, SelectionKey.OP_READ);
//                                        } else {
//                                            Cmd command = new Cmd(cmdWithArgs[0], cmdWithArgs[1], login, password);
//                                            try {
//                                                outputStream.writeObject(command);
//                                                outputStream.flush();
//                                                client.write(ByteBuffer.wrap(byteArrayOutputStream.toByteArray()));
//                                            } catch (IOException e) {
//                                                System.out.println("сервер не отвечает");
//                                                continue;
//                                            }
//                                            clientSocket.register(selector, SelectionKey.OP_READ);
//                                        }
//                                    }
//                                }
//                            }
//                            //то есть у меня сейчас на руках сама команда и введенные аргументы
//                            //пора бы сделать объект команды,в параметры передать ее название и аргс
//                            //а потом можно и передать объект на сервер
//                            if (key.isReadable()) {
//                                client = (SocketChannel) key.channel();
//                                ByteBuffer data = ByteBuffer.allocate(16384);
//                                try {
//                                    client.read(data);
//                                    ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data.array());
//                                    ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
//                                    Object o = objectInputStream.readObject();
//
//                                    if (o.equals("Вы успешно зарегестрировались!")){
//                                        System.out.println(o);
//                                        log_or_reg = true;
//                                        clientSocket.register(selector, SelectionKey.OP_WRITE);
//                                    }
//                                    else if (o.equals("такой пользователь уже существует")){
//                                        System.out.println(o);
//                                        log_or_reg = false;
//                                        clientSocket.register(selector, SelectionKey.OP_WRITE);
//
//                                    }
//                                    else if (o instanceof ArrayList){
//                                        login = (String) ((ArrayList<?>) o).get(1);
//                                        password = (String) ((ArrayList<?>) o).get(2);
//
//                                        System.out.println(o);
//                                        log_or_reg = true;
//                                        clientSocket.register(selector, SelectionKey.OP_WRITE);
//                                    }
//                                    else if (o.equals("такой учетной записи не было найдено")){
//                                        System.out.println(o);
//                                        log_or_reg = false;
//                                        clientSocket.register(selector, SelectionKey.OP_WRITE);
//                                    }
//                                    else {
//                                        System.out.println("server's answer - \n" + o);
//                                        log_or_reg = true;
//                                        clientSocket.register(selector, SelectionKey.OP_WRITE);
//                                    }
//
//
//                                } catch (IOException e) {
//                                    System.out.println("сервак отлетел");
//                                    key.cancel();
//                                    clientSocket.register(selector, SelectionKey.OP_WRITE);
//                                }
//                            }
//
//                            }
//                    }
//                } catch (IOException e) {
//                    System.out.println("сервера нет/хост занят");
//                } catch (ClassNotFoundException e) {
//                    System.out.println("ClassNot");
//                }
//        } catch (UnresolvedAddressException e) {
//            System.out.println("вы ввели что-то не то");
//        }
//    }
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
//}
