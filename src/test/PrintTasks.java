package test;

import test.documents.AbstractDocument;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

class PrintTasks {
    /*
    TaskProducer генерирует задания на печать и кладет в потокобезопасную очередь LinkedBlockingQueue.
    Printer забирает документы из очереди и помещает их в писок напечатанных докуменов.
     */
    private final static LinkedBlockingQueue<AbstractDocument> queue = new LinkedBlockingQueue<>();
    private final static ArrayList<AbstractDocument> printedDocuments = new ArrayList<>();
    private AbstractDocument lastSentDocument;



    String getStatus() {
        return String.format("***"+ConsoleHelper.NEW_LINE+
                "Current status: Number of printed documents is %d, number of queued documents is %d",printedDocuments.size(),queue.size())+
                ConsoleHelper.NEW_LINE+"***";
    }

    void add(AbstractDocument abstractDocument) {
        queue.add(abstractDocument);
        lastSentDocument = abstractDocument;
    }

    AbstractDocument take() {
        try {
            return queue.take();
        } catch (InterruptedException x) {return null;}
    }

    void addPrintedDocument(AbstractDocument doc) {
        printedDocuments.add(doc);
    }

    void cancelLastSentDocument() {
        if (queue.remove(lastSentDocument))
            ConsoleHelper.writeMsg(String.format("%s was removed from queue",lastSentDocument.getName()));
        else
            ConsoleHelper.writeMsg("Document has printed already or removed from queue");
    }

    void deleteDocumentByName() {
        try {
            StringBuilder sb = new StringBuilder();
            ConsoleHelper.writeMsg("Enter document name with extension (i.e.: example.txt):");
            String name = ConsoleHelper.readString();
            Iterator<AbstractDocument> it = queue.iterator();
            while(it.hasNext()) {
                AbstractDocument doc = it.next();
                if (doc.getName().equals(name)) {
                    sb.append(String.format("%s was removed from queue",doc.getName()));
                    sb.append(ConsoleHelper.NEW_LINE);
                    it.remove();
                }
            }
            if (sb.toString().isEmpty())
                ConsoleHelper.writeMsg("No such documents in queue");
            else
                ConsoleHelper.writeMsg(sb.toString().trim());
        } catch (Exception e) {
            ConsoleHelper.printWrongInput();
        }
    }

    List<AbstractDocument> cancelAllTasks() {
        ArrayList<AbstractDocument> list = new ArrayList<>();
        queue.drainTo(list);
        return list;
    }

    /*
    Вывод напечатанных документов в консоль и файл. В списке printedDocuments хранятся документы
    в порядке печати.
     */
    void printPrintedDocuments() {
        if (printedDocuments.isEmpty()) {
            ConsoleHelper.writeMsg("List of printed documents is empty.");
            return;
        }
        String fileName = "./"+ PrintManagerEmulator.class.getPackage().getName() +"/PrintedDocuments.txt";
        String listName = "Printed documents:";
        formatAndPrintDocumentsList(printedDocuments,fileName,listName);
    }

    void printCancelledDocuments() {
        List<AbstractDocument> cancelledTasks = cancelAllTasks();
        if (cancelledTasks.isEmpty()) {
            ConsoleHelper.writeMsg("Print queue is empty.");
            return;
        }
        String fileName = "./"+ PrintManagerEmulator.class.getPackage().getName() +"/CancelledDocuments.txt";
        String listName = "Cancelled documents:";
        formatAndPrintDocumentsList(cancelledTasks,fileName,listName);
    }

    /*
    Вывод отсортированных документов в консоль и файл. Компаратор выбирается пользователем в меню.
    Для сортировки используется копия списка printedDocuments.
     */
    void printSortedPrintedDocs(Comparator<AbstractDocument> comp) {
        if (printedDocuments.isEmpty()) {
            ConsoleHelper.writeMsg("List of printed documents is empty.");
            return;
        }
        List<AbstractDocument> list = new ArrayList<>(printedDocuments);
        list.sort(comp);
        String fileName = "./"+ PrintManagerEmulator.class.getPackage().getName() +"/SortedDocuments.txt";
        String listName = "Sorted documents:";
        formatAndPrintDocumentsList(list,fileName,listName);
    }

    /*
    Форматирование списков документов и вывод.
     */
    private void formatAndPrintDocumentsList(List<AbstractDocument> list,String fileName,String listName) {
        StringBuilder sb = new StringBuilder();
        sb.append(listName);
        sb.append(ConsoleHelper.NEW_LINE);
        for (AbstractDocument doc:list
                ) {
            sb.append(doc.toString());
            sb.append(ConsoleHelper.NEW_LINE);
        }
        sb.append("END of list*******************");
        sb.append(ConsoleHelper.NEW_LINE);
        sb.append(ConsoleHelper.NEW_LINE);
        String result = sb.toString();
        ConsoleHelper.writeMsg(result);
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            writer.write(result);
        } catch (IOException x) {ConsoleHelper.writeMsg("Can't write list to file "+fileName);}
    }

    void printAveragePrintTime() {
        if (printedDocuments.isEmpty()) {
            ConsoleHelper.writeMsg("List of printed documents is empty." + ConsoleHelper.NEW_LINE);
            return;
        }
        int sum = 0;
        for (AbstractDocument doc: printedDocuments
             ) {
            sum +=doc.getPrintTime();
        }
        String msg = String.format("Average print time is %.2f",(float)sum/printedDocuments.size());
        ConsoleHelper.writeMsg(msg + ConsoleHelper.NEW_LINE);
    }


    static class DocTypeComparator implements Comparator<AbstractDocument> {
        @Override
        public int compare(AbstractDocument o1, AbstractDocument o2) {
            return o1.getDocType().compareTo(o2.getDocType());
        }
    }
    static class PrintTimeComparator implements Comparator<AbstractDocument> {
        @Override
        public int compare(AbstractDocument o1, AbstractDocument o2) {
            return o1.getPrintTime()-o2.getPrintTime();
        }
    }
    static class PaperSizeComparator implements Comparator<AbstractDocument> {
        @Override
        public int compare(AbstractDocument o1, AbstractDocument o2) {
            return o2.getPaperSize().compareTo(o1.getPaperSize());
        }
    }

}

