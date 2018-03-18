package com.swisshof.selfcheckout;


import com.swisshof.selfcheckout.gui.MainFrame;
import com.swisshof.selfcheckout.statemachine.MainStm;
import com.swisshof.selfcheckout.terminal.TerminalController;

public class SelfCheckout {


	protected SelfCheckoutContext context;
	protected MainStm mainStm;
	protected MainFrame guiMainFrame;
	
	protected TerminalController terminalController;
	
	protected ResourceProvider resourceProvider;
	
	public static void main(String[] args) {
		new SelfCheckout();
		
	}

	public SelfCheckout() {
		super();
		resourceProvider = new ResourceProvider();
		context = new SelfCheckoutContext();
		mainStm = new MainStm(context);
		context.setMainStm(mainStm);
		
		terminalController = new TerminalController(context);
		context.setTerminal(terminalController);
		context.setResourceProvider(resourceProvider);		
		guiMainFrame = new MainFrame(context);
		
		context.setGui(guiMainFrame);

		
		// show GUI
		guiMainFrame.startGui();
		
		// enter init state
		mainStm.init();
		

		
	}

}
