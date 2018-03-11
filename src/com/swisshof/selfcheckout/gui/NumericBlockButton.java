package com.swisshof.selfcheckout.gui;

import javax.swing.JButton;

class NumericBlockButton extends JButton
{
	protected int amount;
	
	public NumericBlockButton(int amount) {
		super(Integer.toString(amount));
		this.amount = amount;
	}

	public int getAmount() {
		return amount;
	}

	private static final long serialVersionUID = 1L;
	
}