package juego;
import java.io.File;
import java.io.IOException;
import java.util.Random;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class SFX {
	private File archivo_sonido;
	private Clip clip_archivo;
	private Random rnd;
    
    public SFX(File archivo_sonido) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
    	// Carga los sonidos a utilizar
    	this.archivo_sonido=archivo_sonido;
        AudioInputStream audio_archivo = AudioSystem.getAudioInputStream(this.archivo_sonido);
        this.clip_archivo= AudioSystem.getClip();
        this.clip_archivo.open(audio_archivo);
        this.rnd = new Random();
    }
    
    public void reproducir_audio() {
    	this.clip_archivo.start();
    	if(this.clip_archivo.getFramePosition()== this.clip_archivo.getFrameLength()) {
    		this.clip_archivo.setFramePosition(0);
    	}
    }
    
    public void detener_audio() {
    	this.clip_archivo.stop();
    	this.clip_archivo.setFramePosition(0);
    }
    
    public void reproducir_audio_en_bucle() {
    	this.clip_archivo.start();
    	this.clip_archivo.loop(Clip.LOOP_CONTINUOUSLY);
    }  
    
    public void reproducir_frase() {
    	this.clip_archivo.start();
    	Juego.permite_frase = -15;
    	Juego.indice = rnd.nextInt(Juego.sonidos_rambo.length);
    	if(this.clip_archivo.getFramePosition()== this.clip_archivo.getFrameLength()) {
    		this.clip_archivo.setFramePosition(0);
    	}
    }      
}
