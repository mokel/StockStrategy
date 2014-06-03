package fr.mokel.jstockstrategy.data;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import fr.mokel.jstockstrategy.model.DayValue;
import fr.mokel.jstockstrategy.utils.LogUtils;

public class Main {

    /**
	 * @param args
	 * @throws IOException
	 * @throws MarketDataException
	 */
	public static void main(String[] args) throws MarketDataException {
		LogUtils.configure();
		MarketDataServices s = new MarketDataServices();
		List<DayValue> c = s.getPrices("ACA.PA", LocalDate.now().minusMonths(20), LocalDate.now()
				.minusDays(1));
    	System.out.println(c);
    }
}
