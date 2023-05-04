package service;

import qqView.QQView;
import qqcommon.Message;
import qqcommon.MessageType;
import qqcommon.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import static java.lang.Thread.sleep;

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
                ClientConnectServerThread cst = new ClientConnectServerThread(socket);
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
    public void OnlineList(){
        try {
            Message obj = new Message();
            obj.setMassageType(MessageType.GET_ONLINE_USER);
            Socket socket1 = ManageConnectServerThread.searchCST(user.getUserID()).getSocket();
            ObjectOutputStream oos = new ObjectOutputStream(socket1.getOutputStream());
            oos.writeObject(obj);
            QQView.val = true;
        } catch (Exception e) {

        }


    }

    public void ExitUser(){
        Message mes = new Message();
        mes.setMassageType(MessageType.USER_EXIT);
        mes.setSender(user.getUserID());
        try {
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(mes);
        } catch (IOException e) {

        }
        ManageConnectServerThread.delCST(user.getUserID()).setLoop(false);

    }

}
