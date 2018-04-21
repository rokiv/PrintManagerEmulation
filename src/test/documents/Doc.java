package test.documents;

class Doc extends AbstractDocument {
    private final static int PRINT_TIME = 7;
    private final static String PAPER_SIZE = "A4";

    Doc(String name) {
        super(DocType.DOC, name, PRINT_TIME, PAPER_SIZE);
    }
}
