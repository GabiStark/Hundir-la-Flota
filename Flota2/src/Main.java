import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Main {

	public static Scanner sc = new Scanner(System.in);
	public static char tableroIA [][] = new char [6][6];
	public static char tableroJugador [][] = new char [6][6];
	public static char tableroAdicional [][] = new char [6][6];
	public static int barcosJugador=10;
	public static int barcosIA=10;

	public static char [][] iniciarTablero() {
		
		//se inician los tableros introduciendo agua (-) en todas las posiciones.
		char tableroIniciado [][] = new char [6][6];

		for (int i=0;i<tableroIniciado.length;i++) {
			for (int j=0;j<tableroIniciado[i].length;j++) {
				tableroIniciado[i][j]='-';
			}
		}

		return tableroIniciado;
	}

	public static char [][] prepararTableroIA() {
		
		//Se prepara el tablero de la IA introduciendo valores aleatorios para sustituir 10 posiciones de agua (-) con barcos (B)

		char tableroFinal [][] = iniciarTablero();

		//pongo 10 de límite en el for porque va a haber 10 barcos solamente de primeras
		for (int i=0;i<10;i++) {
			
			int numeroA =  (int) Math.floor(Math.random()*6+0);
			int numeroB =  (int) Math.floor(Math.random()*6+0);
			
			if (tableroFinal[numeroA][numeroB]=='B') {
				i--;
			} else {
				tableroFinal[numeroA][numeroB]='B';
			}
			
		}

		return tableroFinal;
	}
	
	public static int [] pedirCoordenadas() {

		//pedimos las coordenadas, las transformamos en dos ints que introducimos en un array.

		int coordenadas [] = new int [2];
		String inicial=sc.nextLine();

		for (int i=0;i<2;i++) {

			switch(inicial.charAt(i)){
			case 'a':
			case 'A':
			case '1':
				coordenadas[i]=0;
				break;
			case 'b':
			case 'B':
			case '2':
				coordenadas[i]=1;
				break;
			case 'c':
			case 'C':
			case '3':
				coordenadas[i]=2;
				break;
			case 'd':
			case 'D':
			case '4':
				coordenadas[i]=3;
				break;
			case 'e':
			case 'E':
			case '5':
				coordenadas[i]=4;
				break;
			case 'f':
			case 'F':
			case '6':
				coordenadas[i]=5;
				break;
			case 'l':
			case 'L':
				System.out.println("Leyenda:");
				mostrarLeyenda();
				coordenadas[i]=6;  //pongo un if más tarde para dar error y volver a pedir coordenadas
				break;
			default:
				System.out.println("Las coordenadas introducidas no son válidas.");
				coordenadas[i]=6;  //pongo un if más tarde para dar error y volver a pedir coordenadas
				break;
			}
		}


		return coordenadas;

	}

	public static char [][] prepararTableroJugador(){

		char tableroFinal [][] = iniciarTablero();
		int cont=10;

		//pongo 10 de límite en el for porque va a haber 10 barcos solamente de primeras

		do {

			System.out.println("\n \nIntroduzca Letra de la A a la F y Número del 1 al 6 para posicionar sus "+cont+" barcos restantes");
			int coordenadas [] = pedirCoordenadas();
			
			if (coordenadas[0]<6 && coordenadas[1]<6) {
				if (tableroFinal[coordenadas[0]][coordenadas[1]]=='B') {
					System.out.println("Posición repetida, por favor introduzca una nueva posición");
				} else {
					tableroFinal[coordenadas[0]][coordenadas[1]]='B';
					limpiarPantalla();
					cont--;
					if (cont>0) {mostrarTablero(tableroFinal);};
				}
			} else {
				System.out.println("Por favor, introduzca unas coordenadas correctas");
			}

		} while (cont>0);

		return tableroFinal;
	}
	
	public static void limpiarPantalla() {
		for (int i=0;i<40;i++) {
			System.out.println("");
		}
	}

	public static void mostrarTablero(char [][] tablero) {
		char [] letras = {'A','B','C','D','E','F'};
		System.out.println("\n  1 2 3 4 5 6");
		for (int i=0;i<tablero.length;i++) {
			System.out.print(letras[i]);
			for (int j=0;j<tablero[i].length;j++){
				System.out.print(" "+tablero[i][j]);
			}
			System.out.println();
		}
	}

	public static void mostrarLeyenda () {
		System.out.println("- => Agua \nO => Fallo \nB => Barco \nX => Tocado \nL => Mostrar Leyenda");
	}
	
	public static char [][] atacar(char [][] tableroAtacado){
		
		char tableroFinal [][] = tableroAtacado;
		
		int coordenadas [] = pedirCoordenadas();
		
		if (coordenadas[0]<6 && coordenadas[1]<6) {
			
				switch (tableroFinal[coordenadas[0]][coordenadas[1]]) {
				case '-':
					System.out.println("<<Agua>>");
					tableroFinal[coordenadas[0]][coordenadas[1]]='O';
					tableroAdicional[coordenadas[0]][coordenadas[1]]='O';
					break;
				case 'B':
					System.out.println("<<Tocado y hundido>>");
					tableroFinal[coordenadas[0]][coordenadas[1]]='X';
					tableroAdicional[coordenadas[0]][coordenadas[1]]='X';
					barcosIA--;
					mostrarTablero(tableroAdicional);
					if(barcosIA>0) {
						tableroFinal=atacar(tableroFinal);
					}
					break;
				case 'O':
				case 'X':
					System.out.println("Esa posición ya ha sido atacada, vuelva a intentarlo");
					tableroFinal=atacar(tableroFinal);
					break;
				}
			} else {
				System.out.println("El número introducido no es correcto, por favor vuelva a intentarlo.");
				tableroFinal=atacar(tableroFinal);
			}
		
		return tableroFinal;
	}
	
	public static char [][] atacarIA (char [][] tableroAtacado){

		char tableroFinal[][]=tableroAtacado;
		
		int a;
		int b;
		char letraA='a';

		do {
			
			a = (int) Math.floor(Math.random()*6+0);
			b = (int) Math.floor(Math.random()*6+0);
			
			switch(a) {
			case 0:
				letraA='A';
				break;
			case 1:
				letraA='B';
				break;
			case 2:
				letraA='C';
				break;
			case 3:
				letraA='D';
				break;
			case 4:
				letraA='E';
				break;
			case 5:
				letraA='F';
				break;
			}
			
		}while (tableroFinal[a][b]=='O'||tableroFinal[a][b]=='X');

			switch (tableroFinal[a][b]) {
			case '-':
				System.out.println("<<La IA ha atacado "+letraA +""+ (b+1) +" impactando en Agua>>");
				tableroFinal[a][b]='O';
				break;
			case 'B':
				System.out.println("<<La IA ha atacado "+letraA +""+ (b+1) +" impactando en un barco y hundiéndolo>>");
				tableroFinal[a][b]='X';
				barcosJugador--;
				if(barcosIA>0) {
					tableroFinal=atacarIA(tableroFinal);
				}
				break;
			}

		return tableroFinal;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub


		tableroIA=prepararTableroIA();
		tableroAdicional=iniciarTablero();
		
		mostrarLeyenda();
		
		System.out.println();
		
		tableroJugador=prepararTableroJugador();  //Aquí empieza la partida realmente
		
		System.out.println("Barcos Jugador: "+ barcosJugador+ " ----------------- Barcos IA: "+barcosIA);
		mostrarTablero(tableroAdicional);
		mostrarTablero(tableroJugador);
		System.out.println("Podemos comenzar.");

		do {
			
			System.out.println("Introduzca una coordenada para lanzar un ataque al enemigo, o 'L' para ver la leyenda:");
			
			tableroIA=atacar(tableroIA); //Turno del jugador
			
			if(barcosIA!=0) {
				System.out.println();
				tableroJugador=atacarIA(tableroJugador); //Turno de la IA
			} 

			// mostrarTablero(tableroIA);
			
			System.out.println("\n \nBarcos Jugador: "+ barcosJugador+ " ----------------- Barcos IA: "+barcosIA);
			
			mostrarTablero(tableroAdicional);
			mostrarTablero(tableroJugador);
			
		}while(barcosJugador>0 && barcosIA>0);
		
		if (barcosIA==0) {
			System.out.println("Enhorabuena, has ganado y aún quedan "+barcosJugador+" barcos tuyos en pie.");
		} else {
			System.out.println("Lo sentimos, has perdido.\nTablero de la IA:\n");
			mostrarTablero(tableroIA);
		}

	}

}
