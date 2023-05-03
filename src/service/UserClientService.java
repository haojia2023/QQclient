package service;

import qqcommon.Message;
import qqcommon.MessageType;
import qqcommon.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class UserClientService {
    private User user;
    private Socket socket;

    public boolean checkUser(String name,String psw){
        user = new User(name,psw);
        try {
            socket = new Socket(InetAddress.getByName("127.0.0.1"), 9999);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(user);
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Message o = (Message) ois.readObject();
            if(MessageType.LOGIN_SUCCEED.equals(o.getMassageType())) {
                ClientConnectServerThread cst = new ClientConnectServerThread();
                cst.start();
                ManageConnectServerThread.addCST(user.getUserID(),cst);
                return true;
            }
            else socket.close();
        } catch (Exception e) {
            System.out.println("连接异常");
        }
        return false;
    }
    private void printOnlineUser(){
        try {
            Message obj = new Message();
            obj.setMassageType(MessageType.GET_ONLINE_USER);
            Socket socket1 = ManageConnectServerThread.searchCST(user.getUserID()).getSocket();
            ObjectOutputStream oos = new ObjectOutputStream(socket1.getOutputStream());
            oos.writeObject(obj);

        } catch (Exception e) {

        }


    }

}
