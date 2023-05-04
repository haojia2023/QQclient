package service;

import qqView.QQView;
import qqcommon.Message;
import qqcommon.MessageType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class ClientConnectServerThread extends Thread {
    private boolean loop = true;

    public void setLoop(boolean loop) {
        this.loop = loop;
    }

    public Socket getSocket() {
        return socket;
    }
    public ClientConnectServerThread(Socket socket){
        this.socket = socket;
    }
    private Socket socket;

    @Override
    public void run() {
        while(loop){
            try {
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Message o = (Message) ois.readObject();

                if(o.getMassageType().equals(MessageType.SEND_ONLINE_USER)) {
                    String[] s = o.getContent().split(" ");
                    System.out.println("用户列表");
                    for(String user:s)
                        System.out.println("User" + user);
                    QQView.val = false;
                }else {

                }
            } catch (Exception e) {

            }

        }
    }
}
