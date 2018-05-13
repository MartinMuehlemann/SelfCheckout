package com.swisshof.selfcheckout.log;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class TransactionLogger implements ITransactionLogger{

	protected Logger logger = LogManager.getLogger("TRANSACTION");

	
	@Override
	public void logTransactionSucessful(double amount, String brandName)
	{
		logger.info(String.format("Transaction successful, %s, CHF %.2f", brandName, amount)); 
	}

}
