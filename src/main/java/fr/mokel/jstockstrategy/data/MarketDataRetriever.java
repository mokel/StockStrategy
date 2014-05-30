package fr.mokel.jstockstrategy.data;


import java.time.LocalDate;
import java.util.List;

import fr.mokel.jstockstrategy.model.DayValue;

public interface MarketDataRetriever {

	List<DayValue> getData(String code, LocalDate date);
}
