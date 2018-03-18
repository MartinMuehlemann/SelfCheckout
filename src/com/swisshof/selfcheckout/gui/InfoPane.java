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

	protected SelfCheckoutContext context = null;
	protected JLabel lblIcon;
	protected JLabel lblInfoText;
	protected JButton btnOK;
	
	public InfoPane(SelfCheckoutContext context)
	{
		super();
		this.context = context;
		
		GridBagLayout gbl = new GridBagLayout();
		setLayout(gbl);
		
		lblIcon = new JLabel("Icon");
		lblInfoText = new JLabel();
		lblInfoText.setText("infoT text");
		btnOK = new JButton("OK");
		btnOK.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				context.getMainStm().processEvent(Events.BTN_CONFIRM);
				
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
		add(btnOK, lc);
		
	}
	
	
	private static final long serialVersionUID = 1L;

}
