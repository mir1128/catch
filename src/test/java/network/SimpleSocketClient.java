package network;

import java.io.*;
import java.net.Socket;

public class SimpleSocketClient {
    BufferedReader br;
    PrintWriter out;
    Socket socket;
    int port;

    public SimpleSocketClient(String serverAddress, int port) throws IOException {
        this.port = port;
        socket = new Socket(serverAddress, port);

        br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
    }

    public void sendMsg(String msg){
        out.println(msg);
    }

    public String readMsg() throws IOException {
        int SIZE = 1024;
        char buffer[] = new char[SIZE];

        StringBuilder stringBuilder = new StringBuilder();

        int thunk;
        while ((thunk = br.read(buffer, 0, SIZE)) == 1024){
            stringBuilder.append(buffer);
        }
        if (thunk != -1){
            stringBuilder.append(buffer, 0, thunk);
        }

        return stringBuilder.toString();
    }

    public void close() throws IOException {
        socket.close();
    }
}

