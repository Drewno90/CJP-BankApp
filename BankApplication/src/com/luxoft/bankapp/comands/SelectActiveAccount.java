package com.luxoft.bankapp.comands;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.luxoft.bankapp.dao.AccountDAO;
import com.luxoft.bankapp.dao.AccountDAOImpl;
import com.luxoft.bankapp.handling_exceptions.DAOException;
import com.luxoft.bankapp.model.Account;
import com.luxoft.bankapp.service.BankService;
import com.luxoft.bankapp.service.BankServiceImpl;

public class SelectActiveAccount implements Command {

	@Override
	public void execute() {

        AccountDAO accountDAO = new AccountDAOImpl();
        List<Account> accountList = new ArrayList<Account>();
		BankService bankService= new BankServiceImpl();
        try {	
        accountList = accountDAO.getClientAccounts(BankCommander.currentClient.getId()); 
        
    } catch (DAOException e) {
        e.printStackTrace();
    }
        int accountNum=0;
        for(Account account: accountList)
        {
        	if(account.getClientId()==BankCommander.currentClient.getId())
        	{
        		System.out.println("Type:"+ account.getAccountType());
        		System.out.println("account Id="+accountNum);
        	}
        	accountNum++;
        }
		Scanner scan = new Scanner(System.in);
		System.out.println("Choose account (id)");
		int selectedAccount = scan.nextInt();
        bankService.setActiveAccount(BankCommander.currentClient,accountList.get(selectedAccount));
	}

	@Override
	public void printCommandInfo() {
		System.out.println("Set active Account");
		
	}

}