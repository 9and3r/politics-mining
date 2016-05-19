package Dominio;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;

public class Entrada
{
	private String nombreArchivo;
	private String path;
	private String contenido;
	private String titulo;
	private String fuente;
	private String sobre;
	private boolean valido=false;
	private int pos,neu,neg;

	public Entrada(File file)
	{
		procesarArchivo(file);
	}

	public boolean archivoValido(){return valido;}



	public String getNombreArchivo() {return nombreArchivo;}
	public void setNombreArchivo(String nombreArchivo) {this.nombreArchivo = nombreArchivo;}
	public String getPath() {return path;}
	public void setPath(String path) {this.path = path;}
	public String getContenido() {return contenido;}
	public void setContenido(String contenido) {this.contenido = contenido;}
	public String getTitulo() {return titulo;}
	public void setTitulo(String titulo) {this.titulo = titulo;}
	public String getFuente() {return fuente;}
	public void setFuente(String fuente) {this.fuente = fuente;}
	public String getSobre() {return sobre;}
	public void setSobre(String sobre) {this.sobre = sobre;}
	public boolean isValido() {return valido;}
	public void setValido(boolean valido) {this.valido = valido;}
	public int getPos() {return pos;}
	public void setPos(int pos) {this.pos = pos;}
	public int getNeu() {return neu;}
	public void setNeu(int neu) {this.neu = neu;}
	public int getNeg() {return neg;}
	public void setNeg(int neg) {this.neg = neg;}

	public void procesarArchivo(File archivo)
	{
		nombreArchivo = archivo.getName();
		if(nombreArchivo.endsWith(".txt"))
		{
			path = archivo.getPath();
			try{
				contenido = leerArchivo(path);
			}catch(Exception e)
			{
				System.out.println("\tError al cargar " + nombreArchivo);
			}

			//División del contenido completo del archivo en filas.
			String[] filas = contenido.split("\n");

			//Encontrar línea de comienzo de metadatos
			int metaDatosPos=-1;
			for(int i=0; i<filas.length;i++)
			{
				if(filas[i].equals("##########"))
				{
					metaDatosPos=i;
				}
			}

			//Metadatos detectados correctamente
			System.out.println("\tProcesando " + nombreArchivo );
			if(metaDatosPos!=-1){
				titulo = filas[metaDatosPos+1].split("title=")[1];
				//System.out.println("\t\tTitulo="+titulo);
				fuente = filas[metaDatosPos+2].split("domain=")[1];
				//System.out.println("\t\tFuente="+fuente);
				sobre = filas[metaDatosPos+3].split("about=")[1];
				//System.out.println("\t\tSobre="+sobre);
				try{
					pos = Integer.parseInt(filas[metaDatosPos+4].split("positive=")[1]);
				}catch(Exception e)
				{
					//System.out.println(e.toString());
					pos = 0;
				}
				try{
					neu = Integer.parseInt(filas[metaDatosPos+5].split("neutral=")[1]);
				}catch(Exception e)
				{
					//System.out.println(e.toString());
					neu = 0;
				}
				try{
					neg = Integer.parseInt(filas[metaDatosPos+6].split("negative=")[1]);
				}catch(Exception e)
				{
					//System.out.println(e.toString());
					neg = 0;
				}
				valido=true;
			}
			else
			{
				valido=false;
			}
		}

	}



	String leerArchivo(String path) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(path));
		try {
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append("\n");
				line = br.readLine();
			}
			return sb.toString();
		} finally {
			br.close();
		}
	}


	public String getMetaDatos()
	{
		return "Nombre: "+ nombreArchivo+
				"\nPath: "+ path+
				"\nTítulo: "+titulo+
				"\nFuente: "+fuente+
				"\nSobre: " + sobre + "\n";
	}

	public void mostrarMetaDatos()
	{
		System.out.println(getMetaDatos());
	}

	public String toString()
	{
		return nombreArchivo;
	}

	public String getContenidoSinMetadatos()
	{
		String[] filas = contenido.split("\n");
		String frase = "";
		int metaDatosPos=-1;
		for(int i=0; i<filas.length;i++)
		{
			if(filas[i].equals("##########"))
			{
				metaDatosPos=i;
			}
		}
		for(int i=0;i<metaDatosPos;i++)
		{
			frase = frase + filas[i] + "\n";
		}
		return frase; 

	}

	public String getSentiment()
	{

		DecimalFormat f = new DecimalFormat("##.00");

		String frase="";
		float calculo = 0;
		frase = frase + "Positivos: " + pos;
		if(pos!=0 && pos+neu+neg!=0)
		{
			calculo = ((float)pos / (float)(pos+neg+neu))*100;
			frase = frase + " (" +  f.format(calculo)+ "%)";
		}
		frase = frase + "\nNeutros: " + neu;
		if(neu!=0 && pos+neu+neg!=0)
		{
			calculo = ((float)neu / (float)(pos+neg+neu))*100;
			frase = frase + " (" +  f.format(calculo)+ "%)";
		}
		frase = frase + "\nNegativos: " + neg;
		if(neg!=0 && pos+neu+neg!=0){
			calculo = ((float)neg / (float)(pos+neg+neu))*100;
			frase = frase + " (" +  f.format(calculo)+ "%)";
		}

		return frase;
	}

}
