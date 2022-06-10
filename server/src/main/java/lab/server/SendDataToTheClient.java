package lab.server;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

public class SendDataToTheClient implements Runnable {
    private ObjectOutputStream outputStream;
    private Object o;
    private SocketChannel channel;
    private SelectionKey key;

    public SendDataToTheClient(SocketChannel channel, Object o, SelectionKey key){
        this.channel = channel;
        this.o = o;
        this.key = key;
    }

    @Override
    public void run() {
        try{
            sendData();
        }catch (IOException e){
            key.cancel();
            System.out.println("пользователь отключился");
        }
    }

    public void sendData() throws IOException {
        ByteArrayOutputStream byteArrayInputStream = new ByteArrayOutputStream();
        this.outputStream = new ObjectOutputStream(byteArrayInputStream);
        outputStream.writeObject(o);
        outputStream.flush();
        channel.write(ByteBuffer.wrap(byteArrayInputStream.toByteArray()));
    }

    public void closeOutput() throws IOException{
        this.outputStream.close();
    }
}
