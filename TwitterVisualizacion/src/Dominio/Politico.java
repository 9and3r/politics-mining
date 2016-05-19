package Dominio;
import java.text.DecimalFormat;
import java.util.Vector;

public class Politico
{
	private String nombre;
	Vector<Entrada> entradas;

	public Politico()
	{
		nombre ="";
		entradas = new Vector<Entrada>();
	}

	public Politico(String n)
	{
		nombre = n;
		entradas = new Vector<Entrada>();
	}

	public String getNombre(){return nombre;}
	public void setNombre(String n){nombre=n;}
	public Vector<Entrada> getEntradas(){return entradas;}


	//FUNCIONES - ENTRADAS

	public int getNumeroEntradas(){return entradas.size();}
	public boolean contieneEntrada(Entrada e){return entradas.contains(e);}
	public void addEntrada(Entrada e){if(!contieneEntrada(e))entradas.add(e);}


	public boolean equals(Politico p){return nombre.equals(p.getNombre());}
	
	
	//Visualización
	public String getDatos()
	{
		String frase = "";
		frase = frase + "Nombre: " + nombre + "\n";
		frase = frase + "Entradas:\n";
		for(int i=0;i<entradas.size();i++)
		{
			frase = frase + entradas.elementAt(i).getMetaDatos() + "\n\n";
		}
		return frase; 
	}
	
	
	public String toString(){return nombre;}
	
	
	//Sentiment
	
	public int getSumaPositivos()
	{
		int suma=0; 
		for(int i=0;i<entradas.size();i++)
		{
			suma = suma + entradas.elementAt(i).getPos();
		}
		return suma;
	}
	
	public int getSumaNeutros()
	{
		int suma=0; 
		for(int i=0;i<entradas.size();i++)
		{
			suma = suma + entradas.elementAt(i).getNeu();
		}
		return suma;
	}
	
	public int getSumaNegativos()
	{
		int suma=0; 
		for(int i=0;i<entradas.size();i++)
		{
			suma = suma + entradas.elementAt(i).getNeg();
		}
		return suma;
	}
	
	public String getSentiment()
	{
		String frase = "";
		DecimalFormat f = new DecimalFormat("##.00");
		
		int pos = getSumaPositivos();
		int neu = getSumaNeutros();
		int neg = getSumaNegativos();
		int total = pos + neu + neg;
		frase = frase + "Positivos: " + pos;
		if(total!=0) frase = frase + " (" +f.format(((float)pos/(float)total)*100.00) + "%)"; 
		frase = frase + "\nNeutros: " + neu;
		if(total!=0) frase = frase + " (" +f.format(((float)neu/(float)total)*100.00) + "%)"; 
		frase = frase + "\nNegativos: " + neg;
		if(total!=0) frase = frase + " (" +f.format(((float)neg/(float)total)*100.00) + "%)"; 
		//frase = frase + "\nTotal: " +total;
		return frase;
	}

}


