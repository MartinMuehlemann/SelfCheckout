package com.swisshof.selfcheckout;


import com.swisshof.selfcheckout.gui.MainFrame;
import com.swisshof.selfcheckout.statemachine.MainStm;

public class SelfCheckout {


	protected SelfCheckoutContext context;
	protected MainStm mainStm;
	protected MainFrame guiMainFrame;
	
	public static void main(String[] args) {
		new SelfCheckout();
		
	}

	public SelfCheckout() {
		super();
		context = new SelfCheckoutContext();
		mainStm = new MainStm(context);
		context.setMainStm(mainStm);
		
		guiMainFrame = new MainFrame(context);
		
		mainStm.setGui(guiMainFrame);
		
		// enter init state
		mainStm.init();
		
		// show GUI
		guiMainFrame.startGui();
		
	}

}
