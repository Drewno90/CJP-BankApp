package com.luxoft.bankapp.requests;

import com.luxoft.bankapp.model.Bank;
import com.luxoft.bankapp.model.BankInfo;


public class BankStatisticsRequest implements Request {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3923775007422593462L;
	private Bank bank;

	public BankStatisticsRequest(Bank bank) {
		this.bank=bank;
	}

	@Override
	public void printInfo() {
		System.out.println("Get bank statistics");

	}

	@Override
	public String execute() {
		BankInfo bankInfo = new BankInfo();
		bankInfo.setBank(bank);
	  	return bankInfo.printReport() ;

	}

}
