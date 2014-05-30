package fr.mokel.jstockstrategy.indicator;

import fr.mokel.jstockstrategy.indicator.CciIndicator.CciIndicatorParams;
import fr.mokel.jstockstrategy.indicator.CrossMovingAverageIndicator.CrossMovingAverageIndicatorParams;
import fr.mokel.jstockstrategy.indicator.DerivativeIndicator.DerivativeIndicatorParams;
import fr.mokel.jstockstrategy.indicator.FastFourierIndicator.FastFourierParams;
import fr.mokel.jstockstrategy.indicator.MovingAverageIndicator.MovingAverageIndicatorParams;

public enum IndicatorType {

	// sformatter:off
	None("", null, false, false),
	SMA("Moving Avg", MovingAverageIndicatorParams.class, true, false), CMA("Cross Moving Avg",
			CrossMovingAverageIndicatorParams.class, false, false),
 CCI("CCI",
			CciIndicatorParams.class, false, false), FFT("FFT", FastFourierParams.class, false,
			false),
	DERIVATIVE("Derivative", DerivativeIndicatorParams.class, false, false);
	// @formatter:on
	
	private boolean onStockChart;

	private IndicatorType(String label, Class<?> clazz, boolean onStockChart, boolean needRangeAxis) {
		this.onStockChart = onStockChart;
		this.needRangeAxis = needRangeAxis;
		this.label = label;
		this.clazz = clazz;
	}

	private boolean needRangeAxis;
	private String label;
	private Class<?> clazz;

	public String getLabel() {
		return label;
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public boolean isOnStockChart() {
		return onStockChart;
	}

	public boolean isNeedRangeAxis() {
		return needRangeAxis;
	}

	@Override
	public String toString() {
		return label;
	}
}
