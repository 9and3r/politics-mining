package aplicacion;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CodingErrorAction;
import java.util.Vector;

public class GestorFicheros {

	protected static final String sepTexto = "##########";
	protected static final String separador = File.separator;

	private static String inPath = System.getProperty("user.dir")+separador+"input"+separador;
	private static String temp = System.getProperty("user.dir")+separador+"tmp"+separador;
	private static String outPath = System.getProperty("user.dir")+separador+"output"+separador;
	private static GestorFicheros instance;

	private GestorFicheros(){}

	public static void setPath(String input, String temp, String output){
		setInPath(input);
		setTemp(temp);
		setOutPath(output);
	}

	public static void setInPath(String path) {
		if(!path.endsWith(separador))
			path+=separador;
		GestorFicheros.inPath = path;
	}

	public static void setTemp(String path) {
		if(!path.endsWith(separador))
			path+=separador;
		GestorFicheros.temp = path;
	}

	public static void setOutPath(String path) {
		if(!path.endsWith(separador))
			path+=separador;
		GestorFicheros.outPath = path;
	}

	public static String getTemp() {
		return temp;
	}

	public static GestorFicheros getInstance(){
		if(instance==null)
			instance = new GestorFicheros();
		return instance;
	}

	public static Texto leerFichero(String path, String filename){
		try {
			BufferedReader br = new BufferedReader(new FileReader(path+filename));
			StringBuilder text = new StringBuilder();
			StringBuilder title = new StringBuilder();
			StringBuilder domain = new StringBuilder();
			StringBuilder about = new StringBuilder();
			String line = br.readLine();
			boolean fin = false;
			while (line != null) {
				if(!line.matches("\\s*")){
					if(!fin){
						if(line.startsWith(sepTexto))
							fin=true;
						else{
							text.append(line);
							text.append("\n");
						}
					}
					if(fin){
						if(line.startsWith("title")){
							title.append(line.split("=")[1]);
						}else 
							if(line.startsWith("domain")){
								domain.append(line.split("=")[1]);
							}else 
								if(line.startsWith("about")){
									about.append(line.split("=")[1]);
								}
					}
				}
				line = br.readLine();
			}
			br.close();
			return new Texto(filename,text.toString(),title.toString(),domain.toString(),about.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		} 
	}

	//Lee todos los archivos de la carpeta de input
	public static Vector<Texto> leerEntrada(){
		Vector<Texto> textos = new Vector<Texto>();
		Vector<String> files = getFiles(inPath);
		for(String file:files)
			textos.add(leerFichero(inPath, file));
		return textos;
	}

	public static String crearTemp(String text, String filename) {
		// TODO Auto-generated method stub
		try {			
			File file = new File(temp+filename);
			file.getParentFile().mkdirs();
			CharsetEncoder encoder = Charset.forName("UTF-8").newEncoder();
			encoder.onMalformedInput(CodingErrorAction.REPORT);
			encoder.onUnmappableCharacter(CodingErrorAction.REPORT);
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),encoder));	
			out.write(text);
			out.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		return temp+filename;
	}

	/*
	 * En vez de sacar solo el nombre del fichero, 
	 * saca el path relativo para crear carpetas 
	 * y evitar ficheros con mismo nombre en distintas subcarpetas
	 */
	private static Vector<String>  getFiles(String folderPath){
		Vector<String> files = new Vector<String>();
		for(File f:(new File(folderPath)).listFiles())
			if(f.isDirectory())
				files.addAll(getFiles(f.toString()));
			else
				files.add(f.toString().replace(folderPath, ""));
		return files;
	}

	public static void crearSalida(Texto text) {
		// TODO Auto-generated method stub
		try {			
			File file = new File(outPath+text.getPath());
			file.getParentFile().mkdirs();
			CharsetEncoder encoder = Charset.forName("UTF-8").newEncoder();
			encoder.onMalformedInput(CodingErrorAction.REPORT);
			encoder.onUnmappableCharacter(CodingErrorAction.REPORT);
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file),encoder));	
			out.write(text.toString());
			out.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
