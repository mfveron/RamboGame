package juego;

import java.awt.Image;
import entorno.Entorno;

public class LaserRojo {
	private double x;
    private double y;
    private double velocidad;
    private double maxVel;
    private double angle;
    private int ancho;
    private int alto;
	private Image imagen;
	private boolean debeEliminar;
    
    
    public LaserRojo(double x, double y, double velocidad, double maxVel, double angle, int ancho, int alto, Image imagen) {
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.velocidad = velocidad + maxVel;
        this.maxVel = maxVel;
        this.ancho = ancho;
        this.alto = alto;
        this.imagen = imagen;
    }  

    public double getX() { //Representa la coordenada x del láser
        return x;
    }

    public double getY() { //Representa la coordenada y del láser
        return y;
    }

    public int getAncho() { //Representa el ancho del láser
        return ancho;
    }

    public int getAlto() { //Representa el alto del láser
        return alto;
    }
    
    ///////////////////////////////////////////// Método de movimiento //////////////////////////////////////////////////////
    
    public void mover() {
        // Actualiza la posición del láser según su velocidad y ángulo
        x += velocidad * Math.cos(angle);
        y += velocidad * Math.sin(angle);
        
        // Verifica si el láser sale de los límites de la pantalla
        if (x <= 31 || x >= 865 || y < 30 || y >= 682) {
            debeEliminar = true; // Marca el láser para su eliminación
            
        }
    }
    
    /////////////////////////////////////////////// Método de Dibujo ///////////////////////////////////////////////////////////
    
    public void dibujarImagen(Entorno e){
        // Dibuja la imagen del láser
    	e.dibujarImagen(imagen, x, y, 0, 2.0);
	}
    
    //////////////////////////////////////////////// Método si se debe eliminar /////////////////////////////////////////////////////
    
	public boolean setDebeEliminar(boolean debeEliminar) {
		return this.debeEliminar=debeEliminar;
	}
    
    public boolean debeEliminar() {
        return debeEliminar;
    }    
}
