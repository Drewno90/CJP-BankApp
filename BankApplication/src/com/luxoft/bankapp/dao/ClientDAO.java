package com.luxoft.bankapp.dao;

import java.sql.SQLException;
import java.util.Set;

import com.luxoft.bankapp.handling_exceptions.DAOException;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;

public interface ClientDAO {


    Client findClientByName (Bank bank, String name) throws DAOException;

   
    Set<Client> getAllClients (Bank bank) throws DAOException;


    void save (Client client) throws DAOException, SQLException;


    void remove (Client client) throws DAOException, SQLException;

}