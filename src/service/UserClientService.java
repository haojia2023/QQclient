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
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserClientService {
    private User user;
    private Socket socket;
    public boolean IsClosed(){
        return socket.isClosed();
    }
    public boolean checkUser(String name,String psw){
        user = new User(name,psw);
        try {
            socket = new Socket(InetAddress.getByName("127.0.0.1"), 9999);
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(user);
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Message o = (Message) ois.readObject();
            if(MessageType.LOGIN_SUCCEED.equals(o.getMessageType())) {
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
            obj.setMessageType(MessageType.GET_ONLINE_USER);
            Socket socket1 = ManageConnectServerThread.searchCST(user.getUserID()).getSocket();
            ObjectOutputStream oos = new ObjectOutputStream(socket1.getOutputStream());
            oos.writeObject(obj);
            QQView.val = true;
        } catch (Exception e) {

        }


    }

    public void SendUser(String getterID,String content){
        Message message = new Message();
        message.setMessageType(MessageType.COMMON_MES);
        message.setSendTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));//设置发送的时间的格式
        message.setSender(user.getUserID());
        message.setGetter(getterID);
        message.setContent(content);
        try {
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ExitUser(){
        Message mes = new Message();
        mes.setMessageType(MessageType.USER_EXIT);
        mes.setSender(user.getUserID());
        try {
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(mes);
        } catch (IOException e) {

        }
        ClientConnectServerThread clientConnectServerThread = ManageConnectServerThread.delCST(user.getUserID());
        clientConnectServerThread.setLoop(false);
        try {
            clientConnectServerThread.getSocket().close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
