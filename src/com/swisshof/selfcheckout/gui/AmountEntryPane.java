package com.swisshof.selfcheckout.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.swisshof.selfcheckout.SelfCheckoutContext;
import com.swisshof.selfcheckout.statemachine.MainStm.Events;

public class AmountEntryPane extends JPanel implements NumericBlock.IAmoutChanged
{

	protected final static Color COLOR_BG = Color.WHITE;
	private static final long serialVersionUID = 1L;
	
	protected JFormattedTextField txtPayAmount = null;
	protected JLabel lblUserInfo = null;
	protected JButton btnPay = null;
	protected NumericBlock numericBlock = null;
	
	protected Font fontPayAmountField = null;
	protected Font fontUserInfo = null;
	protected Font fontButtons = null;
	
	protected ImageIcon imageIcon = null;

	protected SelfCheckoutContext context = null;
	
	public AmountEntryPane(SelfCheckoutContext context)
	{
		super();
		this.context = context;
		
		Font baseFont = context.getResourceProvider().getFont();
		
		fontPayAmountField = baseFont.deriveFont(Font.PLAIN, 200);
		fontUserInfo = baseFont.deriveFont(Font.PLAIN, 20);		
		fontButtons = baseFont.deriveFont(Font.BOLD, 100);
		
		GridBagLayout gbl = new GridBagLayout();
		setLayout(gbl);
		
		setBackground(COLOR_BG);
		

		GridBagConstraints lc = new GridBagConstraints();
		lc.fill = GridBagConstraints.CENTER;

		txtPayAmount = new JFormattedTextField();
		txtPayAmount.setText(getCurrentAmountString());
		txtPayAmount.setFont(fontPayAmountField);
		txtPayAmount.setHorizontalAlignment(SwingConstants.CENTER);
		txtPayAmount.setBorder(BorderFactory.createEmptyBorder());
		
		JLabel lblCurrency = new JLabel("CHF");
		lblCurrency.setFont(fontUserInfo);

		lblUserInfo  = new JLabel(context.getString("wizard.enter_amount"));
		lblUserInfo.setFont(fontUserInfo);
		lblUserInfo.setHorizontalAlignment(SwingConstants.CENTER);

		numericBlock = new NumericBlock(context, this);

		btnPay = new JButton("Bezahlen"); 
		btnPay.setFont(fontButtons);
		btnPay.setEnabled(false);
		btnPay.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				AmountEntryPane.this.context.getMainStm().processEvent(Events.BTN_PAY);
			}
			
		});

		JLabel lblSwisshofLogo = new JLabel(imageIcon, JLabel.RIGHT);
		
		lc.weightx = 0.5;
		lc.weighty = 1.0;
		lc.gridx = 0;
		lc.gridy = 0;
		
		lc.gridwidth = 1;
		lc.gridheight = 5;
		lc.gridx = 0;
		
		
		add(numericBlock, lc);
		
		lc.gridx = 1;
		lc.weightx = 0.5;
		lc.gridheight = 1;
		add(lblSwisshofLogo);

	
		lc.gridy++;
		add(lblCurrency, lc);
		
		lc.gridy++;
		lc.fill = GridBagConstraints.BOTH;
		add(txtPayAmount, lc);

		lc.gridy++;
		add(lblUserInfo, lc);
		
		lc.gridy++;
		lc.fill = GridBagConstraints.CENTER;
		add(btnPay, lc);
	}

	public void reset() {
		numericBlock.reset();
	}
	
	public void enableBtnPay(boolean enable) {
		btnPay.setEnabled(enable);
	}
	
	public void enableKeyBlock(boolean enable) {
		numericBlock.enable(enable);
	}
	
	public void setInfoText(String infoText) {
		lblUserInfo.setText(infoText);
	}
	
	@Override
	public void amountEntryChanged(double newAmount) {
		context.setCurrentAmount(newAmount);
		
		// update amount on screen
		txtPayAmount.setText(getCurrentAmountString());
		btnPay.setEnabled(context.getCurrentAmount() > 0.0);

		context.getMainStm().processEvent(Events.AMOUNT_CHANGED);
	}
	
	protected String getCurrentAmountString() {
		return String.format("%.2f", context.getCurrentAmount());
	}
	

}
