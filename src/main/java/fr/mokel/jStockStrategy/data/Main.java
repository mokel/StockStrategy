package fr.mokel.jStockStrategy.data;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Properties;

import fr.mokel.jStockStrategy.model.DayValue;

public class Main {

    protected static Properties props = new Properties();

    /**
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
    	
    	System.out.println(System.getProperty("java.class.path"));
    	YahooDataRetriever m = new YahooDataRetriever();
		List<DayValue> c = m.getData("ACA.PA", LocalDate.now());
    	System.out.println(c);
    }
}
