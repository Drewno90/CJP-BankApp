package com.luxoft.bankapp.dao;

import com.luxoft.bankapp.handling_exceptions.BankNotFoundException;
import com.luxoft.bankapp.handling_exceptions.DAOException;
import com.luxoft.bankapp.model.Bank;

interface BankDAO {

    /**

      * Finds Bank by its name.

      * Do not load the list of the clients.

      * @ Param name

      * @ Return

      */

    Bank getBankByName (String name) throws DAOException, BankNotFoundException;



void save(Bank bank) throws DAOException;



void remove(Bank bank) throws DAOException;

}