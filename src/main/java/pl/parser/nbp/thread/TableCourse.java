package pl.parser.nbp.thread;

import org.apache.commons.io.IOUtils;
import pl.parser.nbp.model.ExchangeRateTable;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.Callable;

public class TableCourse implements Callable<ExchangeRateTable> {

    private String documentHash;

    public TableCourse(String documentHash) {
        this.documentHash = documentHash;
    }

    @Override
    public ExchangeRateTable call() throws Exception {
        InputStream input = null;
        ExchangeRateTable exchangeRateTable = new ExchangeRateTable();
        try {
            JAXBContext jc = JAXBContext.newInstance(ExchangeRateTable.class);
            Unmarshaller unmarshaller = jc.createUnmarshaller();
            String XML_URL_PREFIX = "http://www.nbp.pl/kursy/xml/";
            String XML_URL_SUFFIX = ".xml";
            input = new URL(XML_URL_PREFIX + documentHash + XML_URL_SUFFIX).openStream();
            exchangeRateTable = (ExchangeRateTable) unmarshaller.unmarshal(input);
        } catch (JAXBException | IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(input);
        }
        return exchangeRateTable;
    }
}
