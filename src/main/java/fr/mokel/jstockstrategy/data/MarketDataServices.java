package fr.mokel.jstockstrategy.data;

import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import fr.mokel.jstockstrategy.model.DayValue;

public class MarketDataServices extends Observable {

	public List<DayValue> getPrices(String code, LocalDate fromDate, LocalDate toDate)
			throws MarketDataException {
		LocalDate from = getBusinessDay(fromDate, 1);
		LocalDate to = getBusinessDay(toDate, -1);
		LocalDataRetriever localRetriever = new LocalDataRetriever();
		List<DayValue> prices = new ArrayList<DayValue>(localRetriever.getPrices(code));
		if (!isComplete(prices, from, to)) {
			// the list is not complete
			// load from yahoo
			YahooDataRetriever yahooRetriever = new YahooDataRetriever();
			// 5 Year history (default)
			prices = new ArrayList<DayValue>(yahooRetriever.getPrices(code));
			CsvHelper.writeCsv(code, prices);
		}
		prices = CsvHelper.filter(prices, from, to);
		if (!isComplete(prices, from, to)) {
			throw new MarketDataException(MessageFormat.format("Cannot get prices from {0} to {1}",
					from, to));
		}
		// for the gui
		setChanged();
		notifyObservers(prices);
		return prices;
	}

	private LocalDate getBusinessDay(LocalDate date, int direction) {
		LocalDate result = date;
		while (result.getDayOfWeek().getValue() > 5)
			if (direction > 0) {
				result = result.plusDays(1);
			} else {
				result = result.plusDays(-1);
			}
		return result;
	}

	private boolean isComplete(List<DayValue> prices, LocalDate from, LocalDate to) {
		return prices.size() > 0
				&& !prices.get(0).getDate().isAfter(from)
				&& !prices.get(prices.size() - 1).getDate().isBefore(to);
	}

}
