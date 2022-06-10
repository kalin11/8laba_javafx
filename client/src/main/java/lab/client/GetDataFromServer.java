package lab.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class GetDataFromServer {
    public Object getData(Socket socket) throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
        Object o = objectInputStream.readObject();
        return o;

    }
}
