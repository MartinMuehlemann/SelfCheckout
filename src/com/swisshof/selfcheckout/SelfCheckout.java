package com.swisshof.selfcheckout;


import com.swisshof.selfcheckout.gui.MainFrame;
import com.swisshof.selfcheckout.statemachine.MainStm;
import com.swisshof.selfcheckout.terminal.TerminalController;

public class SelfCheckout {


	protected SelfCheckoutContext context;
	protected MainStm mainStm;
	protected MainFrame guiMainFrame;
	
	protected TerminalController terminalController;
	
	public static void main(String[] args) {
		new SelfCheckout();
		
	}

	public SelfCheckout() {
		super();
		context = new SelfCheckoutContext();
		mainStm = new MainStm(context);
		context.setMainStm(mainStm);
		
		terminalController = new TerminalController(context);
		
		guiMainFrame = new MainFrame(context);
		
		context.setGui(guiMainFrame);
		context.setTerminal(terminalController);
		
		// show GUI
		guiMainFrame.startGui();
		
		// enter init state
		mainStm.init();
		

		
	}

}
