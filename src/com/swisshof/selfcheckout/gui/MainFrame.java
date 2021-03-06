package com.swisshof.selfcheckout.gui;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import com.swisshof.selfcheckout.SelfCheckoutContext;

public class MainFrame implements IGui
{
	protected static final int DISPLAY_SIZE_X = 1280;
	protected static final int DISPLAY_SIZE_Y = 800;
	
	protected static boolean debugging = false;
	

	protected SelfCheckoutContext context = null;
	protected JFrame frame;
	protected AmountEntryPane amountEntryPane = null;
	protected InfoPane infoPane = null;
	protected SystemInactivePane systemInactivePane = null;
	protected ServiceModePane serviceModePane = null;
	
	public MainFrame(SelfCheckoutContext context)
	{
		this.context = context;
		
		debugging = context.getResourceProvider().getConfigParameterAsBoolean("ui.debugging", false);
		
		amountEntryPane = new AmountEntryPane(context);
		infoPane = new InfoPane(context);
		systemInactivePane = new SystemInactivePane(context);
		serviceModePane = new ServiceModePane(context);
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
	
	public void showSystemInactiveView() {
		frame.setContentPane(systemInactivePane);
		frame.pack();		
	}
	
	public void showServiceModeView() {
		serviceModePane.reset();
		frame.setContentPane(serviceModePane);
		frame.pack();		
	}	
	public void showInfoView()
	{
		frame.setContentPane(infoPane);
		frame.pack();
	}
	
	public void showInfoView(InformationType type, DisplayedButtons buttons, String infoText)
	{
		infoPane.setInfoText(type, buttons, infoText);
		frame.setContentPane(infoPane);
		frame.pack();
	}
	
	public void setInfoText(InformationType type, DisplayedButtons buttons, String infoText)
	{
		infoPane.setInfoText(type, buttons, infoText);
	}
	
	@Override
	public void setBtnUser2Text(String btnText) {
		infoPane.setBtnUser2Text(btnText);
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
		
		if (debugging == false) {
			// Transparent 16 x 16 pixel cursor image.
			BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
	
			// Create a new blank cursor.
			Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");
	
			// Set the blank cursor to each JFrame.
			amountEntryPane.setCursor(blankCursor);
			infoPane.setCursor(blankCursor);
			systemInactivePane.setCursor(blankCursor);
			
		}
		
		showAmountEntryPane();
	}
	
	public void enableBtnConfirm(boolean enable) {
		infoPane.enableBtnConfirm(enable);
	}
	
	public void enableBtnUser2(boolean enable) {
		infoPane.enableBtnUser2(enable);
	}
	
	@Override
	public void notifyTerminalRequestDone(boolean success, String details) {
		serviceModePane.notifyTerminalRequestDone(success, details);
	}

	
}
