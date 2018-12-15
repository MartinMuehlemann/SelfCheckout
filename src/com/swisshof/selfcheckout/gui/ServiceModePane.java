package com.swisshof.selfcheckout.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

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

		Font baseFontRegular = context.getResourceProvider().getFont(FontIdentifier.FrutigerCondensed);	
		
		GridBagLayout gbl = new GridBagLayout();
		setLayout(gbl);
				
		

		GridBagConstraints lc = new GridBagConstraints();
		
		JLabel lblTitle= new JLabel("Service Mode");
		lc.gridx = 0;
		lc.gridy = 0;
		lc.gridwidth = 2;
		lc.anchor = GridBagConstraints.CENTER;
		lc.fill = GridBagConstraints.BOTH;
		lblTitle.setFont(baseFontRegular.deriveFont(Font.PLAIN, 100));
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		add(lblTitle, lc);
		
		int numCtrls = 0;
		lc.weightx = 0.3;
		lc.gridx = 0;
		lc.gridy = 1;
		lc.gridwidth = 1;
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
		lc.gridy++;
		numCtrls++;
		add(addButton("Activate", new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				context.getTerminal().activate();
			}
		}), lc);
		lc.gridy++;
		numCtrls++;
		add(addButton("Deactivate", new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				context.getTerminal().deactivate();
			}
		}), lc);
		numCtrls++;
		
		lc.gridx = 1;
		lc.gridheight = numCtrls;
		lc.gridy = 1;
		lc.anchor = GridBagConstraints.EAST;
		lc.fill = GridBagConstraints.BOTH;
		lc.weightx = 0.7;
		lc.insets = new Insets(10, 10, 10, 10);
		lblResult = new JLabel("n/a");
		lblResult.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		JScrollPane scroller = new JScrollPane(lblResult, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		add(scroller, lc);
		
		lc.gridx = 0;		
		lc.gridy = numCtrls + 1;
		lc.gridwidth = 2;
		lc.fill = GridBagConstraints.VERTICAL;
		lc.anchor = GridBagConstraints.CENTER;
		add(addButton("Exit",new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				context.getMainStm().processEvent(Events.BTN_EXIT);
				
			}
		}), lc);
	}
	
	public void notifyTerminalRequestDone(boolean success, String details) {

		String strLabel = "<html><p>";
		for (String l : details.split("\n"))
		{
			strLabel += l + "<br>";
		}
		
		strLabel += "</p></html>";
		lblResult.setText(strLabel);
	}
	
	public void reset() {
		lblResult.setText("n/a");
	}
	
	private static final long serialVersionUID = 1L;
}
