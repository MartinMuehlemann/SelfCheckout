package com.swisshof.selfcheckout.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import com.swisshof.selfcheckout.SelfCheckoutContext;
import com.swisshof.selfcheckout.Constants;
import com.swisshof.selfcheckout.IResourceProvider.ImageIdentifier;

public class SystemInactivePane extends JPanel
{

	private static final long serialVersionUID = 1L;
	protected SelfCheckoutContext context = null;
	
	public SystemInactivePane(SelfCheckoutContext context)
	{
		super();
		this.context = context;
		
		GridBagLayout gbl = new GridBagLayout();
		setLayout(gbl);
		
		setBackground(Constants.COLOR_BG);

		JLabel lblSwisshofLogo = new JLabel(context.getResourceProvider().getImage(ImageIdentifier.SwisshofLogoBig));
		
		
		GridBagConstraints lc = new GridBagConstraints();
		lc.gridx = 0;
		lc.gridy = 0;
		lc.fill = GridBagConstraints.BOTH;
		lc.anchor = GridBagConstraints.CENTER;
		add(lblSwisshofLogo, lc);

	}


}
