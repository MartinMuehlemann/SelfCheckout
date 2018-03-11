package com.swisshof.selfcheckout.gui;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Queue;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import com.six.timapi.Amount;
import com.six.timapi.Receipt;
import com.six.timapi.TimException;
import com.six.timapi.TransactionResponse;
import com.six.timapi.constants.Currency;
import com.six.timapi.constants.TransactionType;
import com.swisshof.selfcheckout.IGui;
import com.swisshof.selfcheckout.SelfCheckoutContext;

public class MainFrame implements IGui{
	
	private static Logger logger = Logger.getLogger("GUI");
	
	
	protected static final int DISPLAY_SIZE_X = 1280;
	protected static final int DISPLAY_SIZE_Y = 800;
	
	protected JLabel txtPayAmount = null;
	protected JLabel lblUserInfo = null;
	protected JButton[] btnKeyblock = new JButton[10];
	protected JButton btnKeyblockDot = null;
	protected JButton btnKeyblockClear = null;
	protected JButton btnPay = null;
	protected Font fontNummericBlock = null;
	protected Font fontPayAmountField = null;
	protected Font fontUserInfo = null;
	protected Font fontButtons = null;
	
	protected ArrayList<Integer> queueAmountEntry = new ArrayList<Integer>();
	
	
	protected SelfCheckoutContext context = null;
	
	public MainFrame(SelfCheckoutContext context)
	{
		this.context = context;
		loadResources();
	}
	
	
	public void enableBtnPay(boolean enable) {
		btnPay.setEnabled(enable);
	}
	
	public void clearAllAmountEntry() {
		queueAmountEntry.clear();
	}
	
	
	protected double getAmount() {
		double amount = 0.0;
		for (Integer digit : queueAmountEntry) {
			amount *= 10;
			amount += digit;
		}
		return amount;
	}
	
	private void loadResources() {
//		try {
//			InputStream is = getClass().getResourceAsStream("/FTB.ttf");
//			fontNummericBlock = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(Font.BOLD, 14);
//		}catch  (Exception e) {
//			logger.severe("Exception while loading font: " + e.getMessage());
//		}
		
		fontNummericBlock = new Font("Arial", Font.BOLD, 150);
		fontPayAmountField = new Font("Arial", Font.BOLD, 200);
		fontUserInfo = new Font("Arial", Font.PLAIN, 12);		
		fontButtons = new Font("Arial", Font.BOLD, 100);
	}
	
	public void startGui()
	{
		//Application Window
		JFrame frame = new JFrame("TIM API Example ECR");
		Container contentPane = frame.getContentPane();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gbl = new GridBagLayout();
		contentPane.setLayout(gbl);
		

		GridBagConstraints lc = new GridBagConstraints();
		lc.fill = GridBagConstraints.BOTH;

		
		txtPayAmount = new JLabel(getCurrentAmountString());
		txtPayAmount.setFont(fontPayAmountField);
		txtPayAmount.setHorizontalAlignment(SwingConstants.RIGHT);

		lblUserInfo  = new JLabel("Bitte geben Sie den Betrag ein, anschliessend drücken Sie die Taste 'Bezahlen'");
		lblUserInfo.setFont(fontUserInfo);
		lblUserInfo.setHorizontalAlignment(SwingConstants.RIGHT);

		for (int i = 0; i < btnKeyblock.length; i++) {
			btnKeyblock[i] = new NumericBlockButton(i);
			btnKeyblock[i].setFont(fontNummericBlock);
			btnKeyblock[i].addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					
					queueAmountEntry.add(((NumericBlockButton)e.getSource()).getAmount());
					
					context.setCurrentAmount(getAmount());
					
					// update amount on screen
					txtPayAmount.setText(getCurrentAmountString());
					
					context.getMainStm().amountChanged();
					
					btnPay.setEnabled(context.getCurrentAmount() > 0.0);
				}
			});
		}
		
		btnKeyblockDot = new JButton(".");
		btnKeyblockDot.setFont(fontNummericBlock);
		
		btnKeyblockClear = new JButton("C");
		btnKeyblockClear.setFont(fontNummericBlock);
		btnKeyblockClear.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if(queueAmountEntry.size() > 0) {
					queueAmountEntry.remove(queueAmountEntry.size() - 1);
					
					context.setCurrentAmount(getAmount());
					
					// update amount on screen
					txtPayAmount.setText(getCurrentAmountString());
					
					context.getMainStm().amountChanged();
					
					btnPay.setEnabled(context.getCurrentAmount() > 0.0);
				}
			}
			
		});
		
		
		btnPay = new JButton("Bezahlen"); 
		btnPay.setFont(fontButtons);
		btnPay.setEnabled(false);
		btnPay.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
//				try {
//				// Start transaction. Automatically connects to and activates the terminal
//				TransactionResponse trxResponse = terminal.transaction(TransactionType.PURCHASE, new Amount(currentAmount, Currency.CHF));
//				// If successful
//				lblUserInfo.setText("Bezahlung erfolgreich");
//				// Both cardholder and merchant receipt are returned
//				for (Receipt receipt : trxResponse.getPrintData().getReceipts()) {
//					logger.info(receipt.getRecipient() + receipt.getValue());
//				}
//			} catch (TimException te) {
//				logger.info("Transaction failed, exception: " + te.toString());
//			} catch (Exception se) {
//				logger.info("Systemexception: " + se.getMessage());
//			}		

			}
			
		});

		
		lc.weightx = 0.5;
		lc.weighty = 1.0;
		lc.gridx = 0;
		lc.gridy = 0;
		lc.gridwidth = 3;
		
		lc.gridwidth = 1;
		lc.gridx = 0;
		contentPane.add(btnKeyblock[7], lc);
		lc.gridx++;
		contentPane.add(btnKeyblock[8], lc);
		lc.gridx++;
		contentPane.add(btnKeyblock[9], lc);
		
		lc.weightx = 1.0;
		lc.gridx++;
		contentPane.add(txtPayAmount, lc);
		
		// next line
		lc.weightx = 0.5;
		lc.gridy++;
		lc.gridx = 0;
		contentPane.add(btnKeyblock[4], lc);
		lc.gridx++;
		contentPane.add(btnKeyblock[5], lc);
		lc.gridx++;
		contentPane.add(btnKeyblock[6], lc);

		lc.weightx = 1.0;
		lc.gridx++;
		contentPane.add(lblUserInfo, lc);

		// next line
		lc.gridy++;
		lc.gridx = 0;
		contentPane.add(btnKeyblock[1], lc);
		lc.gridx++;
		contentPane.add(btnKeyblock[2], lc);
		lc.gridx++;
		contentPane.add(btnKeyblock[3], lc);
		
		lc.weightx = 1.0;
		lc.gridx++;
		contentPane.add(btnPay, lc);
		// next line
		lc.gridy++;
		lc.gridx = 0;
		contentPane.add(btnKeyblock[0], lc);
		lc.gridx++;
		contentPane.add(btnKeyblockDot, lc);
		lc.gridx++;
		contentPane.add(btnKeyblockClear, lc);
		
		
		frame.setSize(DISPLAY_SIZE_X, DISPLAY_SIZE_Y);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//frame.setUndecorated(true);
		//frame.setAlwaysOnTop(true);
		frame.pack();
		frame.setMinimumSize(new Dimension(DISPLAY_SIZE_X, DISPLAY_SIZE_Y));
		
		
		JFrame.setDefaultLookAndFeelDecorated(true);
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
	}
	
	protected String getCurrentAmountString() {
		return String.format("%.2f", context.getCurrentAmount());
	}

}
