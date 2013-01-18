package com.oro.legendoftest.view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;

public enum HUD
{	
	INSTANCE;
	
	public JLabel label1;
	public JLabel label2;
	HUD()
	{
		label1 = new JLabel("Max/Current Level 1/1", JLabel.RIGHT);
		label1.setForeground(new Color (0x000000));
		label1.setBackground(new Color(0xffffff));
		label1.setOpaque(false);
        label1.setBorder(new EmptyBorder(5, 0, 0, 20) );
	}
	public void add(JFrame frame, JLabel label, String layout )
	{
		frame.add(label, layout);
	}
}