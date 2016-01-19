package com.luxoft.bankapp.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.luxoft.bankapp.handling_exceptions.BankNotFoundException;
import com.luxoft.bankapp.handling_exceptions.DAOException;
import com.luxoft.bankapp.model.Bank;

class BankDAOImpl extends BaseDAOImpl {

    Connection conn;

    public Bank getBankByName(String name) throws DAOException, BankNotFoundException {

        Bank bank = new Bank(name);
       String sql = "SELECT ID, NAME FROM BANK WHERE name=?";
       PreparedStatement stmt;

        try {
             openConnection();
             stmt = conn.prepareStatement(sql);
             stmt.setString(1, name);
           ResultSet rs = stmt.executeQuery();
           if (rs.next()) {
             int id  = rs.getInt("ID");
             bank.setId(id);
           } else {
             throw new BankNotFoundException(name);
           }
        } catch (SQLException e) {
             e.printStackTrace();
             throw new DAOException();
        } finally {
             closeConnection();
        }
        return bank;
    }
}