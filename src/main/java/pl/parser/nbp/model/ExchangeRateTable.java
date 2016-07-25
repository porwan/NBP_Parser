package pl.parser.nbp.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "tabela_kursow")
@XmlAccessorType(XmlAccessType.FIELD)
public class ExchangeRateTable {

    @XmlElement(name = "numer_tabeli")
    private String tableNumber;
    @XmlElement(name = "data_notowania")
    private String quotationDate;
    @XmlElement(name = "data_publikacji")
    private String publicationDate;
    @XmlElement(name = "pozycja")
    private List<Position> position;

    ExchangeRateTable(String tableNumber, String quotationDate, String publicationDate, List<Position> position) {
        this.tableNumber = tableNumber;
        this.quotationDate = quotationDate;
        this.publicationDate = publicationDate;
        this.position = position;
    }

    public ExchangeRateTable() {
    }

    public List<Position> getPosition() {
        return position;
    }

    public void setPosition(List<Position> position) {
        this.position = position;
    }

    public String getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(String tableNumber) {
        this.tableNumber = tableNumber;
    }

    public String getQuotationDate() {
        return quotationDate;
    }

    public void setQuotationDate(String quotationDate) {
        this.quotationDate = quotationDate;
    }

    public String getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }
}
