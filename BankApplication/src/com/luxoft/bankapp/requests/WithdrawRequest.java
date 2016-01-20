package com.luxoft.bankapp.requests;

import com.luxoft.bankapp.comands.BankCommander;
import com.luxoft.bankapp.handling_exceptions.NotEnoughFundsException;
import com.luxoft.bankapp.service.BankService;

public class WithdrawRequest implements Request {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5796215966552026686L;
	private float ammount;
	private BankService bankService;
	private String clientName;
	
	public WithdrawRequest(float ammount, BankService bankService, String name)
	{
		this.ammount=ammount;
		this.bankService=bankService;
		this.clientName=name;
	}
	
	@Override
	public void printInfo() {
		System.out.println("Withdraw");

	}

	@Override
	public String execute() {

    	  		try {
					bankService.findClientByHisName(BankCommander.currentBank, clientName).getActiveAccount().withdraw(ammount);
				} catch (NotEnoughFundsException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	  	
	  	return ("Please take your money from ATM");
		}

}

