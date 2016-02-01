package com.luxoft.bankapp.dao;

import java.sql.SQLException;
import java.util.List;

import com.luxoft.bankapp.handling_exceptions.DAOException;
import com.luxoft.bankapp.model.Account;

public interface AccountDAO {

    public void save(Account account) throws DAOException;

    public void add(Account account) throws DAOException;

    public void removeByClientId(int idClient) throws DAOException, SQLException;

    public List<Account> getClientAccounts(int idClient) throws DAOException;

}