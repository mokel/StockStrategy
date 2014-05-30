package fr.mokel.jstockstrategy.utils;

import org.apache.log4j.PropertyConfigurator;

public class LogUtils {

	public static void configure() {
		PropertyConfigurator.configure(ClassLoader.getSystemResource("logger.properties"));
	}
}
