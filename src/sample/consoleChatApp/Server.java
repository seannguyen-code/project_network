package sample.consoleChatApp;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket ss = new ServerSocket(1201);
            Socket s = ss.accept();

            DataInputStream din = new DataInputStream(s.getInputStream());
            DataOutputStream dout = new DataOutputStream(s.getOutputStream());

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            String msgin="", msgout="";

            while (!msgin.equals("end")) {
                msgin = din.readUTF();
                System.out.println(msgin);

                msgout = br.readLine();
                dout.writeUTF(msgout);
                dout.flush();
            }
            s.close();
        } catch (Exception e) {

        }
    }
}
