package com.swisshof.selfcheckout.log;


public interface ITransactionLogger {
	public void logTransactionSucessful(double amount, String brandName);
	
}
