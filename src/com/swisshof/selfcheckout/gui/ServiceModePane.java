package com.swisshof.selfcheckout.gui;

import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.swisshof.selfcheckout.SelfCheckoutContext;
import com.swisshof.selfcheckout.IResourceProvider.FontIdentifier;
import com.swisshof.selfcheckout.statemachine.MainStm.Events;

public class ServiceModePane extends JPanel {

	protected SelfCheckoutContext context = null;
	
	protected JLabel lblResult = null;
	
	private JButton addButton(String name, ActionListener al) {
		Font baseFontRegular = context.getResourceProvider().getFont(FontIdentifier.FrutigerCondensed);	
		Font fontButtons = baseFontRegular.deriveFont(Font.PLAIN, 40);
		JButton btn = new JButton(name);
		btn.setFont(fontButtons);
		btn.addActionListener(al);
		return btn;
	}
	
	public ServiceModePane(SelfCheckoutContext context)
	{
		this.context = context;

		GridBagLayout gbl = new GridBagLayout();
		setLayout(gbl);
				
		

		GridBagConstraints lc = new GridBagConstraints();
		int numCtrls = 0;
		lc.weightx = 0.3;
		lc.gridx = 0;
		lc.gridy = 0;
		lc.anchor = GridBagConstraints.WEST;
		lc.fill = GridBagConstraints.HORIZONTAL;
		add(addButton("Connect", new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				context.getTerminal().connect();
			}
		}), lc);
		
		lc.gridy++;
		numCtrls++;
		add(addButton("Balance", new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				context.getTerminal().startBalance();
			}
		}), lc);
		
		lc.gridy++;
		numCtrls++;
		add(addButton("Disconnect", new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				context.getTerminal().disconnect();
			}
		}), lc);
		numCtrls++;
		
		lc.gridx = 1;
		lc.gridheight = numCtrls;
		lc.gridy = 0;
		lc.anchor = GridBagConstraints.EAST;
		lc.fill = GridBagConstraints.BOTH;
		lc.weightx = 0.7;
		lblResult = new JLabel("n/a");
		
		add(lblResult, lc);
		
		lc.gridx = 0;		
		lc.gridy = numCtrls;
		lc.gridwidth = 2;
		
		add(addButton("Exit",new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				context.getMainStm().processEvent(Events.BTN_EXIT);
				
			}
		}), lc);
	}
	
	public void notifyTerminalRequestDone(boolean success, String details) {
		lblResult.setText("<html><p>" + details + "</p></html>");
	}
	
	private static final long serialVersionUID = 1L;
}
