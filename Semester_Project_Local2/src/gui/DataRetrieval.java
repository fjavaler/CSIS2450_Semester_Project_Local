package gui;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;

public class DataRetrieval {
	private static Connection conn;
	private static Statement stmt;
	private static ResultSet rs;

	static ArrayList<java.sql.Date> dateArrayList;
	static ArrayList<java.sql.Time> timeArrayList;
	static ArrayList<Double> rainArrayList;
	static ArrayList<Double> windArrayList;
	static ArrayList<Double> humidityArrayList;
	static ArrayList<Double> temperatureArrayList;

	public static void establishConnection() throws Exception {
		String url = "jdbc:mysql://raspystudent.ciopnus8w2eh.us-west-1.rds.amazonaws.com:3306/";

		String userName = "raspystudent";
		String password = "weatherstation";
		String dbName = "raspystudent";
		// String driver = "com.mysql.jdbc.Driver";
		conn = null;
		stmt = null;

		try {
			conn = DriverManager.getConnection(url + dbName, userName, password);
			System.out.println("Database connection successful!");
		} catch (Exception e) {
			throw new Exception("Could not connect to database...");
		}
	}

	public static void retrieveData() throws Exception {
		try {
			rs = getWeather(conn);
			ResultSetMetaData rsmd = rs.getMetaData();
			int columnCount = rsmd.getColumnCount();
			dateArrayList = new ArrayList<>(columnCount);
			timeArrayList = new ArrayList<>(columnCount);
			rainArrayList = new ArrayList<>(columnCount);
			windArrayList = new ArrayList<>(columnCount);
			humidityArrayList = new ArrayList<>(columnCount);
			temperatureArrayList = new ArrayList<>(columnCount);

			while (rs.next()) {
				dateArrayList.add(rs.getDate(1));
				timeArrayList.add(rs.getTime(2));
				temperatureArrayList.add(rs.getDouble(3));
				rainArrayList.add(rs.getDouble(4));
				windArrayList.add(rs.getDouble(5));
				humidityArrayList.add(rs.getDouble(6));

			}
		} catch (Exception e) {
			throw new Exception("Data could not be retrieved from database");
		}

	}

	public static void updateDB(Connection conn, java.sql.Date inputDate, java.sql.Time inputTime, double temperature,
			double rainInches, double windSpeed, double humidityPercent) {
		try {
			stmt = conn.createStatement();

			// String sql = "insert into raspystudent.WEATHER(Date, Time, Temp,
			// Rainfall,
			// Wind,Humidity) " +
			// "values(date,time,Temp,Rainfall,Wind,Humidity)";

			PreparedStatement pst = conn.prepareStatement("insert into WEATHER("
					+ "Date, Time, Temperature, rainInches, windSpeed,humidityPercent) values(?,?,?,?,?,?)");
			pst.setDate(1, inputDate);
			pst.setTime(2, inputTime);
			pst.setDouble(3, temperature);
			pst.setDouble(4, rainInches);
			pst.setDouble(5, windSpeed);
			pst.setDouble(6, humidityPercent);
			pst.execute();
			// pst.close();

		} catch (SQLException e) {
			System.out.println("SQL Exception: " + e);
		}
	}

	public static void closeConnection() throws Exception {
		try {
			if (stmt != null) {
				conn.close();
			}
		} catch (SQLException e) {
		}

		try {
			if (conn != null) {
				conn.close();
				System.out.println("Database connection closed successfully!");
			}
		} catch (SQLException e) {
			throw new Exception("SQL connection not closed properly:\n" + e);
		}
	}

	// Get all data
	public static ResultSet getWeather(Connection conn) throws Exception {
		return getResultSet(conn, "SELECT * FROM WEATHER");

	}

	public static ResultSet getResultSet(Connection conn, String sql) throws Exception {
		Class.forName("com.mysql.jdbc.Driver");
		Statement stmt = conn.createStatement(ResultSet.CONCUR_READ_ONLY, ResultSet.TYPE_FORWARD_ONLY);
		return stmt.executeQuery(sql);

	}

	public static Date stringToSQLDate(String string) {
		SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
		java.sql.Date sqlDate = null;

		try {
			java.util.Date date = format.parse(string);
			sqlDate = new Date(date.getTime());

		} catch (Exception e) {
			System.out.println("Error parsing date string: " + e);
		}
		return sqlDate;
	}

	public static Date todayToSQLDate() {
		SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
		java.sql.Date sqlDate = null;
		GregorianCalendar now = new GregorianCalendar();

		try {
			java.util.Date date = format.parse((now.get(2) + 1) + "-" + now.get(5) + "-" + now.get(1));
			sqlDate = new Date(date.getTime());

		} catch (Exception e) {
			System.out.println("Error parsing date string: " + e);
		}
		return sqlDate;
	}

	public static Time stringToSQLTime(String string) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
		java.sql.Time sqlTime = null;

		try {
			java.util.Date time = simpleDateFormat.parse(string);
			sqlTime = new Time(time.getTime());
		} catch (Exception e) {
			System.out.println("Error parsing time string: " + e);
		}

		return sqlTime;
	}

	public static Time nowToSQLTime() {
		java.sql.Time sqlTime = null;

		try {
			sqlTime = new Time(System.currentTimeMillis());
		} catch (Exception e) {
			System.out.println("Error parsing time string: " + e);
		}

		return sqlTime;
	}

	public static void deleteAll(Connection conn) {
		try {
			stmt = conn.createStatement();
			PreparedStatement pst = conn.prepareStatement("TRUNCATE WEATHER");
			pst.execute();

		} catch (SQLException e) {
		}
	}

	public static void main(String[] args) {
		System.out.println(stringToSQLTime(TIMESOFDAY.NIGHT.getEndTime()));
	}
}