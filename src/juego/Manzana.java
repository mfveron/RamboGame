package juego;

import java.awt.Color;
import entorno.Entorno;

public class Manzana {
	private double x;
	private double y;
	private double ancho;
	private double largo;
	private Color color;
	
	public Manzana(double x, double y, double ancho, double largo, Color color) {
		this.x = x;
		this.y = y;
		this.ancho = ancho;
		this.largo = largo;
		this.color=color;
	}
	
	public static Manzana[] crearManzana(Manzana Manzana[]) {
		Manzana= new Manzana[10];
		Manzana[0]= new Manzana(460, 140, 140, 110, Color.blue);
		Manzana[1]= new Manzana(460, 361, 140, 110, Color.blue);
		Manzana[2]= new Manzana(460, 588, 140, 110, Color.blue);
		Manzana[3]= new Manzana(189.5, 140, 140, 110, Color.blue);
		Manzana[4]= new Manzana(189.5, 361, 140, 110, Color.blue);
		Manzana[5]= new Manzana(189.5, 588, 140, 110, Color.blue);
		Manzana[6]= new Manzana(730.5, 140, 140, 110, Color.blue);
		Manzana[7]= new Manzana(730.5, 361, 140, 110, Color.blue);
		Manzana[8]= new Manzana(730.5, 588, 140, 110, Color.blue);
		Manzana[9]= new Manzana(913, 380, 25, 759, Color.gray);
		return Manzana;
	}
	
	public void dibujar(Entorno e) {
		e.dibujarRectangulo(x, y, ancho, largo, 0, color);
	}

	public double getX() {
		return this.x; 
	}

	public double getY() {
		return this.y; 
	}

	public double getAncho() {
		return this.ancho;
	}

	public double getAlto() {
		return this.largo; 
	}
}
