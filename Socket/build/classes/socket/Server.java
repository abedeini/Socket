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
import java.util.*;
import java.awt.*;
import java.io.PrintWriter;
import javax.swing.*;

/**
 *
 * @author Armand
 */
public class Server extends JFrame {

    private HashSet<String> nicknames = new HashSet<String>();
    private JTextArea jta = new JTextArea();
    private String host;
    private int port;
    private int i = 1;
    private HashSet<PrintWriter> broadcasts = new HashSet<PrintWriter>();

    public static void main(String[] zero) throws IOException {

        new Server(8000);

    }

    public Server(int port) throws IOException {
        //put text area on frame
        setLayout(new BorderLayout());
        add(new JScrollPane(jta), BorderLayout.CENTER);
        setTitle("Server");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        try {
            //create server socket
            ServerSocket server = new ServerSocket(port);
            jta.append("Server started at: " + new Date() + '\n');

            while (true) {
                //listen for connection request
                Socket socket = server.accept();
                //cliet host name and ip 
                InetAddress inetAddress = socket.getInetAddress();
                jta.append("Client" + i + " with ip " + inetAddress.getHostAddress() + " and with name " + inetAddress.getHostName() + " is connected.\n");

                //create a new thread for the connection
                HandleClient task = new HandleClient(socket);
                //start new thread
                new Thread(task).start();
                i++;
            }
        } catch (IOException ex) {
            System.out.println(ex);
        }

    }

    class HandleClient implements Runnable {

        private Socket socket;
        private String nickname;
        private Boolean nick = false;
        private PrintWriter print;

        //construct a thread
        public HandleClient(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                //create data input and output streams
                //reading inout stream from client
                InputStream is = socket.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);

                //Sending the response back to the client.
                OutputStream os = socket.getOutputStream();
                OutputStreamWriter osw = new OutputStreamWriter(os);
                BufferedWriter bw = new BufferedWriter(osw);

                //Message de bienvenue
                bw.write("Veuillez taper '/surnom votre_surnom' pour avoir un surnom \n");
                bw.flush();
                print = new PrintWriter(socket.getOutputStream(), true);
                broadcasts.add(print);

                while (true) {

                    String contenu = br.readLine();
                    //Si le client veut un surnom
                    if (contenu.startsWith("/surnom ")) {
                        String arrayString[] = contenu.split("\\s+");
                        nickname = arrayString[1];
                        nicknames.add(nickname);
                        nick = true;
                        for (PrintWriter write : broadcasts) {
                            write.println("Le client se nomme " + nickname);
                        }
                        jta.append("Le client se nomme " + nickname + "\n");
                    } //Si il n'y a pas de surnom entré
                    else if (nick == false) {
                        jta.append("Client Anonymous : " + contenu + "\n");
                        //On broadcaste le texte à tous les clients
                        for (PrintWriter write : broadcasts) {
                            write.println("Client Anonymous : " + contenu);
                        }
                        //Sinon
                    } else {
                        jta.append(nickname + " : " + contenu + "\n");

                        for (PrintWriter write : broadcasts) {
                            write.println(nickname + " : " + contenu);
                        }
                    }
                }
            } catch (IOException ex) {
                System.err.println(ex);
            } finally {
                //Cas du client qui quitte le chat
                if (nickname != null) {
                    nicknames.remove(nickname);
                }

                if (print != null) {
                    jta.append("Un client a quitté le chat\n");
                    broadcasts.remove(print);
                }
                //On ferme son socket
                try {
                    socket.close();
                } catch (IOException exception) {
                    System.out.println(exception);
                }
            }
        }
    }

}
