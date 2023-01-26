package chucknorris;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        while (true) {
            System.out.println("Please input operation (encode/decode/exit):");
            String userInput = scan.nextLine().trim();
            while (!userInput.equals("exit") && !userInput.equals("encode") && !userInput.equals("decode")) {
                System.out.printf("There is no '%s' operation%n%n", userInput);
                System.out.println("Please input operation (encode/decode/exit):");
                userInput = scan.nextLine().trim();
            }
            if (userInput.equals("exit")) {
                System.out.println("Bye!");
                break;
            }
            if (userInput.equals("encode")) {
                System.out.println("Input string:");
                userInput = scan.nextLine();
                System.out.println("Encoded string:");
                System.out.println(chuckNorrisEncode(userInput));
                System.out.println();
            } else {
                try {
                    System.out.println("Input encoded string:");
                    userInput = scan.nextLine();
                    String decodedString = chuckNorrisDecode(userInput);
                    System.out.println("Decoded string:");
                    System.out.println(decodedString);
                } catch (IllegalArgumentException e) {
                    System.out.println("Encoded string is not valid.");
                }
                System.out.println();
            }
        }
    }

    private static String chuckNorrisEncode(String decodedString) {
        StringBuilder compoundASCIIBinaryString = new StringBuilder();
        for (char c: decodedString.toCharArray()) {
            String cBinaryString = Integer.toBinaryString(c);
            // to make sure we are using 7 digits
            String asciiBinaryString = ("0".repeat(7) + cBinaryString).substring(cBinaryString.length());
            compoundASCIIBinaryString.append(asciiBinaryString);
        }

        // I noticed it is too hard to encode the last character due to the chosen logic
        // so I added a dummy character '2' that will simply be ignored
        compoundASCIIBinaryString.append("2");
        StringBuilder finalEncodedString = new StringBuilder();
        char firstChar = compoundASCIIBinaryString.charAt(0);
        int count = 1;
        for (int i = 1; i < compoundASCIIBinaryString.length(); i++) {
            char iChar = compoundASCIIBinaryString.charAt(i);
            if (iChar == firstChar) {
                count++;
            } else {
                if (firstChar == '1') {
                    finalEncodedString.append("0 ");
                    finalEncodedString.append("0".repeat(count));
                    finalEncodedString.append(" ");
                } else {
                    finalEncodedString.append("00 ");
                    finalEncodedString.append("0".repeat(count));
                    finalEncodedString.append(" ");
                }
                firstChar = iChar;
                count = 1;
            }
        }
        return finalEncodedString.toString().trim();
    }

    private static String chuckNorrisDecode(String encodedString) {
        // let's throw some exceptions in case we get non-encoded input
        // first we check if the input has anything other than zeros and spaces
        if (encodedString.split("[0 ]+").length > 0) {
            throw new IllegalArgumentException();
        }
        StringBuilder binaryDecoded = new StringBuilder();
        // it seems it is easier if I work with an array at this point
        // I'll basically look into pairs of values, check if the first value is 1 or 0
        // and repeat it with the number of zeros we have on the second value
        String[] auxArray = encodedString.split(" ");
        if (auxArray.length % 2 != 0) {
            throw new IllegalArgumentException();
        }
        for (int i = 0; i < auxArray.length; i+= 2) {
            if (!auxArray[i].equals("0") && !auxArray[i].equals("00")) {
                throw new IllegalArgumentException();
            }
            if (auxArray[i].equals("0")) {
                binaryDecoded.append("1".repeat(auxArray[i + 1].length()));
            } else {
                binaryDecoded.append("0".repeat(auxArray[i + 1].length()));
            }
        }
        // let's reuse the auxArray, but now we will split the decodedBinary
        // using regex to make sure all values have the same length 7
        auxArray = binaryDecoded.toString().split("(?<=\\G.{7})");

        // now let's just convert each binary value to a character and return it
        StringBuilder characterDecoded = new StringBuilder();
        for (String s: auxArray) {
            if (s.length() != 7) {
                throw new IllegalArgumentException();
            }
            int binaryToInt = Integer.parseInt(s, 2);
            characterDecoded.append((char) binaryToInt);
        }
        return characterDecoded.toString();
    }
}