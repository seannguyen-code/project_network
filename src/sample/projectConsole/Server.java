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
    public static ArrayList<Socket> listSK;
    public static ArrayList<String> names;
    public static ArrayList<Integer> points;
    public static ArrayList<Boolean> receivedAnsCorrect;
    public static ArrayList<Integer> wrongAnsCount;
    public static ArrayList<Socket> firstSocket;
    public static int timeout;

    public static void main(String[] args) throws IOException, InterruptedException {
        Server.listSK = new ArrayList<>();
        Server.names = new ArrayList<>();
        String playAgain = "Yes";

        do {

        // Nhap so nguoi choi
        do {
        Scanner sc = new Scanner(System.in);
        System.out.println("How many players?: ");
        String n = sc.nextLine();

        try{ Server.numOfPlayers = Integer.parseInt(n);} catch (Exception e) {}
        if (numOfPlayers < 2 || numOfPlayers > 10) {
            System.out.println("Players allow: 2->10`");
            continue;
        }
        else break;
        } while (true);

        // Nhan Socket va validate ten tui no
        ServerSocket server = new ServerSocket(15797);
        System.out.println("Server is listening...");
        while (true) {
            Socket socket = server.accept();
            System.out.println("Connected with " + socket.getInetAddress());
            Server.listSK.add(socket);

            // Validate name
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            String name;

            while (true) {
            name = dis.readUTF();
            //Ten ko hop le
            if (!name.matches("^([A-Za-z][A-Za-z0-9_]*)$")) {
                dos.writeUTF("Wrong format, Try again!");
                continue;
            }
            else if (name.length() > 10) {
                dos.writeUTF("Name is too long, Try again!");
                continue;
            }
            // Trung ten
            else if (!Server.names.isEmpty()) {
                boolean duplicateName = false;
                for (String n : Server.names) {
                    if (name.equals(n)) {
                        dos.writeUTF("Name already exists");
                        duplicateName = true;
                        break;
                    }
                }
                if (duplicateName) continue;
                else {
                    System.out.println("Player accepted: " + name);
                    break;
                }
            }
            else {
                System.out.println("Player accepted: " + name);
                break;
            }
            }
            dos.writeUTF("Registration Completed Successfully");
            Server.names.add(name);
            if (Server.listSK.size() == numOfPlayers) break;
        }

        System.out.println("Enough Players, Game starts!");

        // Nhap race length
        do {
            Scanner sc = new Scanner(System.in);
            System.out.println("Input Race Length: ");
            String n = sc.nextLine();
            try{ raceLength = Integer.parseInt(n);} catch (Exception e) {}

            if (raceLength <= 3 || raceLength >= 26) {
                System.out.println("Race Len: 4->25`");
                continue;
            }
            else break;
        } while (true);

        // Nhap timeout
        do {
            Scanner sc = new Scanner(System.in);
            System.out.println("Input Timeout: ");
            String n = sc.nextLine();
            try{ timeout = Integer.parseInt(n);} catch (Exception e) {}

            if (timeout < 5 || timeout > 60) {
                System.out.println("Timeout: 5->60");
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
                    System.out.println("The Winner is: " + Server.names.get(j));

                    Socket winner = Server.listSK.get(j);
                    DataOutputStream dos = new DataOutputStream(winner.getOutputStream());
                    dos.writeUTF("WIN");

                    try {
                        for (Socket item : Server.listSK) {
                            if (item != winner) {
                                dos = new DataOutputStream(item.getOutputStream());
                                dos.writeUTF("LOSE");
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    break;
                }
                else if (Server.numOfPlayers == 1) {
                    endGame = true;
                    System.out.println("The Winner is: " + Server.names.get(0));
                    Socket winner = Server.listSK.get(j);
                    DataOutputStream dos = new DataOutputStream(winner.getOutputStream());
                    dos.writeUTF("WIN");
                    break;
                }
                else if (Server.numOfPlayers == 0) {
                    endGame = true;
                    System.out.println("0 player left, End Game!");
                    try {
                        for (Socket item : Server.listSK) {
                            DataOutputStream dos = new DataOutputStream(item.getOutputStream());
                            dos = new DataOutputStream(item.getOutputStream());
                            dos.writeUTF("LOSE");

                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                }
                j++;
            }
            if (endGame) break;

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
                    dos.writeUTF("Calculate: " + foo);
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

            for (int i = 0; i < timeout; ++i) {
                for (Socket item: Server.listSK) {
                    dos = new DataOutputStream(item.getOutputStream());
                    dos.writeUTF("Time left: " + String.valueOf(timeout-i));
                }
                TimeUnit.SECONDS.sleep(1);
            }

            // 3. Update diem handle no answers
            // Ko ai tra loi dung
            if (Server.firstSocket.isEmpty()) {
                System.out.println("No correct answer!");
                for (int i=0; i< Server.numOfPlayers; ++i) {
                    //Server.wrongAnsCount.set(i, Server.wrongAnsCount.get(i) + 1);
                    if (Server.points.get(i) > 1) {
                        int value_at_i = Server.points.get(i);
                        value_at_i -= 1;
                        Server.points.set(i, value_at_i);
                    }
                    dos = new DataOutputStream(Server.listSK.get(i).getOutputStream());
                    System.out.println(Server.names.get(i) + " answerd wrong " + Server.wrongAnsCount.get(i) + " times");
                    System.out.println(Server.names.get(i) + " has " + Server.points.get(i) + " points");
                    dos.writeUTF(Server.names.get(i) + " answerd wrong " + Server.wrongAnsCount.get(i) + " times");
                    dos.writeUTF(Server.names.get(i) + " has " + Server.points.get(i) + " points");
                }
                // Loai nguoi choi thua 3 lan lien tiep
                int tempN = Server.numOfPlayers;
                for (int i=0; i< numOfPlayers; ++i) {
                    if (Server.wrongAnsCount.get(i) >= 3) {
                        dos = new DataOutputStream(Server.listSK.get(i).getOutputStream());
                        dos.writeUTF("LOSE");
                        Server.listSK.get(i).close();
                        System.out.println("Player removed: " + Server.names.get(i));

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
                dos = new DataOutputStream(Server.listSK.get(i).getOutputStream());
                dos.writeUTF("The result is: " + result);
                System.out.println("Player " + Server.names.get(i) + " answerd wrong " + Server.wrongAnsCount.get(i) + " times");
                System.out.println("Player " + Server.names.get(i) + " has " + Server.points.get(i) + " points");
                dos.writeUTF(Server.names.get(i) + " answerd wrong " + Server.wrongAnsCount.get(i) + " times");
                dos.writeUTF(Server.names.get(i) + " has " + Server.points.get(i) + " points");
            }

            // Loai nguoi choi thua 3 lan lien tiep
            int tempN = Server.numOfPlayers;
            for (int i=0; i< numOfPlayers; ++i) {
                if (Server.wrongAnsCount.get(i) >= 3) {
                    dos = new DataOutputStream(Server.listSK.get(i).getOutputStream());
                    dos.writeUTF("LOSE");
                    Server.listSK.get(i).close();
                    System.out.println("Player removed: " + Server.names.get(i));
                    try {
                        for (Socket item : Server.listSK) {
                            if (item != Server.listSK.get(i)) {
                                dos = new DataOutputStream(item.getOutputStream());
                                dos.writeUTF("Player removed: " + Server.names.get(i));
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Server.listSK.remove(i);
                    Server.names.remove(i);
                    Server.wrongAnsCount.remove(i);
                    Server.receivedAnsCorrect.remove(i);

                    Server.numOfPlayers -= 1;
                    i -= 1;
                }
            }
            System.out.println("\n\n");
        }


        System.out.println("Game end!");
        System.out.println("Start NEW GAME? \n Yes or No");
        Scanner sc = new Scanner(System.in);
        playAgain = sc.nextLine();
        } while (playAgain.contains("Y"));
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
            DataInputStream dis = new DataInputStream(socket.getInputStream());

            String resultStr = dis.readUTF();

            String pattern = "([-\\d]+)";
            Pattern patt = Pattern.compile(pattern);
            Matcher matcher = patt.matcher(resultStr);

            String extractNum = "";
            if (matcher.find()) {
                extractNum = matcher.group();
            }

            try {
                int result_int = Integer.parseInt(extractNum);
                if (result_int != 999999999) {
                    System.out.println("Player " + Server.names.get(socketIndex) + " answer: " + result_int);
                }


                if (result_int == ReadAnswer.answer && Server.firstSocket.isEmpty()) {
                    Server.receivedAnsCorrect.set(socketIndex, true);
                    Server.firstSocket.add(socket);
                    Server.wrongAnsCount.set(socketIndex, 0);
                } else if (result_int == ReadAnswer.answer) {
                    Server.receivedAnsCorrect.set(socketIndex, true);
                    Server.wrongAnsCount.set(socketIndex, 0);
                } else {
                    Server.wrongAnsCount.set(socketIndex, Server.wrongAnsCount.get(socketIndex) + 1);
                }

                System.out.println(Server.names.get(socketIndex) + " answer is correct: " + Server.receivedAnsCorrect.get(socketIndex));
            }
            catch (Exception e) {
                Server.wrongAnsCount.set(socketIndex, Server.wrongAnsCount.get(socketIndex) + 1);
                System.out.println(Server.names.get(socketIndex) + " answer is correct: " + Server.receivedAnsCorrect.get(socketIndex));
            }

        } catch (Exception e) {
            try {
                socket.close();
            } catch (IOException ex) {
                System.out.println("Ngắt kết nối Server");
            }
        }


    }
}


