package fr.mokel.trade.processors;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;

import fr.mokel.jstockstrategy.functions.Average;
import fr.mokel.jstockstrategy.model.DayValue;

public class AverageTest {

	@org.junit.Test
	public void test() {
		Average a = new Average();
		DayValue res = a.process(createData(1, 2, 3), 0, 3);
		Assert.assertEquals(2d, res.getValue(), 0.001d);
		Assert.assertEquals(LocalDate.now(), res.getDate());
	}

	@org.junit.Test
	public void test2() {
		Average a = new Average();
		DayValue res = a.process(createData(1, 2, 3, 4), 0, 4);
		Assert.assertEquals(2.5d, res.getValue(), 0.001d);
		Assert.assertEquals(LocalDate.now(), res.getDate());
	}

	private List<DayValue> createData(double... values) {
		List<DayValue> list = new ArrayList<DayValue>();
		LocalDate d = LocalDate.now().minusDays(values.length - 1);
		for (int i = 0; i < values.length; i++) {
			DayValue cd = new DayValue(values[i], d);
			list.add(cd);
			d = d.plusDays(1);
		}
		return list;
	}
}
