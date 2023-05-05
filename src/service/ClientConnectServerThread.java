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

                String mes = o.getMessageType();
                if(mes.equals(MessageType.SEND_ONLINE_USER)) {
                    String[] s = o.getContent().split(" ");
                    System.out.println("用户列表");
                    for(String user:s)
                        System.out.println("User" + user);
                    QQView.val = false;
                }else if (mes.equals(MessageType.COMMON_MES) && !(o.getSender().equals(o.getGetter()))){
                    System.out.println(o.getSender() + "对"+ o.getGetter() +"说：" + o.getContent());
                }
            } catch (Exception e) {
                try {
                    socket.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                System.out.println("连接断开");
                return;
            }

        }
    }
}
