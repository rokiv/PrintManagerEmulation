package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

class ConsoleHelper {
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static final String NEW_LINE = System.getProperty("line.separator");

    static void writeMsg(String msg) {
        System.out.println(msg);
    }

    static String readString() {
        String result = null;
        try {
            result = br.readLine();
        } catch (IOException x) {
            x.printStackTrace();
        }
        return result;
    }

    static int readInteger() {
        while (true) {
            try {
                return Integer.parseInt(br.readLine());
            } catch (Exception x) {
                writeMsg("Wrong format of data. Please enter number:");
            }
        }
    }

    static void printAtStart() {
        writeMsg(NEW_LINE+"Emulation started." + NEW_LINE);
    }

    static void printMenu() {
        writeMsg("-------------------------" + NEW_LINE +
                "Enter number to choose operation:" + NEW_LINE +
                "1  Print documents" + NEW_LINE +
                "2  Cancel printing" + NEW_LINE +
                "3  Get list of printed documents" + NEW_LINE +
                "4  Get average print time" + NEW_LINE +
                "0  Exit" + NEW_LINE);

    }

    static void printSubMenu1() {
        writeMsg("-------------------------" + NEW_LINE +
                "Print menu. Enter number to choose option:" + NEW_LINE +
                "1  Print document by file name" + NEW_LINE +
                "2  Create random documents and print" + NEW_LINE +
                "3  Run auto generator of documents" + NEW_LINE +
                "4  Stop auto generator of documents" + NEW_LINE +
                "5  Cancel" + NEW_LINE);
    }

    static void printSubMenu2() {
        writeMsg("-------------------------" + NEW_LINE +
                "Cancel menu. Enter number to choose option:" + NEW_LINE +
                "1  Cancel last sent document" + NEW_LINE +
                "2  Cancel document by name" + NEW_LINE +
                "3  Cancel all queued documents" + NEW_LINE +
                "4  Cancel" + NEW_LINE);
    }

    static void printSubMenu3() {
        writeMsg("-------------------------" + NEW_LINE +
                "Show sorted list of printed documents. Enter number to choose option:" + NEW_LINE +
                "1  Sort by print order" + NEW_LINE +
                "2  Sort by doc type" + NEW_LINE +
                "3  Sort by print time" + NEW_LINE +
                "4  Sort by paper size" + NEW_LINE +
                "5  Cancel" + NEW_LINE);
    }

    static void printWrongInput() {
        writeMsg("Error occurred. Wrong input format.");
    }



    static void printFarewell() {
        writeMsg("Emulation terminated. Good bye!");
    }
}
