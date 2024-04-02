package ServidorMultihilo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.file.Paths;


// El servidor envía un archivo grande a cada cliente
public class HiloHandler implements Runnable {
    private final Socket ch;
    private int id;
    PrintWriter out;
    BufferedReader in;

    // En el constructor creamos los flujos
    public HiloHandler(Socket ch, int cont) throws IOException {
        // Recibe el socket para escuchar conexiones de cliente
        this.ch = ch;
        id = cont + 1;

        // Establece el stream de salida
        out = new PrintWriter(ch.getOutputStream(), true);

        // Establece el stream de entrada
        in = new BufferedReader(new InputStreamReader(ch.getInputStream()));

        System.out.println("Conexion recibida del cliente " + id + ": " + ch.getInetAddress().getHostAddress());
    }

    @Override
    public void run() {
        try {
            // Abrir uun flujo a un archivo grande para que tarde enviándolo
            String pathArchivo = Paths.get("ServidorMultihilo\\archivote.csv").toAbsolutePath().toString();
            File file_in = new File (pathArchivo);

            // Pasar las lineas del archivo al cliente
            FileReader fr;
            fr = new FileReader(file_in);
            BufferedReader br = new BufferedReader(fr);

            String linealeida;

            while ((linealeida = br.readLine()) != null) {
                out.println(linealeida);
            }

            // Enviamos al cliente que el archivo ha sido transmitido
            out.println("EOF");
            br.close();
            fr.close();

            // Leemos la despedida del cliente
            System.out.println("Cliente " + id + ": " + in.readLine());

            // Cerramos las conexiones
            out.close();
            in.close();
            ch.close();
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
}
