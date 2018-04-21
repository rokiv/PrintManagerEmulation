package test;

import test.documents.AbstractDocument;

public class Printer extends Thread {
    private PrintTasks printTasks;

    Printer(PrintTasks printTasks) {
        this.printTasks = printTasks;
    }

    @Override
    public void run() {
        try {
            while (true) {
                AbstractDocument doc = printTasks.take();
                printTasks.addPrintedDocument(doc);
                Thread.sleep(doc.getPrintTime() * 1000);
            }
        } catch (Exception x) {ConsoleHelper.writeMsg("Info: Printer stopped."+ConsoleHelper.NEW_LINE);}

    }

}
