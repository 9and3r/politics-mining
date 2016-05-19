package Dominio;

import java.util.Vector;

import Datos.CargadorDeDatos;

public class ControladorPrincipal
{

	Vector<Politico> politicos;
	CargadorDeDatos cdd;

	public ControladorPrincipal()
	{
		politicos = new Vector<Politico>();
		cdd = new CargadorDeDatos();
	}

	public void cargarDirectorio(String pathDirectorio)
	{
		Vector<Entrada> entradas = cdd.cargarDirectorio(pathDirectorio);
		boolean politicoExistente;
		for(int i=0;i<entradas.size();i++)
		{

			politicoExistente=false;
			for(int j=0;j<politicos.size();j++)
			{
				if(entradas.elementAt(i).getSobre().equals(politicos.elementAt(j).getNombre()))
				{
					/* Politico actual: politicos.elementAt(j)
					 * Entrada actual: entradas.elementAt(i)
					 */

					politicos.elementAt(j).addEntrada(entradas.elementAt(i));

					politicoExistente=true;
				}
			}
			if(politicoExistente==false)
			{
				Politico p = new Politico(entradas.elementAt(i).getSobre());
				p.addEntrada(entradas.elementAt(i));
				politicos.add(p);
			}
		}
	}

	public Vector<Entrada> getEntradasDe(String nombre)
	{
		Vector<Entrada> ve = new Vector<Entrada>();
		for(int i=0;i<politicos.size();i++)
		{
			if(politicos.elementAt(i).getNombre().equals(nombre))
			{
				ve = politicos.elementAt(i).getEntradas();
			}
		}
		return ve;
	}

	public String[] getNombres()
	{
		String nombres[] = new String[politicos.size()];
		for(int i=0;i<politicos.size();i++)
		{
			nombres[i] = politicos.elementAt(i).getNombre();
		}
		return nombres;
	}

	public Vector<Politico> getPoliticos(){return politicos;}

}
