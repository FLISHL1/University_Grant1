package main.server;

import main.config.Config;

import java.sql.*;
public class Server {

    private Connection connection;

    private Server() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://" + Config.URL, Config.LOGIN, Config.PASSWORD);
        } catch (SQLException e) {
            System.out.println("Data Base not connected \n Error!");
            System.out.println(e);
        /*} catch (ClassNotFoundException e){
            System.out.println("JDBS not founded!");
            System.out.println(e);
            System.exit(0);
        }*/
        }
    }
    private static class SignServer {
        public static final Server SIGNSERVER = new Server();
    }

    public static Server getInstance () {
        return SignServer.SIGNSERVER;
    }
}