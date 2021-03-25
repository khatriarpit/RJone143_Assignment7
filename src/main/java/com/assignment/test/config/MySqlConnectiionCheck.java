package com.assignment.test.config;

import java.sql.*;

class MySqlConnectiionCheck {
    public static void main(String args[]) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(
                    "jdbc:mysql://sql3.freemysqlhosting.net:3306/sql3401313?useSSL=false", "sql3401313", "2hHk4Nakya");
//            Connection con = DriverManager.getConnection("jdbc:mysql://sql6.freemysqlhosting.net","sql6401344", "EFLLIHtYEI");
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("select * from User");
            while (rs.next())
                System.out.println(rs.getInt(1) + "  " + rs.getString(2) + "  " + rs.getString(3));
            con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}