package juego;
import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import entorno.Entorno;
import entorno.Herramientas;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego
{
	// El objeto Entorno que controla el tiempo y otros
	private Entorno entorno;
    private Manzana[] manzanas;
    private Jeep[] jeepVertical;
    private Jeep[] jeepHorizontal;
    private Tanque[] tanqueVertical;
    private Tanque[] tanqueHorizontal;
    private Terrorista[] terrorista;
    private Rambo rambo;
    private Rambo2 rambo2;
    private Hitbox hitbox;
    private Hitbox hitboxRambo2;
    private List<LaserVerde> lasersV; // Lista de láseres disparados
    private List<LaserRojo> lasersR;
    private List<LaserAzul> lasersA;
    private int vidas = 3;
    private int puntaje = 0;
    private int tanquesEliminados =0;
    private long tiempoInicial;
    private int duracion = 120;
    private MenuJuego menuJuego; 
    private boolean gameOver = false;
    private boolean herido = false;
    private int tiempoInmune;
    private int puntajeNivel = 20;
    private int tiempoReaparicionTanqueVertical;
    private int tiempoReaparicionTanqueHorizontal;
    private int tiempoReaparicionJeepVertical;
    private int tiempoReaparicionJeepHorizontal;
    static boolean bloqueoDisparo; //Bloqueo de disparo para Rambo
    static boolean bloqueoDisparo2; //Bloqueo de disparo para Rambo2
    private int nivel = 1;
    private boolean ganaste = false;
    private boolean enemigoNuevo = false;
    private int tiempoReaparicionTerroristas;
    private File file_stageclear = new File("Recursos/stageClear.wav");
    private File file_gameOver = new File("Recursos/gameOver.wav");
    private File file_music_ingame = new File("Recursos/sound_ingame.wav");
    private File file_ramboFrase1 = new File("Recursos/rambo_frase1.wav");
    private File file_ramboFrase2 = new File("Recursos/rambo_frase2.wav");
    private File file_godlike = new File("Recursos/godlike.wav");
    private File file_dominating = new File("Recursos/dominating.wav");
    private File file_golpe = new File("Recursos/rambo_dolor.wav");
    private File file_end_music = new File("Recursos/end_music.wav");
    private SFX sonido_stageclear;
    private SFX sonido_gameover;
    private SFX music_ingame;
    static SFX[] sonidos_rambo;
    private SFX sonido_golpe;
    private SFX endMusic;
    private boolean pantallaCreditos = false;
    static int permite_frase = 10;
    static int indice;
    
    
	Juego() throws UnsupportedAudioFileException, IOException, LineUnavailableException
	{	// Inicializar lo que haga falta para el juego
		
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Rambo - Grupo 7", 1280, 720);
		
		// Inicializa el objeto sonido
		sonido_stageclear= new SFX(file_stageclear);
		sonido_gameover= new SFX(file_gameOver);
		music_ingame= new SFX(file_music_ingame);
		endMusic= new SFX(file_end_music);
			
		// Inicializa el array de sonidos de Rambo
		sonidos_rambo = new SFX[4];
		sonidos_rambo[0]= new SFX(file_ramboFrase1);
		sonidos_rambo[1]= new SFX(file_ramboFrase2);
		sonidos_rambo[2]= new SFX(file_godlike);
		sonidos_rambo[3]= new SFX(file_dominating);	
		sonido_golpe= new SFX(file_golpe);
			
		// Inicializa el objeto random para el arreglo de sonidos
		try {
			this.menuJuego = new MenuJuego(); // Inicialización
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		}

		///////////////////////////////////////////////////// Manzanas ///////////////////////////////////////////////////
		manzanas = Manzana.crearManzana(manzanas);
		
		///////////////////////////////////////////////////// Rambo y Rambo2 ///////////////////////////////////////////////////
		rambo = new Rambo(455,680,0,0,3,Herramientas.cargarImagen("RamboDer.png"),
		Herramientas.cargarImagen("RamboIzq.png"),Herramientas.cargarImagen("RamboArr.png"), 
		Herramientas.cargarImagen("RamboAbj.png"), 905, 682);
		
		rambo2 = new Rambo2(35,680,0,0,3,Herramientas.cargarImagen("Rambo2Der.png"),
		Herramientas.cargarImagen("Rambo2Izq.png"),Herramientas.cargarImagen("Rambo2Arr.png"), 
		Herramientas.cargarImagen("Rambo2Abj.png"), 905, 682);
		//
			
		///////////////////////////////////////////////////// Jeep Verticales ///////////////////////////////////////////////////
		jeepVertical = new Jeep[2];
		jeepVertical[0] = new Jeep(25,248,20,50,5, true, Herramientas.cargarImagen("JeepAbj.png"));//Auto Vertical
		jeepVertical[1] = new Jeep(560,300,20,50,5, true, Herramientas.cargarImagen("JeepAbj.png"));//Auto Vertical
		//
		
		///////////////////////////////////////////////////// Jeep Horizontales ///////////////////////////////////////////////////
		jeepHorizontal = new Jeep[1];
		jeepHorizontal[0] = new Jeep(50,215,50,20,5, false, Herramientas.cargarImagen("JeepDer.png"));//Auto Horizontal
		
		///////////////////////////////////////////////////// Tanques Verticales ///////////////////////////////////////////////////
		tanqueVertical = new Tanque[2];
		tanqueVertical[0] = new Tanque(856,500,50,50,2, true, Herramientas.cargarImagen("TanqueAbj.png"));
		tanqueVertical[1] = new Tanque(325,200,50,50,2, true, Herramientas.cargarImagen("TanqueAbj.png"));
		
		///////////////////////////////////////////////////// Tanques Horizontales ///////////////////////////////////////////////////
		tanqueHorizontal = new Tanque[2];
		tanqueHorizontal[0] = new Tanque(50,40,50,50,2, false, Herramientas.cargarImagen("TanqueDer.png"));
		tanqueHorizontal[1] = new Tanque(500,470,50,50,2, false, Herramientas.cargarImagen("TanqueDer.png"));
			
		///////////////////////////////////////////////////// Lasers Verde ///////////////////////////////////////////////////
		lasersV = new ArrayList<>();
		
		///////////////////////////////////////////////////// Lasers Rojo ///////////////////////////////////////////////////
		lasersR = new ArrayList<>();
		
		///////////////////////////////////////////////////// Lasers Azul ///////////////////////////////////////////////////
		lasersA = new ArrayList<>();
		
		// Inicia el juego!
		this.entorno.iniciar();
	}
	
	private void reiniciarJuego(boolean esNivel1) {
		if(esNivel1) {// Si esta en nivel 1 reestablece el juego desde el principio
			// Restablece las variables del juego a su estado inicial
			music_ingame.reproducir_audio_en_bucle();
			vidas = 3;
			puntaje = 0;
			tanquesEliminados = 0;
			tiempoInicial = 0;
			gameOver = false;
			herido = false;
			tiempoInmune = 0;
			enemigoNuevo = false;
			puntajeNivel = 20;
			permite_frase = 10;

			// Reinicializa los elementos del juego, como posición de personaje, manzanas, etc.
			
			/////////////////////////////////////////////////// Rambo y Rambo2 /////////////////////////////////////////////////
			rambo = new Rambo(455, 680, 0, 0, 3, Herramientas.cargarImagen("RamboDer.png"),
			Herramientas.cargarImagen("RamboIzq.png"), Herramientas.cargarImagen("RamboArr.png"),
			Herramientas.cargarImagen("RamboAbj.png"), 905, 680);
			
			rambo2 = new Rambo2(35,680,0,0,3,Herramientas.cargarImagen("Rambo2Der.png"),
			Herramientas.cargarImagen("Rambo2Izq.png"),Herramientas.cargarImagen("Rambo2Arr.png"), 
			Herramientas.cargarImagen("Rambo2Abj.png"), 905, 682);
			//
			
			/////////////////////////////////////////////////// Tanques Verticales /////////////////////////////////////////////////
			tanqueVertical = new Tanque[2];
			tanqueVertical[0] = new Tanque(856, 500, 50, 50, 2, true, Herramientas.cargarImagen("TanqueAbj.png"));
			tanqueVertical[1] = new Tanque(325, 200, 50, 50, 2, true, Herramientas.cargarImagen("TanqueAbj.png"));
			//

			/////////////////////////////////////////////////// Tanques Horizontales /////////////////////////////////////////////////
			tanqueHorizontal = new Tanque[2];
			tanqueHorizontal[0] = new Tanque(50, 40, 50, 50, 2, false, Herramientas.cargarImagen("TanqueDer.png"));
			tanqueHorizontal[1] = new Tanque(500, 470, 50, 50, 2, false, Herramientas.cargarImagen("TanqueDer.png"));
			//

			/////////////////////////////////////////////////// Jeep Verticales /////////////////////////////////////////////////
			jeepVertical = new Jeep[2];
			jeepVertical[0] = new Jeep(25, 248, 20, 50, 5, true, Herramientas.cargarImagen("JeepAbj.png"));																	
			jeepVertical[1] = new Jeep(560, 300, 20, 50, 5, true, Herramientas.cargarImagen("JeepAbj.png"));
																											
			/////////////////////////////////////////////////// Jeep Horizontales /////////////////////////////////////////////////
			jeepHorizontal = new Jeep[1];
			jeepHorizontal[0] = new Jeep(50, 215, 50, 20, 5, false, Herramientas.cargarImagen("JeepDer.png"));
			
			///////////////////////////////////////////////// Lasers Verde /////////////////////////////////////////////////
			// Limpia la lista de láseres
			lasersV.clear();
			
			///////////////////////////////////////////////// Temporizador /////////////////////////////////////////////////
			// Inicia nuevamente el temporizador
			tiempoInicial = System.currentTimeMillis();
			
		}else { // Reestablece el juego con la dificultad obtenida al pasar de nivel
			// Restablece las variables del juego a su estado inicial
			music_ingame.reproducir_audio_en_bucle();
			vidas = 3;
			puntaje = 0;
			tanquesEliminados = 0;
			tiempoInicial = 0;
			gameOver = false;
			herido = false;
			tiempoInmune = 0;
			permite_frase = 10;

			// Reinicializa los elementos del juego, como posición de personaje, manzanas, etc.
			
			/////////////////////////////////////////////////// Rambo y Rambo2 /////////////////////////////////////////////////
			rambo = new Rambo(455, 680, 0, 0, 3, Herramientas.cargarImagen("RamboDer.png"),
			Herramientas.cargarImagen("RamboIzq.png"), Herramientas.cargarImagen("RamboArr.png"),
			Herramientas.cargarImagen("RamboAbj.png"), 905, 680);
			
			rambo2 = new Rambo2(35,680,0,0,3,Herramientas.cargarImagen("Rambo2Der.png"),
			Herramientas.cargarImagen("Rambo2Izq.png"),Herramientas.cargarImagen("Rambo2Arr.png"), 
			Herramientas.cargarImagen("Rambo2Abj.png"), 905, 682);
			
			/////////////////////////////////////////////////// Tanques Verticales /////////////////////////////////////////////////
			tanqueVertical = new Tanque[2];
			tanqueVertical[0] = new Tanque(856, 500, 50, 50, 2, true, Herramientas.cargarImagen("TanqueAbj.png"));
			tanqueVertical[1] = new Tanque(325, 200, 50, 50, 2, true, Herramientas.cargarImagen("TanqueAbj.png"));

			/////////////////////////////////////////////////// Tanques Horizontales /////////////////////////////////////////////////
			tanqueHorizontal = new Tanque[2];
			tanqueHorizontal[0] = new Tanque(50, 40, 50, 50, 2, false, Herramientas.cargarImagen("TanqueDer.png"));
			tanqueHorizontal[1] = new Tanque(500, 470, 50, 50, 2, false, Herramientas.cargarImagen("TanqueDer.png"));

			/////////////////////////////////////////////////// Jeep Verticales /////////////////////////////////////////////////
			jeepVertical = new Jeep[2]; 
			jeepVertical[0] = new Jeep(25, 248, 20, 50, 5, true, Herramientas.cargarImagen("JeepAbj.png"));
			jeepVertical[1] = new Jeep(560, 300, 20, 50, 5, true, Herramientas.cargarImagen("JeepAbj.png"));

			/////////////////////////////////////////////////// Jeep Horizontales /////////////////////////////////////////////////
			jeepHorizontal = new Jeep[1];
			jeepHorizontal[0] = new Jeep(50, 215, 50, 20, 5, false, Herramientas.cargarImagen("JeepDer.png"));
			
			/////////////////////////////////////////////////// Terrorista /////////////////////////////////////////////////
			terrorista = new Terrorista[2];
			terrorista[0] = new Terrorista(200, 270, 50, 50, 2, Herramientas.cargarImagen("TerroristaDer.png"));
			terrorista[1] = new Terrorista(700, 680, 50, 50, 2, Herramientas.cargarImagen("TerroristaDer.png"));

			/////////////////////////////////////////////////// Lasers Verde /////////////////////////////////////////////////
			//Limpia la lista de láseres
			lasersV.clear();
			
			///////////////////////////////////////////////// Temporizador /////////////////////////////////////////////////
			// Inicia nuevamente el temporizador
			tiempoInicial = System.currentTimeMillis();
		}
    }

	public void tick() // Procesamiento de un instante de tiempo
	{
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////// Logica Modo 1 jugador /////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    	if (menuJuego.estaJuego1JugComenzado()) {
    		music_ingame.reproducir_audio_en_bucle();
    		// Si el juego ha comenzado
    		///////////////////////////////////////////////// Pantalla si "ganaste" /////////////////////////////////////////////////
    		// Bloque para mostrar "Ganaste" y avanzar al siguiente nivel
            if (ganaste) {
            	music_ingame.detener_audio();
            	if (nivel == 6) {
            		// Lógica específica para el nivel 6
            		endMusic.reproducir_audio_en_bucle();
            		entorno.dibujarImagen(Herramientas.cargarImagen("WinScreenRambo.png"), entorno.ancho()/2, entorno.alto()/2, 0);
            		entorno.cambiarFont("Impact", 100, Color.RED);
                    entorno.escribirTexto("The End", 480, 150);
                    entorno.cambiarFont("Impact", 35, Color.WHITE);
					entorno.escribirTexto("Presione Enter para Continuar", 425, 300);
					if (entorno.sePresiono(entorno.TECLA_ENTER)) {
						pantallaCreditos = true;
					}
				if (pantallaCreditos) {
					entorno.dibujarRectangulo(entorno.ancho() / 2, entorno.alto() / 2, entorno.ancho(), entorno.alto(), 0, Color.BLACK);
					entorno.cambiarFont("Impact", 75, Color.RED);
					entorno.escribirTexto("Realizado por:", 150, 150);
					entorno.cambiarFont("Impact", 50, Color.WHITE);
					entorno.escribirTexto("Martinez Iraola Tobias", 150, 300);
					entorno.cambiarFont("Impact", 50, Color.WHITE);
					entorno.escribirTexto("Leonardo Agustin Plaza", 150, 400);
					entorno.cambiarFont("Impact", 50, Color.WHITE);
					entorno.escribirTexto("Matias Federico Veron", 150, 500);
					entorno.cambiarFont("Impact", 35, Color.YELLOW);
					entorno.escribirTexto("Presione F para salir", 500, 700);
					entorno.dibujarImagen(Herramientas.cargarImagen("chegames_Logo.png"), 1000, 300, 0);
					entorno.cambiarFont("Impact", 35, Color.WHITE);
					entorno.escribirTexto("2023", 960, 500);
					// Si el jugador presiona "F" se cierra el juego
	                if (entorno.sePresiono('f')) {
	                	System.exit(0);
	                }
				}
                    
            	}else {
					sonido_stageclear.reproducir_audio();
					entorno.dibujarImagen(Herramientas.cargarImagen("RamboWin.png"), 270, 510, 0);
					entorno.cambiarFont("Impact", 100, Color.GREEN);
					entorno.escribirTexto("¡Nivel " + nivel + " Completado!", 230, 150);
					entorno.cambiarFont("Impact", 35, Color.yellow);
					entorno.escribirTexto("Presione Enter para Continuar", 550, 400);
					entorno.cambiarFont("Impact", 30, Color.RED);
					entorno.escribirTexto("Presione F para Salir", 640, 500);

					if (entorno.sePresiono(entorno.TECLA_ENTER)) {
						sonido_stageclear.detener_audio();
						nivel++; // Avanza al siguiente nivel
						reiniciarJuego(false); // Reinicia el juego
						ganaste = false; // Restablece la bandera de "ganaste"

						// Aumentar la velocidad de los tanques Verticales
						for (Tanque tanque : tanqueVertical) {
							if (tanque != null) {
								tanque.aumentarVelocidad(nivel); // Aumenta la velocidad del tanque
							}
						}

						// Aumentar la velocidad de los tanques Horizontales
						for (Tanque tanque : tanqueHorizontal) {
							if (tanque != null) {
								tanque.aumentarVelocidad(nivel); // Aumenta la velocidad del tanque
							}
						}

						// Aumentar la velocidad de los jeep Verticales
						for (Jeep jeep : jeepVertical) {
							if (jeep != null) {
								jeep.aumentarVelocidad(nivel); // Aumenta la velocidad del jeep
							}
						}

						// Aumentar la velocidad de los jeep Horizontales
						for (Jeep jeep : jeepHorizontal) {
							if (jeep != null) {
								jeep.aumentarVelocidad(nivel); // Aumenta la velocidad del jeep
							}
						}
						
						//Logica de Niveles
						if (nivel == 2) {
							enemigoNuevo = true;
							puntajeNivel = 25;
							duracion -= 24;
						}
						if (nivel == 3) {
							enemigoNuevo = true;
							puntajeNivel = 30;
							duracion -= 24;
						}
						if (nivel == 4) {
							enemigoNuevo = true;
							puntajeNivel = 35;
							duracion -= 24;
						}
						if (nivel == 5) {
							enemigoNuevo = true;
							puntajeNivel = 35;
							duracion = 15;
						}
					}
				}
                // Si el jugador presiona "F" se cierra el juego
                if (entorno.sePresiono('f')) {
                	System.exit(0);
                }
                
            }else if (gameOver) {
            	///////////////////////////////////////////////////// Pantalla "Game Over" ///////////////////////////////////////////////////
            	music_ingame.detener_audio();
            	sonido_gameover.reproducir_audio();
            	nivel = 1;
            	duracion = 120;
    			entorno.dibujarImagen(Herramientas.cargarImagen("GameOver.png"), 630,200, 0);
                entorno.cambiarFont("Impact", 100, Color.RED);
                entorno.escribirTexto("G a m e  O v e r", 340, 150);
                
                entorno.cambiarFont("Impact", 50, Color.GREEN);
                entorno.escribirTexto("Presione Enter para Reiniciar", 340, 550);
                
                entorno.cambiarFont("Impact", 30, Color.YELLOW);
                entorno.escribirTexto("Presione F para Salir", 510, 650);
                
                  
                // Si el jugador presiona "ENTER" se reinicia el juego
                if (entorno.sePresiono(entorno.TECLA_ENTER)) {
                	sonido_gameover.detener_audio();
                    // Restablecer el juego
                    reiniciarJuego(true);
                }
                // Si el jugador presiona "F" se cierra el juego
                if (entorno.sePresiono('f')) {
                	System.exit(0);
                }
                
                
			} else {
				//Bloque de codigo de todo lo que pasa dutante el Juego
				
				if (tiempoInicial == 0) {
					// Inicia el temporizador cuando el juego comienza
					tiempoInicial = System.currentTimeMillis();
				}
				
				///////////////////////////////////////////////////// Manzanas ///////////////////////////////////////////////////////////
				for (Manzana m : manzanas) {// Dibuja las manzanas
					m.dibujar(entorno);
				}	
				
				///////////////////////////////////////////////////// Hitbox de Rambo ///////////////////////////////////////////////////
				hitbox= new Hitbox(rambo.getX(), rambo.getY(), rambo.getAncho()+40, rambo.getAlto()+70);
				hitbox.dibujarHitbox(entorno);
				
				// Carga la imagen de fondo del Tablero (las calles)
				entorno.dibujarImagen(Herramientas.cargarImagen("Tablero.png"), entorno.ancho() / 2.817,
						entorno.alto() / 2, 0);	
				
				///////////////////////////////////////////////////// Rambo ///////////////////////////////////////////////////
				rambo.dibujarImagen(entorno);// Dibuja al personaje
				rambo.moverIzquierda(entorno);// Hace que el personaje se mueva a la izquierda
				rambo.moverDerecha(entorno);// Hace que el personaje se mueva a la derecha
				rambo.moverArriba(entorno);// Hace que el personaje se mueva a la arriba
				rambo.moverAbajo(entorno);// Hace que el personaje se mueva a la abajo
				rambo.disparar(entorno, lasersV);// Hace que el personaje dispare

				/////////////////////////////////////////////////// Tiempo in Game /////////////////////////////////////////////////
				// Calcula el tiempo transcurrido
				long tiempoActual = System.currentTimeMillis();
				long tiempoTranscurrido = tiempoActual - tiempoInicial;

				// Calcula el tiempo restante en segundos
				int tiempoRestante = duracion - (int) (tiempoTranscurrido / 1000);

				// Calcula minutos y segundos restantes
				int minutosRestantes = tiempoRestante / 60;
				int segundosRestantes = tiempoRestante % 60;
				
				///////////////////////////////////////////////// Jugador Herido /////////////////////////////////////////////////
				//Cada vez que el jugador es herido, la variable herido pasa a true y cuando pasan 3 segundos regresa a false
		        if(tiempoRestante>=tiempoInmune && herido==true) {
		        	String msjInmunidad = "Inmunidad: " +(tiempoRestante-tiempoInmune);
					double xCPuntaje = rambo.getX()-40;
					double yCPuntaje = rambo.getY()+55; // Posición Y para el contador de vidas
					entorno.cambiarFont("Arial", 20, Color.RED);
					entorno.escribirTexto(msjInmunidad, xCPuntaje, yCPuntaje);
		        	if(tiempoRestante==tiempoInmune) {
		        		herido=false;		 
		        	}		        			    
		        }
		   
		        ///////////////////////////////////////////////// Reaparicion /////////////////////////////////////////////////
		        // Codigo de reaparicion de tanques verticales
		        if(tiempoRestante==tiempoReaparicionTanqueVertical) {
		        	for(int i=0; i<tanqueVertical.length; i++) {
		        		if(tanqueVertical[i]==null) {
		        			if(i==0) {
		        				tanqueVertical[0] = new Tanque(856,500,50,50,2, true, Herramientas.cargarImagen("TanqueAbj.png"));
		        			}else {
		        				tanqueVertical[1] = new Tanque(325,200,50,50,2, true, Herramientas.cargarImagen("TanqueAbj.png"));
		        			}
		        		}
		        	}
		        }
		        
		        // Codigo de reaparicion de tanques horizontales
		        if(tiempoRestante==tiempoReaparicionTanqueHorizontal) {
		        	for(int v=0; v<tanqueHorizontal.length; v++) {
		        		if(tanqueHorizontal[v]==null) {
		        			if(v==0) {
		        				tanqueHorizontal[0] = new Tanque(50,40,50,50,2, false, Herramientas.cargarImagen("TanqueDer.png"));
		        			}else {
		        				tanqueHorizontal[1] = new Tanque(500,470,50,50,2, false, Herramientas.cargarImagen("TanqueDer.png"));
		        			}
		        		}
		        	}
		        }
		        
		        // Codigo de reaparicion de jeep verticales
		        if(tiempoRestante==tiempoReaparicionJeepVertical) {
		        	for(int v=0; v<jeepVertical.length; v++) {
		        		if(jeepVertical[v]==null) {
		        			if(v==0) {
		        				jeepVertical[0] = new Jeep(25,248,20,50,5, true, Herramientas.cargarImagen("JeepAbj.png"));
		        			}else {
		        				jeepVertical[1] = new Jeep(560,300,20,50,5, true, Herramientas.cargarImagen("JeepAbj.png"));
		        			}
		        		}
		        	}
		        }
		        
		        // Codigo de reaparicion de jeep horizontales
		        if(tiempoRestante==tiempoReaparicionJeepHorizontal) {
		        	for(int v=0; v<jeepHorizontal.length; v++) {
		        		if(jeepHorizontal[v]==null) {
		        			jeepHorizontal[0] = new Jeep(50,215,50,20,5, false, Herramientas.cargarImagen("JeepDer.png"));
		        		}
		        	}
		        }		        		       		    
		        
		        // Codigo de reaparicion de terroristas
		        if(tiempoRestante==tiempoReaparicionTerroristas) {
		        	for(int v=0; v<terrorista.length; v++) {
		        		if(terrorista[v]==null) {
		        			if(v==0) {
		        				terrorista[0] = new Terrorista(200, 275, 50, 50, 2, Herramientas.cargarImagen("TerroristaDer.png"));
		        			}else {
		        				terrorista[1] = new Terrorista(700, 680, 50, 50, 2, Herramientas.cargarImagen("TerroristaDer.png"));
		        			}
		        		}
		        	}
		        }
		        
		        ///////////////////////////////////////////////////// Tanques Horizontales ///////////////////////////////////////////////////
				for (int c=0; c<tanqueHorizontal.length; c++) {
					if(tanqueHorizontal[c]!=null) {
						tanqueHorizontal[c].dibujar(entorno);// dibuja las plantas Horizontales
						tanqueHorizontal[c].desplazarseDer();// le da movimiento a las plantas(Derecha)
						tanqueHorizontal[c].disparar(entorno, lasersR, false);
						if (tanqueHorizontal[c].chocasteConH(entorno, 501)) {// si choca con algun limite cambia la trayectoria
							tanqueHorizontal[c].cambiarTrayectoria();
						}
						if (tanqueHorizontal[c].chocasteConRambo(rambo) && herido==false) {
							sonido_golpe.reproducir_audio();
							vidas -= 1;
			        		herido=true;
			        		tiempoInmune=tiempoRestante-5;
						}
						
						if(tanqueHorizontal[c].laserRojoImpactaRambo(lasersR, hitbox) && herido==false) {
							sonido_golpe.reproducir_audio();
							vidas -= 1;//si el laser rojo impacta a Rambo se le resta 1 vida y se pone en estado inmune
			        		herido=true;
			        		tiempoInmune=tiempoRestante-5;
						}
						
						if(tanqueHorizontal[c]!=null && tanqueHorizontal[c].chocasteConLaserVerde(lasersV, tanqueHorizontal, c)) {
							tanqueHorizontal[c]=null;//si el tanque choca con el laser verde se elimina y se activa el respawn y se suman los puntajes
							puntaje+=5;
							tanquesEliminados += 1;
							tiempoReaparicionTanqueHorizontal=tiempoRestante-8;
						}					
					}
				}
				
				///////////////////////////////////////////////////// Tanques Verticales ///////////////////////////////////////////////////
				for (int x=0; x<tanqueVertical.length; x++) {
					if(tanqueVertical[x]!=null) {
						tanqueVertical[x].dibujar(entorno);// dibuja las plantas verticales
						tanqueVertical[x].desplazarseAbj();// le da movimiento a las plantas(Abajo)
						tanqueVertical[x].disparar(entorno, lasersR, true);
						if (tanqueVertical[x].chocasteConV(entorno, 30)) {// si choca con algun limite cambia la trayectoria
							tanqueVertical[x].cambiarTrayectoria();
						}
						if (tanqueVertical[x].chocasteConRambo(rambo) && herido==false) {
							sonido_golpe.reproducir_audio();
							vidas -= 1;
			        		herido=true;
			        		tiempoInmune=tiempoRestante-5;
						}
						
						if(tanqueVertical[x].laserRojoImpactaRambo(lasersR, hitbox) && herido==false) {
							sonido_golpe.reproducir_audio();
							vidas -= 1;//si el laser rojo impacta a Rambo se le resta 1 vida y se pone en estado inmune
			        		herido=true;
			        		tiempoInmune=tiempoRestante-5;
						}
						
						if(tanqueVertical[x]!=null && tanqueVertical[x].chocasteConLaserVerde(lasersV, tanqueVertical, x)) {
							tanqueVertical[x]=null;//si el tanque choca con el laser verde se elimina y se activa el respawn y se suman los puntajes
							puntaje += 5;
							tanquesEliminados += 1;
							tiempoReaparicionTanqueVertical=tiempoRestante-8;
						}
					}
				}

				///////////////////////////////////////////////////// Jeep Verticales ///////////////////////////////////////////////////
				for (int x=0; x<jeepVertical.length; x++) {
					if(jeepVertical[x]!=null) {
						jeepVertical[x].dibujar(entorno);// dibuja los autos verticales
						jeepVertical[x].desplazarseAbj();// le da movimiento a los autos(Abajo)
						jeepVertical[x].chocasteConLaserVerde(lasersV, jeepVertical, x);
						if (jeepVertical[x].chocasteConV(entorno, 30)) {// si choca con algun limite cambia la trayectoria
							jeepVertical[x].cambiarTrayectoria();
						}
						if (jeepVertical[x].chocasteConRambo(rambo) && herido==false) {
							sonido_golpe.reproducir_audio();
							vidas -= 1;
			        		herido=true;
			        		tiempoInmune=tiempoRestante-5;
						}
						if(jeepVertical[x]!=null && jeepVertical[x].chocasteConLaserRojo(lasersR, jeepVertical, x)) {
							jeepVertical[x]=null;//si el jeep choca con el laser rojo se elimina y se activa el respawn
							tiempoReaparicionJeepVertical=tiempoRestante-8;
						}											
					}
				}

				///////////////////////////////////////////////////// Jeep Horizontales ///////////////////////////////////////////////////
				for (int x=0; x<jeepHorizontal.length; x++) {
					if(jeepHorizontal[x]!=null) {
						jeepHorizontal[x].dibujar(entorno);// dibuja los autos horizontales
						jeepHorizontal[x].desplazarseDer();// le da movimiento a los autos(Derecha)
						jeepHorizontal[x].chocasteConLaserVerde(lasersV, jeepHorizontal, x);
						if (jeepHorizontal[x].chocasteConH(entorno, 501)) {// si choca con algun limite cambia la trayectoria
							jeepHorizontal[x].cambiarTrayectoria();
						}
						if (jeepHorizontal[x].chocasteConRambo(rambo) && herido==false) {
							sonido_golpe.reproducir_audio();
							vidas -= 1;
			        		herido=true;
			        		tiempoInmune=tiempoRestante-5;
						}
						if(jeepHorizontal[x]!=null && jeepHorizontal[x].chocasteConLaserRojo(lasersR, jeepHorizontal, x)) {
							jeepHorizontal[x]=null;//si el jeep choca con el laser rojo se elimina y se activa el respawn
							tiempoReaparicionJeepHorizontal=tiempoRestante-8;
						}
					}					
				}
				
				///////////////////////////////////////////////////// Terroristas ///////////////////////////////////////////////////
				if(enemigoNuevo) {
					for (int c=0; c<terrorista.length; c++) {
					if(terrorista[c]!=null) {
						terrorista[c].dibujar(entorno);// dibuja las plantas Horizontales
						terrorista[c].desplazarseDer();// le da movimiento a las plantas(Derecha)
						terrorista[c].disparar(entorno, lasersR);
						if (terrorista[c].chocasteConH(entorno, 501)) {// si choca con algun limite cambia la trayectoria
							terrorista[c].cambiarTrayectoria();
						}
						if (terrorista[c].chocasteConRambo(rambo) && herido==false) {// si choca con rambo se cierra el juego
							sonido_golpe.reproducir_audio();
							vidas -= 1;
			        		herido=true;
			        		tiempoInmune=tiempoRestante-5;
						}
						
						if(terrorista[c].laserRojoImpactaRambo(lasersR, hitbox) && herido==false) {
							sonido_golpe.reproducir_audio();
							vidas -= 1;//si el laser rojo impacta a Rambo se le resta 1 vida y se pone en estado inmune
			        		herido=true;
			        		tiempoInmune=tiempoRestante-5;
						}
						
						if(terrorista[c]!=null && terrorista[c].chocasteConLaserVerde(lasersV, terrorista, c)) {
							terrorista[c]=null;//si el terrorista choca con el laser verde se elimina y se activa el respawn y se suman los puntajes
							puntaje+=5;
							tiempoReaparicionTerroristas=tiempoRestante-3;
							}					
						}
					}
				}

				/////////////////////////////////////////////////// Lasers Verdes /////////////////////////////////////////////////
				for (int i = lasersV.size() - 1; i >= 0; i--) {
				    LaserVerde laser = lasersV.get(i);
				    laser.mover(); // Actualiza y le da movimiento a los lasers
				    laser.colisionLaserMapa();
				    laser.colisionLaserVyLaserR(lasersR);
				    laser.dibujarImagen(entorno);
				    if (laser.debeEliminar()) {
				        lasersV.remove(i); // Elimina el láser de la lista
				        bloqueoDisparo=false;
				    }
				}
				
				/////////////////////////////////////////////////// Lasers Rojo /////////////////////////////////////////////////
				for (int i = lasersR.size() - 1; i >= 0; i--) {
				    LaserRojo laser = lasersR.get(i);
				    laser.mover(); // Actualiza y le da movimiento a los lasers
				    laser.dibujarImagen(entorno);
				    if (laser.debeEliminar()) {
				        lasersR.remove(i); // Elimina el láser de la lista
				    }				    	        
				}
				
				/////////////////////////////////////////////////// Tiempo /////////////////////////////////////////////////
				String msjStringTiempo = "TIEMPO:";
				int xSTiempo = entorno.ancho() - 280; // Posición X para el contador de tiempo
				int ySTiempo = 100; // Posición Y para el contador de tiempo
				entorno.cambiarFont("Arial", 50, Color.RED);
				entorno.escribirTexto(msjStringTiempo, xSTiempo, ySTiempo);

				int xCTiempo = entorno.ancho() - 340;// Posición X para el contador de tiempo
				int yCTiempo = 170; // Posición Y para el contador de tiempo
				entorno.cambiarFont("Arial", 50, Color.RED);
				entorno.escribirTexto(String.format("%02d:%02d", minutosRestantes, segundosRestantes), xCTiempo + 90,
						yCTiempo);
				
				/////////////////////////////////////////////////// Vidas /////////////////////////////////////////////////
				String msjStringVidas = "VIDAS:";
				int xVidas = entorno.ancho() - 260; // Posición X para el contador de vidas
				int yVidas = 270; // Posición Y para el contador de vidas
				entorno.cambiarFont("Arial", 50, Color.GREEN);
				entorno.escribirTexto(msjStringVidas, xVidas, yVidas);

				String msjContadorVidas = "" + vidas;
				int xCVidas = entorno.ancho() - 200; // Posición X para el contador de vidas
				int yCVidas = 340; // Posición Y para el contador de vidas
				entorno.cambiarFont("Arial", 50, Color.GREEN);
				entorno.escribirTexto(msjContadorVidas, xCVidas, yCVidas);

				/////////////////////////////////////////////////// Puntaje ////////////////////////////////////////////////////
				String msjStringPuntaje = "PUNTAJE:";
				int xSPuntaje = entorno.ancho() - 290; // Posición X para el puntaje
				int ySPuntaje = 450;// Posición Y para el puntaje
				entorno.cambiarFont("Arial", 50, Color.YELLOW);
				entorno.escribirTexto(msjStringPuntaje, xSPuntaje, ySPuntaje);

				String msjContadorPuntaje = "" + puntaje + "/" + puntajeNivel;
				int xCPuntaje = entorno.ancho() - 230; // Posición X para el puntaje
				int yCPuntaje = 520; // Posición Y para el puntaje
				entorno.cambiarFont("Arial", 50, Color.YELLOW);
				entorno.escribirTexto(msjContadorPuntaje, xCPuntaje, yCPuntaje);
				
				///////////////////////////////////////////// Tanques Eliminados ////////////////////////////////////////////////
				String msjStringTanquesE = "Tanques Eliminados:";
				int xSTanqueE = entorno.ancho() - 340; // Posición X para el contador de tanques eliminados
				int ySTanqueE = 620;// Posición Y para el contador de tanques eliminados
				entorno.cambiarFont("Arial", 35, Color.WHITE);
				entorno.escribirTexto(msjStringTanquesE, xSTanqueE, ySTanqueE);
				
				String msjContadorTanque = "" + tanquesEliminados;
				int xCTanque = entorno.ancho() - 185; // Posición X para el contador de tanques eliminados
				int yCTanque = 690; // Posición Y para el contador de tanques eliminados
				entorno.cambiarFont("Arial", 40, Color.WHITE);
				entorno.escribirTexto(msjContadorTanque, xCTanque, yCTanque);
		        
		        ///////////////////////////////////////////////// Ganar el nivel /////////////////////////////////////////////////
		        // Verifica si el puntaje alcanza el objetivo
		        if (puntaje >= puntajeNivel) {
		            ganaste = true;
		        }
		        
		        ///////////////////////////////////////////////// Perder el nivel /////////////////////////////////////////////////
				//Si las vidas llegan a 0 o el tiempo se acaba se activa la pantalla GameOver
				if (vidas <= 0 || tiempoRestante <= 0) {
	                gameOver = true;
	            }
				
				///////////////////////////////////////////////// Sonido Rambo in Game ////////////////////////////////////////////
				//El personaje reproduce una frase cada vez que llega a los 10 puntos
				if(puntaje == permite_frase) {
					sonidos_rambo[indice].reproducir_frase();
				}
				
			}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////           
////////////////////////////////////////////////// Logica Modo 2 jugadores //////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		} else if(menuJuego.estaJuego2JugComenzado()) {
			music_ingame.reproducir_audio_en_bucle();
			/// Si el juego ha comenzado
			///////////////////////////////////////////////// Pantalla si "ganaste" /////////////////////////////////////////////////
			// Bloque para mostrar "Ganaste" y avanzar al siguiente nivel
            if (ganaste) {
            	music_ingame.detener_audio();
				if (nivel == 6) {
					endMusic.reproducir_audio_en_bucle();
					// Lógica específica para el nivel 6
					entorno.dibujarImagen(Herramientas.cargarImagen("WinScreenRambo.png"), entorno.ancho() / 2,
							entorno.alto() / 2, 0);
					entorno.cambiarFont("Impact", 100, Color.RED);
					entorno.escribirTexto("The End", 480, 150);
					entorno.cambiarFont("Impact", 35, Color.WHITE);
					entorno.escribirTexto("Presione Enter para Continuar", 425, 300);
					if (entorno.sePresiono(entorno.TECLA_ENTER)) {
						pantallaCreditos = true;
					}
					if (pantallaCreditos) {
						entorno.dibujarRectangulo(entorno.ancho() / 2, entorno.alto() / 2, entorno.ancho(),
								entorno.alto(), 0, Color.BLACK);
						entorno.cambiarFont("Impact", 75, Color.RED);
						entorno.escribirTexto("Realizado por:", 150, 150);
						entorno.cambiarFont("Impact", 50, Color.WHITE);
						entorno.escribirTexto("Martinez Iraola Tobias", 150, 300);
						entorno.cambiarFont("Impact", 50, Color.WHITE);
						entorno.escribirTexto("Leonardo Agustin Plaza", 150, 400);
						entorno.cambiarFont("Impact", 50, Color.WHITE);
						entorno.escribirTexto("Matias Federico Veron", 150, 500);
						entorno.cambiarFont("Impact", 35, Color.YELLOW);
						entorno.escribirTexto("Presione F para salir", 500, 700);
						entorno.dibujarImagen(Herramientas.cargarImagen("chegames_Logo.png"), 1000, 300, 0);
						entorno.cambiarFont("Impact", 35, Color.WHITE);
						entorno.escribirTexto("2023", 960, 500);
						// Si el jugador presiona "F" se cierra el juego
						if (entorno.sePresiono('f')) {
							System.exit(0);
						}
					}

				} else {
					sonido_stageclear.reproducir_audio();
					entorno.dibujarImagen(Herramientas.cargarImagen("RamboWin.png"), 270, 510, 0);
					entorno.cambiarFont("Impact", 100, Color.GREEN);
					entorno.escribirTexto("¡Nivel " + nivel + " Completado!", 230, 150);
					entorno.cambiarFont("Impact", 35, Color.yellow);
					entorno.escribirTexto("Presione Enter para Continuar", 550, 400);
					entorno.cambiarFont("Impact", 30, Color.RED);
					entorno.escribirTexto("Presione F para Salir", 640, 500);

					if (entorno.sePresiono(entorno.TECLA_ENTER)) {
						sonido_stageclear.detener_audio();
						nivel++; // Avanza al siguiente nivel
						reiniciarJuego(false); // Reinicia el juego
						ganaste = false; // Restablece la bandera de "ganaste"

						// Aumentar la velocidad de los tanques Verticales
						for (Tanque tanque : tanqueVertical) {
							if (tanque != null) {
								tanque.aumentarVelocidad(nivel); // Aumenta la velocidad del tanque
							}
						}

						// Aumentar la velocidad de los tanques Horizontales
						for (Tanque tanque : tanqueHorizontal) {
							if (tanque != null) {
								tanque.aumentarVelocidad(nivel); // Aumenta la velocidad del tanque
							}
						}

						// Aumentar la velocidad de los jeep Verticales
						for (Jeep jeep : jeepVertical) {
							if (jeep != null) {
								jeep.aumentarVelocidad(nivel); // Aumenta la velocidad del jeep
							}
						}

						// Aumentar la velocidad de los jeep Horizontales
						for (Jeep jeep : jeepHorizontal) {
							if (jeep != null) {
								jeep.aumentarVelocidad(nivel); // Aumenta la velocidad del jeep
							}
						}
						
						//Logica de Niveles
						if (nivel == 2) {
							enemigoNuevo = true;
							puntajeNivel = 30;
							duracion -= 24;
						}
						if (nivel == 3) {
							enemigoNuevo = true;
							puntajeNivel = 35;
							duracion -= 24;
						}
						if (nivel == 4) {
							enemigoNuevo = true;
							puntajeNivel = 40;
							duracion -= 24;
						}
						if (nivel == 5) {
							enemigoNuevo = true;
							puntajeNivel = 40;
							duracion = 15;
						}
					}
				}
				// Si el jugador presiona "F" se cierra el juego
				if (entorno.sePresiono('f')) {
					System.exit(0);
				}

            }else if (gameOver) {
            	/////////////////////////////////////////////////// Pantalla "Game Over" /////////////////////////////////////////////////
    			music_ingame.detener_audio();
            	sonido_gameover.reproducir_audio();
            	nivel = 1;
            	duracion = 120;
            	entorno.dibujarImagen(Herramientas.cargarImagen("GameOver.png"), 630,200, 0);
                entorno.cambiarFont("Impact", 100, Color.RED);
                entorno.escribirTexto("G a m e  O v e r", 340, 150);
                
                entorno.cambiarFont("Impact", 50, Color.GREEN);
                entorno.escribirTexto("Presione Enter para Reiniciar", 340, 550);
                
                entorno.cambiarFont("Impact", 30, Color.YELLOW);
                entorno.escribirTexto("Presione F para Salir", 510, 650);
                
                  
                // Si el jugador presiona "ENTER" se reinicia el juego
                if (entorno.sePresiono(entorno.TECLA_ENTER)) {
                	sonido_gameover.detener_audio();
                    // Restablecer el juego
                    reiniciarJuego(true);
                }
                // Si el jugador presiona "F" se cierra el juego
                if (entorno.sePresiono('f')) {
                	System.exit(0);
                }
                
			} else {
				//Bloque de codigo de todo lo que pasa dutante el Juego
				
				///////////////////////////////////////////////// Inicia el tiempo del juego /////////////////////////////////////////////////
				if (tiempoInicial == 0) {
					// Inicia el temporizador cuando el juego comienza
					tiempoInicial = System.currentTimeMillis();
				}
				
				/////////////////////////////////////////////////// Manzanas ///////////////////////////////////////////////////
				for (Manzana m : manzanas) {// Dibuja las manzanas
					m.dibujar(entorno);
				}	
				
				/////////////////////////////////////////////////// Hitbox de Rambo /////////////////////////////////////////////////
				hitbox= new Hitbox(rambo.getX(), rambo.getY(), rambo.getAncho()+40, rambo.getAlto()+70);
				hitbox.dibujarHitbox(entorno);
				
				hitboxRambo2 = new Hitbox(rambo2.getX(), rambo2.getY(), rambo2.getAncho()+40, rambo2.getAlto()+70);
				hitbox.dibujarHitbox(entorno);
				
				// Carga la imagen de fondo del Tablero (las calles)
				entorno.dibujarImagen(Herramientas.cargarImagen("Tablero.png"), entorno.ancho() / 2.817,
						entorno.alto() / 2, 0);	
				
				/////////////////////////////////////////////////// Rambo1 y Rambo2 /////////////////////////////////////////////////
				rambo.dibujarImagen(entorno);// Dibuja al personaje
				rambo2.dibujarImagen(entorno);
				rambo.moverIzquierda(entorno);// Hace que el personaje se mueva a la izquierda
				rambo2.moverIzquierda(entorno);
				rambo.moverDerecha(entorno);// Hace que el personaje se mueva a la derecha
				rambo2.moverDerecha(entorno);
				rambo.moverArriba(entorno);// Hace que el personaje se mueva a la arriba
				rambo2.moverArriba(entorno);
				rambo.moverAbajo(entorno);// Hace que el personaje se mueva a la abajo
				rambo2.moverAbajo(entorno);
				rambo.disparar(entorno, lasersV);// Hace que el personaje dispare
				rambo2.disparar(entorno, lasersA);

				/////////////////////////////////////////////////// Tiempo in Game /////////////////////////////////////////////////
				// Calcula el tiempo transcurrido
				long tiempoActual = System.currentTimeMillis();
				long tiempoTranscurrido = tiempoActual - tiempoInicial;

				// Calcula el tiempo restante en segundos
				int tiempoRestante = duracion - (int) (tiempoTranscurrido / 1000);

				// Calcula minutos y segundos restantes
				int minutosRestantes = tiempoRestante / 60;
				int segundosRestantes = tiempoRestante % 60;
				
				///////////////////////////////////////////////// Jugador Herido /////////////////////////////////////////////////
				//Cada vez que el jugador es herido, la variable herido pasa a true y cuando pasan 3 segundos regresa a false
		        if(tiempoRestante>=tiempoInmune && herido==true) {
		        	String msjInmunidad = "Inmunidad: " +(tiempoRestante-tiempoInmune);
					double xCPuntaje = rambo.getX()-40;
					double yCPuntaje = rambo.getY()+55; // Posición Y para el contador de vidas
					entorno.cambiarFont("Arial", 20, Color.RED);
					entorno.escribirTexto(msjInmunidad, xCPuntaje, yCPuntaje);
					String msjInmunidad2 = "Inmunidad: " +(tiempoRestante-tiempoInmune);
					double xCPuntaje2 = rambo2.getX()-40;
					double yCPuntaje2 = rambo2.getY()+55; // Posición Y para el contador de vidas
					entorno.cambiarFont("Arial", 20, Color.RED);
					entorno.escribirTexto(msjInmunidad2, xCPuntaje2, yCPuntaje2);
		        	if(tiempoRestante==tiempoInmune) {
		        		herido=false;		 
		        	}		        			    
		        }
		        ///////////////////////////////////////////////// Reaparicion /////////////////////////////////////////////////
		        // Codigo de reaparicion de tanques verticales
		        if(tiempoRestante==tiempoReaparicionTanqueVertical) {
		        	for(int i=0; i<tanqueVertical.length; i++) {
		        		if(tanqueVertical[i]==null) {
		        			if(i==0) {
		        				tanqueVertical[0] = new Tanque(856,500,50,50,2, true, Herramientas.cargarImagen("TanqueAbj.png"));
		        			}else {
		        				tanqueVertical[1] = new Tanque(325,200,50,50,2, true, Herramientas.cargarImagen("TanqueAbj.png"));
		        			}
		        		}
		        	}
		        }
		        
		        // Codigo de reaparicion de tanques horizontales
		        if(tiempoRestante==tiempoReaparicionTanqueHorizontal) {
		        	for(int v=0; v<tanqueHorizontal.length; v++) {
		        		if(tanqueHorizontal[v]==null) {
		        			if(v==0) {
		        				tanqueHorizontal[0] = new Tanque(50,40,50,50,2, false, Herramientas.cargarImagen("TanqueDer.png"));
		        			}else {
		        				tanqueHorizontal[1] = new Tanque(500,470,50,50,2, false, Herramientas.cargarImagen("TanqueDer.png"));
		        			}
		        		}
		        	}
		        }
		        
		        // Codigo de reaparicion de jeep verticales
		        if(tiempoRestante==tiempoReaparicionJeepVertical) {
		        	for(int v=0; v<jeepVertical.length; v++) {
		        		if(jeepVertical[v]==null) {
		        			if(v==0) {
		        				jeepVertical[0] = new Jeep(25,248,20,50,5, true, Herramientas.cargarImagen("JeepAbj.png"));
		        			}else {
		        				jeepVertical[1] = new Jeep(560,300,20,50,5, true, Herramientas.cargarImagen("JeepAbj.png"));
		        			}
		        		}
		        	}
		        }
		        
		        // Codigo de reaparicion de jeep horizontales
		        if(tiempoRestante==tiempoReaparicionJeepHorizontal) {
		        	for(int v=0; v<jeepHorizontal.length; v++) {
		        		if(jeepHorizontal[v]==null) {
		        			jeepHorizontal[0] = new Jeep(50,215,50,20,5, false, Herramientas.cargarImagen("JeepDer.png"));
		        		}
		        	}
		        }		 
		        
		        // Codigo de reaparicion de terroristas
		        if(tiempoRestante==tiempoReaparicionTerroristas) {
		        	for(int v=0; v<terrorista.length; v++) {
		        		if(terrorista[v]==null) {
		        			if(v==0) {
		        				terrorista[0] = new Terrorista(200, 275, 50, 50, 2, Herramientas.cargarImagen("TerroristaDer.png"));
		        			}else {
		        				terrorista[1] = new Terrorista(700, 680, 50, 50, 2, Herramientas.cargarImagen("TerroristaDer.png"));
		        			}
		        		}
		        	}
		        }
		        
		        /////////////////////////////////////////////////// Tanques Horizontales /////////////////////////////////////////////////
				for (int c=0; c<tanqueHorizontal.length; c++) {
					if(tanqueHorizontal[c]!=null) {
						tanqueHorizontal[c].dibujar(entorno);// dibuja las plantas Horizontales
						tanqueHorizontal[c].desplazarseDer();// le da movimiento a las plantas(Derecha)
						tanqueHorizontal[c].disparar(entorno, lasersR, false);
						if (tanqueHorizontal[c].chocasteConH(entorno, 501)) {// si choca con algun limite cambia la trayectoria
							tanqueHorizontal[c].cambiarTrayectoria();
						}
						if (tanqueHorizontal[c].chocasteConRambo(rambo) && herido==false) {
							sonido_golpe.reproducir_audio();
							vidas -= 1;
			        		herido=true;
			        		tiempoInmune=tiempoRestante-5;
						}
						if (tanqueHorizontal[c].chocasteConRambo2(rambo2) && herido==false) {
							vidas -= 1;
			        		herido=true;
			        		tiempoInmune=tiempoRestante-5;
						}
						
						if(tanqueHorizontal[c].laserRojoImpactaRambo(lasersR, hitbox) && herido==false) {
							sonido_golpe.reproducir_audio();
							vidas -= 1;//si el laser rojo impacta a Rambo se le resta 1 vida y se pone en estado inmune
			        		herido=true;
			        		tiempoInmune=tiempoRestante-5;
						}
						
						if(tanqueHorizontal[c].laserRojoImpactaRambo2(lasersR, hitboxRambo2) && herido==false) {
							sonido_golpe.reproducir_audio();
							vidas -= 1;//si el laser rojo impacta a Rambo2 se le resta 1 vida y se pone en estado inmune
			        		herido=true;
			        		tiempoInmune=tiempoRestante-5;
						}
						
						if(tanqueHorizontal[c]!=null && tanqueHorizontal[c].chocasteConLaserVerde(lasersV, tanqueHorizontal, c)) {
							tanqueHorizontal[c]=null;//si el tanque choca con el laser verde se elimina y se activa el respawn y se suman los puntajes
							puntaje+=5;
							tanquesEliminados += 1;
							tiempoReaparicionTanqueHorizontal=tiempoRestante-8;
						}
						
						if(tanqueHorizontal[c]!=null && tanqueHorizontal[c].chocasteConLaserAzul(lasersA, tanqueHorizontal, c)) {
							tanqueHorizontal[c]=null;//si el tanque choca con el laser azul se elimina y se activa el respawn y se suman los puntajes
							puntaje+=5;
							tanquesEliminados += 1;
							tiempoReaparicionTanqueHorizontal=tiempoRestante-8;
						}	
					}
				}
				   
				/////////////////////////////////////////////////// Tanques Verticales /////////////////////////////////////////////////
				for (int x=0; x<tanqueVertical.length; x++) {
					if(tanqueVertical[x]!=null) {
						tanqueVertical[x].dibujar(entorno);// dibuja las plantas verticales
						tanqueVertical[x].desplazarseAbj();// le da movimiento a las plantas(Abajo)
						tanqueVertical[x].disparar(entorno, lasersR, true);
						if (tanqueVertical[x].chocasteConV(entorno, 30)) {// si choca con algun limite cambia la trayectoria
							tanqueVertical[x].cambiarTrayectoria();
						}
						if (tanqueVertical[x].chocasteConRambo(rambo) && herido==false) {
							sonido_golpe.reproducir_audio();
							vidas -= 1;
			        		herido=true;
			        		tiempoInmune=tiempoRestante-5;
						}
						if (tanqueVertical[x].chocasteConRambo2(rambo2) && herido==false) {
							sonido_golpe.reproducir_audio();
							vidas -= 1;
			        		herido=true;
			        		tiempoInmune=tiempoRestante-5;
						}
						
						if(tanqueVertical[x].laserRojoImpactaRambo(lasersR, hitbox) && herido==false) {
							sonido_golpe.reproducir_audio();
							vidas -= 1;//si el laser rojo impacta a Rambo se le resta 1 vida y se pone en estado inmune
			        		herido=true;
			        		tiempoInmune=tiempoRestante-5;
						}
						
						if(tanqueVertical[x].laserRojoImpactaRambo2(lasersR, hitboxRambo2) && herido==false) {
							sonido_golpe.reproducir_audio();
							vidas -= 1;//si el laser rojo impacta a Rambo2 se le resta 1 vida y se pone en estado inmune
			        		herido=true;
			        		tiempoInmune=tiempoRestante-5;
						}
						
						if(tanqueVertical[x]!=null && tanqueVertical[x].chocasteConLaserVerde(lasersV, tanqueVertical, x)) {
							tanqueVertical[x]=null;//si el tanque choca con el laser verde se elimina y se activa el respawn y se suman los puntajes
							puntaje += 5;
							tanquesEliminados += 1;
							tiempoReaparicionTanqueVertical=tiempoRestante-8;
						}
						
						if(tanqueVertical[x]!=null && tanqueVertical[x].chocasteConLaserAzul(lasersA, tanqueVertical, x)) {
							tanqueVertical[x]=null;//si el tanque choca con el laser azul se elimina y se activa el respawn y se suman los puntajes
							puntaje += 5;
							tanquesEliminados += 1;
							tiempoReaparicionTanqueVertical=tiempoRestante-8;
						}
					}
				}

				/////////////////////////////////////////////////// Jeep Verticales /////////////////////////////////////////////////
				for (int x=0; x<jeepVertical.length; x++) {
					if(jeepVertical[x]!=null) {
						jeepVertical[x].dibujar(entorno);// dibuja los autos verticales
						jeepVertical[x].desplazarseAbj();// le da movimiento a los autos(Abajo)
						jeepVertical[x].chocasteConLaserVerde(lasersV, jeepVertical, x);
						if (jeepVertical[x].chocasteConV(entorno, 30)) {// si choca con algun limite cambia la trayectoria
							jeepVertical[x].cambiarTrayectoria();
						}
						if (jeepVertical[x].chocasteConRambo(rambo) && herido==false) {
							sonido_golpe.reproducir_audio();
							vidas -= 1;
			        		herido=true;
			        		tiempoInmune=tiempoRestante-5;
						}
						if (jeepVertical[x].chocasteConRambo2(rambo2) && herido==false) {
							sonido_golpe.reproducir_audio();
							vidas -= 1;
			        		herido=true;
			        		tiempoInmune=tiempoRestante-5;
						}
						if(jeepVertical[x]!=null && jeepVertical[x].chocasteConLaserRojo(lasersR, jeepVertical, x)) {
							jeepVertical[x]=null;//si el jeep choca con el laser rojo se elimina y se activa el respawn
							tiempoReaparicionJeepVertical=tiempoRestante-8;
						}											
					}
				}

				/////////////////////////////////////////////////// Jeep Horizontales /////////////////////////////////////////////////
				for (int x=0; x<jeepHorizontal.length; x++) {
					if(jeepHorizontal[x]!=null) {
						jeepHorizontal[x].dibujar(entorno);// dibuja los autos horizontales
						jeepHorizontal[x].desplazarseDer();// le da movimiento a los autos(Derecha)
						jeepHorizontal[x].chocasteConLaserVerde(lasersV, jeepHorizontal, x);
						if (jeepHorizontal[x].chocasteConH(entorno, 501)) {// si choca con algun limite cambia la trayectoria
							jeepHorizontal[x].cambiarTrayectoria();
						}
						if (jeepHorizontal[x].chocasteConRambo(rambo) && herido==false) {
							sonido_golpe.reproducir_audio();
							vidas -= 1;
			        		herido=true;
			        		tiempoInmune=tiempoRestante-5;
						}
						if (jeepHorizontal[x].chocasteConRambo2(rambo2) && herido==false) {
							sonido_golpe.reproducir_audio();
							vidas -= 1;
			        		herido=true;
			        		tiempoInmune=tiempoRestante-5;
						}
						if(jeepHorizontal[x]!=null && jeepHorizontal[x].chocasteConLaserRojo(lasersR, jeepHorizontal, x)) {
							jeepHorizontal[x]=null;//si el jeep choca con el laser rojo se elimina y se activa el respawn
							tiempoReaparicionJeepHorizontal=tiempoRestante-8;
						}
					}					
				}
				
				/////////////////////////////////////////////////// Terroristas /////////////////////////////////////////////////
				if (enemigoNuevo) {
					for (int c = 0; c < terrorista.length; c++) {
						if (terrorista[c] != null) {
							terrorista[c].dibujar(entorno);// dibuja las plantas Horizontales
							terrorista[c].desplazarseDer();// le da movimiento a las plantas(Derecha)
							terrorista[c].disparar(entorno, lasersR);
							if (terrorista[c].chocasteConH(entorno, 501)) {// si choca con algun limite cambia la trayectoria
								terrorista[c].cambiarTrayectoria();
							}
							if (terrorista[c].chocasteConRambo(rambo) && herido == false) {
								sonido_golpe.reproducir_audio();
								vidas -= 1;
								herido = true;
								tiempoInmune = tiempoRestante - 5;
							}
							if (terrorista[c].chocasteConRambo2(rambo2) && herido == false) {
								sonido_golpe.reproducir_audio();
								vidas -= 1;
								herido = true;
								tiempoInmune = tiempoRestante - 5;
							}

							if (terrorista[c].laserRojoImpactaRambo(lasersR, hitbox) && herido == false) {
								sonido_golpe.reproducir_audio();
								vidas -= 1;//si el laser rojo impacta a Rambo se le resta 1 vida y se pone en estado inmune
								herido = true;
								tiempoInmune = tiempoRestante - 5;
							}
							
							if (terrorista[c].laserRojoImpactaRambo2(lasersR, hitboxRambo2) && herido == false) {
								sonido_golpe.reproducir_audio();
								vidas -= 1;//si el laser rojo impacta a Rambo2 se le resta 1 vida y se pone en estado inmune
								herido = true;
								tiempoInmune = tiempoRestante - 5;
							}

							if (terrorista[c] != null && terrorista[c].chocasteConLaserVerde(lasersV, terrorista, c)) {
								terrorista[c] = null;//si el terrorista choca con el laser verde se elimina y se activa el respawn y se suman los puntajes
								puntaje += 5;
								tiempoReaparicionTerroristas = tiempoRestante - 3;
							}
							if (terrorista[c] != null && terrorista[c].chocasteConLaserAzul(lasersA, terrorista, c)) {
								terrorista[c] = null;//si el terrorista choca con el laser azul se elimina y se activa el respawn y se suman los puntajes
								puntaje += 5;
								tiempoReaparicionTerroristas = tiempoRestante - 3;
							}

						}
					}
				}

				/////////////////////////////////////////////////// Lasers Verde /////////////////////////////////////////////////
				for (int i = lasersV.size() - 1; i >= 0; i--) {
				    LaserVerde laser = lasersV.get(i);
				    laser.mover(); // Actualiza y le da movimiento a los lasers
				    laser.colisionLaserMapa();
				    laser.colisionLaserVyLaserR(lasersR);
				    laser.dibujarImagen(entorno);
				    if (laser.debeEliminar()) {
				        lasersV.remove(i); // Elimina el láser de la lista
				        bloqueoDisparo=false;
				    }
				}
				
				/////////////////////////////////////////////////// Lasers Azul /////////////////////////////////////////////////
				for (int i = lasersA.size() - 1; i >= 0; i--) {
					LaserAzul laser = lasersA.get(i);
					laser.mover(); // Actualiza y le da movimiento a los lasers
					laser.colisionLaserMapa();
					laser.colisionLaserAyLaserR(lasersR);
					laser.dibujarImagen(entorno);
					if (laser.debeEliminar()) {
						lasersA.remove(i); // Elimina el láser de la lista
						bloqueoDisparo2 = false;
					}
				}
				
				////////////////////////////////////////////////// Lasers Rojo /////////////////////////////////////////////////
				for (int i = lasersR.size() - 1; i >= 0; i--) {
				    LaserRojo laser = lasersR.get(i);
				    laser.mover(); // Actualiza y le da movimiento a los lasers
				    laser.dibujarImagen(entorno);
				    if (laser.debeEliminar()) {
				        lasersR.remove(i); // Elimina el láser de la lista
				    }				    	        
				}
				
				/////////////////////////////////////////////////// Tiempo /////////////////////////////////////////////////
				String msjStringTiempo = "TIEMPO:";
				int xSTiempo = entorno.ancho() - 280; // Posición X para el contador de vidas
				int ySTiempo = 100; // Posición Y para el contador de vidas
				entorno.cambiarFont("Arial", 50, Color.RED);
				entorno.escribirTexto(msjStringTiempo, xSTiempo, ySTiempo);

				int xCTiempo = entorno.ancho() - 340;// Posición X para el contador de vidas
				int yCTiempo = 170; // Posición Y para el contador de vidas
				entorno.cambiarFont("Arial", 50, Color.RED);
				entorno.escribirTexto(String.format("%02d:%02d", minutosRestantes, segundosRestantes), xCTiempo + 90, yCTiempo);
				
				/////////////////////////////////////////////////// Vidas /////////////////////////////////////////////////
				String msjStringVidas = "VIDAS:";
				int xVidas = entorno.ancho() - 260; // Posición X para el contador de vidas
				int yVidas = 270; // Posición Y para el contador de vidas
				entorno.cambiarFont("Arial", 50, Color.GREEN);
				entorno.escribirTexto(msjStringVidas, xVidas, yVidas);

				String msjContadorVidas = "" + vidas;
				int xCVidas = entorno.ancho() - 200; // Posición X para el contador de vidas
				int yCVidas = 340; // Posición Y para el contador de vidas
				entorno.cambiarFont("Arial", 50, Color.GREEN);
				entorno.escribirTexto(msjContadorVidas, xCVidas, yCVidas);

				///////////////////////////////////////////////// Puntaje /////////////////////////////////////////////////
				String msjStringPuntaje = "PUNTAJE:";
				int xSPuntaje = entorno.ancho() - 290; // Posición X para el contador de vidas
				int ySPuntaje = 450;// Posición Y para el contador de vidas
				entorno.cambiarFont("Arial", 50, Color.YELLOW);
				entorno.escribirTexto(msjStringPuntaje, xSPuntaje, ySPuntaje);

				String msjContadorPuntaje = "" + puntaje + "/" + puntajeNivel;
				int xCPuntaje = entorno.ancho() - 200; // Posición X para el contador de vidas
				int yCPuntaje = 520; // Posición Y para el contador de vidas
				entorno.cambiarFont("Arial", 50, Color.YELLOW);
				entorno.escribirTexto(msjContadorPuntaje, xCPuntaje, yCPuntaje);
				
				///////////////////////////////////////////// Tanques Eliminados ////////////////////////////////////////////////
				String msjStringTanquesE = "Tanques Eliminados:";
				int xSTanqueE = entorno.ancho() - 340; // Posición X para el contador de tanques eliminados
				int ySTanqueE = 620;// Posición Y para el contador de tanques eliminados
				entorno.cambiarFont("Arial", 35, Color.WHITE);
				entorno.escribirTexto(msjStringTanquesE, xSTanqueE, ySTanqueE);
				
				String msjContadorTanque = "" + tanquesEliminados;
				int xCTanque = entorno.ancho() - 185; // Posición X para el contador de tanques eliminados
				int yCTanque = 690; // Posición Y para el contador de tanques eliminados
				entorno.cambiarFont("Arial", 40, Color.WHITE);
				entorno.escribirTexto(msjContadorTanque, xCTanque, yCTanque);
				
		        ///////////////////////////////////////////////// Ganar el nivel /////////////////////////////////////////////////
		        // Verifica si el puntaje alcanza el objetivo
		        if (puntaje >= puntajeNivel) {
		            ganaste = true;
		        }
		        
		        ///////////////////////////////////////////////// Perder el nivel /////////////////////////////////////////////////
				//Si las vidas llegan a 0 o el tiempo se acaba se activa la pantalla GameOver
				if (vidas <= 0 || tiempoRestante <= 0) {
	                gameOver = true;
	            }
				
				///////////////////////////////////////////////// Sonido Rambo in Game ////////////////////////////////////////////
				//El personaje reproduce una frase cada vez que llega a los 10 puntos
				if(puntaje == permite_frase) {
					sonidos_rambo[indice].reproducir_frase();
				}
				
			}
		}else {
			/////////////////////////////////////////////////// Lógica del menú /////////////////////////////////////////////////
			try {
				menuJuego.tick(entorno);
			} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			tiempoInicial = 0;
		}
	}
	

	@SuppressWarnings("unused")
	public static void main(String[] args) throws UnsupportedAudioFileException, IOException, LineUnavailableException
	{
		Juego juego = new Juego();
	}
}
