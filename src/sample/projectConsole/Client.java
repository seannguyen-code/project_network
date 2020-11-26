package sample.projectConsole;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private InetAddress host;
    private int port;
    private String name;

    public Client(InetAddress host, int port) {
        this.host = host;
        this.port = port;
    }

    public void nameRegister(Socket client) throws IOException {
        DataInputStream dis = new DataInputStream(client.getInputStream());

        try {
            Scanner sc = new Scanner(System.in);
            System.out.print("Nhập vào tên của bạn: ");
            name = sc.nextLine();

            DataOutputStream dos = new DataOutputStream(client.getOutputStream());
            dos.writeUTF(name);
            while (true) {
                String msg = dis.readUTF();
                System.out.println(msg);
                if (msg.contains("Registration Completed Successfully")) break;
                sc = new Scanner(System.in);
                System.out.print("Nhập vào tên của bạn: ");
                name = sc.nextLine();

                dos = new DataOutputStream(client.getOutputStream());
                dos.writeUTF(name);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void execute() throws IOException {
        //Phần bổ sung
        Socket client = new Socket(host, port);

        nameRegister(client);

        ReadClient read = new ReadClient(client);
        read.start();
        WriteClient write = new WriteClient(client, name);
        write.start();
    }


    public static void main(String[] args) throws IOException {
        Client client = new Client(InetAddress.getLocalHost(), 15797);
        client.execute();
    }
}

class ReadClient extends Thread{
    private Socket client;

    public ReadClient(Socket client) {
        this.client = client;
    }

    @Override
    public void run() {
        DataInputStream dis = null;
        try {
            dis = new DataInputStream(client.getInputStream());
            while(true) {
                String sms = dis.readUTF();
                System.out.println(sms);
            }
        } catch (Exception e) {
            try {
                dis.close();
                client.close();
            } catch (IOException ex) {
                System.out.println("Ngắt kết nối Server");
            }
        }
    }
}

class WriteClient extends Thread{
    private Socket client;
    private String name;

    public WriteClient(Socket client, String name) {
        this.client = client;
        this.name = name;
    }

    @Override
    public void run() {
        DataOutputStream dos = null;
        Scanner sc = null;
        try {
            dos = new DataOutputStream(client.getOutputStream());
            sc = new Scanner(System.in);
            while(true) {
                String sms = sc.nextLine();
                dos.writeUTF(name + ": " + sms);
            }
        } catch (Exception e) {
            try {
                dos.close();
                client.close();
            } catch (IOException ex) {
                System.out.println("Ngắt kết nối Server");
            }
        }
    }

}
