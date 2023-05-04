package qqView;

import service.UserClientService;

import java.util.Scanner;

public class QQView {
    public static void main(String[] args) {
        new QQView().startMain();
    }
    public static volatile boolean val = false;
    private boolean loop=true;
    private String[] secMenuOptions = {"显示用户","群发消息","私聊消息","发送文件",null,null,null,null,"系统退出"};
    private Scanner scanner = new Scanner(System.in);
    private UserClientService ucs = new UserClientService();
    private void startMain(){
        while(loop){
            System.out.println("登录界面");
            System.out.println("1 登录账号");
            System.out.println("9 退出系统");
            switch (scanner.next()) {
                case "1":
                    System.out.println("请输入账号：");
                    String user = scanner.next();
                    System.out.println("请输入密码：");
                    String psw = scanner.next();

                    if(ucs.checkUser(user,psw)) {

                        System.out.println("welcome" + user);
                        boolean loop = true;
                        while(loop){

                            int i = 0;
                            for (String s : secMenuOptions) {
                                i++;
                                if (s != null)
                                    System.out.println(i + "\t" + s);
                            }
                            switch (scanner.next()) {
                                case "1":
                                    //System.out.println(secMenuOptions[0]);
                                    ucs.OnlineList();
                                    while(val){}
                                    break;
                                case "2":
                                    System.out.println(secMenuOptions[1]);
                                    break;
                                case "3":
                                    System.out.println(secMenuOptions[2]);
                                    break;
                                case "4":
                                    System.out.println(secMenuOptions[3]);
                                    break;
                                case "9":
                                    loop = false;
                                    ucs.ExitUser();
                                    break;
                                default:
                                    System.out.println("参数错误");
                            }
                        }
                    }else {
                        System.out.println("登录失败");
                    }
                    break;
                case "9":
                    loop = false;
                    break;
                default:
                    System.out.println("参数错误");
                    break;

            }

            }
        }
    }
