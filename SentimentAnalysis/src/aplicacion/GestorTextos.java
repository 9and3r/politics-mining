package aplicacion;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

public class GestorTextos {
	
	private static HashMap<Integer,Texto> textos = new HashMap<Integer,Texto>();
	private static GestorTextos instance;
	
	private GestorTextos(){}
	
	public static GestorTextos getInstance(){
		if(instance==null)
			instance = new GestorTextos();
		return instance;
	}
	
	public static HashMap<Integer, Texto> cargarTextos(Collection<Texto> entrada){
		textos = new HashMap<Integer,Texto>();
		for(Texto t:entrada){
			addTexto(t);
		}
		return textos;
	}
	
	private static void addTexto(Texto t){
		textos.put(t.getId(),t);
	}
	
	public static synchronized void setPolaridad(int id, int pos, int neu, int neg){
		textos.get(id).setPolaridad(pos,neu,neg);
	}
	
	public static Set<Integer> getKeys(){
		return textos.keySet();
	}
	
	public static Texto getTexto(int id){
		return textos.get(id);
	}
	
	public static void generarSalida(){
		for(Texto t:textos.values())
			GestorFicheros.crearSalida(t);
	}
}
