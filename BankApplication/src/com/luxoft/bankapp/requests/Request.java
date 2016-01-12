package com.luxoft.bankapp.requests;

import java.io.Serializable;

public interface Request extends Serializable{
	
	public void printInfo();
	public String execute();
	
}
