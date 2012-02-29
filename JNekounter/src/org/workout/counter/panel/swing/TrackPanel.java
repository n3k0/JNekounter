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

import javax.swing.JButton;
import javax.swing.JPanel;

import org.workout.counter.util.Mp3Player;

/**
 * Clase que construye un panel que agrega tracks de musica en formato mp3
 * @author n3k0
 *
 */
public class TrackPanel {
	
	private JPanel trackPanel;
	
	private JButton trackButton;
	
	private Mp3Player player;
	
	private static final int WIDTH = 500;
	private static final int HEIGHT = 30;
	
	/**
	 * Metodo que retorna un panel previamente configurado
	 */
	public JPanel buildPanel(){
		
		trackPanel = new JPanel(null);
		trackPanel.setSize( WIDTH , HEIGHT );
		
		trackButton = new JButton("Agrega un track en formato Mp3");
		trackButton.setBounds( 10 , 0 , 480 , 25 );
		
		trackPanel.add(trackButton);
		
		return trackPanel;
	}
	
	public Mp3Player getPlayer() {
		return player;
	}

	public void setPlayer(Mp3Player player) {
		this.player = player;
	}

	public int getHeight() {
		return HEIGHT;
	}

	public int getWidth() {
		return WIDTH;
	}

	public JButton getTrackButton() {
		return trackButton;
	}
}