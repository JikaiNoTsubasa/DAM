package com.dbiservices.dam.utils;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Icons {

	public static ImageIcon logoIcon, favIcon, goIcon;
	
	static {
		try {
			logoIcon = new ImageIcon(ImageIO.read(new File(Const.ICONS_LOCATION+"/DamIcon.png")));
			favIcon = new ImageIcon(ImageIO.read(new File(Const.ICONS_LOCATION+"/o_fav.png")));
			goIcon = new ImageIcon(ImageIO.read(new File(Const.ICONS_LOCATION+"/o_go.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
