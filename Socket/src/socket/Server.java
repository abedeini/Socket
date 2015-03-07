/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Armand
 */
public class Server {

    private String host;
    private int port;

    public Server(/*InetAddress ip,*/int port) throws IOException {

        ServerSocket server = new ServerSocket(port);
        int i = 0;
        while (true) {
            Socket s = server.accept();
            System.out.println("Client"+i+" d'adresse ip " + s.getRemoteSocketAddress().toString() + " s'est connecté !");
            i++;
            InputStream is = s.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String number = br.readLine();
            System.out.println(number);
            
             //Sending the response back to the client.
                OutputStream os = s.getOutputStream();
                OutputStreamWriter osw = new OutputStreamWriter(os);
                BufferedWriter bw = new BufferedWriter(osw);
                bw.write("Bonjour Client\n");
                bw.flush();

        }

    }

    public static void main(String[] zero) throws IOException {

        Server server = new Server(1235);

    }

}
