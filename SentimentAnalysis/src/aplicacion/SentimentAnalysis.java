package aplicacion;

import java.util.HashMap;
import java.util.Vector;

public class SentimentAnalysis {
	public static void main(String[] args) {
		parseArgs(args);
		HashMap<Integer,Texto> textos = GestorTextos.cargarTextos(GestorFicheros.leerEntrada());
		for(Texto t:textos.values()){
			Elixa.ejecutar(t);
		}
		GestorTextos.generarSalida();

	}
	
	private static void parseArgs(String[] args){
		int opt = 0;
		for(String arg:args){
			if(opt==0){
				if(arg.compareTo("-h")==0||arg.compareTo("--help")==0){
					helpMenu();
					System.exit(0);
				}					
				if(arg.compareTo("-i")==0||arg.compareTo("--input")==0){
					opt=1;
					continue;
				}			
				if(arg.compareTo("-o")==0||arg.compareTo("--output")==0){
					opt=2;
					continue;
				}			
				if(arg.compareTo("-t")==0||arg.compareTo("--temp")==0){
					opt=3;
					continue;
				}		
			}else{
				switch(opt){
				case 1:
					GestorFicheros.setInPath(arg);
					opt=0;
					break;
				case 2:
					GestorFicheros.setOutPath(arg);
					opt=0;
					break;
				}
			}
		}
	}
	private static void helpMenu(){
		System.out.println("\nSentiment Analysis\nUsage: java -jar SentimentAnalysis.jar [OPTIONS]"+
				"\n\n\t-i or --input: change input path. All subfolders and their files will be analyzed too."+
				"\n\t\tdefault input folder: input"+
				"\n\n\t-o or --output: change output path."+
				"\n\t\tdefault output folder: output"+
				"\n\n\t-t or --temp: change temporal files folder."+
				"\n\t\tdefault temp folder: temp/"+
				"\n\n\t-h or --help: display this help menu.\n\n"
				);
	}
}
