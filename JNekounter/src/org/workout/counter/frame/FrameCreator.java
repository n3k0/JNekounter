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

package org.workout.counter.frame;

import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.workout.counter.panel.swing.ContainerPanel;
import org.workout.counter.panel.swing.CronometerPanel;
import org.workout.counter.panel.swing.OptionPanel;
import org.workout.counter.panel.swing.TabataPanel;
import org.workout.counter.panel.swing.TrackPanel;
import org.workout.counter.util.Mp3Player;
/**
 * Clase que construye un objeto de tipo JFrame, en base a los paneles que 
 * se le adhieran
 * @author n3k0
 *
 */
public class FrameCreator implements Runnable , ActionListener{
	
	private JFrame frame;
	
	private OptionPanel option;
	private TrackPanel track;
	private ContainerPanel container;
	private CronometerPanel cronometer;
	private TabataPanel tabata;
	
	private JPanel optionPanel;
	private JPanel trackPanel;
	private JPanel containerPanel;
	private JPanel cronometerPanel;
	private JPanel tabataPanel;
	
	private Mp3Player player;
	private File song;
	
	private int WIDTH = 510;	
	
	/**
	 * Constructor con propiedades de decoracion para el objeto JFrame
	 */
	public FrameCreator(){
		frame = new JFrame();
		frame.setResizable(false);
		frame.getContentPane().setLayout( null );
		frame.setVisible( true );
		frame.setTitle( "JWorkounter" );
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		this.configureFrame();
	}
	
	/**
	 * Metodo que configura el JFrame, conforme a los paneles que se agreguen
	 */
	private void configureFrame(){
				
		option = new OptionPanel();
		optionPanel = option.buildPanel();
				
		track = new TrackPanel();
		trackPanel = track.buildPanel();
				
		container = new ContainerPanel();
		containerPanel = container.buildPanel();
		
		cronometer = new CronometerPanel();
		cronometerPanel  = cronometer.buildPanel();
		
		tabata = new TabataPanel(); 
		tabataPanel = tabata.buildPanel();
		
		optionPanel.setBounds(0, 0, optionPanel.getWidth() , optionPanel.getHeight());
		trackPanel.setBounds(0 , (optionPanel.getHeight() ) , trackPanel.getWidth(), trackPanel.getHeight());
		containerPanel.setBounds( 0, ( optionPanel.getHeight() + trackPanel.getHeight() ) , containerPanel.getWidth() , containerPanel.getHeight());
				
		containerPanel.add( cronometerPanel , OptionPanel.OPTION_CRONOMETER );
		containerPanel.add( tabataPanel , OptionPanel.OPTION_TABATA );
		
		frame.setBounds( this.putFrameOnDesktop( WIDTH, this.getCronometerSize() ));
		
		frame.add(optionPanel);
		frame.add(trackPanel);
		frame.add(containerPanel);
		
		option.getRadioCronos().addActionListener( this);
		option.getRadioTabata().addActionListener( this );
		
		track.getTrackButton().addActionListener( this );
		
		cronometer.getBeginWorkout().addActionListener( this );
		tabata.getBeginWorkout().addActionListener( this );
		
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		if( event.getSource().equals( option.getRadioCronos() ) ) {
			frame.setSize(WIDTH, this.getCronometerSize() );
			containerPanel.setSize( container.getWidth(), cronometer.getHeight());
			((CardLayout) containerPanel.getLayout()).show( containerPanel , OptionPanel.OPTION_CRONOMETER );
		}
		else if( event.getSource().equals( option.getRadioTabata() ) ) {
			frame.setSize(WIDTH, this.getTabataSize() );
			containerPanel.setSize( container.getWidth(), tabata.getHeight());
			((CardLayout) containerPanel.getLayout()).show( containerPanel , OptionPanel.OPTION_TABATA );
		}
		else if( event.getSource().equals( cronometer.getBeginWorkout() )){
			cronometer.runCronometer();
			
			if(player != null){
				player.playMp3();
			}
		}
		else if( event.getSource().equals( tabata.getBeginWorkout() )){
			tabata.runCronometer();
			
			if(player != null){
				player.playMp3();
			}
			
		}
		else if( event.getSource().equals( track.getTrackButton() )){
			JFileChooser chooser = new JFileChooser();
		    FileNameExtensionFilter filter = new FileNameExtensionFilter("Mp3 Music tracks", "mp3");
		    chooser.setFileFilter(filter);
		    chooser.showOpenDialog( track.getTrackButton() );
		    
		    song = chooser.getSelectedFile();
		    
			if ( song != null ) {
				if (song.getName().endsWith("mp3")) {
					player = new Mp3Player();
					player.setFileToPlay( song );
				}
				else {
					JOptionPane.showMessageDialog( frame , "Tipo de archivo incorrecto ", "Error",	JOptionPane.ERROR_MESSAGE );
				}
			}
		}
	}
	
	/**
	 * Metodo que inicializa la posicion del frame al centro de la pantalla
	 * @param width
	 * @param height
	 * @return objeto Rectangle que indica el lugar y tamaño que ocupa el frame en pantalla
	 */
	private Rectangle putFrameOnDesktop( int width , int height ){
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		return new Rectangle( ( d.width - width ) / 2 , ( d.height - height ) / 2 , width , height );
	}
	
	/**
	 * Calcula la altura total del frame cuando se selecciona la opcion de cronometro
	 * @return valor de la altura del frame
	 */
	private int getCronometerSize(){
		return option.getHeight() + 5 +
			   track.getHeight() + 5 +
			   cronometer.getHeight();
	}
	
	/**
	 * Calcula la altura total del frame cuando se selecciona la opcion de tabata
	 * @return valor de la altura del frame
	 */
	private int getTabataSize(){
		return option.getHeight() + 5 +
			   track.getHeight() + 5 +
			   tabata.getHeight();
	}
	
	@Override
	public void run() {}
}