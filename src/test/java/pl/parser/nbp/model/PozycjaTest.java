package pl.parser.nbp.model;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class PozycjaTest {

    private static final String CURRENCY_CODE = "USD";
    private static final String BUYING_RATE = "3,24";
    private static final String SELLING_RATE = "2,33";
    private static final String CONVERTER = "1";
    private static final String CURRENCY_NAME = "dolar";
    private Position position;

    @Before
    public void setUp() throws Exception {
        position = new Position("dolar", "1", "USD", "3,24", "2,33");
    }

    @Test
    public void testPositionDetails() throws Exception {
        assertThat(CURRENCY_CODE).isEqualTo(position.getCurrencyCode());
        assertThat(BUYING_RATE).isEqualTo(position.getBuyingRate());
        assertThat(SELLING_RATE).isEqualTo(position.getSellingRate());
        assertThat(CURRENCY_NAME).isEqualTo(position.getCurrencyName());
        assertThat(CONVERTER).isEqualTo(position.getConverter());
    }

}
