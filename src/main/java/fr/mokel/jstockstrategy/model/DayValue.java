package fr.mokel.jstockstrategy.model;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class DayValue {

	private double value;
	private LocalDate date;
	private double high;
	private double low;
	private double close;
	private double volume;
	private double open;

	public DayValue() {
	}

	public DayValue(double value, LocalDate date) {
		this.value = value;
		this.date = date;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
		this.value = value;
	}

	public LocalDate getDate() {
		return date;
	}
	public Date getJavaUtilDate() {
		return Date.from(date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public double getHigh() {
		return high;
	}

	public void setHigh(double high) {
		this.high = high;
	}

	public double getLow() {
		return low;
	}

	public void setLow(double low) {
		this.low = low;
	}

	public double getClose() {
		return close;
	}

	public void setClose(double close) {
		this.close = close;
	}

	public double getVolume() {
		return volume;
	}

	public void setVolume(double volume) {
		this.volume = volume;
	}

	public double getOpen() {
		return open;
	}

	public void setOpen(double open) {
		this.open = open;
	}

	public void setDateString(String dateString) {
		this.date = LocalDate.parse(dateString);
	}

	@Override
	public String toString() {
		return "[value=" + value + ", date=" + date + "]";
	}

}
