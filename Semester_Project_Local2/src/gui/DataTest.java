package gui;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * JUnit Jupiter Test Methods for Data class
 * 
 * @author Frederick Javalera
 */
class DataTest
{
	Data testData;
	Data testData1;
	Data testData2;
	Data testData3;
	Data testData4;

	/**
	 * 
	 * @throws Exception
	 */
	@BeforeEach
	void setUp() throws Exception
	{
		testData = new Data(2017, 12, 5, TIMESOFDAY.MORNING);
		testData1 = new Data(2017, 12, 5, TIMESOFDAY.AFTERNOON);
		testData2 = new Data(2017, 12, 5, TIMESOFDAY.EVENING);
		testData3 = new Data(2017, 12, 6, TIMESOFDAY.MORNING);
	}

	/**
	 * 
	 * @throws Exception
	 */
	@AfterEach
	void tearDown() throws Exception
	{
		testData = null;
		testData1 = null;
		testData2 = null;
		testData3 = null;
	}

	/**
	 * 
	 */
	@Test
	void testData()
	{
		assertAll(() ->
		{
			assertEquals(2017, testData.getYear());
		}, () ->
		{
			assertEquals(12, testData2.getMonth());
		}, () ->
		{
			assertEquals(6, testData3.getDay());
		}, () ->
		{
			assertEquals(TIMESOFDAY.AFTERNOON, testData1.getTimeOfDay());
		});
	}

	/**
	 * 
	 */
	@Test
	void testGetAverTempF()
	{
		assertAll(() ->
		{
			assertEquals(42.8, testData.getAverTempF());
		}, () ->
		{
			assertEquals(41.0, testData1.getAverTempF());
		}, () ->
		{
			assertEquals(32.0, testData2.getAverTempF());
		}, () ->
		{
			assertEquals(32.0, testData3.getAverTempF());
		});
	}

	/**
	 * 
	 */
	@Test
	void testGetAverWindspeedMph()
	{
		assertAll(() ->
		{
			assertEquals(0.0, testData.getAverWindspeedMph());
		}, () ->
		{
			assertEquals(0.0, testData1.getAverWindspeedMph());
		}, () ->
		{
			assertEquals(0.0, testData2.getAverWindspeedMph());
		}, () ->
		{
			assertEquals(0.0, testData3.getAverWindspeedMph());
		});
	}

	/**
	 * 
	 */
	@Test
	void testGetRainfallCentimeters()
	{
		assertAll(() ->
		{
			assertEquals(1, testData.getRainfallCentimeters());
		}, () ->
		{
			assertEquals(2, testData1.getRainfallCentimeters());
		}, () ->
		{
			assertEquals(1, testData2.getRainfallCentimeters());
		}, () ->
		{
			assertEquals(0, testData3.getRainfallCentimeters());
		});
	}

	/**
	 * 
	 */
	@Test
	void testGetAverHumidity()
	{
		assertAll(() ->
		{
			assertEquals(28, testData.getAverHumidity());
		}, () ->
		{
			assertEquals(30, testData1.getAverHumidity());
		}, () ->
		{
			assertEquals(36, testData2.getAverHumidity());
		}, () ->
		{
			assertEquals(41, testData3.getAverHumidity());
		});
	}

}
