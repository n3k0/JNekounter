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

import java.awt.CardLayout;

import javax.swing.JPanel;
/**
 * Clase que hace funcion de contenedor de los distintos cronometros  mediante 
 * el useo de un CardLayout el cual se cambia mediante eventos que detona
 * un objeto de tipo OptionPanel
 * 
 * @author n3k0
 *
 */
public class ContainerPanel {
	
	private JPanel containerPanel;
	
	private static final int WIDTH = 500;
	private static final int HEIGHT = 235;
	
	/**
	 * Metodo que retorna un panel previamente configurado
	 */
	public JPanel buildPanel() {
		
		CardLayout cardLayout =  new CardLayout();
		containerPanel = new JPanel(cardLayout);
		containerPanel.setSize( WIDTH , HEIGHT );
		return containerPanel;
	}

	/**
	 * Metodo que retorna la altura preconfigurada del panel
	 * @return HEIGHT
	 */
	public int getHeight() {
		return HEIGHT;
	}

	/**
	 * Metodo que retorna la anchura preconfigurada del panel
	 * @return WIDTH
	 */
	public int getWidth() {
		return WIDTH;
	}
}