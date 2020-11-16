package sample.projectConsole;

import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestZone {
    public static ArrayList<String> names;

    public static void main(String[] args)  {
        TestZone.names = new ArrayList<>();
        TestZone.names.add("A");
        TestZone.names.add("B");
        TestZone.names.add("C");
        TestZone.names.add("D");
        System.out.println(TestZone.names);
        System.out.println(TestZone.names.size());
        TestZone.names.remove(TestZone.names.get(0));
        TestZone.names.remove(TestZone.names.get(0));
        TestZone.names.remove(TestZone.names.get(0));

        TestZone.names.set(0, "ABC");
        System.out.println(TestZone.names);
        System.out.println(TestZone.names.size());


//        String resultStr = "asdasdl: 123";
//        String pattern = "(\\d+)";
//        Pattern patt = Pattern.compile(pattern);
//        Matcher matcher = patt.matcher(resultStr);
//
//        String extractNum = "";
//        if (matcher.find()) {
//            extractNum = matcher.group();
//        }
//        System.out.println(extractNum);

//        int result_int = Integer.parseInt(extractNum);
//        System.out.println("Ket qua: " + result_int);

//        TestZone.names = new ArrayList<>();
//        TestZone.names.add("A");
//        TestZone.names.add("B");
//        TestZone.names.add("C");
//        TestZone.names.add("D");
//        System.out.println(TestZone.names.indexOf("D"));

//        for (int i=0; i< numOfPlayers; ++i) points[i] = 1;
//
//        for (int i=0; i< numOfPlayers; ++i) System.out.println(points[i]);
//        // Tao hai so ngau nhien
//        int randomNum1 = ThreadLocalRandom.current().nextInt(-10000, 10000 + 1);
//        String str_random1 = String.valueOf(randomNum1);
//        int randomNum2 = ThreadLocalRandom.current().nextInt(-10000, 10000 + 1);
//        String str_random2 = String.valueOf(randomNum2);
//
//        // Chon 1 operator
//        String[] operators;
//        operators = new String[]{"+", "-", "*", "/", "%"};
//        int op = ThreadLocalRandom.current().nextInt(0, 4 + 1);
//
//        // Bieu thuc gui cho CLIENTS
//        String foo = "(" + str_random1 + ")" + operators[op] +"("+ str_random2+ ")";
//
//        // Tinh toan bieu thuc
//        String pattern = "\\)([+\\-\\*%\\/])\\(";
//        Pattern patt = Pattern.compile(pattern);
//        Matcher matcher = patt.matcher(foo);
//
//        String extractOp = "";
//        if (matcher.find()) {
//            extractOp = matcher.group();
//        }
//
//        int result = 0;
//        if (extractOp.contains("+")) result = randomNum1 + randomNum2;
//        else if (extractOp.contains("-")) result = randomNum1 - randomNum2;
//        else if (extractOp.contains("*")) result = randomNum1 * randomNum2;
//        else if (extractOp.contains("/")) result = randomNum1 / randomNum2;
//        else if (extractOp.contains("%")) result = randomNum1 % randomNum2;
//        System.out.println(foo);
//        System.out.println(String.valueOf(result));
    }
}
