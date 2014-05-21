package fr.mokel.jStockStrategy.indicator;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.complex.Complex;
import org.apache.commons.math3.transform.DftNormalization;
import org.apache.commons.math3.transform.FastFourierTransformer;
import org.apache.commons.math3.transform.TransformType;

import fr.mokel.jStockStrategy.functions.Average;
import fr.mokel.jStockStrategy.gui.util.SwingField;
import fr.mokel.jStockStrategy.model.DayValue;

public class FastFourierIndicator implements IndicatorChart {

	@Override
	public List<DayValue> process(List<DayValue> prices, IndicatorParameters iparams) {
		DerivativeIndicator derivat = new DerivativeIndicator();
		List<DayValue> derivative = derivat.process(prices, null);
		int power2Rest = getPower2(derivative.size());
		double[] data = new double[power2Rest];
		int tab = 0;
		Average avg = new Average();
		double avgValue = avg.process(derivative, derivative.size() - power2Rest, power2Rest)
				.getValue();
		for (int i = derivative.size() - power2Rest; i < derivative.size(); i++) {
			data[tab] = derivative.get(i).getValue() - avgValue;
			tab++;
		}
		FastFourierTransformer fourier = new FastFourierTransformer(DftNormalization.STANDARD);
		Complex[] res = fourier.transform(data, TransformType.FORWARD);
		List<DayValue> fft = new ArrayList<DayValue>();
		// for (int i = 0; i < res.length; i++) {
		// DayValue dv = new DayValue(res[i].getReal(), derivative.get(
		// (derivative.size() - power2Rest) + i).getDate());
		// fft.add(dv);
		// }

		return fft;
	}

	private int getPower2(int size) {
		int res = 1;
		while (res <= size) {
			res = res * 2;
		}
		return res / 2;
	}

	public static class FastFourierParams implements IndicatorParameters {
		@SwingField(order = 1)
		int noParam = 20;

		public int getNoParam() {
			return noParam;
		}

		public void setNoParam(int noParam) {
			this.noParam = noParam;
		}


		@Override
		public IndicatorChart createIndicatorInstance() {
			return new FastFourierIndicator();
		}

	}

}
