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
     */
    public static void main(String[] args) throws IOException {
		LogUtils.configure();
    	YahooDataRetriever m = new YahooDataRetriever();
		List<DayValue> c = m.getPrices("ACA.PA", LocalDate.now().minusMonths(20), LocalDate.now()
				.minusDays(20));
    	System.out.println(c);
    }
}
