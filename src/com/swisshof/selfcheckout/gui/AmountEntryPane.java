package com.swisshof.selfcheckout.gui;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.swisshof.selfcheckout.SelfCheckoutContext;
import com.swisshof.selfcheckout.Constants;
import com.swisshof.selfcheckout.IResourceProvider.FontIdentifier;
import com.swisshof.selfcheckout.IResourceProvider.ImageIdentifier;
import com.swisshof.selfcheckout.statemachine.MainStm.Events;

public class AmountEntryPane extends JPanel implements NumericBlock.IAmoutChanged
{

	private static final long serialVersionUID = 1L;
	
	protected JFormattedTextField txtPayAmount = null;
	protected JLabel lblUserInfo = null;
	protected JButton btnPay = null;
	protected NumericBlock numericBlock = null;
	
	protected Font fontPayAmountField = null;
	protected Font fontUserInfo = null;
	protected Font fontCurrency = null;
	protected Font fontButtons = null;

	protected SelfCheckoutContext context = null;
	
	public AmountEntryPane(SelfCheckoutContext context)
	{
		super();
		this.context = context;
		
		Font baseFontBold = context.getResourceProvider().getFont(FontIdentifier.FrutigerBold);
		Font baseFontRegular = context.getResourceProvider().getFont(FontIdentifier.FrutigerCondensed);	
		
		fontPayAmountField = baseFontRegular.deriveFont(Font.PLAIN, 200);
		fontUserInfo = baseFontRegular.deriveFont(Font.PLAIN, 36);
		fontCurrency = baseFontRegular.deriveFont(Font.PLAIN, 50);		
		fontButtons = baseFontBold.deriveFont(Font.BOLD, 100);
		
		GridBagLayout gbl = new GridBagLayout();
		setLayout(gbl);
		
		setBackground(Constants.COLOR_BG);

		txtPayAmount = new JFormattedTextField();
		txtPayAmount.setText(getCurrentAmountString());
		txtPayAmount.setFont(fontPayAmountField);
		txtPayAmount.setHorizontalAlignment(SwingConstants.RIGHT);
		txtPayAmount.setBorder(BorderFactory.createEmptyBorder());
		
		JLabel lblCurrency = new JLabel("CHF");
		lblCurrency.setFont(fontCurrency);
		lblCurrency.setHorizontalAlignment(SwingConstants.RIGHT);

		lblUserInfo  = new JLabel(getWizardString("wizard.enter_amount"));
		lblUserInfo.setFont(fontUserInfo);
		lblUserInfo.setHorizontalAlignment(SwingConstants.CENTER);

		numericBlock = new NumericBlock(context, this);

		btnPay = new JButton(context.getString("btn.pay")); 
		btnPay.setFont(fontButtons);
		btnPay.setEnabled(false);
		btnPay.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				AmountEntryPane.this.context.getMainStm().processEvent(Events.BTN_PAY);
			}
			
		});

		JLabel lblSwisshofLogo = new JLabel(context.getResourceProvider().getImage(ImageIdentifier.SwisshofLogo));
		
		
		GridBagConstraints lc = new GridBagConstraints();
		lc.weightx = 0.5;
		lc.weighty = 0.8;
		lc.gridx = 0;
		lc.gridy = 0;
		
		lc.gridwidth = 1;
		lc.gridheight = 4;
		lc.gridx = 0;

		add(numericBlock, lc);
		
		lc.gridwidth = 2;
		lc.gridx = 1;
		lc.weightx = 2.0;
		lc.weighty = 0.1;
		lc.gridheight = 1;
		lc.anchor = GridBagConstraints.NORTH;
		add(lblSwisshofLogo, lc);
	
		lc.gridy++;
		lc.weighty = 0.2;
		lc.weightx = 0.5;
		lc.gridwidth = 1;
		lc.ipadx = 20;
		lc.anchor = GridBagConstraints.WEST;
		lc.fill = GridBagConstraints.NONE;
		add(lblCurrency, lc);

		lc.gridx = 2;
		lc.weightx = 1.5;
		lc.anchor = GridBagConstraints.EAST;
		lc.fill = GridBagConstraints.HORIZONTAL;
		add(txtPayAmount, lc);

		lc.gridx = 1;
		lc.gridwidth = 2;
		lc.weightx = 2.0;
		lc.weighty = 1.0;
		lc.gridy++;
		lc.fill = GridBagConstraints.BOTH;
		lc.anchor = GridBagConstraints.CENTER;
		add(lblUserInfo, lc);
		
		lc.gridy++;
		lc.fill = GridBagConstraints.NONE;
		lc.anchor = GridBagConstraints.CENTER;
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
	
	protected String getWizardString(String key) {
		return "<html><center>" + context.getString(key) + "</center></html>";
	}

}
