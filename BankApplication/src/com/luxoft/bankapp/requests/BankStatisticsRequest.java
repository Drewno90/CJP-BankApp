package com.luxoft.bankapp.requests;

import com.luxoft.bankapp.comands.BankCommander;
import com.luxoft.bankapp.model.BankInfo;


public class BankStatisticsRequest implements Request {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3923775007422593462L;

	@Override
	public void printInfo() {
		System.out.println("Get bank statistics");

	}

	@Override
	public String execute() {
		BankInfo bankInfo = new BankInfo();
		bankInfo.setBank(BankCommander.currentBank);
	  	return bankInfo.printReport() ;

	}

}

