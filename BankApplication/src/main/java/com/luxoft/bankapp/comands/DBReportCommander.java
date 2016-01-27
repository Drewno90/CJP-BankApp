package com.luxoft.bankapp.comands;

import com.luxoft.bankapp.model.BankInfo;

public class DBReportCommander implements Command {

	@Override
	public void execute() {

		BankInfo bankInfo = new BankInfo(BankCommander.currentBank);
		System.out.println(bankInfo.printReport());
	}

	@Override
	public void printCommandInfo() {
		System.out.println("Print bank report");

	}

}
