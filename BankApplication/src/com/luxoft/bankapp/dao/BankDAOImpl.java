package com.luxoft.bankapp.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.luxoft.bankapp.handling_exceptions.BankNotFoundException;
import com.luxoft.bankapp.handling_exceptions.DAOException;
import com.luxoft.bankapp.model.Bank;

public class BankDAOImpl extends BaseDAOImpl implements BankDAO{

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
        	 exceptionsLog.severe("SqlException while getting bank by name ");
             e.printStackTrace();
             throw new DAOException();
        } finally {
             closeConnection();
        }
        return bank;
    }


	@Override
	public void save(Bank bank) throws DAOException, SQLException {

     String sql = "INSERT INTO BANK(name) " +
             "VALUES(?)"; 
  
  PreparedStatement stmt;
  try {
      openConnection();
      stmt = conn.prepareStatement(sql);
      stmt.setString(1, bank.getBankName());

      if (stmt.execute()) {
    	  dbLog.info("Client saved in bank");
    	  clientsLog.info("Client saved in bank");
          System.out.println("Client saved");
      }
  } catch (SQLException e) {
	  exceptionsLog.severe("SQLException while saving bank");
      e.printStackTrace();
      throw new DAOException();
  } finally {
      closeConnection();
  }
		
	}

	@Override
	public void remove(Bank bank) throws DAOException, SQLException {
	     
		 String sql = ("DELETE FROM BANK WHERE id=?"); 

		 PreparedStatement stmt;
	        try {
	            openConnection();
	            stmt = conn.prepareStatement(sql);
	            stmt.setInt(1, bank.getId());
	            if (stmt.execute()) {
	            	clientsLog.info("Client removed from bank");
	                System.out.println("Client removed");
	            }
	        } catch (SQLException e) {
	      	  exceptionsLog.severe("SQLException while removing bank");
	            e.printStackTrace();
	            throw new DAOException();
	        } finally {
	            closeConnection();
	        }
		
	}
}