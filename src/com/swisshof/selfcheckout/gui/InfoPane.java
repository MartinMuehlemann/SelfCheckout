package com.swisshof.selfcheckout.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.swisshof.selfcheckout.Constants;
import com.swisshof.selfcheckout.IResourceProvider.FontIdentifier;
import com.swisshof.selfcheckout.IResourceProvider.ImageIdentifier;
import com.swisshof.selfcheckout.SelfCheckoutContext;
import com.swisshof.selfcheckout.statemachine.MainStm.Events;

public class InfoPane extends JPanel {

	public enum InformationType {
		INFO_PROGRESS,
		INFO_SUCCESS,
		INFO_WARNING,
		INFO_ERROR
	}
	
	public enum DisplayedButtons {
		BTN_OK,
		BTN_ABORT,
		BTN_YES_NO,
		BTN_NONE
	}
	
	protected SelfCheckoutContext context = null;
	protected JLabel lblIcon;
	protected JLabel lblInfoText;
	protected JButton btn1;
	protected JButton btn2;	

	
	
	protected InformationType type = InformationType.INFO_PROGRESS;
	protected DisplayedButtons displayedButtons = DisplayedButtons.BTN_NONE;
	
	public InfoPane(SelfCheckoutContext context)
	{
		super();
		this.context = context;
		
		Font baseFontRegular = context.getResourceProvider().getFont(FontIdentifier.FrutigerCondensed);	
		Font baseFontBold = context.getResourceProvider().getFont(FontIdentifier.FrutigerBold);	

		
		Font fontInfoText = baseFontRegular.deriveFont(Font.PLAIN, 36);		
		Font fontButton = baseFontBold.deriveFont(Font.PLAIN, 36);
		
		GridBagLayout gbl = new GridBagLayout();
		setLayout(gbl);
		
		setBackground(Constants.COLOR_BG);
		
		lblIcon = new JLabel();
		lblInfoText = new JLabel();
		lblInfoText.setText("info text");
		lblInfoText.setFont(fontInfoText);
		btn1 = new JButton("Btn1");
		btn1.setFont(fontButton);
		btn1.setPreferredSize(new Dimension(300, 120));
		btn1.setBackground(Constants.COLOR_BG);
	
		btn1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				switch(displayedButtons) {
					case BTN_OK:
						InfoPane.this.context.getMainStm().processEvent(Events.BTN_OK);
						break;
					case BTN_ABORT:
						InfoPane.this.context.getMainStm().processEvent(Events.BTN_ABORT);
						break;
						
					case BTN_YES_NO:
						InfoPane.this.context.getMainStm().processEvent(Events.BTN_YES);
						break;
						
					case BTN_NONE:
						break;
				}
			}
		});
		
		btn2 = new JButton("Btn2");
		btn2.setFont(fontButton);
		btn2.setPreferredSize(new Dimension(300, 120));
		btn2.setBackground(Constants.COLOR_BG);
		btn2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				switch(displayedButtons) {
					case BTN_OK:
						break;
						
					case BTN_ABORT:
						break;
						
					case BTN_YES_NO:
						InfoPane.this.context.getMainStm().processEvent(Events.BTN_NO);
						break;
						
					case BTN_NONE:
						break;
			}
			}
		});	
		
		GridBagConstraints lc = new GridBagConstraints();
		lc.gridx = 0;
		lc.gridy = 0;
		lc.gridwidth = 2;

		lc.anchor = GridBagConstraints.CENTER;
		lc.weighty = 0.2;
		add(lblIcon, lc);
		
		lc.gridy++;
		lc.weighty = 0.3;
		lc.fill = GridBagConstraints.BOTH;
		add(lblInfoText, lc);
		
		//placeButtons();
	}
		
	public void setInfoText(InformationType type, DisplayedButtons buttons, String infoText) 
	{
		this.type = type;
		this.displayedButtons = buttons;
				
		switch(type) {
		case INFO_ERROR:
			lblIcon.setIcon(context.getResourceProvider().getImage(ImageIdentifier.Failure));
			lblInfoText.setForeground(Constants.COLOR_FAILURE);
			break;
			
		case INFO_PROGRESS:
			lblIcon.setIcon(context.getResourceProvider().getImage(ImageIdentifier.Hourglass));
			lblInfoText.setForeground(Constants.COLOR_PROGRESS);
			break;
			
		case INFO_WARNING:
			lblIcon.setIcon(context.getResourceProvider().getImage(ImageIdentifier.Warning));
			lblInfoText.setForeground(Constants.COLOR_WARNING);
			break;
			
		case INFO_SUCCESS:
			lblIcon.setIcon(context.getResourceProvider().getImage(ImageIdentifier.Success));
			lblInfoText.setForeground(Constants.COLOR_SUCCESS);
			break;
		default:
			break;
		
		}
		
		lblInfoText.setText("<html><center>" + infoText + "</center></html>");
		
		switch(displayedButtons) {
			case BTN_ABORT:
				btn1.setText(context.getString("infopane.btn.abort"));
				break;
			case BTN_NONE:
				btn1.setVisible(false);
				btn2.setVisible(false);
				break;
				
			case BTN_OK:
				btn1.setText(context.getString("infopane.btn.ok"));
				btn1.setVisible(true);
				btn2.setVisible(false);
				break;
			case BTN_YES_NO:
				btn1.setText(context.getString("infopane.btn.yes"));
				btn2.setText(context.getString("infopane.btn.no"));
				btn1.setVisible(true);
				btn2.setVisible(true);
				break;
		
		}
		
		placeButtons();
	}
	
	public void enableBtnConfirm(boolean enable)
	{
		btn1.setEnabled(enable);
		btn2.setEnabled(enable);
	}

	private void placeButtons()
	{
		remove(btn1);
		remove(btn2);
		
		GridBagConstraints lc = new GridBagConstraints();
		lc.anchor = GridBagConstraints.CENTER;	
		lc.gridy = 2;
		lc.gridx = 0;
		lc.weighty = 0.3;
		lc.fill = GridBagConstraints.NONE;
		
		switch(displayedButtons)
		{
			case BTN_NONE:
				break;
				
			case BTN_ABORT:
			case BTN_OK:
				lc.gridwidth = 2;
				add(btn1, lc);
				break;
				
			case BTN_YES_NO:
				lc.gridwidth = 1;
				add(btn1, lc);
				lc.gridx++;
				add(btn2, lc);
				break;
		}
		
	}
	
	private static final long serialVersionUID = 1L;

}
