package service;

import qqView.QQView;
import qqcommon.Message;
import qqcommon.MessageType;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.nio.file.Paths;
import java.util.Scanner;

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
                    System.out.println(o.getSendTime() + "\t" +o.getSender() + "对"+ o.getGetter() +"说：" + o.getContent());
                }else if(mes.equals(MessageType.File_MES)){
                    QQView.val = true;
                    System.out.println("接收文件：" + o.getSender() + "\\" + o.getSrc() + "到" + o.getGetter() + "\\" + o.getDest() +"，接收文件？（Y\\N）");
                    Scanner scanner1 = new Scanner(System.in);
                    if(scanner1.next().equalsIgnoreCase("n")) {
                        continue;
                    }
                    System.out.println("需要更改文件地址吗？（Y\\N）");
                    if(scanner1.next().equalsIgnoreCase("y")){
                        do {
                            System.out.println("请输入正确的存放地址：");
                            o.setDest(scanner1.next());
                        }
                        while(!Paths.get(o.getDest()).isAbsolute());
                    }
                    QQView.val = false;

                        File file = new File(o.getDest());
                        if (!file.exists()) {
                            file.getParentFile().mkdirs();
                        }
                        FileOutputStream fos = null;
                        try {
                            fos = new FileOutputStream(file, false);
                            fos.write(o.getFileBytes(),0,o.getFileLen());
                        } catch (IOException e) {
                            System.out.println("写入失败");
                        } finally {
                            try {
                                fos.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    System.out.println("接收成功");
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
