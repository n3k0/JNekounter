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

import javax.sound.midi.Synthesizer;
import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
public class Beep {
   public static Beep getBeeper() {
      return new Beep();
   }

   private Synthesizer synth;
   private MidiChannel channel;
   private Beep() {
      try { 
         synth = MidiSystem.getSynthesizer();
         synth.open();
         channel = synth.getChannels()[13]; // puede no estar disponible el canal 13...  
      } catch( MidiUnavailableException mue ) {

			System.err.println( "[Beep.<init>:MidiUnavailableException] " + mue.getMessage() );
			java.awt.Toolkit.getDefaultToolkit().beep(); // :P para Mr(-) jo jo jo 
      }
   }
   public void beep() {
      this.beep( 500 );
   }
   public void beep( int delay ) {
      this.beep( 1, delay );
   }
   public void beep( final int times, final int delay ) {
      this.beep( times, delay, false );
   }
   public void beep( final int times, final int delay , final boolean close ) {
      
      if ( times < 1 ) {
         // ¬¬ ... bueno ya acabé
         return;
      }

      new Thread( new Runnable() {
         public void run() {
            for( int i = 0 ; i < times ; i++ ) try {
               channel.noteOn( 70, 1000);
               channel.noteOff( 70 );
               Thread.currentThread().sleep( delay );
            } catch( InterruptedException ie ){
            }
            if ( close ) {
               Beep.this.close();
            }
         }
      }).start();
      
   }

   public void close() {
      synth.close();
   }
}