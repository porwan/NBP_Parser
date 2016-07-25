package pl.parser.nbp.model;

import org.junit.Before;
import org.junit.Test;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

public class ExchangeRateTableTest {
    private static final String TABLE_NUMBER = "198/C/NBP/2011";
    private static final String QUOTATION_DATE = "2011-10-11";
    private static final String PUBLICATION_DATE = "2011-10-12";
    private ExchangeRateTable exchangeRateTable;

    @Before
    public void setUp() throws Exception {
        exchangeRateTable = new ExchangeRateTable("198/C/NBP/2011", "2011-10-11", "2011-10-12", Collections.emptyList());
    }

    @Test
    public void testExchangeRateTableDetails() throws Exception {
        assertThat(TABLE_NUMBER).isEqualTo(exchangeRateTable.getTableNumber());
        assertThat(QUOTATION_DATE).isEqualTo(exchangeRateTable.getQuotationDate());
        assertThat(PUBLICATION_DATE).isEqualTo(exchangeRateTable.getPublicationDate());
        assertThat(exchangeRateTable.getPosition()).isEmpty();
    }
}
