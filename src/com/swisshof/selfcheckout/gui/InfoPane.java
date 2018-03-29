package com.swisshof.selfcheckout.gui;

import java.awt.Color;
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
		INFO_ERROR
	}
	
	protected SelfCheckoutContext context = null;
	protected JLabel lblIcon;
	protected JLabel lblInfoText;
	protected JButton button;
	
	
	
	
	protected InformationType type = InformationType.INFO_PROGRESS;
	
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
		
		button = new JButton("OK");
		button.setFont(fontButton);
		button.setPreferredSize(new Dimension(300, 120));
		button.setBackground(Constants.COLOR_BG);
		//button.setForeground(Constants.COLOR_SUCCESS);

		
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				switch(type) {
				case INFO_ERROR:
					InfoPane.this.context.getMainStm().processEvent(Events.BTN_CONFIRM);
					break;
				case INFO_PROGRESS:
					InfoPane.this.context.getMainStm().processEvent(Events.BTN_ABORT);
					break;
				case INFO_SUCCESS:
					InfoPane.this.context.getMainStm().processEvent(Events.BTN_CONFIRM);
					break;
				default:
					break;
				
				}
			}
		});
		
		GridBagConstraints lc = new GridBagConstraints();
		lc.gridx = 0;
		lc.gridy = 0;

		lc.anchor = GridBagConstraints.CENTER;
		lc.weighty = 0.2;
		add(lblIcon, lc);
		
		lc.gridy++;
		lc.weighty = 0.3;
		lc.fill = GridBagConstraints.BOTH;
		add(lblInfoText, lc);
		
		lc.gridy++;
		lc.weighty = 0.3;
		lc.fill = GridBagConstraints.NONE;
		add(button, lc);
		
	}
		
	public void setInfoText(InformationType type, String infoText) 
	{
		this.type = type;
				
		switch(type) {
		case INFO_ERROR:
			lblIcon.setIcon(context.getResourceProvider().getImage(ImageIdentifier.Failure));
			button.setText(context.getString("infopane.btn.ok"));
			lblInfoText.setForeground(Constants.COLOR_FAILURE);
			break;
		case INFO_PROGRESS:
			lblIcon.setIcon(context.getResourceProvider().getImage(ImageIdentifier.Hourglass));
			button.setText(context.getString("infopane.btn.abort"));
			lblInfoText.setForeground(Constants.COLOR_PROGRESS);
			break;
		case INFO_SUCCESS:
			lblIcon.setIcon(context.getResourceProvider().getImage(ImageIdentifier.Success));
			button.setText(context.getString("infopane.btn.ok"));
			lblInfoText.setForeground(Constants.COLOR_SUCCESS);
			break;
		default:
			break;
		
		}
		
		lblInfoText.setText("<html><center>" + infoText + "</center></html>");
	}
	
	public void enableBtnConfirm(boolean enable)
	{
		button.setEnabled(enable);
	}

	private static final long serialVersionUID = 1L;

}
