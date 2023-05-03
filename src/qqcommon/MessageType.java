package qqcommon;

public interface MessageType {
    String LOGIN_SUCCEED = "1";//登录成功
    String LOGIN_FAIL = "2";//登录失败
    String GET_ONLINE_USER = "3";//获取在线用户
    String SEND_ONLINE_USER = "4";//发送在线用户
    String COMMON_MES = "5";;//普通消息
    String USER_EXIT = "6";//退出登录
}
