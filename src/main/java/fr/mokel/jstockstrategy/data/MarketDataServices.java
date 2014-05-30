package fr.mokel.jstockstrategy.data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import fr.mokel.jstockstrategy.model.DayValue;

public class MarketDataServices implements MarketDataRetriever {

	@Override
	public List<DayValue> getPrices(String code, LocalDate from, LocalDate to) {
		MarketDataRetriever localRetriever = new LocalDataRetriever();
		List<DayValue> prices = new ArrayList<DayValue>(localRetriever.getPrices(code, from, to));
		if (!isComplete(prices, from, to)) {
			//there is a gap in price list.
			//load from the last date "localPrices" to the "to" date
			YahooDataRetriever yahooRetriever = new YahooDataRetriever();
			LocalDate newFrom = !prices.isEmpty() ? prices.get(prices.size() - 1).getDate() : from;
			List<DayValue> yahooPrices = yahooRetriever.getPrices(code, newFrom, to);
			prices.addAll(yahooPrices);
			CsvHelper.writeCsv(code, prices);
		}
		return prices;
	}

	private boolean isComplete(List<DayValue> prices, LocalDate from, LocalDate to) {
		return prices.size() > 0
				&& prices.get(0).getDate().equals(from)
				&& prices.get(prices.size() - 1).getDate().equals(to);
	}
}
