/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

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
import javax.swing.*;

/**
 *
 * @author Armand
 */
public class Server extends JFrame{
    private JTextArea jta = new JTextArea();
    private String host;
    private int port;
    
public static void main(String[] zero) throws IOException {

         new Server(8000);

    }

    public Server(int port) throws IOException {
        //put text area on frame
        setLayout(new BorderLayout());
        add(new JScrollPane(jta),BorderLayout.CENTER);
        setTitle("Server");
        setSize(500,300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        try{
        //create server socket
        ServerSocket server = new ServerSocket(port);
        jta.append("Sever started at: "+ new Date()+'\n');
                int i = 1;
        //listen for connection request
            Socket socket = server.accept();
            
            jta.append("Client"+i+" with ip " + socket.getRemoteSocketAddress().toString() + " is connected.");
            
        //create data input and output streams
        //reading inout stream from client
            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            
                    //Sending the response back to the client.
                OutputStream os = socket.getOutputStream();
                OutputStreamWriter osw = new OutputStreamWriter(os);
                BufferedWriter bw = new BufferedWriter(osw);

        while (true) {
            
            //receive
            String number = br.readLine();
            System.out.println(number);
            jta.append(number + "\n");
            //send    
            bw.write("Hi Client " + i+ "\n");
            bw.flush();
            i++;
        }
        }
        catch(IOException ex){
            System.out.println(ex);
        }
    }

    

}

