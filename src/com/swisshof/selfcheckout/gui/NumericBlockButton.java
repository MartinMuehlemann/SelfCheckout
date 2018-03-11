package com.swisshof.selfcheckout.gui;

import javax.swing.JButton;

class NumericBlockButton extends JButton
{
	protected Integer amount;
	
	public NumericBlockButton(Integer amount) {
		super(Integer.toString(amount));
		this.amount = amount;
	}

	public Integer getAmount() {
		return amount;
	}

	private static final long serialVersionUID = 1L;
	
}