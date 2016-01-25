package com.luxoft.bankapp.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Set;

import com.luxoft.bankapp.handling_exceptions.DAOException;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;
import com.luxoft.bankapp.model.Gender;

public class ClientDAOImpl extends BaseDAOImpl implements ClientDAO{

	@Override
	public Client findClientByName(Bank bank, String name) throws DAOException {
		
		int id = 0;
		String clientName = null;
		float initialOverdraft = 0;
		String mailAddress = null;
		String phoneNumber = null;
		String city = null;
		Gender gender = null;
		int bankId = 0;
		 try {
			 	openConnection();
	            Statement stmt = conn.createStatement();
	            String sql = "SELECT id, name, initial_overdraft, mail_address, phone_number, city, gender, bank_id FROM CLIENT WHERE CLIENT.NAME ='"+name+"';";
	            ResultSet rs = stmt.executeQuery(sql);
	            
	            while(rs.next()) {
	                // Retrieve by column name
	                id  = rs.getInt("id");
	                clientName = rs.getString("name");
	                initialOverdraft = rs.getFloat("initial_overdraft");
	                mailAddress = rs.getString("mail_address");
	                phoneNumber = rs.getString("phone_number");
	                city = rs.getString("city");
	                String genders = rs.getString("gender");
	        		if(genders.equals("Male"))
	        			gender = Gender.MALE;
	        		else if(genders.equals("Female"))
	        			gender = Gender.FEMALE;
	                bankId  = rs.getInt("bank_id");
	            }
	            clientsLog.info("Client attached");
	        } catch(SQLException e) {
	        	exceptionsLog.severe("SQLException while finding client by name");
	            e.printStackTrace();
	        }finally {
	            closeConnection();
	        }
		return new Client(id, clientName, initialOverdraft, mailAddress, phoneNumber, city, gender, bankId);
	}

	@Override
	public Set<Client> getAllClients(Bank bank) throws DAOException {
		int id = 0;
		String clientName = null;
		float initialOverdraft = 0;
		String mailAddress = null;
		String phoneNumber = null;
		String city = null;
		Gender gender = null;
		int bankId = 0;
		Set<Client> clientList = new HashSet<Client>();
		
		 try {
			 	openConnection();
	            Statement stmt = conn.createStatement();
	            String sql = "SELECT * FROM CLIENT ";
	            ResultSet rs = stmt.executeQuery(sql);
	            
	            while(rs.next()) {
	                // Retrieve by column name
	                id  = rs.getInt("id");
	                clientName = rs.getString("name");
	                initialOverdraft = rs.getFloat("initial_overdraft");
	                mailAddress = rs.getString("mail_address");
	                phoneNumber = rs.getString("phone_number");
	                city = rs.getString("city");
	                String genders = rs.getString("gender");
	        		if(genders.equals("Male"))
	        			gender = Gender.MALE;
	        		else if(genders.equals("Female"))
	        			gender = Gender.FEMALE;
	                bankId  = rs.getInt("bank_id");
	        		clientList.add(new Client(id, clientName, initialOverdraft, mailAddress, phoneNumber, city, gender, bankId));

	            }
	            clientsLog.info("Clients list attached");
	        } catch(SQLException e) {
	        	exceptionsLog.severe("SQLException while getting all clients");
	            e.printStackTrace();
	        }finally {
	            closeConnection();
	        }
		 
		return clientList;
	}

	@Override
	public void save(Client client) throws DAOException, SQLException {
		
		 String sql = "UPDATE CLIENT SET name=?,initial_overdraft=?,mail_address=?,phone_number=?,city=?,gender=?,bank_id=? WHERE id=?"; 
	     
	     PreparedStatement stmt;
	     try {
	         openConnection();
	         stmt = conn.prepareStatement(sql);
	         stmt.setString(1, client.getName());
	         stmt.setFloat(2, client.getInitialOverdraft());
	         stmt.setString(3, client.getMailAddress());
	         stmt.setString(4, client.getPhoneNumber());
	         stmt.setString(5, client.getCity());
	         if (client.getClientGender().equals(Gender.MALE)) {
	             stmt.setString(6, "Male");
	         } else if (client.getClientGender().equals(Gender.FEMALE)) {
	             stmt.setString(6, "Female");
	         }
	         stmt.setInt(7, client.getBankId());
	         stmt.setInt(8, client.getId());
	         if (stmt.execute()) {
	        	 dbLog.info("Client saved");
	             System.out.println("Client saved");
	         }
	         AccountDAOImpl accountDAO=new AccountDAOImpl();
			accountDAO.save((client.getActiveAccount()));
	     } catch (SQLException e) {
	    	 exceptionsLog.severe("SQLException while saving client ");
	         e.printStackTrace();
	         throw new DAOException();
	     } finally {
	         closeConnection();
	     }
		
	}

	@Override
	public void add(Client client) throws DAOException, SQLException {
		
	     String sql = "INSERT INTO CLIENT (name,initial_overdraft,mail_address,phone_number,city,gender,bank_id) " +
	                "VALUES(?,?,?,?,?,?,?);"; 
	     
	     PreparedStatement stmt;
	     try {
	         openConnection();
	         stmt = conn.prepareStatement(sql);
	         stmt.setString(1, client.getName());
	         stmt.setFloat(2, client.getInitialOverdraft());
	         stmt.setString(3, client.getMailAddress());
	         stmt.setString(4, client.getPhoneNumber());
	         stmt.setString(5, client.getCity());
	         if (client.getClientGender().equals(Gender.MALE)) {
	             stmt.setString(6, "Female");
	         } else if (client.getClientGender().equals(Gender.FEMALE)) {
	             stmt.setString(6, "Male");
	         }
	         stmt.setInt(7, client.getBankId());

	         if (stmt.execute()) {
	        	 dbLog.info("Client added");
	             System.out.println("Client saved");
	         }
	     } catch (SQLException e) {
	    	 exceptionsLog.severe("SQLException while adding client");
	         e.printStackTrace();
	         throw new DAOException();
	     } finally {
	         closeConnection();
	     }	
    
		
	}
	
	@Override
	public void remove(Client client) throws DAOException, SQLException {
		
		 String sql = ("DELETE FROM CLIENT WHERE id=? ;"); 

		 PreparedStatement stmt;
	        try {
	            openConnection();
	            stmt = conn.prepareStatement(sql);
	            stmt.setInt(1, client.getId());
	            if (stmt.execute()) {
	            	dbLog.info("Client removed");
	                System.out.println("Client removed");
	            }
	        } catch (SQLException e) {
	        	exceptionsLog.severe("SQLException while removing client");
	            e.printStackTrace();
	            throw new DAOException();
	        } finally {
	            closeConnection();
	        }
	}

}
