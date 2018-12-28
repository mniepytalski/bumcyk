package pl.cybertech.bumcyk.ear.diagrams;

import java.awt.Graphics;

import javax.swing.JComponent;

import pl.cybertech.bumcyk.ear.SoundData;

public abstract class Diagram {
	
	abstract public Diagrams getName();
	
	abstract public void print(Graphics g, JComponent component, SoundData soundData) ;

}
