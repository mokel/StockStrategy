package fr.mokel.jstockstrategy.strategy;

import java.util.ArrayList;
import java.util.List;

import fr.mokel.jstockstrategy.model.DayValue;
import fr.mokel.jstockstrategy.model.Stock;

public abstract class Strategy2 {

	private Stock stock;

	private Double stopLoss = null;// 0.1d;

	private Double maxWin = null;// 0.1d;

	private DayValue entryPoint;

	private List<Trade> trades;

	public List<Trade> process(Stock s) {
		stock = s;
		trades = new ArrayList<Trade>();
		if (checkDataLength()) {
			preProcess(stock.getList());
			for (int i = getMinDataLength() - 1; i < getDataSize(); i++) {
				DayValue dayValue = stock.getList().get(i);
				if (hasToStopLoss(dayValue) || hasMaxWin(dayValue)) {
					newTrade(dayValue);
				}
				analyse(dayValue, i);
				if (isEntryPoint()) {
					entryPoint = dayValue;
				} else if (isExitPoint() && entryPoint != null) {
					newTrade(dayValue);
				}
			}
		}
		return trades;
	}

	public DayValue getEntryPoint() {
		return entryPoint;
	}
	private boolean hasMaxWin(DayValue dayValue) {
		if (maxWin != null && entryPoint != null) {
			return new Trade(entryPoint, dayValue).getPerformance() > (1 + maxWin.doubleValue());
		}
		return false;
	}

	private void newTrade(DayValue dayValue) {
		trades.add(new Trade(entryPoint, dayValue));
		entryPoint = null;
	}

	private boolean hasToStopLoss(DayValue dayValue) {
		if (stopLoss != null && entryPoint != null) {
			return new Trade(entryPoint, dayValue).getPerformance() <= (1 - stopLoss.doubleValue());
		}
		return false;
	}

	public Trade computeFutureTrade() {
		if(entryPoint == null) {
			return null;
		} else {
			DayValue exit = computeExitPoint(entryPoint);
			return new Trade(entryPoint, exit);
		}
	}

	abstract DayValue computeExitPoint(DayValue entryPoint);

	void preProcess(List<DayValue> list) {

	}

	int getDataSize() {
		return stock.getList().size();
	}
	abstract boolean isExitPoint();

	abstract boolean isEntryPoint();

	abstract void analyse(DayValue dayValue, int sotckListIndex);

	abstract boolean checkDataLength();

	abstract int getMinDataLength();

	public abstract StrategyParamters getParameters();

	public abstract void setParameters(StrategyParamters params);


}
