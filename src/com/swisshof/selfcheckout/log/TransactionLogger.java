package com.swisshof.selfcheckout.log;

import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class TransactionLogger implements ITransactionLogger{

	protected Logger logger = LogManager.getLogger("TRANSACTION");

	
	@Override
	public void logTransactionSucessfol(double amount) {
		logger.info("Transaction successful CHF {}", amount); 
	}

}
