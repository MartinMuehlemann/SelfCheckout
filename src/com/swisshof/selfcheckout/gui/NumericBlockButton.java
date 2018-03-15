package com.swisshof.selfcheckout.gui;

import javax.swing.JButton;

class NumericBlockButton extends JButton
{
	
	public enum Digit {

		ZERO (0),
		ONE	(1),
		TWO (2),
		THREE (3),
		FOUR (4),
		FIVE (5),
		SIX (6),
		SEVEN (7),
		EIGHT (8),
		NINE (9),
		DOT(-1);
		
		private Integer value;
		
		Digit(Integer value) {
			this.value = value;
		}
		
		public boolean isDigit() {
			return value >= 0;
		}
		
		public boolean isDot() {
			return value == -1;
		}
		
		public Integer getIntegerValue()
		{
			if(isDigit()) {
				return value;
			} else {
				throw new java.lang.UnsupportedOperationException();
			}
		}

		@Override
		public String toString() {
			if (isDigit()) {
				return Integer.toString(value);
			} else if (isDot()) {
				return ".";
			} else {
				return "?";
			}
		}
	}
	
	protected Digit digit;
	
	public NumericBlockButton(Digit digit) {
		super(digit.toString());
		this.digit = digit;
	}

	public Digit getAmount() {
		return digit;
	}

	private static final long serialVersionUID = 1L;
	
}