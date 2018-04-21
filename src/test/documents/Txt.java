package test.documents;

class Txt extends AbstractDocument {
    private final static int PRINT_TIME = 4;
    private final static String PAPER_SIZE = "A4";

    Txt(String name) {
        super(DocType.TXT, name, PRINT_TIME, PAPER_SIZE);
    }
}
