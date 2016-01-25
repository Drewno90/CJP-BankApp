package com.luxoft.bankapp.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.luxoft.bankapp.handling_exceptions.DAOException;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.model.CheckingAccount;
import com.luxoft.bankapp.model.SavingAccount;

public class AccountDAOImpl extends BaseDAOImpl implements AccountDAO{

	@Override
	public void save(Account account) throws DAOException {
		
		
	    String sql = "UPDATE ACCOUNTS SET type=?,balance=?,overdraft=?,client_id=? WHERE client_id="+account.getClientId()+";";
		PreparedStatement stmt;
	     try {
	         openConnection();
	         stmt = conn.prepareStatement(sql);
	         stmt.setString(1, account.getAccountType());
	         stmt.setFloat(2, account.getBalance());
	         if (account.getAccountType().equals("Checking Account")) 
	        	 stmt.setFloat(3, ((CheckingAccount) account).getOverdraft());
	         else
	        	 stmt.setFloat(3, 0f);
	         stmt.setInt(4, account.getClientId());

	         if (stmt.execute()) {
	             System.out.println("Account saved");
	         }
	     } catch (SQLException e) {
	   	  exceptionsLog.severe("SQLException while saving account");
	         e.printStackTrace();
	         throw new DAOException();
	     } finally {
	         closeConnection();
	     }
		
	}

	@Override
	public void add(Account account) throws DAOException {
		 String sql = "INSERT INTO ACCOUNTS(type,balance,overdraft,client_id) " +
	                "VALUES(?,?,?,?)"; 
		PreparedStatement stmt;
	     try {
	         openConnection();
	         stmt = conn.prepareStatement(sql);
	         stmt.setString(1, account.getAccountType());
	         stmt.setFloat(2, account.getBalance());
	         if (account.getAccountType().equals("Checking Account")) 
	        	 stmt.setFloat(3, ((CheckingAccount) account).getOverdraft());
	         else
	        	 stmt.setFloat(3, 0f);
	         stmt.setInt(4, account.getClientId());

	         if (stmt.execute()) {
	             System.out.println("Account added");
	         }
	     } catch (SQLException e) {
	   	  exceptionsLog.severe("SQLException while adding new account");
	         e.printStackTrace();
	         throw new DAOException();
	     } finally {
	         closeConnection();
	     }
		
	}

	@Override
	public void removeByClientId(int idClient) throws DAOException, SQLException {
	     
		 String sql = ("DELETE FROM ACCOUNTS WHERE client_id=?"); 

		 PreparedStatement stmt;
	        try {
	            openConnection();
	            stmt = conn.prepareStatement(sql);
	            stmt.setInt(1, idClient);
	            if (stmt.execute()) {
	                System.out.println("Account removed");
	            }
	        } catch (SQLException e) {
	      	  exceptionsLog.severe("SQLException while removing by client id");
	            e.printStackTrace();
	            throw new DAOException();
	        } finally {
	            closeConnection();
	        }
		
	}

	@Override
	public List<Account> getClientAccounts(int idClient) throws DAOException {
		
		int id = 0;
		String type = null;
		float balance = 0;
		float overdraft = 0;
		int clientId = 0;
		List<Account> accountList = new ArrayList<Account>();

		 try {
			 	openConnection();
	            Statement stmt = conn.createStatement();
	            String sql = "SELECT * FROM ACCOUNTS ";
	            ResultSet rs = stmt.executeQuery(sql);
	            
	            while(rs.next()) {
	                // Retrieve by column name
	                id  = rs.getInt("id");
	                type = rs.getString("type");
	                balance = rs.getFloat("balance");
	                overdraft = rs.getFloat("overdraft");
	                clientId  = rs.getInt("client_id");
	                if(type.equals("CheckingAccount"))
	                	accountList.add(new CheckingAccount(id, type, balance, overdraft, clientId));
	                else if(type.equals("SavingAccount"))
	                	accountList.add(new SavingAccount(id, type, balance, clientId));
	                else
	                	System.out.println("Incorrect type of Account in id=" + id);
	            }
	        } catch(SQLException e) {
	      	  exceptionsLog.severe("SQLException while getting client accounts");
	            e.printStackTrace();
	        } finally {
	            closeConnection();
	        }
		 
		return accountList;
	}

}
