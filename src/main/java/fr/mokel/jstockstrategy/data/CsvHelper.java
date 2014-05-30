package fr.mokel.jstockstrategy.data;

import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import au.com.bytecode.opencsv.bean.CsvToBean;
import au.com.bytecode.opencsv.bean.HeaderColumnNameTranslateMappingStrategy;
import fr.mokel.jstockstrategy.model.DayValue;

public class CsvHelper {

	static void writeCsv(String code, List<DayValue> prices) {
		String fileName = createFileName(code);
		List<String[]> bars = new ArrayList<String[]>();
		bars.add(getHeaders());
		for (DayValue dayValue : prices) {
			String[] barString = transform(dayValue);
			bars.add(barString);
		}
		FileWriter fw;
		try {
			fw = new FileWriter(fileName);
			CSVWriter w = new CSVWriter(fw, ',', (char) 0);
			w.writeAll(bars);
			w.flush();
			w.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	static String[] transform(DayValue dayValue) {
		NumberFormat nf = NumberFormat.getInstance(Locale.ENGLISH);
		nf.setGroupingUsed(false);
		return new String[] {
				dayValue.getDate().format(
						DateTimeFormatter.ofPattern("yyyy-MM-dd")),
				nf.format(dayValue.getOpen()), nf.format(dayValue.getHigh()),
				nf.format(dayValue.getLow()), nf.format(dayValue.getClose()),
				nf.format(dayValue.getVolume()), nf.format(dayValue.getOpen()) };
	}

	private static String[] getHeaders() {
		return new String[] { "Date", "Open", "High", "Low", "Close", "Volume",
				"Adj Close" };
	}

	static String createFileName(String code) {
		return code + ".csv";
	}

	static List<DayValue> reverseList(List<DayValue> toReverse) {
		List<DayValue> result = new ArrayList<DayValue>();
		for (int i = toReverse.size() - 1; i >= 0; i--) {
			result.add(toReverse.get(i));
		}
		return result;
	}

	static List<DayValue> readCsv(CSVReader reader) {
		CsvToBean<DayValue> toBean = new CsvToBean<DayValue>();
		HeaderColumnNameTranslateMappingStrategy<DayValue> strat = new HeaderColumnNameTranslateMappingStrategy<DayValue>();
		Map<String, String> map = new HashMap<String, String>();
		map.put("Date", "dateString");
		map.put("Open", "open");
		map.put("High", "high");
		map.put("Low", "low");
		map.put("Close", "close");
		map.put("Volume", "volume");
		map.put("Adj Close", "value");
		strat.setColumnMapping(map);
		strat.setType(DayValue.class);
		return new ArrayList<DayValue>(toBean.parse(strat, reader));
	}
	

	public static List<DayValue> filter(List<DayValue> list, LocalDate from, LocalDate to) {
		List<DayValue> data = new ArrayList<DayValue>();
		for (DayValue chartData : list) {
			if (!(chartData.getDate().isAfter(to)
			|| chartData.getDate().isBefore(from))) {
				DayValue dv = new DayValue(chartData.getValue(),
						chartData.getDate());
				dv.setHigh(chartData.getHigh());
				dv.setLow(chartData.getLow());
				dv.setClose(chartData.getClose());
				data.add(dv);
			}
		}
		return data;
	}

}
