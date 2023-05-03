package service;

import qqcommon.Message;
import qqcommon.MessageType;

import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientConnectServerThread extends Thread {
    public Socket getSocket() {
        return socket;
    }

    private Socket socket;

    @Override
    public void run() {
        while(true){
            try {
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Message o = (Message) ois.readObject();

                if(o.getMassageType().equals(MessageType.SEND_ONLINE_USER)) {
                    String[] s = o.getContent().split(" ");
                    for(String user:s)
                        System.out.println(user);
                }else {

                }
            } catch (Exception e) {

            }

        }
    }
}
