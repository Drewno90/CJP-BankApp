package com.luxoft.bankapp.handling_exceptions;

public class OverDraftLimitExceededException extends NotEnoughFundsException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7070459519452708954L;
	private float overdraft;

	public OverDraftLimitExceededException(float amount, float overdraft) {
		super(amount);
		this.overdraft = overdraft;
	}

	public float GetMaximumAmmountToGet() {
		return this.overdraft - this.getAmount();
	}

}
