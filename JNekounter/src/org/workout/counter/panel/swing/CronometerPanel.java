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

import java.awt.Dimension;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;

import org.workout.counter.util.FontSearcher;

public class CronometerPanel {

	private JPanel cronometerPanel;
	private JPanel parameterPanel;
	private JPanel counterPanel;
	
	private JLabel labelMinutesTaped;
	private JLabel labelSecondsTaped;
	
	private JTextField txtMinutesTaped;
	private JTextField txtSecondsTaped;
	
	private JLabel labelMinutes;
	private JLabel labelSeconds;
	private JLabel dots;
	
	private JButton beginWorkout;
	
	private Icon go;
	private Icon stop;
	
	private static final int WIDTH = 500;
	private static final int HEIGHT = 250;
	
	private static final String EMPTY_STRING = "";
	private static final String ZERO_STRING = "0";
	
	private boolean workoutStarted;
	
	private SwingWorker<Void, Void> swingWorker;
	
	/**
	 * Metodo que retorna un panel previamente configurado
	 */
	public JPanel buildPanel(){
		
		cronometerPanel = new JPanel(null);
		cronometerPanel.setSize( WIDTH , HEIGHT );
		
		parameterPanel = new JPanel();
		parameterPanel.setBounds( 0 , 0, 490 , 100 );
		parameterPanel.setLayout( null );
		
		labelMinutesTaped = new JLabel("Teclea los minutos");
		labelMinutesTaped.setHorizontalAlignment(SwingConstants.RIGHT);
		labelMinutesTaped.setBounds(10, 10, 175, 25);
		
		labelSecondsTaped = new JLabel("Teclea los segundos");
		labelSecondsTaped.setHorizontalAlignment(SwingConstants.RIGHT);
		labelSecondsTaped.setBounds(10, 55, 175, 25);
		
		txtMinutesTaped = new JTextField("00");
		txtMinutesTaped.setHorizontalAlignment(SwingConstants.CENTER);
		txtMinutesTaped.setBounds(200, 10, 70, 25);
		
		txtSecondsTaped = new JTextField("00");
		txtSecondsTaped.setHorizontalAlignment(SwingConstants.CENTER);
		txtSecondsTaped.setBounds(200, 55, 70, 25);
		
		go = new ImageIcon( CronometerPanel.class.getResource( "/img/run.gif" ));
		stop = new ImageIcon( CronometerPanel.class.getResource( "/img/zzz.gif" ));
		
		beginWorkout = new JButton( "Inicio" , stop );
		beginWorkout.setHorizontalTextPosition(SwingConstants.CENTER);
		beginWorkout.setVerticalTextPosition(SwingConstants.BOTTOM) ;
		beginWorkout.setVerticalAlignment(SwingConstants.BOTTOM);
		beginWorkout.setBounds(319 , 10 , 160, 80);
		
		parameterPanel.add( labelMinutesTaped );
		parameterPanel.add( txtMinutesTaped );
		
		parameterPanel.add( labelSecondsTaped );
		parameterPanel.add( txtSecondsTaped );
		parameterPanel.add( beginWorkout );
		
		counterPanel = new JPanel();
		counterPanel.setLayout( null );
		counterPanel.setBounds( 0, 110 , 490 	, 115 );
		
		labelMinutes = new JLabel("00");
		labelMinutes.setHorizontalAlignment(SwingConstants.CENTER);
		labelMinutes.setBounds(39, 11, 170, 100);
		
		labelSeconds = new JLabel("00");
		labelSeconds.setHorizontalAlignment(SwingConstants.CENTER);
		labelSeconds.setBounds(277, 11, 170, 100);
		
		dots = new JLabel(":");
		dots.setHorizontalAlignment(SwingConstants.CENTER);
		dots.setBounds(207, 11, 70, 100);
		
		labelMinutes.setFont( FontSearcher.chargeFont() );
		labelSeconds.setFont( FontSearcher.chargeFont() );
		dots.setFont( FontSearcher.chargeFont() );
		
		labelMinutes.setPreferredSize( new Dimension(250, 100));
		labelSeconds.setPreferredSize( new Dimension(250, 100));
		dots.setPreferredSize( new Dimension(30, 100));
		
		counterPanel.add(labelMinutes);
		counterPanel.add(dots);
		counterPanel.add(labelSeconds);
		
		cronometerPanel.add( parameterPanel);
		cronometerPanel.add( counterPanel );
		
		return cronometerPanel;
	}

	/**
	 * Metodo que ejecuta el calculo de los intervalos de tiempo
	 * establecidos por el usuario, y refresca las etiquetas 
	 * correspondientes
	 */
	public void runCronometer() {
				
		final int m = Integer.parseInt( txtMinutesTaped.getText());
		final int s = Integer.parseInt( txtSecondsTaped.getText());
		
		labelMinutes.setText( m >= 10 ? EMPTY_STRING + m : ZERO_STRING + m );
		labelSeconds.setText( s >= 10 ? EMPTY_STRING + s : ZERO_STRING + s );
		
		swingWorker = new SwingWorker<Void, Void>(){
			
			int minutesToSet = m;
			int secondsToSet = s;
			@Override
			protected Void doInBackground() throws Exception {
				
				disableElements();
				
				while (secondsToSet >= 0 && minutesToSet >= 0 ) {
					if (secondsToSet > 0) {
						Thread.sleep(1000);
						secondsToSet--;
						labelSeconds.setText( secondsToSet >= 10 ? EMPTY_STRING + secondsToSet : ZERO_STRING + secondsToSet );
					} 
					else if (minutesToSet > 0) {
						minutesToSet--;
						secondsToSet = 60;
						labelMinutes.setText( minutesToSet >= 10 ? EMPTY_STRING + minutesToSet : ZERO_STRING + minutesToSet );
					}
					else{
						enableElements();
						break;
					}
				}
				return null;
			}
		};
		swingWorker.execute();
		
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
	
	/**
	 * Metodo que retorna el objeto de tipo JButton
	 * con el fin de asignarle una accion en el panel principal
	 * @return JButton
	 */
	public JButton getBeginWorkout() {
		return this.beginWorkout;
	}
	
	/**
	 * Habilita campos de texto y el boton que inicializa el contador
	 */
	private void enableElements(){
		workoutStarted = false;
		beginWorkout.setIcon(stop);
		txtMinutesTaped.setEnabled( true );
		txtSecondsTaped.setEnabled( true );
	}
	
	/**
	 * Inhabilita campos de texto y el boton que inicializa el contador
	 */
	private void disableElements(){
		workoutStarted = true;
		beginWorkout.setIcon(go);
		txtMinutesTaped.setEnabled( false );
		txtSecondsTaped.setEnabled( false );
	}

	/**
	 * @return true si el workout ha iniciado false en caso contrario
	 */
	public boolean isWorkoutStarted() {
		return workoutStarted;
	}
}