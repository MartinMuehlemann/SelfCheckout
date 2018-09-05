package com.swisshof.selfcheckout.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

import com.swisshof.selfcheckout.Constants;
import com.swisshof.selfcheckout.IResourceProvider;
import com.swisshof.selfcheckout.SelfCheckoutContext;

import javafx.scene.layout.Border;


public class LanguageSelector extends JPanel {

	public interface ILanguageChanged 
	{
		void languageChanged(String language, String country);
	}
	
	protected class LanguageButton extends JButton
	{
		private String language;
		private String country;
		
		public LanguageButton(String language, String country)
		{
			super();
			this.language = language;
			this.country = country;
		}
	
		public String getLanguage() {
			return language;
		}

		public String getCountry() {
			return country;
		}



		private static final long serialVersionUID = 1L;
		
	}
	
	protected ILanguageChanged languageChangedListener = null;

	
	
	public LanguageSelector(SelfCheckoutContext context, ILanguageChanged languageChangedListener)
	{
		GridBagLayout gbl = new GridBagLayout();
		setLayout(gbl);
		
		GridBagConstraints lc = new GridBagConstraints();
		lc.anchor = GridBagConstraints.CENTER;
		lc.gridy  = 0;
		
		HashMap<String, IResourceProvider.ImageIdentifier> mapLanguages = new HashMap<String, IResourceProvider.ImageIdentifier>();
		mapLanguages.put("de_CH", IResourceProvider.ImageIdentifier.German);
		mapLanguages.put("en_US", IResourceProvider.ImageIdentifier.English);
		mapLanguages.put("fr_FR", IResourceProvider.ImageIdentifier.French);
				
		for (String locale : mapLanguages.keySet()) {
			String[] arrLocale = locale.split("_");
			JButton btn = new LanguageButton(arrLocale[0], arrLocale[1]);
			
			btn.setBackground(Constants.COLOR_BG);
			btn.setIcon(context.getResourceProvider().getImage(mapLanguages.get(locale)));
			btn.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
			btn.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					LanguageButton btn = ((LanguageButton)e.getSource());
					languageChangedListener.languageChanged(btn.getLanguage(), btn.getCountry());
				}
			});
			

			add(btn, lc);
			lc.gridx++;
		}


	}
	
	
	private static final long serialVersionUID = 1L;

}
