package test.documents;

import java.util.Date;
import java.util.Objects;

public abstract class AbstractDocument {
    private String name;
    private int printTime;
    private DocType docType;
    private String paperSize;
    private Date date;

    AbstractDocument(DocType docType, String name, int printTime, String paperSize) {
        this.name = name;
        this.printTime = printTime;
        this.docType = docType;
        this.paperSize = paperSize;
        this.date = new Date();
    }

    public String getName() {
        return name;
    }

    public int getPrintTime() {
        return printTime;
    }

    public DocType getDocType() {
        return docType;
    }

    public String getPaperSize() {
        return paperSize;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractDocument that = (AbstractDocument) o;
        return printTime == that.printTime &&
                Objects.equals(name, that.name) &&
                docType == that.docType &&
                Objects.equals(paperSize, that.paperSize) &&
                Objects.equals(date, that.date);
    }

    @Override
    public int hashCode() {

        return Objects.hash(name, printTime, docType, paperSize, date);
    }

    @Override
    public String toString() {
        return name + '\t' + printTime +'\t' + paperSize;
    }
}
