package com.luxoft.bankapp.handling_exceptions;


public class OverDraftLimitExceededException extends NotEnoughFundsException{
	private float overdraft;
	
	public OverDraftLimitExceededException(float amount, float overdraft) {
		super(amount);
		this.overdraft=overdraft;
	}
	
	public float GetMaximumAmmountToGet()
	{
		return this.overdraft-this.getAmount();
	}
	
}



