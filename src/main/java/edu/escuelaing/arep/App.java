package edu.escuelaing.arep;

import java.io.FileNotFoundException;
import java.io.IOException;
import static spark.Spark.*;
import spark.Request;
import spark.Response;

/**
 * La clase App se encarga de procesar el archivo y realizar las operaciones
 * pertinentes
 *
 */
public class App {
    
    /**
     * Permite mostrar la página para calcular la media y la desviación estándar
     * @param args
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public static void main(String[] args) throws FileNotFoundException, IOException {
        //Probando funcionamiento de la LinkedList
        /**
         * System.out.println( "Hello World!" ); LinkedList list = new
         * LinkedList(); list.insertarInicio(1); list.insertarFin(2);
         * list.insertarFin(3); list.insertarFin((float) 4.4);
         * list.insertarFin(5); System.out.println("After insertions--");
         * list.mostarLista(); list.eliminarUltimo(); System.out.println("After
         * removal--"); list.mostarLista(); list.eliminarPorIndice(1);
         * System.out.println("After removal--"); list.mostarLista();
         * System.out.println("Get Node--"); Nodo node = list.getNodo(1);
        node.mostrarData();*
         */
        
        
        port(getPort());
        get("/", (req, res) -> leer(req, res));
        get("/salida", (req, res) -> mostrar(req, res));
    }
    /**
     * Este método permite al usuario ingresar los números para hacer los cálculos 
     * @param req
     * @param res
     * @return pagina
     */
    private static String leer(Request req, Response res){
        String pagina = "<!DOCTYPE html>"
        		+ "<html><body>"
                        + "<center>"
                        + "<h2>Calculadora de media y desviación estándar de un conjunto de números reales</h2>"
        		+ "<h3>Ingrese el conjunto de números separado por un espacio</h3>"
        		+ "<form action='salida'>"
        		+ "<input  type='text' name='datos' placeholder=\"Ej. 1;2;3;4;9\" required>"
        		+ "<input type='submit' value='Calcular'>"
        		+ "</form>"
                        + "<center>"
        		+ "</body></html>";
        return pagina;
    }
    
    private static String mostrar(Request req, Response res) {
        String[] datos = req.queryParams("datos").split(";");
        LinkedList a = new LinkedList();
        LinkedList b = new LinkedList();
        for (String dato : datos) {
            a.insertarInicio(Float.parseFloat(dato));
            b.insertarFin(Float.parseFloat(dato));
        }
        double med = promedio(a);
        double desviacion = desviacion(b, (float) med);
        String pagina = "<!DOCTYPE html>"
                + "<html><body>"
                + "<center>"
                + "<form actions ='/salida'><h3>Media: " + med + "</h3><br />"
                + "<body>"
                + "<h3>Desviación Estándar: " + desviacion + "</h3></form>"
                + "<h5 style=\"color:#0000CD\"; >Volver a Calcular: </h5>"
                + "<form actions ='entrada'>"
                + "<input  type='text' name='datos' placeholder=\"Ej. 1.0;2.0;3.0;4.0\" required>"
        	+ "<input type='submit' value='Calcular'>"
                + "</form>"
                + "<center>"
                + "</body></html>";
        return pagina;
    }
       

    /**
     * Realizar el promedio de un conjunto de datos dentro de la linked list
     *
     * @param lista
     */
    public static float promedio(LinkedList lista) {
        float sum = 0;
        float tam = lista.getTamano();
        for (int j = 0; j < tam; j++) {
            sum += lista.eliminarPrimero().getI();
            //System.out.println("La media es " + sum );
        }
        float prom = sum / tam;
        return prom;
    }

    /**
     * Realizar el promedio de un conjunto de datos dentro de la linked list
     *
     * @param lista
     * @param prom
     */
    public static float desviacion(LinkedList lista, float prom) {
        int sum = 0;
        int tam = lista.getTamano();
        for (int j = 0; j < tam; j++) {
            float valor = lista.eliminarPrimero().getI();
            sum += Math.pow((valor - prom), 2);
        }
        float desv = (float) Math.pow((sum / (float) (tam - 1)), 0.5);
        return desv;
    }

    static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567; //returns default port if heroku-port isn't set(i.e. on localhost)    }
    }
}
