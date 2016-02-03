package com.luxoft.bankapp.comands;

public class ExitCommand extends Command {

	public void execute() {
		System.exit(0);
	}

	public void printCommandInfo() {
		System.out.println("Exit");
	}
}
