import java.util.ArrayList;
import java.util.Collections;

public class Distancia {

	public static void main(String[] args) {
		String pal1="colchon";
		String pal2="colacao";
		
		Arbol b=prog_din(pal1,pal2);

		//Añadimos el resultado a una lista filtrando el camino de vuelta hasta la raiz
		ArrayList<Arbol> resultado = new ArrayList<Arbol>();
		while(b.getPosicion() != 0) {
			resultado.add(b);
			b=b.getPadre();
		}
		//Imprimimos el numero de operaciones
		System.out.println(resultado.size());
		for(int i=resultado.size()-1; i>=0 ; i--) {
			Arbol arb = resultado.get(i);
			System.out.println(arb.getOperacion() + " "+(arb.getPosicion()+1)+" "+arb.getNodo());
		}
		
	}
	
	public static Arbol prog_din(String pal1, String pal2) {
		//Creamos un arbol cuyo nodo inicial será la palabra original
		ArrayList<Arbol> casos = new ArrayList<Arbol>();
		casos.add(new Arbol(pal1));
		//Inicializamos la posicion de la letra que vamos a tratar
		int posicion=0;
		
		while(!casos.isEmpty()) {
			//Inicializamos el caso a tratar
			Arbol arbol=casos.get(0);
			pal1=casos.get(0).getNodo();
			
			//Solucion encontrada:
			if(pal1.equals(pal2)) {
				return arbol;
			}
			//Avanzamos las letras que coincidan en las 2 palabras
			try {
				while(pal1.charAt(posicion) == pal2.charAt(posicion)) {
					posicion++;
				}
			}
			//En caso de que alguna de las palabras sea mas larga que la otra capturamos el error
			catch (StringIndexOutOfBoundsException e) {}
			
			try {
				//Si la primera palabra es mas larga borramos cuando la hemos consegido
				if(posicion==pal2.length()) casos.add(borrado(arbol,posicion));
				else {
					casos.add(insercion(arbol,posicion,pal2.charAt(posicion)));
					casos.add(borrado(arbol,posicion));
					casos.add(sustitucion(arbol, posicion, pal2.charAt(posicion)));
				}
			}
			//En caso de que alguna de las palabras sea mas larga que la otra capturamos el error
			catch (StringIndexOutOfBoundsException e) {}
			casos.remove(0);
		}		
		return null;
	}
	
	public static Arbol borrado(Arbol arbol, int posicion) {
		String a1 = arbol.getNodo().substring(0, posicion);
		String a2 = arbol.getNodo().substring(posicion+1);
		String res=a1+a2;
		return new Arbol(res,arbol,"Borrado",posicion);
	}
	
	public static Arbol insercion(Arbol arbol, int posicion, char letra) {
		String a1 = arbol.getNodo().substring(0, posicion);
		String a2 = arbol.getNodo().substring(posicion);
		String res=a1+letra+a2;
		return new Arbol(res,arbol,"Insercion",posicion);
	}
	
	public static Arbol sustitucion(Arbol arbol, int posicion, char letra) {
		String a1 = arbol.getNodo().substring(0, posicion);
		String a2 = arbol.getNodo().substring(posicion+1);
		String res=a1+letra+a2;
		return new Arbol(res,arbol,"Sustitucion",posicion);
	}

}
