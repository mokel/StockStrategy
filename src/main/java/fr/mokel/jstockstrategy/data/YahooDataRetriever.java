package fr.mokel.jstockstrategy.data;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.time.LocalDate;
import java.util.List;
import java.util.Observable;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import au.com.bytecode.opencsv.CSVReader;
import fr.mokel.jstockstrategy.model.DayValue;

public class YahooDataRetriever extends Observable implements
		MarketDataRetriever {

	private static final int DEFAULT_LENGTH = 5 * 12;

	private int nbMonth = DEFAULT_LENGTH;

	protected static Properties proxyProps = new Properties();
	private static Logger logger = Logger.getLogger(YahooDataRetriever.class);

	public YahooDataRetriever() {
		YahooDataRetriever.loadConf();
	}

	public int getNbMonth() {
		return nbMonth;
	}

	public void setNbMonth(int nbMonth) {
		this.nbMonth = nbMonth;
	}

	// public void getDataAsync(String code, LocalDate date, Observer o) {
	// deleteObservers();
	// addObserver(o);
	// Runnable run = new Runnable() {
	// @Override
	// public void run() {
	// List<DayValue> result = getData(code, from, to);
	// setChanged();
	// notifyObservers(result);
	// }
	// };
	// Thread t = new Thread(run);
	// t.start();
	// }

	@Override
	public List<DayValue> getPrices(String code, LocalDate from, LocalDate to) {
		CSVReader read = null;
			Retriever ret;
			String url = createUrl(code, from, to);
			logger.info("Fetch: " + url);
			String proxy = YahooDataRetriever.proxyProps.getProperty("proxy.url");
		if (StringUtils.isNotBlank(proxy)) {
				Integer port = Integer.valueOf(YahooDataRetriever.proxyProps.getProperty("proxy.port")).intValue();
				String login =YahooDataRetriever.proxyProps.getProperty("proxy.login");
				String password =YahooDataRetriever.proxyProps.getProperty("proxy.password"); 
				ret = new Retriever(proxy,port,login,password, url);
			} else {
				ret = new Retriever(url);
			}
			ret.load();
			String data = ret.getWebPage();
			read = new CSVReader(new StringReader(data));
		List<DayValue> list = CsvHelper.readCsv(read);
		list = CsvHelper.reverseList(list);
		return list;
	}

	private String createUrl(String code, LocalDate from, LocalDate to) {
		StringBuilder sb = new StringBuilder("https://ichart.finance.yahoo.com/table.csv?s=");
		sb.append(code)
				.append("&g=d")
				.append("&a=").append(from.getMonthValue() - 1)
				.append("&b=").append(from.getDayOfMonth())
				.append("&c=").append(from.getYear())
				.append("&d=").append(to.getMonthValue() - 1)
				.append("&e=").append(to.getDayOfMonth())
				.append("&f=").append(to.getYear())
				.append("&ignore=.csv");
		return sb.toString();
	}

	private static void loadConf() {
		try {
			InputStream is = ClassLoader.getSystemResourceAsStream("app.properties");
			if (is != null) {
				logger.info("Load app.properties");
				proxyProps.load(is);
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
