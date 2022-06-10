package lab.server;

import cmd.Cmd;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

public class GetDataFromClient implements Runnable{
    private Cmd cmd;
    private ByteBuffer data;
    private SocketChannel channel;
    private SelectionKey key;

    public GetDataFromClient(ByteBuffer data, SocketChannel channel, SelectionKey key){
        this.data = data;
        this.channel = channel;
        this.key = key;
    }

    public Cmd getCmd() {
        return cmd;
    }

    @Override
    public void run() {
        try {
            getData();
        } catch (IOException e) {
            key.cancel();
            System.out.println("пользователь отключился");
        }

    }

    public void getData() throws IOException {
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data.array());
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            this.cmd = (Cmd) objectInputStream.readObject();
            if (cmd.getArguments() == null) {
                if (cmd.getMovie() != null) {
                    System.out.println("client's message - " + cmd.getName() + " " + cmd.getMovie() + " " + cmd.getLogin() + " " + cmd.getPassword());
                } else
                    System.out.println("client's message - " + cmd.getName() + " " + cmd.getLogin() + " " + cmd.getPassword());
                    System.out.println(cmd.getCommands());
            } else {
                System.out.println("client's message - " + cmd);
                System.out.println(cmd.getCommands());
            }
        }catch (ClassNotFoundException e){
            System.out.println("ClassNotFoundExecption");
        }

    }


}
