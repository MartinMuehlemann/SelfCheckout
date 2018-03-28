package com.swisshof.selfcheckout.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

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
		
		GridBagLayout gbl = new GridBagLayout();
		setLayout(gbl);
		
		lblIcon = new JLabel("Icon");
		lblInfoText = new JLabel();
		lblInfoText.setText("infoT text");
		button = new JButton("OK");
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
		lc.fill = GridBagConstraints.BOTH;
		lc.gridx = 0;
		lc.gridy = 0;

		
		add(lblIcon, lc);
		
		lc.gridy++;
		add(lblInfoText, lc);
		
		lc.gridy++;
		add(button, lc);
		
	}
		
	public void setInfoText(InformationType type, String infoText) 
	{
		switch(type) {
		case INFO_ERROR:
			lblIcon.setText("Error...");
			button.setText(context.getString("infopane.btn.ok"));
			break;
		case INFO_PROGRESS:
			lblIcon.setText("Progress...");
			button.setText(context.getString("infopane.btn.abort"));

			break;
		case INFO_SUCCESS:
			lblIcon.setText("Success...");
			button.setText(context.getString("infopane.btn.ok"));
			break;
		default:
			break;
		
		}
		
		
		lblInfoText.setText(infoText);
	}
	
	public void enableBtnConfirm(boolean enable)
	{
		button.setEnabled(enable);
	}
	
	private static final long serialVersionUID = 1L;

}
