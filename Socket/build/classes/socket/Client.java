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
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.io.PrintWriter;
import javax.swing.*;

public class Client extends JFrame {
//text field for receiving data

    private JTextField jtf = new JTextField();

//text area to display contents
    private JTextArea jta = new JTextArea();
    //input output variables
    private OutputStream os;
    private OutputStreamWriter osw;
    private BufferedWriter bw;
    private InputStream is;
    private InputStreamReader isr;
    private BufferedReader br;

    public static void main(String[] zero) throws IOException {
        new Client(8000);

    }

    public Client(int port) {
        //panel to hold label and text field

        JPanel p = new JPanel();
        p.setLayout(new BorderLayout());
        p.add(new JLabel("Enter message"), BorderLayout.WEST);
        p.add(jtf, BorderLayout.CENTER);
        jtf.setHorizontalAlignment(JTextField.RIGHT);
        setLayout(new BorderLayout());
        add(p, BorderLayout.NORTH);
        add(new JScrollPane(jta), BorderLayout.CENTER);

        jtf.addActionListener(new TextFieldListener());

        setTitle("Client");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        try {
            //create socket to connect to server
            Socket socket = new Socket(InetAddress.getLocalHost()/*"81.57.249.139"*/, port);

            //input stream to get data from server
            is = socket.getInputStream();
            isr = new InputStreamReader(is);
            br = new BufferedReader(isr);

            //create output stream to send data to server
            os = socket.getOutputStream();
            osw = new OutputStreamWriter(os);
            bw = new BufferedWriter(osw);

            while (true) {
                //Récupérer le message du serveur
                String message = br.readLine();
                jta.append("\n" + message);
            }

            //socket.close();
        } catch (UnknownHostException e) {

            e.printStackTrace();

        } catch (IOException e) {

            e.printStackTrace();

        }

    }

    private class TextFieldListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            try {

                //get the radius from text field
                String name = jtf.getText().trim();

                //send name to server
                bw.write("\n" + name + "\n");
                bw.flush();
                jta.setText("");

            } catch (IOException ex) {
                System.err.println(ex);
            }
        }

    }

}
