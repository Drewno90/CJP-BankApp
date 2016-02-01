package com.luxoft.bankapp.dao;

import java.sql.Connection;

import com.luxoft.bankapp.handling_exceptions.DAOException;

public interface BaseDAO {

    Connection openConnection() throws DAOException;

	void closeConnection();

}