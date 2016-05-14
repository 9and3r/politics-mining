package aplicacion;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.GenericArrayType;
import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;


public class Elixa {

	private static final String sep = GestorFicheros.separador;
	protected static final String path = System.getProperty("user.dir")+sep+"lib"+sep+"Elixa"+sep;

	private Elixa(){}

	public static void ejecutar(Texto texto) {
		String input = generarFicheroTemporal(texto);
		if(input==null) return;
		ProcessBuilder pb = new ProcessBuilder("java", "-jar", "target"+sep+"elixa-0.5.jar", "tag-atp",
				"-f","ireom","-m","es-twt.model","-cn","3","-p","es-twt.cfg",
				"-l","es");
		pb.directory(new File(path));
		//pb.redirectErrorStream(true); //debug, para redirigir error a salida normal
		try {
			pb.redirectInput(new File(input));
			Process p = pb.start();

			LogStreamReader lsr = new LogStreamReader(p.getInputStream(), texto.getId());
			Thread thread = new Thread(lsr, "LogStreamReader");
			thread.start();
			p.waitFor();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static String generarFicheroTemporal(Texto texto){
		String ireom ="";
		int fraseid=0;
		String[] frases= texto.getText().split("(?<=[.?!\\t\\n])(?<![A-Z]\\.)\\s*(?=([A-Z-—\"0-9]|((¿|¡)[A-Z0-9])))");
		for(String frase:frases)
			if(frase.compareTo("")!=0){
				ireom+=texto.getId()+"-"+fraseid+"\t"+frase+"\n";
				fraseid++;
			}
		//ireom+=texto.getId()+"-"+fraseid+"\t"+texto.getText()+"\n";
		return GestorFicheros.crearTemp(ireom,texto.getPath());
	}
}

class LogStreamReader implements Runnable {

	private BufferedReader reader;
	private int textId;

	public LogStreamReader(InputStream is, int id) {
		this.reader = new BufferedReader(new InputStreamReader(is));
		this.textId = id;
	}

	public void run() {
		int pos=0,neg=0,neu=0;
		try {
			String line = reader.readLine();
			while (line != null) {
				System.out.println(line);
				if(!line.startsWith("Feature")){
					String pol = line.split("\t")[2];
					if(0==pol.compareTo("positive")) pos++;
					else if (0==pol.compareTo("negative")) neg++;
					else if (0==pol.compareTo("neutral")) neu++;
				}
				line = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		GestorTextos.setPolaridad(textId, pos, neu, neg);	
	}
}

