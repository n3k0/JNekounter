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

package org.workout.counter.util;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.SwingWorker;

/**
 * Clase de utileria que se encarga de reproducir el track seleccionado
 * @author n3k0
 *
 */
public class Mp3Player{
	
	private File song;
	
	private SwingWorker< Void, Void> swingWorker;
	
	/**
	 * Metodo que ejecuta la reproduccion del archivo previamente seleccionado
	 */
	public void playMp3() {
		swingWorker = new SwingWorker<Void, Void>(){
			
			@Override
			protected Void doInBackground() throws Exception {
				
				AudioInputStream decodedInputStream = null;
				
				if ( song != null && song.length() > 0) {
					try{
						AudioInputStream audioInputStream = AudioSystem.getAudioInputStream( song );
						AudioFormat baseFormat = audioInputStream.getFormat();
						AudioFormat decodedFormat = new AudioFormat(
								AudioFormat.Encoding.PCM_SIGNED,
								baseFormat.getSampleRate(), 16, baseFormat.getChannels(),
								baseFormat.getChannels() * 2, baseFormat.getSampleRate(),
								false);
						decodedInputStream = AudioSystem.getAudioInputStream(decodedFormat, audioInputStream);
						DataLine.Info info = new DataLine.Info(SourceDataLine.class, decodedFormat);
						SourceDataLine souceDataline = (SourceDataLine) AudioSystem.getLine(info);
						
						if(souceDataline != null) {
							souceDataline.open(decodedFormat);
							byte[] data = new byte[4096];
							souceDataline.start();
							
							int nBytesRead;
							while (( nBytesRead = decodedInputStream.read(data, 0, data.length)) != -1) {	
								souceDataline.write(data, 0, nBytesRead);
							}
							souceDataline.drain();
							souceDataline.stop();
							souceDataline.close();
						
						}
					}
					catch (UnsupportedAudioFileException e) {
						System.out.println( "[Mp3Player.playMp3:UnsupportedAudioFileException]" + e.getMessage() );
					}
					catch (LineUnavailableException e) {
						System.out.println( "[Mp3Player.playMp3:LineUnavailableException]" + e.getMessage() );
					}
					catch (IOException e) {
						System.out.println( "[Mp3Player.playMp3:IOException]" + e.getMessage() );
					}
					finally{
						try {
							decodedInputStream.close();
						}
						catch (IOException e) {
							System.out.println( "[Mp3Player.finally.IOException]" + e.getMessage() );
						}
					}
				}
				return null;
			}
		};
		swingWorker.execute();
	}

	/**
	 * Metodo que recibe un objeto de tipo File, el cual representa el track a reproducir
	 * @param song
	 */
	public void setFileToPlay(File song) {
		this.song = song;
	}
}