package fr.mokel.jStockStrategy.indicator;

import java.util.ArrayList;
import java.util.List;

import fr.mokel.jStockStrategy.model.DayValue;

public class DerivativeIndicator implements IndicatorChart {

	@Override
	public List<DayValue> process(List<DayValue> prices, IndicatorParameters params) {
		List<DayValue> derivative = new ArrayList<DayValue>();
		double previous = prices.get(0).getValue();
		for (DayValue dayValue : prices) {
			derivative.add(new DayValue(dayValue.getValue() - previous, dayValue.getDate()));
			previous = dayValue.getValue();
		}
		return derivative;
	}

	public static class DerivativeIndicatorParams implements IndicatorParameters {

		@Override
		public IndicatorChart createIndicatorInstance() {
			return new DerivativeIndicator();
		}

	}
}
