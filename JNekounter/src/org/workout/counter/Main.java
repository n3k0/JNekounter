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
package org.workout.counter;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.workout.counter.frame.FrameCreator;

/**
 * Clase principal, punto de entrada al programa de cronometros.
 * @author n3k0
 *
 */
public class Main {
	
	public static void main( String ... s ){
		try {
			UIManager.setLookAndFeel( "com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel" ) ;
			JFrame.setDefaultLookAndFeelDecorated(true);
		}
		catch (ClassNotFoundException e) {
			System.out.println("[Main.main:ClassNotFoundException]"+ e.getMessage());
		}
		catch (InstantiationException e) {
			System.out.println("[Main.main:InstantiationException]"+ e.getMessage());
		}
		catch (IllegalAccessException e) {
			System.out.println("[Main.main:IllegalAccessException]"+ e.getMessage());
		}
		catch (UnsupportedLookAndFeelException e) {
			System.out.println("[Main.main:UnsupportedLookAndFeelException]"+ e.getMessage());
		}
		finally{
			EventQueue.invokeLater(new Runnable() {
				
				@Override
				public void run() {
					 new FrameCreator();
				}
			});
		}
	}
}