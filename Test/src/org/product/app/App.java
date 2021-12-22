package org.product.app;

import org.product.app.ui.ServiceTableForm;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class App {
    /**
     * библиотека драйвер для субд mysql: mysql:mysql-
     * библиотека ламбок
     *
     * адрес:
     * mydb/root/1234
     */
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        new ServiceTableForm();
    }
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/mdb","root1","1234");
    }

}

