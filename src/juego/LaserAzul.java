package juego;

import java.awt.Image;
import java.util.List;
import entorno.Entorno;

public class LaserAzul {
	private double x;
    private double y;
    private double velocidad;
    private double maxVel;
    private double angle;
    private int ancho;
    private int alto;
	private Image imagen;
	private boolean debeEliminar;
	private Hitbox hitboxLaserAzul;
    
    public LaserAzul(double x, double y, double velocidad, double maxVel, double angle, int ancho, int alto, Image imagen, Hitbox hitboxLaser) {
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.velocidad = velocidad + maxVel;
        this.maxVel = maxVel;
        this.ancho = ancho;
        this.alto = alto;
        this.imagen = imagen;
        this.hitboxLaserAzul=hitboxLaser;
    }  
    
    // Getters para las propiedades del láser
    
    public double getX() { //Representa la coordenada x del láser
        return x;
    }

    public double getY() { //Representa la coordenada y del láser
        return y;
    }

    public int getAncho() { //Representa el ancho del láser
        return ancho;
    }

    public int getAlto() {
        return alto;
    }

    ///////////////////////////////////////////// Método de movimiento //////////////////////////////////////////////////////
    
    public void mover() {
        // Actualiza la posición del láser según su velocidad y ángulo
        x += velocidad * Math.cos(angle);
        y += velocidad * Math.sin(angle);
        hitboxLaserAzul.setX(x += velocidad * Math.cos(angle));
        hitboxLaserAzul.setY( y += velocidad * Math.sin(angle));
        
        // Verifica si el láser sale de los límites de la pantalla
        if (x <= 31 || x >= 875 || y < 30 || y > 682) {
            debeEliminar = true; // Marca el láser para su eliminación
        }
    }
    
    ////////////////////////////////////////////////Método de Dibujo ///////////////////////////////////////////////////////////
    
    public void dibujarImagen(Entorno e){
        // Dibuja la imagen del láser
    	hitboxLaserAzul.dibujarHitbox(e);
    	e.dibujarImagen(imagen, x, y, 0, 2.0);
	}
    
    //////////////////////////////////////////////// Método si se debe eliminar /////////////////////////////////////////////////////
    
    public boolean debeEliminar() {
        return debeEliminar;
    }
    
    ///////////////////////////////////////////// Métodos de colisión /////////////////////////////////////////////////////////
    
    public void colisionLaserMapa() {
    	if( ((x >= 110 && x <= 269) && (y >= 506 && y <= 678)) || ((x >= 374 && x <= 533) && (y >= 506 && y <= 678)) ||
    			((x >= 644 && x <= 812) && (y >= 506 && y <= 678)) || ((x >= 110 && x <= 269) && (y >= 278 && y <= 437)) ||
    			((x >= 374 && x <= 533) && (y >= 278 && y <= 437)) || ((x >= 644 && x <= 812) && (y >= 278 && y <= 437)) ||
    			((x >= 110 && x <= 269) && (y >= 54 && y <= 215)) || ((x >= 374 && x <= 533) && (y >= 54 && y <= 215)) ||
    			((x >= 644 && x <= 812) && (y >= 54 && y <= 215))) {
    		debeEliminar=true;
    	}
    }
    
    public void colisionLaserAyLaserR(List<LaserRojo> lasers) {
    	for(LaserRojo l: lasers) {
    		if(l.getX() + l.getAncho()/2 <= hitboxLaserAzul.getX()+ hitboxLaserAzul.getAncho()/2 
    				&& l.getX() - l.getAncho()/2 >= hitboxLaserAzul.getX() - hitboxLaserAzul.getAncho()/2 &&
    				l.getY() - l.getAlto()/2 >= hitboxLaserAzul.getY()- hitboxLaserAzul.getAlto()/2 &&
    				l.getY() + l.getAlto()/2 <= hitboxLaserAzul.getY()+ hitboxLaserAzul.getAlto()/2) {
    			l.setDebeEliminar(true);
    			debeEliminar=true;
    		}
    	}		    			   
   }
}
