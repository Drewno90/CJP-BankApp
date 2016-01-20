package com.luxoft.bankapp.dao;

import java.sql.SQLException;

import com.luxoft.bankapp.handling_exceptions.BankNotFoundException;
import com.luxoft.bankapp.handling_exceptions.DAOException;
import com.luxoft.bankapp.model.Bank;

public interface BankDAO {

   

    Bank getBankByName (String name) throws DAOException, BankNotFoundException;



void save(Bank bank) throws DAOException, SQLException;



void remove(Bank bank) throws DAOException, SQLException;

}