package com.swisshof.selfcheckout;

import java.awt.Font;

import javax.swing.ImageIcon;

public interface IResourceProvider {

	public enum FontName {
		FRUTIGER_BOLD,
		FRUTIGER_CONDENSED
	}
	
	
	ImageIcon getSwisshofLogo();

	Font getFont(FontName name);

}