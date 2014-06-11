package fr.mokel.jstockstrategy.strategy;

import java.util.List;

import fr.mokel.jstockstrategy.indicator.CciIndicator;
import fr.mokel.jstockstrategy.indicator.CciIndicator.CciIndicatorParams;
import fr.mokel.jstockstrategy.indicator.CrossMovingAverageIndicator.CrossMovingAverageIndicatorParams;
import fr.mokel.jstockstrategy.model.DayValue;

public class MokelStrategy extends Strategy2 {

	private DayValue today;
	private DayValue cmaToday;
	private DayValue yesterday;
	private List<DayValue> cci;
	private List<DayValue> cmaIndicator;
	private MokelParamters params;// = new CciParamters();

	@Override
	boolean checkDataLength() {
		return getDataSize() >= params.getLongSMALength() || getDataSize() >= params.getCciLength();
	}

	@Override
	public MokelParamters getParameters() {
		return params;
	}

	@Override
	public void setParameters(StrategyParamters params) {
		this.params = (MokelParamters) params;
	}

	@Override
	boolean isExitPoint() {
		if (isDataNull()) {
			return false;
		}
		return yesterday.getValue() > 100 && today.getValue() <= 100;
	}

	/**
	 * exit point is when cci = 100
	 */
	@Override
	DayValue computeExitPoint(DayValue entryPoint) {
		// get last cci value
		DayValue lastCci = cci.get(cci.size() - 1);
		return null;
	}

	private boolean isDataNull() {
		return yesterday == null || today == null;
	}

	@Override
	boolean isEntryPoint() {
		if (isDataNull()) {// || cmaToday.getValue() < 0) {
			return false;
		}
		return yesterday.getValue() <= -100 && today.getValue() > -100;
	}

	@Override
	void analyse(DayValue dayValue, int stockListIndex) {
		//stockListIndex i.e. 49
		yesterday = today;
		today = cci.get(stockListIndex - (params.getCciLength() - 1)); // 49 - (5-1) = 45
		// cmaToday = cmaIndicator.get(stockListIndex -
		// (params.getLongSMALength() - 1));
		// 49 - (50 -1) = 0
	}

	@Override
	int getMinDataLength() {
		return Math.max(params.getCciLength(), params.getLongSMALength());
	}

	@Override
	void preProcess(List<DayValue> list) {
		CciIndicator cciIndicator = new CciIndicator();
		CciIndicatorParams cciParams = new CciIndicatorParams();
		cciParams.setPeriod(params.getCciLength());
		cci = cciIndicator.process(list, cciParams);

		CrossMovingAverageIndicatorParams p = new CrossMovingAverageIndicatorParams();
		p.setAverageLarge(params.getLongSMALength());
		p.setAverageSmall(params.getShortSMALength());
		// cmaIndicator = new CrossMovingAverageIndicator().process(list, p);
	}

}
