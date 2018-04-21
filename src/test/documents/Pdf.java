package test.documents;

class Pdf extends AbstractDocument {
    private final static int PRINT_TIME = 6;
    private final static String PAPER_SIZE = "A5";

    Pdf(String name) {
        super(DocType.PDF, name, PRINT_TIME, PAPER_SIZE);
    }

}
