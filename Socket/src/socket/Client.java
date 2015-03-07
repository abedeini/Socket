/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socket;

/**
 *
 * @author Armand
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import java.net.InetAddress;

import java.net.Socket;

import java.net.UnknownHostException;

public class Client {

    private Socket socket;

    public Client(int port/*, InetAddress ip*/)  {

        try {

            socket = new Socket(InetAddress.getLocalHost(), port);
            
            //Send the message to the server
            OutputStream os = socket.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);
            bw.write("Yo Mec :) ! \n");
            bw.flush();
            
            //Get the return message from the server
            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String message = br.readLine();
            System.out.println(message);
            
            //socket.close();

        } catch (UnknownHostException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }

    }
    
     public static void main(String[] zero) throws IOException {
        
       Client client = new Client(1235);
        
        
        
    }
    

}
