package fr.mokel.jstockstrategy.indicator;

import java.util.ArrayList;
import java.util.List;

import fr.mokel.jstockstrategy.functions.Average;
import fr.mokel.jstockstrategy.gui.util.SwingField;
import fr.mokel.jstockstrategy.model.DayValue;

public class MovingAverageIndicator implements IndicatorChart {

	private List<DayValue> data;


	@Override
	public List<DayValue> process(List<DayValue> prices, IndicatorParameters params) {
		MovingAverageIndicatorParams ma = (MovingAverageIndicatorParams) params;
		if (ma.averageSize > prices.size()) {
			throw new IllegalArgumentException("averageSize < window.size()");
		}
		data = new ArrayList<DayValue>();
		Average avg = new Average();
		for (int i = 0; i <= prices.size() - ma.averageSize; i++) {
			DayValue res = avg.process(prices, i, ma.averageSize);
			data.add(res);
		}
		return data;
	}

	public static class MovingAverageIndicatorParams implements IndicatorParameters {
		@SwingField(order = 1)
		private int averageSize = 20;

		public int getAverageSize() {
			return averageSize;
		}

		public void setAverageSize(int averageSize) {
			this.averageSize = averageSize;
		}

		@Override
		public IndicatorChart createIndicatorInstance() {
			return new MovingAverageIndicator();
		}

	}

}
