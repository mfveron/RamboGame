package juego;

import java.awt.Image;
import java.util.List;
import entorno.Entorno;
import entorno.Herramientas;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class Rambo {
	public double x;
	private double y;
	private int ancho;
	private int alto;	
	private double factorDesplazamiento;
	private Image imagenDerecha;
	private Image imagenIzquierda;
	private Image imagenArriba;  
	private Image imagenAbajo;
	private int anchoPantalla;
	private int altoPantalla;
	private boolean mirandoDerecha;
	private boolean mirandoArriba;  
	private boolean mirandoAbajo;
	private boolean teclaArribaPresionada;
	private boolean teclaAbajoPresionada;
	private boolean teclaIzquierdaPresionada;
	private boolean teclaDerechaPresionada;
	private Clip sonidoDisparo;
	private boolean BloqW;
	private boolean BloqA;
	private boolean BloqS;
	private boolean BloqD;

	public Rambo(double x, double y, int ancho, int alto, double f, Image imagenDerecha, Image imagenIzquierda,Image imagenArriba, Image imagenAbajo, int anchoPantalla, int altoPantalla) {
		this.x = x;
		this.y = y;
		this.ancho = ancho;
		this.alto = alto;
		this.factorDesplazamiento = f;
		this.imagenDerecha = imagenDerecha;
		this.imagenIzquierda = imagenIzquierda;
		this.imagenArriba = imagenArriba;
		this.imagenAbajo = imagenAbajo;
		this.mirandoDerecha = false;// Por defecto, el personaje comienza mirando hacia la izquierda
		this.mirandoArriba = false;
		this.mirandoAbajo = false;
		this.anchoPantalla = anchoPantalla;
		this.altoPantalla = altoPantalla;
		teclaArribaPresionada = false;
		teclaAbajoPresionada = false;
		teclaIzquierdaPresionada = false;
		teclaDerechaPresionada = false;

		try {
			// Cargar el sonido del disparo
			File sonidoFile = new File("Recursos/Blaster.wav");
			sonidoDisparo = AudioSystem.getClip();
			sonidoDisparo.open(AudioSystem.getAudioInputStream(sonidoFile));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public double getX() { //Devuelve la coordenada x del personaje
		return x;
	}

	public double getY() { //Devuelve la coordenada y del personaje
		return y;
	}

	public int getAncho() { //Devuelve el ancho del personaje
		return ancho;
	}

	public int getAlto() { //Devuelve el ancho del personaje
		return alto;
	}

	
	//////////////////////////////////////////////// Método de Dibujo /////////////////////////////////////////////////////
	
	public void dibujarImagen(Entorno e){ 
		// Dibuja la imagen del personaje en la pantalla, dependiendo de la dirección en la que está mirando
		if (mirandoDerecha) {
			e.dibujarImagen(imagenDerecha, x, y, 0);
		} else if (mirandoArriba) {
			e.dibujarImagen(imagenArriba, x, y, 0);
		} else if (mirandoAbajo) {
			e.dibujarImagen(imagenAbajo, x, y, 0);
		} else {
			e.dibujarImagen(imagenIzquierda, x, y, 0);
		}
	}

	///////////////////////////////////////////// Métodos de desplazamiento ////////////////////////////////////////////////
	
	public void moverIzquierda(Entorno e) {
		//Controla el movimiento del personaje en la direccion izquierda
		if (e.estaPresionada('a') && !limitesMapaIzquierda() && BloqA==false) {
			//verifica límites del mapa y limitan el movimiento
			BloqW=true; //Bloqueo de teclas
			BloqS=true;
			x -= factorDesplazamiento;
			mirandoDerecha = false;
			mirandoArriba = false;
			mirandoAbajo = false;
			verificarLimitesPantalla();
			// Actualiza el estado de las teclas
			teclaArribaPresionada = false;
			teclaAbajoPresionada = false;
			teclaIzquierdaPresionada = true;
			teclaDerechaPresionada = false;
		}
	}

	public void moverDerecha(Entorno e) {
		//Controla el movimiento del personaje en la direccion derecha
		if (e.estaPresionada('d') && !limitesMapaDerecha() && BloqD==false) {
			//verifica límites del mapa y limitan el movimiento
			BloqW=true; //Bloqueo de teclas
			BloqS=true;
			x += factorDesplazamiento;
			mirandoDerecha = true;
			mirandoArriba = false;
			mirandoAbajo = false;
			verificarLimitesPantalla();
			// Actualiza el estado de las teclas
			teclaArribaPresionada = false;
			teclaAbajoPresionada = false;
			teclaIzquierdaPresionada = false;
			teclaDerechaPresionada = true;
		}
	}

	public void moverArriba(Entorno e) {
		//Controla el movimiento del personaje en la direccion arriba
		if (e.estaPresionada('w') && !limitesMapaArriba() && BloqW==false) {
			//verifica límites del mapa y limitan el movimiento
			BloqA=true; //Bloqueo de teclas
			BloqD=true;
			y -= factorDesplazamiento;
			mirandoArriba = true;
			mirandoAbajo = false;
			mirandoDerecha = false;
			verificarLimitesPantalla();
			// Actualiza el estado de las teclas
			teclaArribaPresionada = true;
			teclaAbajoPresionada = false;
			teclaIzquierdaPresionada = false;
			teclaDerechaPresionada = false;
		}
		BloqA=false; 
		BloqD=false;
	}

	public void moverAbajo(Entorno e) {
		//Controla el movimiento del personaje en la direccion abajo
		if (e.estaPresionada('s') && !limitesMapaAbajo() && BloqS==false) {
			//verifica límites del mapa y limitan el movimiento
			BloqA=true; //Bloqueo de teclas
			BloqD=true;
			y += factorDesplazamiento;
			mirandoAbajo = true;
			mirandoArriba = false;
			mirandoDerecha = false;
			verificarLimitesPantalla();
			// Actualiza el estado de las teclas
			teclaArribaPresionada = false;
			teclaAbajoPresionada = true;
			teclaIzquierdaPresionada = false;
			teclaDerechaPresionada = false;
		}
		BloqW=false;
		BloqA=false;
		BloqD=false;
		BloqS=false;
	}

	/////////////////////////////////////////////// Métodos de limites //////////////////////////////////////////////////
	
	public void verificarLimitesPantalla() {
		// Verifica si el personaje se encuentra fuera de los límites de la pantalla
		if (x < 32) {
			x = 32; // Ajusta la posición a la izquierda de la pantalla
		} else if (x + ancho > anchoPantalla) {
			x = anchoPantalla - ancho; // Ajusta la posición a la derecha de la pantalla
		}

		if (y < 32) {
			y = 32; // Ajusta la posición arriba de la pantalla
		} else if (y + alto > altoPantalla) {
			y = altoPantalla - alto; // Ajusta la posición abajo de la pantalla
		}
	}

	public boolean limitesMapaArriba() {
		if (y == 680 || y == 682 || y == 679) {
			if ((x >= 110 && x <= 269) || (x >= 374 && x <= 533) || (x >= 644 && x <= 812)) {
				return true;
			}
		}
		else if(y==442 || y == 440 || y == 439) {
			if((x >= 110 && x <= 269) || (x >= 374 && x <= 533) || (x >= 644 && x <= 812)) {
				return true;
			}
		}
		else if(y==220 || y == 218 || y == 217) {
			if((x >= 110 && x <= 269) || (x >= 374 && x <= 533) || (x >= 644 && x <= 812)) {
				return true;
			}
		}
		return false;
	}

	public boolean limitesMapaAbajo() {
		if(y==499 || y == 506) {
			if ((x >= 110 && x <= 269) || (x >= 374 && x <= 533) || (x >= 644 && x <= 812)) {
				return true;
			}
		}
		else if(y==277 || y == 275) {
			if ((x >= 110 && x <= 269) || (x >= 374 && x <= 533) || (x >= 644 && x <= 812)) {
				return true;
			}
		}
		else if(y==52 || y == 53) {
			if ((x >= 110 && x <= 269) || (x >= 374 && x <= 533) || (x >= 644 && x <= 812)) {
				return true;
			}
		}
		return false;
	}

	public boolean limitesMapaIzquierda() {
		if(x==269) {
			if ((y >= 506 && y <= 678) || (y >= 278 && y <= 437) || (y >= 54 && y <= 215)) {
				return true;
			}
		}
		else if(x==536) {
			if ((y >= 506 && y <= 678) || (y >= 278 && y <= 437) || (y >= 54 && y <= 215)) {
				return true;
			}
		}
		else if(x==809) {
			if ((y >= 506 && y <= 678) || (y >= 278 && y <= 437) || (y >= 54 && y <= 215)) {
				return true;
			}
		}
		return false;
	}

	public boolean limitesMapaDerecha() {
		if(x==104) {
			if ((y >= 506 && y <= 678) || (y >= 278 && y <= 437) || (y >= 54 && y <= 215)) {
				return true;
			}
		}
		else if(x==374) {
			if ((y >= 506 && y <= 678) || (y >= 278 && y <= 437) || (y >= 54 && y <= 215)) {
				return true;
			}
		}
		else if(x==644) {
			if ((y >= 506 && y <= 678) || (y >= 278 && y <= 437) || (y >= 54 && y <= 215)) {
				return true;
			}
		}
		return false;
	}

	///////////////////////////////////////////// Método de disparo /////////////////////////////////////////////////////////
	
	public void disparar(Entorno e, List<LaserVerde> lasers){
		if (e.sePresiono(e.TECLA_ESPACIO) && Juego.bloqueoDisparo==false) {
			if (sonidoDisparo != null) {
				sonidoDisparo.setFramePosition(0); // Reinicia la reproducción al principio
				sonidoDisparo.start();
			}
			Juego.bloqueoDisparo=true;

			// Determina la dirección del disparo según la última tecla presionada

			double centroX = getX() + getAncho() / 2; // Coordenada X del centro del personaje
			double centroY = getY() + getAlto()/2; // Coordenada Y del centro del personaje

			Image imagenDisparo = Herramientas.cargarImagen("laserGreenIzq.png"); // Imagen por defecto Izquierda
			// Crea un nuevo láser con la dirección determinada
			
			// Crea la hitbox del laser
			Hitbox hitboxLaser = new Hitbox(centroX, centroY, 86, 8);

			double direccionDisparo = Math.PI; // Por defecto, dispara hacia la Izquierda

			if (teclaArribaPresionada) {
				direccionDisparo = 3 * Math.PI / 2; // Disparo hacia arriba
				imagenDisparo = Herramientas.cargarImagen("laserGreenArr.png");
				hitboxLaser.setAncho(8);
				hitboxLaser.setAlto(86);
			} else if (teclaAbajoPresionada) {
				direccionDisparo = Math.PI / 2; // Disparo hacia abajo
				imagenDisparo = Herramientas.cargarImagen("laserGreenAbj.png");
				hitboxLaser.setAncho(8);
				hitboxLaser.setAlto(86);
			} else if (teclaIzquierdaPresionada) {
				direccionDisparo = Math.PI; // Disparo hacia la izquierda
				imagenDisparo = Herramientas.cargarImagen("laserGreenIzq.png");
				hitboxLaser.setAncho(86);
				hitboxLaser.setAlto(8);
			} else if (teclaDerechaPresionada) {
				direccionDisparo = 0; // Disparo hacia la derecha
				imagenDisparo = Herramientas.cargarImagen("laserGreenDer.png");
				hitboxLaser.setAncho(86);
				hitboxLaser.setAlto(8);
			}

			LaserVerde laser = new LaserVerde(centroX, centroY, 1, 5, direccionDisparo, 25, 0, imagenDisparo, hitboxLaser);
			lasers.add(laser);	            	        	            	            
		}
	}
}
