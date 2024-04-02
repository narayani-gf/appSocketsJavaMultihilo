package ServidorMultihilo;

import java.io.IOException;
import java.net.ServerSocket;

public class ServidorMultihilo {

    public static void main(String[] args) {
        // Establece el puerto a utilizar
        int puerto = 8080;
        // Crea un socket de servidor
        try  (ServerSocket ss = new ServerSocket(puerto)){
            int cont = 0;
            System.out.println("Servidor escuchando en el puerto: " + puerto + "...");

            // El servidor va a escuchar conexiones hasta presionar Ctrl + C
            // Cada cliente se manda a un nuevo hilo por lo que siempre
            // estará dispuesto a recibir nuevos clientes
            while (true) {
                // El servidor envía un archivo grande a cada cliente
                System.out.println("El servidor tiene " + cont + " clientes conectados.");
                HiloHandler cliente = new HiloHandler(ss.accept(), cont);
                Thread h1 = new Thread(cliente);
                h1.start();
                cont++;
            }
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
}