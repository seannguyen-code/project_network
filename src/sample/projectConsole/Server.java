package sample.projectConsole;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Server {
    public static int numOfPlayers;
    public static int raceLength;
    private int port;
    public static ArrayList<Socket> listSK;
    public static ArrayList<String> names;
    public static ArrayList<Integer> points;
    //public static int[] points;
    public static ArrayList<Boolean> receivedAnsCorrect;
    //public static boolean[] receivedAnsCorrect;
    public static ArrayList<Integer> wrongAnsCount;
    //public static int[] wrongAnsCount;
    public static ArrayList<Socket> firstSocket;

    public static int timeout;

    // TODO:
    // 1. Timeout moi stage. Roi`
    // 2. Start game moi.
    // 3. Thong bao, logs to all clients

    public static void main(String[] args) throws IOException, InterruptedException {
        Server.listSK = new ArrayList<>();
        Server.names = new ArrayList<>();

        // Nhap so nguoi choi
        do {
        Scanner sc = new Scanner(System.in);
        System.out.println("Nhap so nguoi choi: ");
        String n = sc.nextLine();

        try{ Server.numOfPlayers = Integer.parseInt(n);} catch (Exception e) {}
        if (numOfPlayers < 2 || numOfPlayers > 10) {
            System.out.println("Duu me tao keu nhap so ma`");
            continue;
        }
        else break;
        } while (true);

        // Nhan Socket va validate ten tui no
        // TODO: Chuyen cai nay thanh non-blocking

        ServerSocket server = new ServerSocket(15797);

        System.out.println("Server is listening...");
        while (true) {
            Socket socket = server.accept();
            System.out.println("Đã kết nối với " + socket.getInetAddress());
            Server.listSK.add(socket);
            // Validate name
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            String name;
            while (true) {
            name = dis.readUTF();
            //Ten ko hop le
            if (!name.matches("^([A-Za-z][A-Za-z0-9_]*)$")) {
                dos.writeUTF("Ten khong hop le, doi ten di");
                continue;
            }
            else if (name.length() > 10) {
                dos.writeUTF("Ten dai qua, doi ten di");
                continue;
            }
            // Trung ten
            else if (!Server.names.isEmpty()) {
                boolean duplicateName = false;
                for (String n : Server.names) {
                    if (name.equals(n)) {
                        dos.writeUTF("Ten da ton tai, doi ten di");
                        duplicateName = true;
                        break;
                    }
                }
                if (duplicateName) continue;
                else {
                    System.out.println(name + " Hop le");
                    break;
                }
            }
            else {
                System.out.println(name + " Hop le");
                break;
            }
            }
            dos.writeUTF("Registration Completed Successfully");
            Server.names.add(name);
            if (Server.listSK.size() == numOfPlayers) break;
        }

        // Nhap race length
        do {
            Scanner sc = new Scanner(System.in);
            System.out.println("Nhap do dai duong dua: ");
            String n = sc.nextLine();
            try{ raceLength = Integer.parseInt(n);} catch (Exception e) {}

            if (raceLength <= 3 || raceLength >= 26) {
                System.out.println("Nhap mot so tu 4 toi 25`");
                continue;
            }
            else break;
        } while (true);

        // Khoi tao diem cho N
        Server.points = new ArrayList<>();
        for (int i=0; i< Server.numOfPlayers; ++i) Server.points.add(1);

        // Loop cai game
        // TODO:
        // 1. Viet bieu thuc xuong clients
        // 2. Cho ket qua va check ket qua
        // 3. Tinh diem va thong bao
        // 4. Check end game
        Server.wrongAnsCount = new ArrayList<>();
        for (int i=0; i< Server.numOfPlayers; ++i) {
            Server.wrongAnsCount.add(0);
        }


        while (true) {
            // 1. Viet bieu thuc xuong clients
            // Check end game
            boolean endGame = false;
            int j = 0;
            for (int point : Server.points) {
                if (point >= Server.raceLength) {
                    endGame = true;
                    System.out.println("Va nguoi CHIEN THANG la`: " + Server.names.get(j));
                    break;
                }
                else if (Server.numOfPlayers == 1) {
                    endGame = true;
                    System.out.println("Va nguoi CHIEN THANG la`: " + Server.names.get(0));
                    break;
                }
                else if (Server.numOfPlayers == 0) {
                    endGame = true;
                    System.out.println("Lam` deo gi` con` ai nua~ dau: ");
                    break;
                }
                j++;
            }
            if (endGame) break;

            // Nhap timeout
            do {
                Scanner sc = new Scanner(System.in);
                System.out.println("Nhap thoi gian Timeout: ");
                String n = sc.nextLine();
                try{ timeout = Integer.parseInt(n);} catch (Exception e) {}

                if (timeout < 5 || timeout > 60) {
                    System.out.println("Thoi gian hoi bi lech, thu nhap mot so tu 5 toi 60 di`");
                    continue;
                }
                else break;
            } while (true);

            // Tao hai so ngau nhien
            int randomNum1 = ThreadLocalRandom.current().nextInt(-10000, 10000 + 1);
            String str_random1 = String.valueOf(randomNum1);
            int randomNum2 = ThreadLocalRandom.current().nextInt(-10000, 10000 + 1);
            String str_random2 = String.valueOf(randomNum2);

            // Chon 1 operator
            String[] operators;
            operators = new String[]{"+", "-", "*", "/", "%"};
            int op = ThreadLocalRandom.current().nextInt(0, 4 + 1);

            // Bieu thuc gui cho CLIENTS
            String foo = "(" + str_random1 + ")" + operators[op] +"("+ str_random2+ ") = ?";

            // Tinh toan bieu thuc
            String pattern = "\\)([+\\-\\*%\\/])\\(";
            Pattern patt = Pattern.compile(pattern);
            Matcher matcher = patt.matcher(foo);

            String extractOp = "";
            if (matcher.find()) {
                extractOp = matcher.group();
            }

            int result = 0;
            if (extractOp.contains("+")) result = randomNum1 + randomNum2;
            else if (extractOp.contains("-")) result = randomNum1 - randomNum2;
            else if (extractOp.contains("*")) result = randomNum1 * randomNum2;
            else if (extractOp.contains("/") && randomNum2 == 0) result = 0;
            else if (extractOp.contains("/")) result = randomNum1 / randomNum2;
            else if (extractOp.contains("%")) result = randomNum1 % randomNum2;

            DataOutputStream dos = null;
            try {
                for (Socket item : Server.listSK) {
                    dos = new DataOutputStream(item.getOutputStream());
                    dos.writeUTF("Tinh toan: " + foo);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            // 2. Cho ket qua va check ket qua
            // Init
            Server.firstSocket = new ArrayList<>();
            Server.receivedAnsCorrect = new ArrayList<>();

            for (int i=0; i< Server.numOfPlayers; ++i) {
                Server.receivedAnsCorrect.add(false);
            }


            for (Socket item : Server.listSK) {
                ReadAnswer readAnswer = new ReadAnswer(item, result);
                readAnswer.start();

            }

            TimeUnit.SECONDS.sleep(timeout);

            // 3. Update diem handle no answers
            // Ko ai tra loi dung
            if (Server.firstSocket.isEmpty()) {
                for (int i=0; i< Server.numOfPlayers; ++i) {
                    if (Server.points.get(i) > 1) {
                        int value_at_i = Server.points.get(i);
                        value_at_i -= 1;
                        Server.points.set(i, value_at_i);
                    }
                    System.out.println(Server.names.get(i) + " dat duoc: " + Server.points.get(i) + " diem");
                }
                // Loai nguoi choi thua 3 lan lien tiep
                int tempN = Server.numOfPlayers;
                for (int i=0; i< numOfPlayers; ++i) {
                    System.out.println("Wrong Ans Count:" + Server.wrongAnsCount.get(i) + Server.names.get(i));
                    if (Server.wrongAnsCount.get(i) >= 3) {
                        Server.listSK.get(i).close();
                        System.out.println("Đã ngắt kết nối với " + Server.names.get(i));
                        Server.listSK.remove(i);
                        Server.names.remove(i);
                        Server.wrongAnsCount.remove(i);
                        Server.receivedAnsCorrect.remove(i);

                        Server.numOfPlayers -= 1;
                        i -= 1;
                    }
                }
                continue;
            }

            // Co nguoi tra loi dung
            int firstSocketIndex = Server.listSK.indexOf(Server.firstSocket.get(0));
            int wrongAnsN = 0;
            for (int i=0; i<Server.numOfPlayers; ++i) {
                if (Server.receivedAnsCorrect.get(i) && i != firstSocketIndex) {
                    int value_at_i = Server.points.get(i);
                    value_at_i += 1;
                    Server.points.set(i, value_at_i);
                }
                else if (!Server.receivedAnsCorrect.get(i) && i != firstSocketIndex) {
                    if (Server.points.get(i) > 1) {
                        int value_at_i = Server.points.get(i);
                        value_at_i -= 1;
                        Server.points.set(i, value_at_i);
                    }
                    wrongAnsN += 1;
                }
            }
            // Update diem 1st
            if (wrongAnsN == 0) wrongAnsN = 2;
            Server.points.set(firstSocketIndex, Server.points.get(firstSocketIndex) + wrongAnsN);

            // Thong bao ket qua
            for (int i=0; i< Server.numOfPlayers; ++i) {
                System.out.println(Server.names.get(i) + " dat duoc: " + Server.points.get(i) + " diem");
            }

            // Loai nguoi choi thua 3 lan lien tiep
            int tempN = Server.numOfPlayers;
            for (int i=0; i< numOfPlayers; ++i) {
                System.out.println("Wrong Ans Count:" + Server.wrongAnsCount.get(i) + Server.names.get(i));
                if (Server.wrongAnsCount.get(i) >= 3) {
                    Server.listSK.get(i).close();
                    System.out.println("Đã ngắt kết nối với " + Server.names.get(i));
                    Server.listSK.remove(i);
                    Server.names.remove(i);
                    Server.wrongAnsCount.remove(i);
                    Server.receivedAnsCorrect.remove(i);

                    Server.numOfPlayers -= 1;
                    i -= 1;
                }
            }

        }


        System.out.println("Da toi dich");
    }
}

class ReadAnswer extends Thread {
    private Socket socket;
    public static int answer;


    public ReadAnswer(Socket socket, int answer) {
        this.socket = socket;
        this.answer = answer;
    }


    @Override
    public void run() {

        try {
            int socketIndex = Server.listSK.indexOf(socket);
            System.out.println("Socket index is: " + socketIndex);
            DataInputStream dis = new DataInputStream(socket.getInputStream());

            String resultStr = dis.readUTF();
            //System.out.println(resultStr);

            String pattern = "([-\\d]+)";
            Pattern patt = Pattern.compile(pattern);
            Matcher matcher = patt.matcher(resultStr);

            String extractNum = "";
            if (matcher.find()) {
                extractNum = matcher.group();
            }
            System.out.println(extractNum);

            int result_int = Integer.parseInt(extractNum);
            System.out.println("Ket qua cua " + Server.names.get(socketIndex) + " :" + result_int);

            if (result_int == ReadAnswer.answer && Server.firstSocket.isEmpty()) {
                Server.receivedAnsCorrect.set(socketIndex, true);
                Server.firstSocket.add(socket);
                Server.wrongAnsCount.set(socketIndex, 0);
            }
            else if (result_int == ReadAnswer.answer) {
                Server.receivedAnsCorrect.set(socketIndex, true);
                Server.wrongAnsCount.set(socketIndex, 0);
            }
            else {
                Server.wrongAnsCount.set(socketIndex, Server.wrongAnsCount.get(socketIndex) + 1);
            }

            System.out.println(Server.names.get(socketIndex) + ": receivedAnsCorrect: " + Server.receivedAnsCorrect.get(socketIndex));
            System.out.println(Server.names.get(socketIndex) + ": wrongAnsCount: " + Server.wrongAnsCount.get(socketIndex));


        } catch (Exception e) {
            try {
                socket.close();
            } catch (IOException ex) {
                System.out.println("Ngắt kết nối Server");
            }
        }


    }
}


