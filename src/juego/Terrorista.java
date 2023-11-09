package juego;

import java.awt.Image;
import java.util.List;
import entorno.Entorno;
import entorno.Herramientas;

public class Terrorista {
	private double x;
    private double y;
    private int ancho;
    private int alto;
    private double velocidad;
    private Image imagen;
    private long tiempoUltimoDisparo = 0;

    public Terrorista(double x, double y, int ancho, int alto, double v, Image imagen){
        this.x = x;
        this.y = y; 
        this.ancho = ancho;
        this.alto = alto;
        this.velocidad = v;
        this.imagen = imagen;
    } 
    
    public double getX(){ //Devuelve la coordenada x del terrorista
        return x;
    }
    public double getY(){ //Devuelve la coordenada y del terrorista
        return y;
    }
    
    public double getAncho() { //Devuelve el ancho del terrorista
    	return ancho;
    }
    
    public double getAlto() { //Devuelve el ancho del terrorista
    	return alto;
    }  
    
    ///////////////////////////////////////////// Métodos de desplazamiento ////////////////////////////////////////////////
    
    public void desplazarseIzq(){ //Mueve el terrorista hacia la izquierda
        x -= velocidad;
    }
    public void desplazarseDer(){ //Mueve el terrorista hacia la derecha
        x += velocidad;
    }
    
    public void desplazarseArb(){ //Mueve el terrorista hacia arriba
        y -= velocidad;
    }
    public void desplazarseAbj(){ //Mueve el terrorista hacia abajo
        y += velocidad;
    }

    //////////////////////////////////////////////// Método de Dibujo /////////////////////////////////////////////////////
    
    public void dibujar(Entorno e){ //Dibuja el terrorista en el entorno gráfico utilizando la imagen almacenada
    	e.dibujarImagen(imagen, x, y, 0);
    }
    
    ///////////////////////////////////////////// Métodos de colisión /////////////////////////////////////////////////////
    
    public boolean chocasteConH(Entorno e, int i) { //Verifica si el terrorista choca con los bordes horizontales
		return x <= 0 + this.ancho ||x >= e.ancho() - i;		
	}
    
    public boolean chocasteConV(Entorno e, int i) { //Verifica si el terrorista choca con los bordes horizontales
		return y <= 0 + this.alto ||y >= e.alto() - i;		
	}
    
    public void cambiarTrayectoria() {
    	//Cambia la dirección del terrorista invirtiendo su velocidad y actualiza la imagen según su nueva dirección
    	velocidad *= -1;
            if (velocidad > 0) {
                imagen = Herramientas.cargarImagen("TerroristaDer.png");
            } else {
                imagen = Herramientas.cargarImagen("TerroristaIzq.png");
            }
	}
    
    ///////////////////////////////////////////// Métodos de colisión con otros objetos //////////////////////////////////
    
    public boolean chocasteConRambo(Rambo rambo) {
    	// Verifica si el terrorista colisiona con rambo
        return (x + ancho >= rambo.getX() && x <= rambo.getX() + rambo.getAncho() &&
                y + alto >= rambo.getY() && y <= rambo.getY() + rambo.getAlto());
    }
    
    public boolean chocasteConRambo2(Rambo2 rambo) {
    	// Verifica si el terrorista colisiona con rambo2
        return (x + ancho >= rambo.getX() && x <= rambo.getX() + rambo.getAncho() &&
                y + alto >= rambo.getY() && y <= rambo.getY() + rambo.getAlto());
    }
    
    public boolean chocasteConLaserVerde(List<LaserVerde> lasers, Terrorista[] terrorista, int indice) {
    	// Verifica si el laser verde colisiona con el terrorista
    	for(LaserVerde l: lasers) {
    		if(l.getX()+ l.getAncho() >= terrorista[indice].getX() && l.getX() <= terrorista[indice].getX() + terrorista[indice].getAncho() &&
                    l.getY() + l.getAlto() >= terrorista[indice].getY() && l.getY() <= terrorista[indice].getY() + terrorista[indice].getAlto()) {
    			lasers.remove(l);
    			Juego.bloqueoDisparo=false;
    			return true;
    		}
    	}
    	return false;
    }
    
    public boolean chocasteConLaserAzul(List<LaserAzul> lasers, Terrorista[] terrorista, int indice) {
    	// Verifica si el laser azul colisiona con el terrorista
    	for(LaserAzul l: lasers) {
    		if(l.getX()+ l.getAncho() >= terrorista[indice].getX() && l.getX() <= terrorista[indice].getX() + terrorista[indice].getAncho() &&
                    l.getY() + l.getAlto() >= terrorista[indice].getY() && l.getY() <= terrorista[indice].getY() + terrorista[indice].getAlto()) {
    			lasers.remove(l);
    			Juego.bloqueoDisparo2=false;
    			return true;
    		}
    	}
    	return false;
    }
    
    public boolean laserRojoImpactaRambo(List<LaserRojo> lasers, Hitbox hitbox) {
    	//Verifica si un láser rojo colisiona con una hitbox asociada a rambo
    	for(LaserRojo l: lasers) {
    		if(l.getX() + l.getAncho()/2 <= hitbox.getX()+ hitbox.getAncho()/2 
    				&& l.getX() - l.getAncho()/2 >= hitbox.getX() - hitbox.getAncho()/2 &&
    				l.getY() - l.getAlto()/2 >= hitbox.getY()-hitbox.getAlto()/2 &&
    				l.getY() + l.getAlto()/2 <= hitbox.getY()+hitbox.getAlto()/2) {
    			lasers.remove(l);
    			return true;
    		}
    	}
    	return false;
    }
    
    public boolean laserRojoImpactaRambo2(List<LaserRojo> lasers, Hitbox hitbox) {
    	//Verifica si un láser rojo colisiona con una hitbox asociada a rambo2
    	for(LaserRojo l: lasers) {
    		if(l.getX() + l.getAncho()/2 <= hitbox.getX()+ hitbox.getAncho()/2 
    				&& l.getX() - l.getAncho()/2 >= hitbox.getX() - hitbox.getAncho()/2 &&
    				l.getY() - l.getAlto()/2 >= hitbox.getY()-hitbox.getAlto()/2 &&
    				l.getY() + l.getAlto()/2 <= hitbox.getY()+hitbox.getAlto()/2) {
    			lasers.remove(l);
    			return true;
    		}
    	}
    	return false;
    }
    
    ///////////////////////////////////////////// Método de disparo /////////////////////////////////////////////////////////
       
    public void disparar(Entorno e, List<LaserRojo> lasers){
    	long tiempoActual = System.currentTimeMillis();
    	
    	if (tiempoActual - tiempoUltimoDisparo >= 2000) { // 2000 milisegundos = 2 segundos
    		double centroX = getX(); // Coordenada X del centro de la planta
    		double centroY = getY(); // Coordenada Y del centro de la planta
    		double direccionDisparo; 
    		Image imagenDisparo;
    		
			if (velocidad > 0) {
				// Si la velocidad es positiva, significa que el proyectil se está moviendo hacia la derecha
				direccionDisparo = 0; // Disparo hacia la derecha
				imagenDisparo = Herramientas.cargarImagen("laserRedDer.png");
			} else {
				// Si la velocidad es negativa, el proyectil se está moviendo hacia la izquierda
				direccionDisparo = Math.PI; // Disparo hacia la izquierda
				imagenDisparo = Herramientas.cargarImagen("laserRedIzq.png");
			}

		// Crea un nuevo laser con la dirección determinada
        LaserRojo laser = new LaserRojo(centroX, centroY, 1, 6, direccionDisparo, 0, 0, imagenDisparo);
        lasers.add(laser);
        tiempoUltimoDisparo = tiempoActual;
    	}
    }
    
    ///////////////////////////////////////////// Método de aumento de velocidad /////////////////////////////////////////
    
    public void aumentarVelocidad(int nivel){
    	// Aumentar la velocidad en función del nivel o según lo desees
        velocidad += nivel * 0.5; // Aumentar la velocidad en 0.5 unidades por nivel
    }
    
}
