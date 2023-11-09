package juego;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;
import entorno.Entorno;
import entorno.Herramientas;
import entorno.InterfaceJuego;

public class MenuJuego extends InterfaceJuego{
	private boolean juegoComenzado = false;
	private boolean juego2JugComenzado = false;
    private int opcionSeleccionada = 0;
    private String[] opciones = { "1 PLAYER", "2 PLAYER", "Salir" };
    private File file = new File("Recursos/menuCancion.wav");
    private File selectSFX= new File("Recursos/selectSFX.wav");
    private Clip clip;
    private Clip clip2;
    
    public MenuJuego()throws UnsupportedAudioFileException, IOException, LineUnavailableException {
    	// Carga el sonido del menú
        AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
        AudioInputStream audioStream2 = AudioSystem.getAudioInputStream(selectSFX);
        clip = AudioSystem.getClip();
        clip2 = AudioSystem.getClip();
        clip.open(audioStream);
        clip2.open(audioStream2);
    }
    
    public boolean estaJuego1JugComenzado() {
        return juegoComenzado;
    }
    
    public boolean estaJuego2JugComenzado() {
        return juego2JugComenzado;
    }
    
    public void iniciarCancion()throws UnsupportedAudioFileException, IOException, LineUnavailableException {
    	clip.start();
    	clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    
    public void detenerCancion() {
    	clip.stop();
    }
    
    public void reproducirSFX() {
    	clip2.start();
    	clip2.setFramePosition(1);
    }
    
    public void tick(Entorno entorno) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
    	//Carga la imagen de fondo del Menu (las calles)
    	entorno.dibujarImagen(Herramientas.cargarImagen("FondoMenuR.png"), entorno.ancho()/2, entorno.alto()/2, 0);  
    	iniciarCancion();
        if (!juegoComenzado) {
        	// Cambio de color gradual para el nombre del juego (amarillo a rojo)
        	int colorRojo = 255;
            int colorVerde = (int) (Math.abs(Math.sin(System.currentTimeMillis() * 0.002)) * 255);
            int colorAzul = 0;
        	Color colorTitulo = new Color(colorRojo, colorVerde, colorAzul);
            entorno.cambiarFont("Impact", 150, colorTitulo);
            entorno.escribirTexto("RAMBO", 420, 150);

            for (int i = 0; i < opciones.length; i++) {
            	int tamanio = (i == opcionSeleccionada) ? 90 : 70;
                Color color = (i == opcionSeleccionada) ? Color.YELLOW : Color.WHITE;
                entorno.cambiarFont("Impact", tamanio, color);
                entorno.escribirTexto(opciones[i], 100, 400 + i * 100);
            }
            if (entorno.sePresiono('w')) {
            	reproducirSFX();
                opcionSeleccionada--;
                if (opcionSeleccionada < 0) {
                    opcionSeleccionada = opciones.length - 1;
                }
            }
            if (entorno.sePresiono('s')) {
            	reproducirSFX();
                opcionSeleccionada++;
                if (opcionSeleccionada >= opciones.length) {
                    opcionSeleccionada = 0;
                }
            }
            if (entorno.estaPresionada(entorno.TECLA_ENTER)) {
                // Realiza la acción asociada con la opción seleccionada
            	switch (opcionSeleccionada) {
                case 0:
                    juegoComenzado = true;
                    detenerCancion();
                    break;
                case 1:
                	juego2JugComenzado = true;
                	detenerCancion();
                	break;
                case 2:
                    System.exit(0); // Sale del juego
                    break;
                default:
                    break;
            	}
            }
        }
    }
    
}
