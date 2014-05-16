package fr.mokel.jStockStrategy.indicator;

import java.util.List;

import fr.mokel.jStockStrategy.model.DayValue;

public interface Indicator {

	public abstract DayValue process(List<DayValue> prices, IndicatorParameters params);

}
