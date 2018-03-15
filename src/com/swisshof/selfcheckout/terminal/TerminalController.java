package com.swisshof.selfcheckout.terminal;

import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.six.timapi.Amount;
import com.six.timapi.Receipt;
import com.six.timapi.Terminal;
import com.six.timapi.TerminalSettings;
import com.six.timapi.TimException;
import com.six.timapi.TransactionResponse;
import com.six.timapi.constants.Currency;
import com.six.timapi.constants.TransactionType;

import com.swisshof.selfcheckout.SelfCheckoutContext;

public class TerminalController {
	private static Logger logger = Logger.getLogger("EFT");
	
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
		Logger.getLogger(Terminal.LOGGER_NAME).setLevel(Level.FINE);
		// or
		//   Logger.getLogger(Terminal.LOGGER_NAME).addHandler(myLoggerHandler);
		settings = new TerminalSettings();
		
		//settings.setGuides(EnumSet.of(Guides.RETAIL, Guides.HOSPITALITY));
		
		// You can also change settings without using a configuration file. You have to set
		// at least the terminal identifier of the terminal to connect you.
//		settings.setTerminalId("25190037");
//		settings.setTerminalId("23509105");
//		
		//OmniChannel config
//		settings.setSaferpayTerminalId("17879155");
//		settings.setSaferpayCredentials("QVBJXzQwMDM3MF84MDgzODkxMTpTYWZlcnBheUVUVVRlc3QxNzo=");
//		settings.setSaferpayCustomerId("400370");
//		settings.setSaferpayBaseUrl("https://test.saferpay.com/api");
		
		// Create terminal instance. Connection to the terminal is established the
		// first time a transaction or balance request is send. For doing just
		// transactions this is all you have to do
		terminal = new Terminal(settings);
		terminal.addListener(new TerminalListener(context));
		
		// If the configuration file contains a LogDir entry logging is enabled to
				// a file named TimApi.log using Java Logging Facility. You can change the 
				// logging using
//		Logger.getLogger(Terminal.LOGGER_NAME).setLevel(Level.ALL);
		
//		System.out.println(Logger.getLogger(Terminal.LOGGER_NAME).getHandlers().length);
//		for (Handler handler : Logger.getLogger(Terminal.LOGGER_NAME).getHandlers()){
//			handler.setLevel(Level.FINEST);
//		}
		
		// POS and user identifier have to be set before calling any method
		terminal.setPosId("ECR-01");
		terminal.setUserId(1);
		
		
	}
	
	public void startPayment()
	{

					
		try {
			terminal.transactionAsync(TransactionType.PURCHASE, new Amount(context.getCurrentAmount(), Currency.CHF));
		} catch (TimException te) {
					logger.info("Transaction failed, exception: " + te.toString());		
		}
		
	}
}
