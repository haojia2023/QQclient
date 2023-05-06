package service;

import qqView.QQView;
import qqcommon.Message;
import qqcommon.MessageType;
import qqcommon.User;

import java.io.*;
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

    /**
     *
     * @param senderPath 发送的文件路径
     * @param getter    接受的用户
     * @param getterPath    接受的用户路径
     */
    public boolean SendFile(String senderPath,String getter,String getterPath){
        File file = new File(senderPath);
        if (!file.exists()) return false;
        if(user.getUserID().equals(getter)){
            System.out.print("发送者和接收者不能为同一个人,");
            return false;
        }
        int length = (int) file.length();
        byte[] bytes = new byte[length];
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(senderPath);
            fis.read(bytes);

        } catch (Exception e) {
            return false;
        } finally {
            if(fis != null)
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        Message mes = new Message();
        mes.setMessageType(MessageType.File_MES);
        mes.setSender(user.getUserID());
        mes.setGetter(getter);
        mes.setSrc(senderPath);
        mes.setDest(getterPath);
        mes.setFileLen(length);
        mes.setSendTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));//设置发送的时间的格式
        mes.setFileBytes(bytes);
        try {
            new ObjectOutputStream(socket.getOutputStream()).writeObject(mes);
        } catch (IOException e) {
            return false;
        }
        System.out.println(user.getUserID() + "\\" + senderPath + "到" + getter + "\\" + getterPath + "发送成功");
        return true;
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
