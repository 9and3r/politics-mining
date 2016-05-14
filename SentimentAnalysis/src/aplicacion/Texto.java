package aplicacion;

import java.util.Collection;
import java.util.Vector;

public class Texto {
	
	protected static int id_gen=0;
	private int textId;
	private String path, texto, domain, title, about;
	private int pos=0,neg=0,neu=0;
		
	public Texto(String path, String texto, String tit, String dom,String nomb){
		this.path = path;
		this.textId = id_gen++;
		this.texto = texto;
		this.title = tit;
		this.domain = dom;
		this.about = nomb;
	}

	public String getPath(){
		return this.path;
	}
	
	public String getText(){
		return this.texto;
	}
	
	public int getId(){
		return this.textId;
	}
	

	public void setPos(int pos) {
		this.pos = pos;
	}

	public void setNeg(int neg) {
		this.neg = neg;
	}

	public void setNeu(int neu) {
		this.neu = neu;
	}

	public String toString(){
		return this.texto+GestorFicheros.sepTexto+
				"\ntitle="+this.title+
				"\ndomain="+this.domain+
				"\nabout="+this.about+
				"\npositive="+this.pos+
				"\nneutral="+this.neu+
				"\nnegative="+this.neg+"\n";
	}

	public void setPolaridad(int pos, int neu, int neg) {
		this.setPos(pos);	
		this.setNeu(neu);
		this.setNeg(neg);
	}

}
