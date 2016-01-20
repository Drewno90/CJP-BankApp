package com.luxoft.bankapp.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.luxoft.bankapp.handling_exceptions.DAOException;

public class BaseDAOImpl implements BaseDAO {

    Connection conn;

    public Connection openConnection() throws DAOException {
        try {
            Class.forName("org.h2.Driver"); // this is driver for H2
            conn = DriverManager.getConnection("jdbc:h2:~/test",
                "sa", // login
                "" // password
                );
            return conn;
        } catch(ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new DAOException();
        }
    }

    public void closeConnection() {
        try {
            conn.close();
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
}