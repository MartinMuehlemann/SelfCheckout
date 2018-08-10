package com.swisshof.selfcheckout.gui;

import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

import com.swisshof.selfcheckout.Constants;
import com.swisshof.selfcheckout.SelfCheckoutContext;


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
			super(language);
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
		GridBagLayout gl = new GridBagLayout();
		setLayout(gl);
		
		String[] locales = {"de_CH", "en_US", "fr_FR"};
				
		for (String locale : locales) {
			String[] arrLocale = locale.split("_");
			JButton btn = new LanguageButton(arrLocale[0], arrLocale[1]);
			
			btn.setBackground(Constants.COLOR_BG);
			btn.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					LanguageButton btn = ((LanguageButton)e.getSource());
					languageChangedListener.languageChanged(btn.getLanguage(), btn.getCountry());
				}
			});
			add(btn);
		}


	}
	
	
	private static final long serialVersionUID = 1L;

}
