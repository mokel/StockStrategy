package fr.mokel.jstockstrategy.data;


import java.util.List;

import fr.mokel.jstockstrategy.model.DayValue;

interface MarketDataRetriever {

	List<DayValue> getPrices(String code);
}
