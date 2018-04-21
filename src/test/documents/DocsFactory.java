package test.documents;

public class DocsFactory {

    public static synchronized AbstractDocument createRandomDoc() {
        DocType docType = DocType.values()[(int) (Math.random() * 4)];
        return createDocument(docType, createRandomName());
    }

    public static synchronized AbstractDocument createDocByName(String fileName) throws Exception {
        String[] s = fileName.split("/");
        String lastElement = s[s.length - 1];
        DocType docType = DocType.valueOf(lastElement.split("\\.")[1].toUpperCase());
        String file = lastElement.split("\\.")[0];
        return createDocument(docType, file);
    }

    public static synchronized AbstractDocument createDocument(DocType docType, String fileName) {
        if (fileName==null)
            fileName = createRandomName();
        switch (docType) {
            case DWG:
                return new Dwg(fileName + ".dwg");
            case DOC:
                return new Doc(fileName + ".doc");
            case PDF:
                return new Pdf(fileName + ".pdf");
            case TXT:
                return new Txt(fileName + ".txt");
            default:
                return null;
        }
    }

    private static synchronized String createRandomName() {
        String s = "abcdefghiklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            sb.append(s.charAt((int) (Math.random() * s.length())));
        }
        return sb.toString();
    }
}
