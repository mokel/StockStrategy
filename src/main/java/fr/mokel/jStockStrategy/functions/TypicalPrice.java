package fr.mokel.jStockStrategy.functions;

import fr.mokel.jStockStrategy.model.DayValue;

public class TypicalPrice {

	public DayValue process(DayValue dayValue) {
		double value = 0;
		value += dayValue.getClose();
		value += dayValue.getHigh();
		value += dayValue.getLow();
		DayValue res = new DayValue(value/3, dayValue.getDate());
		return res;
	}
}
