package gui;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.TreeMap;

public class Data {

	private TreeMap<Date, Integer> timeIndex = new TreeMap<>();
	private ArrayList<Double> temporary = new ArrayList<>();
	private int selBeg, selEnd;
	private String query;
	public boolean selAct = true; 
	private int year = 0;
	private int month = 0;
	private int day = 0;
	private TIMESOFDAY timeOfDay; 

	public Data(int _year, int _month, int _day, TIMESOFDAY selection) throws Exception {
		year = _year;
		month = _month;
		day = _day;
		timeOfDay = selection;
		query = month + "-" + day + "-" + year;
		int posCount;
		int startInd = DataRetrieval.dateArrayList.indexOf(DataRetrieval.stringToSQLDate(query));
		int endInd = DataRetrieval.dateArrayList.lastIndexOf(DataRetrieval.stringToSQLDate(query));
		
		if (startInd == -1 || endInd == -1) {
			throw new Exception("No data found for specified date");
		}

		posCount = startInd;
		for (java.sql.Time el : DataRetrieval.timeArrayList.subList(startInd, endInd + 1)) {
			timeIndex.put(el, posCount);
			posCount++;
		}

		//This section of the code is a bit delicate---
		selBeg = validateceiling(selection.getStartTime());
		selEnd = validatefloor(selection.getEndTime());
		
		if(selBeg == -1 || selEnd == -1){
			selAct = false;
		}
		if(selBeg == -1){
			selBeg = startInd;
			System.out.println(startInd);
		}
		if(selEnd == -1){
			selEnd = endInd;
			System.out.println(endInd);
		}
	}
	
	private int validatefloor(String time){
		if(timeIndex.floorKey(DataRetrieval.stringToSQLTime(time)) == null){
			return -1;
		}else{
			return timeIndex.get(timeIndex.floorKey(DataRetrieval.stringToSQLTime(time)));
		}
	}
	
	private int validateceiling(String time){
		if(timeIndex.ceilingKey(DataRetrieval.stringToSQLTime(time)) == null){
			return -1;
		}else{
			return timeIndex.get(timeIndex.ceilingKey(DataRetrieval.stringToSQLTime(time)));
		}		
	}
	
	// Temps
	public double getAverTempC() {
			return ArrayAverage(DataRetrieval.temperatureArrayList, selBeg, selEnd);

	}

	public double getAverTempF() {
		return (getAverTempC() * (9 / 5.0)) + 32;
	}

	public double getHiTempC() {
		temporary = DataRetrieval.temperatureArrayList;
		temporary.sort(new AscendingComparator());
		return temporary.get(temporary.size() - 1);
	}

	public double getLoTempC() {
		temporary = DataRetrieval.temperatureArrayList;
		temporary.sort(new AscendingComparator());
		return temporary.get(0);
	}

	public double getHiTempF() {
		return getHiTempC() * (9 / 5.0) + 32;
	}

	public double getLoTempF() {
		return getLoTempC() * (9 / 5.0) + 32;
	}

	// Windspeed
	public double getAverWindspeedMph() {
		return (getAverWindspeedMps() * 3600) / 1609.3;
	}

	public double getAverWindspeedMps() {
			return ArrayAverage(DataRetrieval.windArrayList, selBeg, selEnd);
	}

	public double getHiWindspeedMph() {
		temporary = DataRetrieval.windArrayList;
		temporary.sort(new AscendingComparator());
		return temporary.get(temporary.size() - 1);
	}

	public double getHiWindspeedMps() {
		temporary = DataRetrieval.windArrayList;
		temporary.sort(new AscendingComparator());
		return temporary.get(0);
	}

	// Rainfall--
	public double getRainfallInches() {
		return getRainfall() / 150.0;
	}

	public double getRainfallCentimeters() {
		return getRainfall() / 45.0;
	}

	private double getRainfall() {
		return ArrayAverage(DataRetrieval.rainArrayList, selBeg, selEnd);
	}

	// Humidity
	public double getAverHumidity() {
			return ArrayAverage(DataRetrieval.humidityArrayList, selBeg, selEnd);
	}

	public double ArrayAverage(ArrayList<Double> in, int low, int high) {
		int sum = 0;
		int siz = 0;

		for (double el : in.subList(low, high)) {
			sum += el;
			siz++;
		}
		return sum / siz;
	}

	public String getDate() {
		return query;
	}

	class AscendingComparator implements Comparator<Double> {
		@Override
		public int compare(Double o1, Double o2) {
			return o1.compareTo(o2);
		}

	}

	class DescendingComparator implements Comparator<Double> {
		@Override
		public int compare(Double o1, Double o2) {
			return o1.compareTo(o2) * -1;
		}

	}

	/**
	 * @return the year
	 */
	public int getYear()
	{
		return year;
	}

	/**
	 * @param year the year to set
	 */
	public void setYear(int year)
	{
		this.year = year;
	}

	/**
	 * @return the month
	 */
	public int getMonth()
	{
		return month;
	}

	/**
	 * @param month the month to set
	 */
	public void setMonth(int month)
	{
		this.month = month;
	}

	/**
	 * @return the day
	 */
	public int getDay()
	{
		return day;
	}

	/**
	 * @param day the day to set
	 */
	public void setDay(int day)
	{
		this.day = day;
	}

	/**
	 * @return the timeOfDay
	 */
	public TIMESOFDAY getTimeOfDay()
	{
		return timeOfDay;
	}

	/**
	 * @param timeOfDay the timeOfDay to set
	 */
	public void setTimeOfDay(TIMESOFDAY timeOfDay)
	{
		this.timeOfDay = timeOfDay;
	}
	
	
}