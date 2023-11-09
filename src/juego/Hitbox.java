package juego;

import java.awt.Color;
import entorno.Entorno;

public class Hitbox {
	private double x;
	private double y;
	private double ancho;
	private double alto;
	
	public Hitbox(double x, double y, double ancho, double alto) {
		this.x=x;
		this.y=y;
		this.ancho=ancho;
		this.alto=alto;
	}
	
	public double getX() { //Devuelve la coordenada x 
		return x;
	}

	public double getY() { //Devuelve la coordenada y
		return y;
	}

	public double getAncho() { //Devuelve el ancho
		return ancho;
	}

	public double getAlto() { //Devuelve el alto
		return alto;
	}
	
	public double setX(double x) { //estable el valor de x 
		return this.x=x;
	}
	
	public double setY(double y) { //estable el valor de y
		return this.y=y;
	}
	
	public double setAncho(double ancho) { //estable el valor del ancho
		return this.ancho=ancho;
	}
	
	public double setAlto(double alto) { ////estable el valor del alto
		return this.alto=alto;
	}
	
	//////////////////////////////////////////////// Metodo de Dibujo //////////////////////////////////////////////////////
	
	public void dibujarHitbox(Entorno e) {
		e.dibujarRectangulo(x, y, ancho, alto, 0, Color.gray);
	}
}
