package pl.parser.nbp.util;

import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class DateUtilsTest {
    private LocalDate startDate;
    private LocalDate endDate;
    private List<String> years = new ArrayList<>();

    @Before
    public void setUp() throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        startDate = new LocalDate(dateFormat.parse("2013-01-28").getTime());
        endDate = new LocalDate(dateFormat.parse("2015-01-31").getTime());
        years.add("2014");
        years.add("2015");
        years.add("2016");
    }

    @Test
    public void testGetAllDaysFromDates() throws Exception {
        List<Date> days = DateUtils.getAllDaysBetweenTwoDates(startDate, endDate);
        assertThat(days).isNotEmpty();
        assertThat(days.size()).isEqualTo(734);
    }

    @Test
    public void testGetAllDaysFromDates_whenEndDateIsBeforeStart() throws Exception {
        List<Date> days = DateUtils.getAllDaysBetweenTwoDates(endDate, startDate);
        assertThat(days).isEmpty();
    }

    @Test
    public void testGetAllYearsFromDates() throws Exception {
        List<String> years = DateUtils.getAllYearsFromTwoDates(startDate, endDate);
        assertThat(years).isNotEmpty();
        assertThat(years.size()).isEqualTo(3);
        assertThat(years.get(0)).isEqualTo("2013");
    }

    @Test
    public void testGetAllYearsFromDates_whenEndDateIsBeforeStart() throws Exception {
        List<String> years = DateUtils.getAllYearsFromTwoDates(endDate, startDate);
        assertThat(years).isEmpty();
    }

    @Test
    public void testGetListWithUrlDateAndHashForYears() throws Exception {
        List<String> urlList = DateUtils.getListWithURLsForProvidedYears(years);
        assertThat(urlList).isNotEmpty();
        assertThat(urlList.size()).isEqualTo(3);
        assertThat(urlList.get(0)).isEqualTo("http://www.nbp.pl/kursy/xml/dir2014.txt");
        assertThat(urlList.get(1)).isEqualTo("http://www.nbp.pl/kursy/xml/dir2015.txt");
        assertThat(urlList.get(2)).isEqualTo("http://www.nbp.pl/kursy/xml/dir.txt");
    }
}