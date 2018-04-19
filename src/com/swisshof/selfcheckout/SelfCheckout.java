package com.swisshof.selfcheckout;


import com.swisshof.selfcheckout.gui.MainFrame;
import com.swisshof.selfcheckout.log.IReceiptsArchiver;
import com.swisshof.selfcheckout.log.ReceiptsArchiver;
import com.swisshof.selfcheckout.printer.IPrinter;
import com.swisshof.selfcheckout.printer.Printer;
import com.swisshof.selfcheckout.statemachine.MainStm;
import com.swisshof.selfcheckout.terminal.TerminalController;

import org.apache.logging.log4j.*;

public class SelfCheckout {

	public final static Logger logger = LogManager.getLogger(SelfCheckout.class.getName());

	protected SelfCheckoutContext context;
	protected MainStm mainStm;
	protected MainFrame guiMainFrame;
	
	protected TerminalController terminalController;
	
	protected ResourceProvider resourceProvider;
	
	protected ReceiptsArchiver receipsArchiver;
	
	protected Printer printer;
	
	public static void main(String[] args) {
		new SelfCheckout();
		
	}

	public SelfCheckout() {
		super();
		logger.info("Main", "strtup...");
		
		resourceProvider = new ResourceProvider();
		context = new SelfCheckoutContext();
		mainStm = new MainStm(context);
		receipsArchiver = new ReceiptsArchiver(context);
		printer = new Printer();
		
		context.setMainStm(mainStm);
		context.setReceiptsArchiver(receipsArchiver);
		context.setPrinter(printer);
		
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
