package gui;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;

public class DataRetrieval {
    int ID= 0;

    private Connection establishConnection(){
        //String url = "jdbc:mysql://raspy.ciopnus8w2eh.us-west-1.rds.amazonaws.com:3306/";
        String url = "jdbc:mysql://raspystudent.ciopnus8w2eh.us-west-1.rds.amazonaws.com:3306/";

        //;sendStringParametersAsUnicode=false"
        String userName = "raspystudent";
        String password = "weatherstation";
        String dbName = "raspystudent";
        String driver = "com.mysql.jdbc.Driver";
        Connection conn = null;
        Statement stmt = null;

        try{

            conn = DriverManager.getConnection(url + dbName, userName, password);
            System.out.println("Database Connection Successful!");
        }
        catch(Exception e){
            System.out.println(e);
            System.out.println("Oops something went wrong when trying to connect to the database");

        }
        return conn;
    }

    // TODO - test me
    private void updateDB(Connection conn, java.sql.Date inputDate, java.sql.Time inputTime,
                          double temperature, double rainInches, double windSpeed, int humidityPercent,int ID){


        Statement stmt = null;

        try{
            stmt = conn.createStatement();

//            String sql = "insert into raspystudent.WEATHER(Date, Time, Temp, Rainfall, Wind,Humidity) " +
//                         "values(date,time,Temp,Rainfall,Wind,Humidity)";

            PreparedStatement pst = conn.prepareStatement("insert into WEATHER(" +
                    "Date, Time, Temperature, rainInches, windSpeed,humidityPercent,ID) values(?,?,?,?,?,?,?)");
            pst.setDate(1,inputDate);
            pst.setTime(2,inputTime);
            pst.setDouble(3,temperature);
            pst.setDouble(4,rainInches);
            pst.setDouble(5,windSpeed);
            pst.setDouble(6,humidityPercent);
            pst.setInt(7,ID);
            pst.execute();
            pst.close();

            //stmt.executeUpdate(sql);
        }

        catch(SQLException e){
            System.out.println("SQL Exception: " + e);
        }

        finally{
            // Close resources
            try{
                if(stmt!=null){
                    conn.close();
                }
            }
            catch(SQLException e){
                System.out.println("SQL Exception: " + e);
                //
            }

            try{
                if(conn!= null){
                    conn.close();
                }
            }
            catch(SQLException e){
                System.out.println("SQL Exception: " + e);
                //
            }
        }


    }


    // TODO - test me
    // Get all data
    public static ResultSet getWeather(Connection conn)throws Exception {
        return getResultSet(conn,"SELECT * FROM WEATHER");

    }

    public static ResultSet getResultSet(Connection conn, String sql)throws Exception{
        Class.forName("com.mysql.jdbc.Driver");
        Statement stmt = conn.createStatement(ResultSet.CONCUR_READ_ONLY, ResultSet.TYPE_FORWARD_ONLY);
        return stmt.executeQuery(sql);

    }



    private static Date stringToSQLDate(String string){
        SimpleDateFormat format = new SimpleDateFormat("MM-dd-yyyy");
        java.sql.Date sqlDate = null;

        try{
            java.util.Date date = format.parse(string);
            sqlDate = new Date(date.getTime());


        }
        catch(Exception e){
            System.out.println("Error parsing date string: " + e);
        }
        return sqlDate;
    }

    private static Time stringToSQLTime(String string){
        java.sql.Time sqlTime = null;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss");


        try{
            java.util.Date time = simpleDateFormat.parse(string);
            sqlTime = new Time(time.getTime());
        }
        catch(Exception e){
            System.out.println("Error parsing time string: " + e);
        }


        return sqlTime;
    }

    private void deleteAll(Connection conn){
        Statement stmt = null;

        try {
            stmt = conn.createStatement();
            PreparedStatement pst = conn.prepareStatement("TRUNCATE WEATHER");


        }
        catch(SQLException e){

        }
    }

    public static void main(String[] args){

        long startTime = System.currentTimeMillis();

        // Unit tests
        DataRetrieval bob = new DataRetrieval();
        Connection connection = bob.establishConnection();

        java.sql.Date myDate = stringToSQLDate("01-01-2011");
        java.sql.Time myTime = stringToSQLTime("07:34:33");

        bob.deleteAll(connection);


        bob.updateDB(connection,myDate,myTime,4,3,2,1,2);




//        // TODO - Turn me into a method
        try{
            ResultSet rs = getWeather(connection);
            while(rs.next()){
                System.out.println(rs.getString(0) + " " + rs.getString(1) + " "
                        + rs.getString(2) + " " + rs.getString(3) + " " +
                        rs.getString(4) + " " + rs.getString(5));
            }
        }
        catch(Exception e){
            //
        }

        bob.deleteAll(connection);

        long endTime = System.currentTimeMillis();
        float elapse = ((endTime-startTime)/1000);
        System.out.println("That took like " + elapse + " seconds");
    }


}