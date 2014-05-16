package fr.mokel.jStockStrategy.indicator;

import java.util.List;

import fr.mokel.jStockStrategy.model.DayValue;

public interface IndicatorChart {

	public abstract List<DayValue> process(List<DayValue> prices, IndicatorParameters params);

}
