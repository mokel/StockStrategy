package fr.mokel.jstockstrategy.data;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import fr.mokel.jstockstrategy.model.DayValue;
import fr.mokel.jstockstrategy.utils.LogUtils;

public class LoadDataTest {

	@BeforeClass
	public static void before() {
		LogUtils.configure();
	}

	@Test
	public void dayValueListTest() throws FileNotFoundException, IOException {
		MarketDataServices m = new MarketDataServices();
		List<DayValue> c = m.getPrices("ACA.PA", LocalDate.now().minusMonths(20).minusDays(3),
				LocalDate.now()
				.minusDays(20));
		System.out.println(c);
	}

}
