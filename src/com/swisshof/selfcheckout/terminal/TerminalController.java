package com.swisshof.selfcheckout.terminal;


import org.apache.logging.log4j.*;

import com.six.timapi.Amount;
import com.six.timapi.Terminal;
import com.six.timapi.TerminalSettings;
import com.six.timapi.TimException;
import com.six.timapi.constants.CardReaderStatus;
import com.six.timapi.constants.Currency;
import com.six.timapi.constants.TransactionType;

import com.swisshof.selfcheckout.SelfCheckoutContext;

public class TerminalController {
	protected Logger logger = LogManager.getLogger("TERMINAL");
	protected SelfCheckoutContext context;
	/**
	 * Terminal settings.
	 */
	protected TerminalSettings settings = null;
	
	/**
	 * Terminal instance.
	 */
	protected Terminal terminal = null;

	public TerminalController(SelfCheckoutContext context) {
		this.context = context;
		
		// Create terminal settings. The configuration is loaded from the file named
		// "TimApi.cfg" located in the run-path of your application. If not found
		// the default configuration is used.
		// 
		// If the configuration file contains a LogDir entry logging is enabled to
		// a file named TimApi.log using Java Logging Facility. You can change the 
		// logging using
		//   Logger.getLogger(Terminal.LOGGER_NAME).setLevel(Level.FINE);
		// or
		//   Logger.getLogger(Terminal.LOGGER_NAME).addHandler(myLoggerHandler);
		
		java.util.logging.Logger.getLogger(Terminal.LOGGER_NAME).setLevel(java.util.logging.Level.WARNING);

		settings = new TerminalSettings();
		
		// Create terminal instance. Connection to the terminal is established the
		// first time a transaction or balance request is send. For doing just
		// transactions this is all you have to do
		terminal = new Terminal(settings);
		terminal.addListener(new TerminalListener(context));
				
		// POS and user identifier have to be set before calling any method
		terminal.setPosId("ECR-01");
		terminal.setUserId(1);

	}
	
	public void startPayment()
	{
		try {
			logger.info("Start transaction: amount: {}", context.getCurrentAmount());
			terminal.transactionAsync(TransactionType.PURCHASE, new Amount(context.getCurrentAmount(), Currency.CHF));
		} catch (TimException te) {
			logger.info("Transaction failed, exception: " + te.toString());		
		}
		
	}
	
	public void abortTransaction() {
		try {
			logger.info("Cancel transaction");
			terminal.cancel();
		} catch (TimException te) {
			logger.info("Abort Transaction failed, exception: " + te.toString());		
		}
	}
	
	public boolean isCardInserted()
	{
		CardReaderStatus crs = terminal.getTerminalStatus().getCardReaderStatus();
		return (crs == CardReaderStatus.CARD_INSERTED);
	}
	
	public void startBalance() {
		try {
			logger.info("Start Balance...");
			terminal.balanceAsync();
		} catch (TimException te) {
			logger.info("Balance failed, exception: " + te.toString());		
		}
	}
	
	public void startDeactivate() {
		try {
			logger.info("Start Deactivate...");
			terminal.deactivateAsync();
		} catch (TimException te) {
			logger.info("Deactivate failed, exception: " + te.toString());		
		}
	}
	
	public void connect()
	{
		try {
			logger.info("Connect...");
			terminal.connect();
		} catch (TimException te) {
			logger.info("Connect failed, exception: " + te.toString());		
		}
	}
	
	public void disconnect()
	{
		try {
			logger.info("Disconnect...");
			terminal.disconnect();
		} catch (TimException te) {
			logger.info("Disconnect failed, exception: " + te.toString());		
		}
	}

	public void reboot()
	{
		try {
			logger.info("Start Reboot...");
			terminal.rebootAsync();
		} catch (TimException te) {
			logger.info("Reboot failed, exception: " + te.toString());		
		}
	}
}
