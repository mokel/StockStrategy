package fr.mokel.jStockStrategy.functions;

import java.util.List;

import fr.mokel.jStockStrategy.model.DayValue;

public class WeightedAverage {

	public WeightedAverage() {
	}

	public DayValue getValue(List<DayValue> data) {
		double value = 0;
		int weight = 1;
		for (DayValue dayValue : data) {
			value += dayValue.getValue() * weight;
			weight++;
		}
		DayValue res = new DayValue(0, data.get(data.size() - 1).getDate());
		int denominator = data.size() * (data.size() + 1) / 2;
		res.setValue(value / denominator);
		return res;
	}

}
