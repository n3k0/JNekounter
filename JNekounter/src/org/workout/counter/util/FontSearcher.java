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

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;
/**
 * Clase que carga la fuente en los paneles de contadores
 * @author N3k0
 *
 */
public class FontSearcher {

	private static int SIZE = 30;
	
	/**
	 * Metodo que carga la fuente elegida... jo, como si hubiera muchas ;-)
	 * @return objeto Font con propiedades predefinidas
	 */
	public static Font chargeFont() {
		
		try{
			InputStream stream = FontSearcher.class.getResourceAsStream( "/fonts/Computerfont.ttf" );
			Font font = Font.createFont( Font.PLAIN , stream );
			return font.deriveFont(100F);
		}
		catch (IOException e) {
			System.out.println( "[FontSearcher.chargeSelectedFont:IOException]" + e.getMessage() );
			return new Font( Font.SANS_SERIF , Font.BOLD , SIZE );
		}
		catch (FontFormatException e) {
			System.out.println( "[FontSearcher.chargeSelectedFont:FontFormatException]" + e.getMessage() );
			return new Font( Font.SANS_SERIF , Font.BOLD , SIZE );
		}
	}
}