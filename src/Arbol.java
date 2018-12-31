import java.util.ArrayList;

public class Arbol {
	public String nodo = "";
	public ArrayList<Arbol> hijos= new ArrayList<Arbol>();
	public Arbol padre;
	public String operacion="";
	public int posicion;

	public Arbol(String cadena) {
		nodo=cadena;
	}
	
	public Arbol(String cadena, Arbol padre) {
		nodo=cadena;
		this.padre=padre;
	}
	
	public Arbol(String cadena, Arbol padre,String operacion, int posicion) {
		nodo=cadena;
		this.padre=padre;
		this.operacion=operacion;
		this.posicion=posicion;
	}
	
	public int getPosicion() {
		return posicion;
	}

	public void setPosicion(int posicion) {
		this.posicion = posicion;
	}

	public String getOperacion() {
		return operacion;
	}

	public void setOperacion(String operacion) {
		this.operacion = operacion;
	}


	public String getNodo() {
		return nodo;
	}

	public void setNodo(String nodo) {
		this.nodo = nodo;
	}

	public ArrayList<Arbol> getHijos() {
		return hijos;
	}

	public void setHijos(ArrayList<Arbol> hijos) {
		this.hijos = hijos;
	}

	public Arbol getPadre() {
		return padre;
	}

	public void setPadre(Arbol padre) {
		this.padre = padre;
	}
}
