package Datos;
import java.io.File;
import java.util.Vector;


import Dominio.Entrada;

public class CargadorDeDatos
{
	private static Vector<Entrada> entradas;
	//private static Vector<Politico> politicos;

	public CargadorDeDatos()
	{
		entradas = new Vector<Entrada>();
	}
	
	public static boolean formatoValido(File f)
	{
		//Se comprueba si el formato del archivo es txt
		return f.getName().endsWith(".txt");
	}
	
	public Vector<Entrada> cargarDirectorio(String pathDirectorio)
	{
		/** Esta carga en el sistema todos los archivos que estén
		 *  en el directorio que se le pasa como parámetro y tengan 
		 *  como formato "txt". 
		 *  @param 	directorio: path del directorio cuyos archivos
		 *  					se queiren cargar.  **/
		
		Vector<Entrada> ve = new Vector<Entrada>();
		
		File f = new File(pathDirectorio);
		if (f.exists())
		{ // Directorio existe

			System.out.print("Cargando " + pathDirectorio);

			//Se guardan los ficheros del directorio en un array
			File[] ficheros = f.listFiles();

			//Si el directorio no contiene archivos --> Se notifica
			if(ficheros.length==0) System.out.println("Directorio vacio");
			System.out.println();

			Entrada e;

			//Se procesan los archivos del directorio
			for (int x=0;x<ficheros.length;x++)
			{
				//Se crea una Entrada
				e = new Entrada(ficheros[x]);
				if(e.archivoValido() && !entradas.contains(e) && formatoValido(ficheros[x]))
				{
					entradas.add(e);
				}
				if(e.archivoValido() && !ve.contains(e) && formatoValido(ficheros[x]))
				{
					ve.add(e);
				}
			}
		}
		else
		{ //Directorio no existe 
			System.out.println("Directorio no encontrado");
		}
		System.out.println("Se han carcado " + ve.size() + "entradas");
		return ve;
	}
	
	
	}
