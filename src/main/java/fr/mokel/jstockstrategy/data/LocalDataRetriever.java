package fr.mokel.jstockstrategy.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;

import au.com.bytecode.opencsv.CSVReader;
import fr.mokel.jstockstrategy.model.DayValue;

class LocalDataRetriever implements MarketDataRetriever {

	private static Logger logger = Logger.getLogger(LocalDataRetriever.class);

	@Override
	public List<DayValue> getPrices(String code) {
		CSVReader read = null;
		File csv = new File(CsvHelper.createFileName(code));
		if (csv.canRead()) {
			try {
				logger.info("Read data from disk");
				read = new CSVReader(new FileReader(csv));
				List<DayValue> list = CsvHelper.readCsv(read);
				return list;
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		}
		return Collections.emptyList();
	}

}
