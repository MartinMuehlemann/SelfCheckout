package com.swisshof.selfcheckout.log;

import java.util.Date;

public interface ITransactionLogger {
	public void logTransactionSucessful(double amount, String brandName, double daySaldo);
	
}
