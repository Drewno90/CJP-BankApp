package com.luxoft.bankapp.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

import com.luxoft.bankapp.handling_exceptions.DAOException;

public class BaseDAOImpl implements BaseDAO {

	Connection conn;

	Logger exceptionsLog = Logger.getLogger("exceptions." + this.getClass().getName());
	Logger dbLog = Logger.getLogger("db." + this.getClass().getName());
	Logger clientsLog = Logger.getLogger("clients." + this.getClass().getName());

	public Connection openConnection() throws DAOException {
		try {
			Class.forName("org.h2.Driver"); // this is driver for H2
			conn = DriverManager.getConnection("jdbc:h2:~/testdb", "sa", // login
					"" // password
			);
			dbLog.info("Connected to database");
			return conn;
		} catch (ClassNotFoundException | SQLException e) {
			exceptionsLog.severe("error while opening connection- " + e.getMessage());
			e.printStackTrace();
			throw new DAOException();
		}
	}

	public void closeConnection() {
		try {
			conn.close();
			dbLog.info("Disconnected with database");
		} catch (SQLException e) {
			exceptionsLog.severe("SQLException while closing connection");
			e.printStackTrace();
		}
	}
}