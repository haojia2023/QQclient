package service;

import java.util.HashMap;

public class ManageConnectServerThread {
    private static HashMap<String ,ClientConnectServerThread> hs = new HashMap<>();

    public static void addCST(String userID,ClientConnectServerThread cst){
        hs.put(userID,cst);
    }

    public static ClientConnectServerThread searchCST(String userID){
        return hs.get(userID);
    }

    public static ClientConnectServerThread delCST(String userID){return hs.remove(userID);}
}
