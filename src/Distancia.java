import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
public class Distancia {

	String cadena=""; // Variable utilizada para guardar el contenido del vector e imprimirlo 
    ArrayList<String> listaPalabras= new ArrayList<String>();
    //En caso de querer sobrescribir el fichero de salida poner a true:
    boolean sobrescribir=true;
    
    public static void main(String[] args) {
        //Ejemplo : 
    	//new Distancia("-t","in","out");
        new Distancia(args[0],args[1],args[2]);
    }
    
	public Distancia(String select, String f_entrada, String f_salida){
		boolean traza=(select.trim().equals("-t") || select.trim().equals("-T"));
        boolean help=(select.trim().equals("-h") || select.trim().equals("-H"));
        if(!traza && !help ){
            f_salida=f_entrada;
            f_entrada=select;
        }
        if(f_entrada.isEmpty())System.out.println("Seleccione archivo de entrada: "+f_entrada);
        //Solo se permitiran archivos con formato *.txt, en caso de que no se ponga expresamente se añadira por el programa.
        if(!f_entrada.endsWith(".txt"))f_entrada=f_entrada+".txt";
        if(!f_salida.isEmpty() && !f_salida.endsWith(".txt"))f_salida=f_salida+".txt";
        //(-h)OPCION HELP o AYUDA
        if (help){
            System.out.println("SINTAXIS:");
            System.out.println("servicio [-t] [-h] [fichero_entrada] [fichero_salida]");
            System.out.println("-t\t\t\t Traza la construcción de la distancia de edición");
            System.out.println("-h\t\t\t Muestra esta ayuda");
            System.out.println("fichero_entrada\t\t Nombre del fiechero de entrada");
            System.out.println("fichero_salida\t\t Nombre del fichero de salida");
        }               
        //Caso con archivo de entrada
        else if(!f_entrada.isEmpty()){
            try {
                //Lectura del fichero de entrada
                BufferedReader lector = new BufferedReader(new FileReader(f_entrada));
                String linea = lector.readLine();
                while(linea != null){
                	listaPalabras.add(linea);   
                    //Se rellena el ArrayList con los coeficientes existentes
                    linea = lector.readLine();
                }
                lector.close();
                if(listaPalabras.size()!=2) {
                	System.out.println("El fichero de entrada es incorrecto.");
                	System.exit(1);                	
                }
            }
            catch (Exception e){
            	System.out.println("Ha ocurrido un error no previsto\n"+e.getMessage());
            	System.exit(1);
            }            
        }
        else{System.out.println("Comando incorrecto");}
        //Casos de salida de fichero
        try {
        	cadena=algoritmo(listaPalabras.get(0),listaPalabras.get(1));
            //Sin archivo de salida, imprimimos por pantalla
            if(f_salida.isEmpty()){
                System.out.println(cadena);    
            }
            //Guardamos el contenido en el archivo de salida
            else if(f_salida!=null || !f_salida.isEmpty()) {
                File nuevo=new File(f_salida);
                String ruta=nuevo.getAbsolutePath();
                File archivo=new File(ruta);
                if(!sobrescribir && archivo.exists()){
                    System.out.println("Error, no se permite sobreescribrir.");
                }
                else {
                	PrintWriter writer = new PrintWriter(archivo); 
                	writer.print(cadena); 
                	writer.close(); 
                }  
            }
        }
        catch(Exception e){System.out.println("Ha ocurrido un error no previsto");}
    }
	  
	//Metodo que ejecuta el algoritmo y devuelve la respuesta obtenida
	public String algoritmo(String pal1, String pal2) {	
		String salidaConsola="";
		Arbol b=prog_dinnamica(pal1,pal2);
		//Añadimos el resultado a una lista filtrando el camino de vuelta hasta la raiz
		ArrayList<Arbol> resultado = new ArrayList<Arbol>();
		while(b.getPosicion() != 0) {
			resultado.add(b);
			b=b.getPadre();
		}
		//Imprimimos el numero de operaciones
		salidaConsola+=resultado.size()+"\n";
		for(int i=resultado.size()-1; i>=0 ; i--) {
			Arbol arb = resultado.get(i);
			salidaConsola+=arb.getOperacion() + " "+(arb.getPosicion()+1)+" "+arb.getNodo()+"\n";
		}
		return salidaConsola;		
	}
	
	//Metodo que realiza el algoritmo de programacion dinamica que construye el árbol con las posibilidades
	public static Arbol prog_dinnamica(String pal1, String pal2) {
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
