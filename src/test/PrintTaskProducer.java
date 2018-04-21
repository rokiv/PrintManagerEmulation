package test;

import test.documents.DocType;
import test.documents.DocsFactory;

class PrintTaskProducer {
    private PrintTasks printTasks;
    private AutoGenerator autoGenerator;
    private boolean isEnabled = false;

    PrintTaskProducer(PrintTasks printTasks) {
        this.printTasks = printTasks;
    }

    /*
    Создание файла по введенному имени. Строка просто парсится,
    определяется расширение файла и создается соответствующий тип документа.
    При некорректном вводе или вводе неподдерживающегося формата выводится
    сообщение о невалидности данных.
     */
    void createByName() {
        ConsoleHelper.writeMsg("Enter file name in the following format /path/name.<extension>");
        try {
            String fileName = ConsoleHelper.readString();
            printTasks.add(DocsFactory.createDocByName(fileName));
            ConsoleHelper.writeMsg("Document was successfully added to queue.");
        } catch (Exception x) {
            ConsoleHelper.printWrongInput();
        }
    }

    /*
    Создание заданного количества случайных документов типов, перечисленных в Enum'е,
    имена генерируются случайным наборов 8 символов английского алфавита и цифр. При вводе
    неподдерживаемого формата выводится сообщение о невалидности ввода.
     */
    void createRandomPrintTasks() {
        ConsoleHelper.writeMsg("Enter documents type and count (i.e: pdf 10)");
        try {
            String input = ConsoleHelper.readString();
            DocType docType = DocType.valueOf(input.split(" ")[0].toUpperCase());
            int count = Integer.parseInt(input.split(" ")[1]);
            for (int i = 0; i < count; i++) {
                printTasks.add(DocsFactory.createDocument(docType, null));
            }
            ConsoleHelper.writeMsg(String.format("%d documents of %s type was added to queue", count, docType.toString()));
        } catch (Exception x) {
            ConsoleHelper.printWrongInput();
        }
    }


    void startAutoGenerator() {
        if (!isEnabled) {
            autoGenerator = new AutoGenerator();
            autoGenerator.start();
            isEnabled = true;
            ConsoleHelper.writeMsg("The task generator started successfully."+ConsoleHelper.NEW_LINE);
        } else
            ConsoleHelper.writeMsg("The task generator has been started already."+ConsoleHelper.NEW_LINE);
    }

    void stopAutoGenerator() {
        if (isEnabled) {
            autoGenerator.interrupt();
            isEnabled = false;
        }
    }

    /*
    После запуска тред генерирует случайные документы и отправляет их на печать
     */
    private class AutoGenerator extends Thread {
        @Override
        public void run() {
            try {
                while (!isInterrupted()) {
                    printTasks.add(DocsFactory.createRandomDoc());
                    int sleepTime = (int) (Math.random() * 12);

                    Thread.sleep(sleepTime * 1000);

                }
            } catch (InterruptedException x) {
                ConsoleHelper.writeMsg("Info: Auto generator stopped"+ConsoleHelper.NEW_LINE);
            }
        }
    }

}
