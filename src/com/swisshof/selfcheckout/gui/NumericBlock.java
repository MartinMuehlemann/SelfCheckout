package com.swisshof.selfcheckout.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.swisshof.selfcheckout.IResourceProvider.FontIdentifier;
import com.swisshof.selfcheckout.Constants;
import com.swisshof.selfcheckout.SelfCheckoutContext;

public class NumericBlock extends JPanel {

	public interface IAmoutChanged 
	{
		void amountEntryChanged(double newAmount);
	}
	
	
	private enum AmountEnterState{
		ENTER_DIGIT,
		ENTER_FRACTION_10,
		ENTER_FRACTION_100,
		ENTER_FRACTION_END
	}
	
	

	protected ArrayList<NumericBlockButton.Digit> queueAmountEntry = new ArrayList<NumericBlockButton.Digit>();
	protected Map<NumericBlockButton.Digit, JButton> mapBtnsKeyBlock = new HashMap<NumericBlockButton.Digit, JButton>();
	
	protected IAmoutChanged amountChangedListener = null;
	
	
	public NumericBlock(SelfCheckoutContext context, IAmoutChanged amountChangedListener) {
		super();
		this.amountChangedListener = amountChangedListener;
				
		GridBagLayout gl = new GridBagLayout();
		setLayout(gl);
		
		for (NumericBlockButton.Digit digit : NumericBlockButton.Digit.values()) {
			NumericBlockButton btn = new NumericBlockButton(digit);
			Font baseFont = context.getResourceProvider().getFont(FontIdentifier.FrutigerBold);
			btn.setFont(baseFont.deriveFont(Font.BOLD, 150));
			btn.setPreferredSize(new Dimension(180, 180));
			btn.setBackground(Constants.COLOR_BG);
			btn.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					NumericBlockButton.Digit digit = ((NumericBlockButton)e.getSource()).getAmount();
					processDigit(digit);
				}
			});
			mapBtnsKeyBlock.put(digit, btn);
		}


		GridBagConstraints lc = new GridBagConstraints();
		lc.fill = GridBagConstraints.BOTH;
		lc.weightx = 1.0;
		lc.weighty = 1.0;
		lc.gridx = 0;
		lc.gridy = 0;
		
		add(mapBtnsKeyBlock.get(NumericBlockButton.Digit.SEVEN), lc);
		lc.gridx++;
		add(mapBtnsKeyBlock.get(NumericBlockButton.Digit.EIGHT), lc);
		lc.gridx++;
		add(mapBtnsKeyBlock.get(NumericBlockButton.Digit.NINE), lc);
		
		
		lc.gridx = 0;
		lc.gridy++;
		add(mapBtnsKeyBlock.get(NumericBlockButton.Digit.FOUR), lc);
		lc.gridx++;
		add(mapBtnsKeyBlock.get(NumericBlockButton.Digit.FIVE), lc);
		lc.gridx++;
		add(mapBtnsKeyBlock.get(NumericBlockButton.Digit.SIX), lc);
		
		lc.gridx = 0;
		lc.gridy++;
		add(mapBtnsKeyBlock.get(NumericBlockButton.Digit.ONE), lc);
		lc.gridx++;
		add(mapBtnsKeyBlock.get(NumericBlockButton.Digit.TWO), lc);
		lc.gridx++;
		add(mapBtnsKeyBlock.get(NumericBlockButton.Digit.THREE), lc);
		
		lc.gridx = 0;
		lc.gridy++;
		add(mapBtnsKeyBlock.get(NumericBlockButton.Digit.ZERO), lc);
		lc.gridx++;
		add(mapBtnsKeyBlock.get(NumericBlockButton.Digit.DOT), lc);
		lc.gridx++;
		add(mapBtnsKeyBlock.get(NumericBlockButton.Digit.CLEAR), lc);

	}

	public void reset() {
		queueAmountEntry.clear();
		amountChangedListener.amountEntryChanged(0.0);
	}
	
	public void enable(boolean enable) {
		
		for (JButton btn : mapBtnsKeyBlock.values()) {
			btn.setEnabled(enable);
		}
	}
	
	public double getAmount() {
		double amount = 0.0;
		NumericBlockButton.Digit digitToRemove = null;
		AmountEnterState state = AmountEnterState.ENTER_DIGIT;
		for (NumericBlockButton.Digit digit : queueAmountEntry) {
			if (digit.isDigit()) {
				switch(state) {
					case ENTER_DIGIT:
						amount *= 10.0;
						amount += digit.getIntegerValue();
						break;
						
					case ENTER_FRACTION_10:
						amount += digit.getIntegerValue() / 10.0;
						state = AmountEnterState.ENTER_FRACTION_100;
						break;
						
					case ENTER_FRACTION_100:
						amount += digit.getIntegerValue() / 100.0;
						state = AmountEnterState.ENTER_FRACTION_END;
						break;
						
					case ENTER_FRACTION_END:
						digitToRemove = digit;
						break;
						
					default:
							break;
				}
			} else if (digit.isDot()) {
				state = AmountEnterState.ENTER_FRACTION_10;
			} else if (digit.isClear()) {
				digitToRemove = digit;
			}
		}
		
		if (digitToRemove != null) {
			queueAmountEntry.remove(digitToRemove);
		}
		
		return Math.round(amount * 100.0) / 100.0;
	}
	
	protected void processDigit(NumericBlockButton.Digit digit)
	{
;
		if (digit.isClear()) {
			if (queueAmountEntry.isEmpty() != true) {
				queueAmountEntry.remove(queueAmountEntry.size() - 1);
				NumericBlock.this.amountChangedListener.amountEntryChanged(getAmount());
			}
		} else {
			queueAmountEntry.add(digit);
			
			queueAmountEntry = checkEntry();
			
			double amount = getAmount();
			if(amount <= SelfCheckoutContext.MAX_AMOUNT) {
				NumericBlock.this.amountChangedListener.amountEntryChanged(amount);
			} else {
				queueAmountEntry.remove(queueAmountEntry.size() - 1);
			}
		}

	}
	
	
	public ArrayList<NumericBlockButton.Digit> checkEntry() {
		ArrayList<NumericBlockButton.Digit> ret = new ArrayList<NumericBlockButton.Digit>();

		AmountEnterState state = AmountEnterState.ENTER_DIGIT;
		for (NumericBlockButton.Digit digit : queueAmountEntry) {
			if (digit.isDigit()) {
				switch(state) {
					case ENTER_DIGIT:
						ret.add(digit);
						break;
						
					case ENTER_FRACTION_10:
						ret.add(digit);
						state = AmountEnterState.ENTER_FRACTION_100;
						break;
						
					case ENTER_FRACTION_100:
						if ((digit.getIntegerValue() == 0) ||
								(digit.getIntegerValue() == 5))
						{
							ret.add(digit);
						}
						state = AmountEnterState.ENTER_FRACTION_END;
						break;
						
					case ENTER_FRACTION_END:
						// do not add it to the list of digits
						break;
						
					default:
							break;
				}
			} else if (digit.isDot()) {
				switch(state) {
					case ENTER_DIGIT:
						state = AmountEnterState.ENTER_FRACTION_10;
						ret.add(digit);
						break;
						
					case ENTER_FRACTION_10:
					case ENTER_FRACTION_100:
					case ENTER_FRACTION_END:
					default:
						// skip second dot
						break;
				}

			} else if (digit.isClear()) {
				// remove the last digit
				if (ret.size() > 0) {
					ret.remove(ret.size() - 1);
				}
			}
		}

		return ret;
	}

	
	private static final long serialVersionUID = 1L;
	
}
