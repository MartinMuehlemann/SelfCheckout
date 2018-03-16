package com.swisshof.selfcheckout.gui;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.swisshof.selfcheckout.IGui;
import com.swisshof.selfcheckout.SelfCheckoutContext;
import com.swisshof.selfcheckout.statemachine.MainStm.Events;

public class MainFrame implements IGui{
	
	private static Logger logger = Logger.getLogger("GUI");
	private final static String RESOURCE_BASE_PACKAGE = "res/";
	
	private enum AmountEnterState{
		ENTER_DIGIT,
		ENTER_FRACTION_10,
		ENTER_FRACTION_100,
		ENTER_FRACTION_END
	}
	
	
	protected static final int DISPLAY_SIZE_X = 1280;
	protected static final int DISPLAY_SIZE_Y = 800;
	
	
	protected final static Color COLOR_BG = Color.WHITE;
	
	protected JFormattedTextField txtPayAmount = null;
	protected JLabel lblUserInfo = null;
	protected Map<NumericBlockButton.Digit, JButton> mapBtnsKeyBlock = new HashMap<NumericBlockButton.Digit, JButton>();
	protected JButton btnKeyblockClear = null;
	protected JButton btnPay = null;
	protected Font fontNummericBlock = null;
	protected Font fontPayAmountField = null;
	protected Font fontUserInfo = null;
	protected Font fontButtons = null;
	
	protected ImageIcon imageIcon = null;
	
	protected ArrayList<NumericBlockButton.Digit> queueAmountEntry = new ArrayList<NumericBlockButton.Digit>();
	
	
	protected SelfCheckoutContext context = null;
	
	public MainFrame(SelfCheckoutContext context)
	{
		this.context = context;
		loadResources();
	}
	
	
	public void enableBtnPay(boolean enable) {
		btnPay.setEnabled(enable);
	}
	
	public void enableKeyBlock(boolean enable) {
		
		for (JButton btn : mapBtnsKeyBlock.values()) {
			btn.setEnabled(enable);
		}
		btnKeyblockClear.setEnabled(enable);
	}
	
	public void resetGui() {
		queueAmountEntry.clear();
		context.setCurrentAmount(getAmount());
		btnPay.setEnabled(context.getCurrentAmount() > 0.0);
		// update amount on screen
		txtPayAmount.setText(getCurrentAmountString());
	}
	
	public void setStatusText(String statusText) {
		lblUserInfo.setText(statusText);
	}
	
	protected double getAmount() {
		double amount = 0.0;
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
						queueAmountEntry.remove(queueAmountEntry.size() - 1);
						break;
						
					default:
							break;
				}
			} else if (digit.isDot()) {
				state = AmountEnterState.ENTER_FRACTION_10;
			}

		}
		return amount;
	}
	
	private void loadResources() {
		
		try {
			URL url = getClass().getClassLoader().getResource(RESOURCE_BASE_PACKAGE + "Logo_154x100.jpg");
			imageIcon = new ImageIcon(url);
		}catch (Exception e) {
			logger.severe("Exception while loading logo: " + e.getMessage());
		}

		Font font = null;
		try {
			InputStream is = getClass().getClassLoader().getResourceAsStream("res/FTB.ttf");
			font = Font.createFont(Font.TRUETYPE_FONT, is);
		}catch  (Exception e) {
			logger.severe("Exception while loading font: " + e.getMessage());
			font = new Font("Arial", Font.BOLD, 12);;
		}
		fontNummericBlock = font.deriveFont(Font.BOLD, 150);;
		fontPayAmountField = font.deriveFont(Font.BOLD, 200);
		fontUserInfo = font.deriveFont(Font.PLAIN, 20);		
		fontButtons = font.deriveFont(Font.BOLD, 100);
	}
	
	private void createKeyblock(JPanel panel) {
		
		GridBagLayout gl = new GridBagLayout();
		panel.setLayout(gl);
		
		for (NumericBlockButton.Digit digit : NumericBlockButton.Digit.values()) {
			NumericBlockButton btn = new NumericBlockButton(digit);
			btn.setFont(fontNummericBlock);
			btn.setPreferredSize(new Dimension(180, 180));
			btn.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					
					queueAmountEntry.add(((NumericBlockButton)e.getSource()).getAmount());
					
					double amount = getAmount();
					if(amount <= SelfCheckoutContext.MAX_AMOUNT) {
						context.setCurrentAmount(getAmount());
					
						// update amount on screen
						txtPayAmount.setText(getCurrentAmountString());
						
						context.getMainStm().processEvent(Events.AMOUNT_CHANGED);
					} else {
						queueAmountEntry.remove(queueAmountEntry.size() - 1);
					}
					
					btnPay.setEnabled(context.getCurrentAmount() > 0.0);
				}
			});
			mapBtnsKeyBlock.put(digit, btn);
		}
		

		
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
					
					context.getMainStm().processEvent(Events.AMOUNT_CHANGED);
					
					btnPay.setEnabled(context.getCurrentAmount() > 0.0);
				}
			}
			
		});
		
		GridBagConstraints lc = new GridBagConstraints();
		lc.fill = GridBagConstraints.BOTH;
		lc.weightx = 1.0;
		lc.weighty = 1.0;
		lc.gridx = 0;
		lc.gridy = 0;
		
		panel.add(mapBtnsKeyBlock.get(NumericBlockButton.Digit.SEVEN), lc);
		lc.gridx++;
		panel.add(mapBtnsKeyBlock.get(NumericBlockButton.Digit.EIGHT), lc);
		lc.gridx++;
		panel.add(mapBtnsKeyBlock.get(NumericBlockButton.Digit.NINE), lc);
		
		
		lc.gridx = 0;
		lc.gridy++;
		panel.add(mapBtnsKeyBlock.get(NumericBlockButton.Digit.FOUR), lc);
		lc.gridx++;
		panel.add(mapBtnsKeyBlock.get(NumericBlockButton.Digit.FIVE), lc);
		lc.gridx++;
		panel.add(mapBtnsKeyBlock.get(NumericBlockButton.Digit.SIX), lc);
		
		lc.gridx = 0;
		lc.gridy++;
		panel.add(mapBtnsKeyBlock.get(NumericBlockButton.Digit.ONE), lc);
		lc.gridx++;
		panel.add(mapBtnsKeyBlock.get(NumericBlockButton.Digit.TWO), lc);
		lc.gridx++;
		panel.add(mapBtnsKeyBlock.get(NumericBlockButton.Digit.THREE), lc);
		
		lc.gridx = 0;
		lc.gridy++;
		panel.add(mapBtnsKeyBlock.get(NumericBlockButton.Digit.ZERO), lc);
		lc.gridx++;
		panel.add(mapBtnsKeyBlock.get(NumericBlockButton.Digit.DOT), lc);
		lc.gridx++;
		panel.add(btnKeyblockClear, lc);
		
	}
	
	public void startGui()
	{
		//Application Window
		JFrame frame = new JFrame("SelfCheckoutTerminal");
		Container contentPane = frame.getContentPane();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gbl = new GridBagLayout();
		contentPane.setLayout(gbl);
		
		contentPane.setBackground(COLOR_BG);
		

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

		JPanel panelNumericBlock = new JPanel();
		
		createKeyblock(panelNumericBlock);
		
		btnPay = new JButton("Bezahlen"); 
		btnPay.setFont(fontButtons);
		btnPay.setEnabled(false);
		btnPay.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				context.getMainStm().processEvent(Events.BTN_PAY);
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
		
		
		contentPane.add(panelNumericBlock, lc);
		
		lc.gridx = 1;
		lc.weightx = 0.5;
		lc.gridheight = 1;
		contentPane.add(lblSwisshofLogo);

	
		lc.gridy++;
		contentPane.add(lblCurrency, lc);
		
		lc.gridy++;
		lc.fill = GridBagConstraints.BOTH;
		contentPane.add(txtPayAmount, lc);

		lc.gridy++;
		contentPane.add(lblUserInfo, lc);
		
		lc.gridy++;
		lc.fill = GridBagConstraints.CENTER;
		contentPane.add(btnPay, lc);
	
		
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
