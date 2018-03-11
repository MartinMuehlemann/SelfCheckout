package com.swisshof.selfcheckout.terminal;

import java.util.logging.Logger;

import com.six.timapi.CardData;
import com.six.timapi.DefaultTerminalListener;
import com.six.timapi.Terminal;
import com.six.timapi.TimEvent;
import com.six.timapi.TimException;
import com.six.timapi.TransactionResponse;
import com.swisshof.selfcheckout.SelfCheckoutContext;

public class TerminalListener extends DefaultTerminalListener {
	private static Logger logger = Logger.getLogger("EFT");
	protected SelfCheckoutContext context;
	
	
	public TerminalListener(SelfCheckoutContext context)
	{
		this.context = context;
	}
	
	@Override
	public void connectCompleted(TimEvent arg0) {
		logger.info("Connect completed");
		super.connectCompleted(arg0);
	}

	@Override
	public void disconnected(Terminal arg0, TimException arg1) {
		logger.info("Disconnect");
		super.disconnected(arg0, arg1);
	}

	@Override
	public void initTransactionCompleted(TimEvent arg0, CardData arg1) {
		logger.info("initTransactionCompleted");
		super.initTransactionCompleted(arg0, arg1);
	}

	@Override
	public void loginCompleted(TimEvent arg0) {
		logger.info("loginCompleted");
		super.loginCompleted(arg0);
	}

	@Override
	public void logoutCompleted(TimEvent arg0) {
		logger.info("logoutCompleted");
		super.logoutCompleted(arg0);
	}

	@Override
	public void terminalStatusChanged(Terminal arg0) {
		logger.info("terminalStatusChanged");
		super.terminalStatusChanged(arg0);
	}

	@Override
	public void transactionCompleted(TimEvent arg0, TransactionResponse arg1) {
		logger.info("transactionCompleted");
		super.transactionCompleted(arg0, arg1);
	}
	
}