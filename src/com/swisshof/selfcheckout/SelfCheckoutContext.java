package com.swisshof.selfcheckout;

import java.util.Locale;
import java.util.ResourceBundle;

import com.swisshof.selfcheckout.statemachine.MainStm;
import com.swisshof.selfcheckout.terminal.TerminalController;

public class SelfCheckoutContext {
	public static final double MAX_AMOUNT = 999.95;
	
	private double currentAmount = 0.0;
	
	private MainStm mainStm = null;
	protected IGui gui = null;
	protected TerminalController terminal = null;
	protected ResourceBundle strings = null;
	
	protected IResourceProvider resourceProvider = null;
	
	public IResourceProvider getResourceProvider() {
		return resourceProvider;
	}


	public void setResourceProvider(IResourceProvider resourceProvider) {
		this.resourceProvider = resourceProvider;
	}


	public MainStm getMainStm() {
		return mainStm;
	}


	public void setMainStm(MainStm mainStm) {
		this.mainStm = mainStm;
	}


	public SelfCheckoutContext() {
		currentAmount = 0.0;
		
		Locale locale = new Locale("de", "CH");
		strings = ResourceBundle.getBundle("res.Strings", locale);
		
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
	
	public String getString(String key)
	{
		return strings.getString(key);
	}
	
}
