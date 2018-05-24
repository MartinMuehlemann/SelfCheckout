package com.swisshof.selfcheckout;


import com.swisshof.selfcheckout.gui.IGui;
import com.swisshof.selfcheckout.log.IReceiptsArchiver;
import com.swisshof.selfcheckout.log.ITransactionLogger;
import com.swisshof.selfcheckout.printer.IPrinter;
import com.swisshof.selfcheckout.statemachine.MainStm;
import com.swisshof.selfcheckout.terminal.TerminalController;

public class SelfCheckoutContext {
	public static final double MAX_AMOUNT = 999.95;
	
	private double currentAmount = 0.0;
	
	private MainStm mainStm = null;
	protected IGui gui = null;
	protected TerminalController terminal = null;
	
	protected IResourceProvider resourceProvider = null;
	
	protected IReceiptsArchiver receiptsArchiver = null;
	protected ITransactionLogger transactionLogger = null;

	protected boolean gotoInactiveRequested = false;

	protected IPrinter printer = null;
	
	public IPrinter getPrinter() {
		return printer;
	}


	public void setPrinter(IPrinter printer) {
		this.printer = printer;
	}


	public IResourceProvider getResourceProvider() {
		return resourceProvider;
	}


	public void setResourceProvider(IResourceProvider resourceProvider) {
		this.resourceProvider = resourceProvider;
	}

	
	public IReceiptsArchiver getReceiptsArchiver() {
		return receiptsArchiver;
	}


	public void setReceiptsArchiver(IReceiptsArchiver receiptsArchiver) {
		this.receiptsArchiver = receiptsArchiver;
	}

	public ITransactionLogger getTransactionLogger() {
		return transactionLogger;
	}


	public void setTransactionLogger(ITransactionLogger transactionLogger) {
		this.transactionLogger = transactionLogger;
	}


	public MainStm getMainStm() {
		return mainStm;
	}


	public void setMainStm(MainStm mainStm) {
		this.mainStm = mainStm;
	}


	public boolean isGotoInactiveRequested() {
		return gotoInactiveRequested;
	}


	public void setGotoInactiveRequested(boolean gotoInactiveRequested) {
		this.gotoInactiveRequested = gotoInactiveRequested;
	}


	public SelfCheckoutContext() {
		currentAmount = 0.0;
		gotoInactiveRequested = false;
	}
		
	public double getCurrentAmount() {
		return currentAmount;
	}

	public void setCurrentAmount(double currentAmount)
	{
		this.currentAmount = currentAmount;
	}

	public void setGui(IGui gui) {
		this.gui = gui;
	}
	
	public IGui getGui() {
		return gui;
	}


	public TerminalController getTerminal() {
		return terminal;
	}

	public void setTerminal(TerminalController terminal) {
		this.terminal = terminal;
	}

}
