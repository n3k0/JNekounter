/*
 * JNekounter - https://github.com/n3k0/JNekounter
 * 
 * Copyright (C) 2012 N3k0
 * 
 * JNekounter is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published
 * by the Free Software Foundation; either version 2 of the License,
 * or (at your option) any later version.
 *
 * JNekounter is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Cobertura; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307
 * USA
 */

package org.workout.counter.panel.swing;

import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class OptionPanel{
	
	private JPanel optionPanel;
	private ButtonGroup buttonGroup;
	
	private JRadioButton radioCronos;
	private JRadioButton radioTabata;
	
	private static final int WIDTH = 500;
	private static final int HEIGHT = 50;
	
	public static final String OPTION_CRONOMETER = "Cronometro";
	public static final String OPTION_TABATA = "Tabata";
	
	/**
	 * @wbp.parser.entryPoint
	 */
	public JPanel buildPanel() {
		
		optionPanel = new JPanel(null);
		optionPanel.setSize( WIDTH , HEIGHT );
		
		radioCronos = new JRadioButton( OPTION_CRONOMETER );
		radioTabata = new JRadioButton( OPTION_TABATA );
		
		buttonGroup = new ButtonGroup();
		buttonGroup.add(radioCronos);
		buttonGroup.add(radioTabata);
		
		radioCronos.setBounds( 110 , 10, 100, 25);
		radioTabata.setBounds( 310 , 10, 100, 25);
		
		optionPanel.add( radioCronos );
		optionPanel.add( radioTabata );
		
		return optionPanel;
	}

	public int getHeight() {
		return HEIGHT;
	}

	public int getWidth() {
		return WIDTH;
	}

	public JRadioButton getRadioCronos() {
		return radioCronos;
	}

	public JRadioButton getRadioTabata() {
		return radioTabata;
	}
}