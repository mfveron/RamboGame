package juego;
import java.awt.Image;
import java.util.List;
import entorno.Entorno;
import entorno.Herramientas;

public class Jeep {
	private double x;
    private double y;
    private int ancho;
    private int alto;
    private double velocidad;
    private Image imagen;
    private boolean esVertical;

    public Jeep(double x, double y, int ancho, int alto, double v,boolean esVertical, Image imagen){
        this.x = x;
        this.y = y; 
        this.ancho = ancho;
        this.alto = alto;
        this.velocidad = v;
        this.esVertical = esVertical;
        this.imagen = imagen;
    } 
    
    public double getX(){ //Devuelve la coordenada x del jeep
        return x;
    }
    public double getY(){ //Devuelve la coordenada y del jeep
        return y;
    }
    
    public int getAncho() { //Devuelve el ancho del jeep
		return ancho;
	}
	
	public int getAlto() { //Devuelve el alto del jeep
		return alto;
	}
	
	///////////////////////////////////////////// Métodos de desplazamiento ////////////////////////////////////////////////
	
	public void desplazarseIzq(){ //Mueve el jeep hacia la izquierda
        x -= velocidad;
    }
    public void desplazarseDer(){ //Mueve el jeep hacia la derecha
        x += velocidad;
    }
    
    public void desplazarseArb(){ //Mueve el jeep hacia arriba
        y -= velocidad;
    }
    public void desplazarseAbj(){ //Mueve el jeep hacia abajo
        y += velocidad;
    }
    
    //////////////////////////////////////////////// Metodo de Dibujo //////////////////////////////////////////////////////
    
    public void dibujar(Entorno e){ //Dibuja el jeep en el entorno gráfico utilizando la imagen almacenada
    	e.dibujarImagen(imagen, x, y, 0);
    }
    
    ///////////////////////////////////////////// Métodos de colisión /////////////////////////////////////////////////////
    
    public boolean chocasteConH(Entorno e, int i) { //Verifica si el jeep choca con los bordes horizontales 
		return x <= 0 + this.ancho ||x >= e.ancho() - i;		
	}
    
    public boolean chocasteConV(Entorno e, int i) { //Verifica si el jeep choca con los bordes verticales
		return y <= 0 + this.alto ||y >= e.alto() - i;		
	}
    
    public void cambiarTrayectoria() {
    	//Cambia la dirección del jeep invirtiendo su velocidad y actualiza la imagen según su nueva dirección
    	velocidad *= -1;
    	if (esVertical) {
            if (velocidad > 0) {
                imagen = Herramientas.cargarImagen("JeepAbj.png");
            } else {
                imagen = Herramientas.cargarImagen("JeepArr.png");
            }
        } else {
            if (velocidad > 0) {
                imagen = Herramientas.cargarImagen("JeepDer.png");
            } else {
                imagen = Herramientas.cargarImagen("JeepIzq.png");
            }
        }
	}
    ///////////////////////////////////////////// Métodos de colisión con otros objetos //////////////////////////////////
    
    public boolean chocasteConRambo(Rambo rambo) {
        // Verifica si el jeep colisiona con rambo
        return (x + ancho >= rambo.getX() && x <= rambo.getX() + rambo.getAncho() &&
                y + alto >= rambo.getY() && y <= rambo.getY() + rambo.getAlto());
    }
    
    public boolean chocasteConRambo2(Rambo2 rambo) {
    	// Verifica si el jeep colisiona con rambo2
        return (x + ancho >= rambo.getX() && x <= rambo.getX() + rambo.getAncho() &&
                y + alto >= rambo.getY() && y <= rambo.getY() + rambo.getAlto());
    }
    
    public boolean chocasteConLaserRojo(List<LaserRojo> lasers, Jeep[] jeep, int indice) {
    	// Verifica si el laser rojo colisiona con el jeep
    	for(LaserRojo l: lasers) {
    		if(l.getX()+ l.getAncho() >= jeep[indice].getX() && l.getX() <= jeep[indice].getX() + jeep[indice].getAncho() &&
                    l.getY() + l.getAlto() >= jeep[indice].getY() && l.getY() <= jeep[indice].getY() + jeep[indice].getAlto()) {
    			lasers.remove(l);
    			return true;
    		}
    	}
    	return false;
    }
    
    public boolean chocasteConLaserVerde(List<LaserVerde> lasers, Jeep[] jeep, int indice) {
    	// Verifica si el laser verde colisiona con el jeep
    	for(LaserVerde l: lasers) {
    		if(l.getX()+ l.getAncho() >= jeep[indice].getX() && l.getX() <= jeep[indice].getX() + jeep[indice].getAncho() &&
                    l.getY() + l.getAlto() >= jeep[indice].getY() && l.getY() <= jeep[indice].getY() + jeep[indice].getAlto()) {
    			lasers.remove(l);
    			Juego.bloqueoDisparo=false;
    			return true;
    		}
    	}
    	return false;
    }
    
    ///////////////////////////////////////////// Método de aumento de velocidad /////////////////////////////////////////
    
    public void aumentarVelocidad(int nivel){
    	// Aumentar la velocidad en función del nivel
        velocidad += nivel * 1; // Aumentar la velocidad en 0.5 unidades por nivel
    }
}
