package fr.mokel.jstockstrategy.indicator;

import java.util.List;

import fr.mokel.jstockstrategy.model.DayValue;

public interface IndicatorChart {

	public abstract List<DayValue> process(List<DayValue> prices, IndicatorParameters params);

}
