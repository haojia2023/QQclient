package qqView;

import java.util.Scanner;

public class QQView {
    public static void main(String[] args) {
        new QQView().startMain();
    }
    private boolean loop=true;
    private String[] secMenuOptions = {"显示用户","群发消息","私聊消息","发送文件",null,null,null,null,"系统退出"};
    private Scanner scanner = new Scanner(System.in);
    private void startMain(){
        while(loop){
            System.out.println("登录界面");
            System.out.println("1 登录账号");
            System.out.println("9 退出系统");
            switch (scanner.nextInt()) {
                case 1:
                    System.out.println("请输入账号：");
                    String name = scanner.next();
                    System.out.println("请输入密码：");
                    String psw = scanner.next();

                    if(true) {
                        System.out.println("登录成功");
                        int i = 1;
                        for (String s:secMenuOptions)
                            if(s != null)
                                System.out.println(i++ + "\t" + s);

                        switch (scanner.nextInt()){
                            case 1:
                                System.out.println(secMenuOptions[0]);
                                break;
                            case 2:
                                System.out.println(secMenuOptions[1]);
                                break;
                            case 3:
                                System.out.println(secMenuOptions[2]);
                                break;
                            case 4:
                                System.out.println(secMenuOptions[3]);
                                break;
                            case 9:
                                System.out.println(secMenuOptions[8]);
                                break;

                        }
                    }else {
                        System.out.println("登录失败");
                    }
                    break;
                case 9:
                    loop = false;
                    break;
                default:
                    System.out.println("参数错误");
                    break;

            }

            }
        }

    }
