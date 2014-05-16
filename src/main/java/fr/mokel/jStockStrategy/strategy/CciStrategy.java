package fr.mokel.jStockStrategy.strategy;

import java.util.List;

import fr.mokel.jStockStrategy.indicator2.CciIndicator;
import fr.mokel.jStockStrategy.indicator2.CciIndicator.CciIndicatorParams;
import fr.mokel.jStockStrategy.model.DayValue;

public class CciStrategy extends Strategy2 {

	private DayValue today;
	private DayValue yesterday;
	private List<DayValue> cci;
	private CciParamters params ;//= new CciParamters();

	@Override
	boolean checkDataLength() {
		return getDataSize() >= params.period;
	}

	@Override
	public CciParamters getParameters() {
		return params;
	}
	
	@Override
	public void setParameters(StrategyParamters params) {
		this.params = (CciParamters) params;
	}
	
	@Override
	boolean isExitPoint() {
		if (isDataNull()) {
			return false;
		}
		return yesterday.getValue() > 100 && today.getValue() <= 100;
	}

	private boolean isDataNull() {
		return yesterday == null || today == null;
	}

	@Override
	boolean isEntryPoint() {
		if (isDataNull()) {
			return false;
		}
		return yesterday.getValue() <= -100 && today.getValue() > -100;
	}

	@Override
	void analyse(DayValue dayValue, int stockListIndex) {
		if(stockListIndex < params.period - 1) {
			return;
		}
		yesterday = today;
		today = cci.get(stockListIndex - (params.period-1));
	}

	int getMinDataLength() {
		return params.period;
	}
	@Override
	void preProcess(List<DayValue> list) {
		CciIndicator cciIndicator = new CciIndicator();
		CciIndicatorParams cciParams = new CciIndicatorParams();
		cciParams.setPeriod(params.period);
		cci = cciIndicator.process(list, cciParams);
	}
}
