package com.swisshof.selfcheckout;

import java.awt.Font;

import javax.swing.ImageIcon;

public interface IResourceProvider {

	public enum FontIdentifier {
		FrutigerBold,
		FrutigerCondensed
	}
	
	public enum ImageIdentifier {
		SwisshofLogo,
		Success,
		Hourglass,
		Failure
	}
	
	
	public ImageIcon getImage(ImageIdentifier id);

	public Font getFont(FontIdentifier id);

}