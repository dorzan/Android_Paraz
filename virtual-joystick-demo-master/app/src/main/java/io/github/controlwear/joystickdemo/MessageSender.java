package io.github.controlwear.joystickdemo;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public  class MessageSender {
    OutputStream dos;

    ClientThread clientThread;
    Thread thread;
    static boolean flag = false;
    String ip;
    //static String str;


    public MessageSender(String ip) {
        this.ip = ip;
        clientThread = new ClientThread();
        thread = new Thread(clientThread);
        thread.start();
    }

    public void send(String str) {
        clientThread.send(str);
    }


    class ClientThread implements Runnable {
        private Socket socket;
        PrintWriter out;

        @Override
        public void run() {
            try {
                socket = new Socket(ip, 7808);
                out = new PrintWriter(new BufferedWriter(
                        new OutputStreamWriter(socket.getOutputStream())), true);

            } catch (UnknownHostException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            // while (true);
        }

        public void send(final String  str) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        if (null != socket) {
                           // PrintWriter out = new PrintWriter(new BufferedWriter(
                                    //new OutputStreamWriter(socket.getOutputStream())),
                                  //  true);
                            out.println(str);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }
}
