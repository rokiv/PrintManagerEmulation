package test;

/** Программа эмулирует работу диспетчера печати. Этот класс является контроллером, который
 * инициирует другие процессы. Взаимодействие с пользователем построено на консольном меню.
 * @author Anover
 * @version 0.99
 * @since 1.7
 */
public class PrintManagerEmulator {
    private Printer printer;
    private PrintTaskProducer taskProducer;
    private PrintTasks printTasks;
    private boolean isStopped = false;

    /** Точка входа в приложение
     * @throws IllegalArgumentException не выбрасываются
     * @param args no args
     */
    public static void main(String[] args) {
        PrintManagerEmulator printManagerEmulator = new PrintManagerEmulator();
        printManagerEmulator.run();
    }


    private void run() {
        ConsoleHelper.printAtStart();
        printTasks = new PrintTasks();
        printer = new Printer(printTasks);
        taskProducer = new PrintTaskProducer(printTasks);
        printer.start();

        while (!isStopped) {
            try {
                ConsoleHelper.writeMsg(printTasks.getStatus());
                ConsoleHelper.printMenu();
                int subMenu = ConsoleHelper.readInteger();
                switch (subMenu) {
                    case 1:
                        ConsoleHelper.printSubMenu1();
                        executeSubMenu1();
                        break;
                    case 2:
                        ConsoleHelper.printSubMenu2();
                        executeSubMenu2();
                        break;
                    case 3:
                        ConsoleHelper.printSubMenu3();
                        executeSubMenu3();
                        break;
                    case 4:
                        printTasks.printAveragePrintTime();
                        break;
                    case 0:
                        taskProducer.stopAutoGenerator();
                        printer.interrupt();
                        printTasks.printCancelledDocuments();
                        isStopped = true;
                        break;
                    default:
                        throw new IllegalArgumentException();

                }
            } catch (IllegalArgumentException x) {
                ConsoleHelper.writeMsg("Wrong parameter. Try again.");
            }

        }
        ConsoleHelper.printFarewell();
    }


    private void executeSubMenu1() {
        try {
            int op = ConsoleHelper.readInteger();
            switch (op) {
                case 1:
                    taskProducer.createByName();
                    break;
                case 2:
                    taskProducer.createRandomPrintTasks();
                    break;
                case 3:
                    taskProducer.startAutoGenerator();
                    break;
                case 4:
                    taskProducer.stopAutoGenerator();
                    break;
                case 5:
                    return;
                default:
                    throw new IllegalArgumentException();

            }
        } catch (IllegalArgumentException x) {
            ConsoleHelper.writeMsg("Wrong parameter. Try again.");
        }
    }

    private void executeSubMenu2() {
        try {
            int op = ConsoleHelper.readInteger();
            switch (op) {
                case 1:
                    printTasks.cancelLastSentDocument();
                    break;
                case 2:
                    printTasks.deleteDocumentByName();
                    break;
                case 3:
                    printTasks.cancelAllTasks();
                    break;
                case 4:
                    return;
                default:
                    throw new IllegalArgumentException();

            }
        } catch (IllegalArgumentException x) {
            ConsoleHelper.writeMsg("Wrong parameter. Try again.");
        }
    }

    private void executeSubMenu3() {
        try {
            int op = ConsoleHelper.readInteger();
            switch (op) {
                case 1:
                    printTasks.printPrintedDocuments();
                    break;
                case 2:
                    printTasks.printSortedPrintedDocs(new PrintTasks.DocTypeComparator());
                    break;
                case 3:
                    printTasks.printSortedPrintedDocs(new PrintTasks.PrintTimeComparator());
                    break;
                case 4:
                    printTasks.printSortedPrintedDocs(new PrintTasks.PaperSizeComparator());
                    break;
                case 5:
                    return;
                default:
                    throw new IllegalArgumentException();

            }
        } catch (IllegalArgumentException x) {
            ConsoleHelper.writeMsg("Wrong parameter. Try again.");
        }
    }
}
