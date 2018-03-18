package com.swisshof.selfcheckout.gui;

import java.awt.Dimension;

import javax.swing.JFrame;


import com.swisshof.selfcheckout.IGui;
import com.swisshof.selfcheckout.SelfCheckoutContext;

public class MainFrame implements IGui
{
	protected static final int DISPLAY_SIZE_X = 1280;
	protected static final int DISPLAY_SIZE_Y = 800;
	
	protected static boolean debugging = true;
	

	protected SelfCheckoutContext context = null;
	protected JFrame frame;
	protected AmountEntryPane amountEntryPane = null;
	protected InfoPane infoPane = null;

	
	public MainFrame(SelfCheckoutContext context)
	{
		this.context = context;
		amountEntryPane = new AmountEntryPane(context);
		infoPane = new InfoPane(context);
	}
	
	
	public void enableBtnPay(boolean enable) {
		amountEntryPane.enableBtnPay(enable);
	}
	
	public void enableKeyBlock(boolean enable) {
		amountEntryPane.enableKeyBlock(enable);
	}
	
	public void setInfoText(String infoText) {
		amountEntryPane.setInfoText(infoText);
	}


	public void showAmountEntryPane() {
		frame.setContentPane(amountEntryPane);
		frame.pack();
	}
	
	public void showEntryAmountView()
	{
		amountEntryPane.reset();
		frame.setContentPane(amountEntryPane);
		frame.pack();
	}
	
	public void showInfoView()
	{
		frame.setContentPane(infoPane);
		frame.pack();
	}
	
	public void startGui()
	{
		//Application Window
		frame = new JFrame("SelfCheckoutTerminal");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		
		frame.setSize(DISPLAY_SIZE_X, DISPLAY_SIZE_Y);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		if (debugging == false) {
			frame.setUndecorated(true);
			frame.setAlwaysOnTop(true);
		}
		frame.pack();
		frame.setMinimumSize(new Dimension(DISPLAY_SIZE_X, DISPLAY_SIZE_Y));
		
		
		JFrame.setDefaultLookAndFeelDecorated(true);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		
		showAmountEntryPane();
	}
	
	
}
