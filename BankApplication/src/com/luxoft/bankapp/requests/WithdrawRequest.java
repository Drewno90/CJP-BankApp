package com.luxoft.bankapp.requests;

import com.luxoft.bankapp.bank_application.BankServer;
import com.luxoft.bankapp.handling_exceptions.NotEnoughFundsException;
import com.luxoft.bankapp.handling_exceptions.OverDraftLimitExceededException;
import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.Client;

public class WithdrawRequest implements Request {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5796215966552026686L;
	private String clientName;
	private Bank bank;
	private float ammount;

	public WithdrawRequest(String clientName, Bank bank, float ammount)
	{
		this.clientName=clientName;
		this.bank=bank;
		this.ammount=ammount;
	}
	
	@Override
	public void printInfo() {
		System.out.println("Withdraw");

	}

	@Override
	public String execute() {
		
		Client client = BankServer.bankService.getClient(bank, clientName);
	  	
	  	
	  	int flag=0;
	  	do{

    	  	try{
    	  		flag=0;
    	  		client.getActiveAccount().withdraw(ammount);
    	  	} catch (OverDraftLimitExceededException e)
    	  	{
    	  		flag=1;
    	  		return ("Overdraft Limit Exceeded. Maximum you can get is " + e.GetMaximumAmmountToGet() + " Try again.");
    	  		
    	  	}catch (NotEnoughFundsException e)
    	  	{
    	  		flag=1;
    	  		return ("Not enough founds on your account. Maximum you can get is " + e.getAmount());
    	  	}
    	  	
	  		}while(flag==1);
	  	
	  	return ("Please take your money from ATM");
		}

}
