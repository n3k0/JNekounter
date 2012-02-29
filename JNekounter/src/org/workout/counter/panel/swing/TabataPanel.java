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

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;

import org.workout.counter.util.FontSearcher;

/**
 * Clase que construye un panel que sirve para contar series 
 * por intervalos de tiempo, entre tiempos activos (workout) y descansos.
 * @author n3k0
 *
 */
public class TabataPanel {
	
	private JPanel tabataPanel;
	private JPanel parameterPanel;
	private JPanel counterPanel;
	
	private JLabel labelSeriesToSet;
	private JLabel labelWorkToSet;
	private JLabel labelRestToSet;
	
	private JTextField txtSeriesToSet;
	private JTextField txtWorkToSet;
	private JTextField txtRestToSet;
	
	private JLabel labelSeries;
	private JLabel countSeries;
	
	private JLabel labelSecondsWork;
	private JLabel countSecondsWork;
	
	private JLabel labelSecondsRest;
	private JLabel countSecondsRest;
	
	private JButton beginWorkout;
	
	private SwingWorker<Void, Void> swingWorker;
	
	private static final String EMPTY_STRING = "";
	private static final String ZERO_STRING = "0";
	
	private Icon go;
	private Icon stop;
	
	private static final int WIDTH = 500;
	private static final int HEIGHT = 380;
	
	private boolean workoutStarted;
	
	/**
	 * Metodo que retorna un panel previamente configurado
	 */
	public JPanel buildPanel(){
		
		tabataPanel = new JPanel(null);
		tabataPanel.setSize( WIDTH , HEIGHT );
		
		parameterPanel = new JPanel( null );
		parameterPanel.setBounds(0,0, 490 , 120);
		
		labelSeriesToSet = new JLabel("N\u00FAmero de Series:");
		labelSeriesToSet.setHorizontalAlignment(SwingConstants.RIGHT);
		labelSeriesToSet.setBounds( 10,10,180,25);
		labelWorkToSet = new JLabel("Duraci\u00F3n de la serie:");
		labelWorkToSet.setHorizontalAlignment(SwingConstants.RIGHT);
		labelWorkToSet.setBounds( 10,46,180,25);
		labelRestToSet = new JLabel("Descanso entre series:");
		labelRestToSet.setHorizontalAlignment(SwingConstants.RIGHT);
		labelRestToSet.setBounds( 10,82,180,25);
		
		txtSeriesToSet = new JTextField("00");
		txtSeriesToSet.setHorizontalAlignment(SwingConstants.LEFT);
		txtSeriesToSet.setBounds( 220,10,100,25);
		
		txtWorkToSet = new JTextField("00");
		txtWorkToSet.setHorizontalAlignment(SwingConstants.LEFT);
		txtWorkToSet.setBounds( 220,46,100,25);
		
		txtRestToSet = new JTextField("00");
		txtRestToSet.setHorizontalAlignment(SwingConstants.LEFT);
		txtRestToSet.setBounds( 220,82,100,25);
		
		go = new ImageIcon( TabataPanel.class.getResource( "/img/run.gif" ));
		stop = new ImageIcon( TabataPanel.class.getResource( "/img/zzz.gif" ));
		
		beginWorkout = new JButton("Inicio" , stop );
		beginWorkout.setHorizontalTextPosition(SwingConstants.CENTER);
		beginWorkout.setVerticalTextPosition(SwingConstants.BOTTOM) ;
		beginWorkout.setVerticalAlignment(SwingConstants.BOTTOM);
		beginWorkout.setBounds(334, 10, 150, 90);
		beginWorkout.setActionCommand( "beginTabata" );
		
		parameterPanel.add( labelSeriesToSet);
		parameterPanel.add( labelWorkToSet);
		parameterPanel.add(labelRestToSet);
		parameterPanel.add(txtSeriesToSet);
		parameterPanel.add(txtWorkToSet);
		parameterPanel.add(txtRestToSet);
		parameterPanel.add( beginWorkout );
		
		counterPanel = new JPanel( null );
		counterPanel.setBounds(0, 130, 490, 230);
		
		labelSeries = new JLabel("Serie:");
		labelSeries.setHorizontalAlignment(SwingConstants.CENTER);
		labelSeries.setBounds( 170 , 5 , 100 , 25 );
		
		labelSecondsWork = new JLabel("Workout");
		labelSecondsWork.setHorizontalAlignment(SwingConstants.CENTER);
		labelSecondsWork.setBounds( 55 , 105 , 100 , 25 );
		
		labelSecondsRest = new JLabel("Descanso");
		labelSecondsRest.setHorizontalAlignment(SwingConstants.CENTER);
		labelSecondsRest.setBounds( 340 , 105 , 100 , 25 );
		
		countSeries = new JLabel("00");
		countSeries.setHorizontalAlignment(SwingConstants.CENTER);
		countSeries.setBounds( 130 , 25 , 200 , 100 );
		countSeries.setFont( FontSearcher.chargeFont() );
		
		countSecondsWork = new JLabel("00");
		countSecondsWork.setHorizontalAlignment(SwingConstants.CENTER);
		countSecondsWork.setBounds( 10 , 130 , 200 , 100 );
		countSecondsWork.setFont( FontSearcher.chargeFont() );
		
		countSecondsRest = new JLabel("00");
		countSecondsRest.setHorizontalAlignment(SwingConstants.CENTER);
		countSecondsRest.setBounds( 290 , 130 , 200 , 100 );
		countSecondsRest.setFont( FontSearcher.chargeFont() );
		
		counterPanel.add( labelSeries);
		counterPanel.add(countSeries);
		counterPanel.add(labelSecondsWork);
		counterPanel.add(labelSecondsRest);
		counterPanel.add(countSecondsWork);
		counterPanel.add(countSecondsRest);
		
		tabataPanel.add(parameterPanel);
		tabataPanel.add(counterPanel);
		
		return tabataPanel;
	}

	/**
	 * Metodo que ejecuta el calculo de los intervalos de tiempo
	 * establecidos por el usuario, y refresca las etiquetas 
	 * correspondientes
	 */
	public void runCronometer() {
		
		final int s = Integer.parseInt( txtSeriesToSet.getText() );
		final int w = Integer.parseInt( txtWorkToSet.getText() );
		final int r = Integer.parseInt( txtRestToSet.getText() );
		
		countSecondsWork.setText( w >= 10 ? EMPTY_STRING + w : ZERO_STRING + w );
		countSecondsRest.setText( r >= 10 ? EMPTY_STRING + r : ZERO_STRING + r );	
		countSeries.setText( s >= 10 ? EMPTY_STRING + s : ZERO_STRING + s );
		
		swingWorker = new SwingWorker<Void, Void>(){
			
			int seriesToSet = s;
			int workToSet = w;
			int restToSet = r;
			
			@Override
			protected Void doInBackground() throws Exception {
				
				disableElements();
				
				while( seriesToSet >= 0){
					
					if( workToSet > 0 || restToSet > 0 ){
						while ( workToSet > 0 ) {
							Thread.sleep(1000);
							workToSet--;
							countSecondsWork.setText( workToSet >= 10 ? EMPTY_STRING + workToSet : ZERO_STRING + workToSet );
						}
						while ( restToSet > 0 ) {
							Thread.sleep(1000);
							restToSet--;
							countSecondsRest.setText( restToSet >= 10 ? EMPTY_STRING + restToSet : ZERO_STRING + restToSet );
						}
					}
					
					if ( seriesToSet == 0 && workToSet == 0 && restToSet == 0){
						enableElements();
						break;
					}
					
					seriesToSet--;
					workToSet = w;
					restToSet = r;
					
					countSecondsWork.setText( workToSet >= 10 ? EMPTY_STRING + workToSet : ZERO_STRING + workToSet );
					countSecondsRest.setText( restToSet >= 10 ? EMPTY_STRING + restToSet : ZERO_STRING + restToSet );	
					countSeries.setText( seriesToSet >= 10 ? EMPTY_STRING + seriesToSet : ZERO_STRING + seriesToSet );
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
		txtRestToSet.setEnabled( true );
		txtSeriesToSet.setEnabled( true );
		txtWorkToSet.setEnabled( true );
	}
	
	/**
	 * Inhabilita campos de texto y el boton que inicializa el contador
	 */
	private void disableElements(){
		workoutStarted = true;
		beginWorkout.setIcon(go);
		txtRestToSet.setEnabled( true );
		txtSeriesToSet.setEnabled( true );
		txtWorkToSet.setEnabled( true );
	}
	
	/**
	 * @return true si el workout ha iniciado false en caso contrario
	 */
	public boolean isWorkoutStarted() {
		return workoutStarted;
	}
}