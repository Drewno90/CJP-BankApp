package com.luxoft.bankapp.bank_application;

public class BankServerMonitor implements Runnable {

	boolean isRunning = true;

	@Override
	public void run() {
		while (isRunning) {
			System.out.println("Number of connected client " + BankServerThreaded.getCount());

			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
