package test.documents;

class Dwg extends AbstractDocument {
    private final static int PRINT_TIME = 12;
    private final static String PAPER_SIZE = "A2";

    Dwg(String name) {
        super(DocType.DWG, name, PRINT_TIME, PAPER_SIZE);
    }
}
