package fr.mokel.jStockStrategy.data;


import java.time.LocalDate;
import java.util.List;

import fr.mokel.jStockStrategy.model.DayValue;

public interface MarketDataRetriever {

	List<DayValue> getData(String code, LocalDate date);
}
