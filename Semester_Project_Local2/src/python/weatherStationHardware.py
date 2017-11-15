# Code based on a great tutorial by Matt from raspberrypi-spy.co.uk 
# of how to use the MCP3008 IC chip with modifications by me, Bryce M
# for the specific type of weather sensors we are using

#DHT library from Adafruit for the temperature and humidity sensor

import spidev
import time
import os
import Adafruit_DHT

dht = Adafruit_DHT.AM2302

#Open SPI bus
spi = spidev.SpiDev()
spi.open(0,0)

# Function to read SPI data from MCP3008
# Channel must be an integer 0-7
def ReadChannel(channel):
	adc = spi.xfer2([1,(8+channel)<<4,0])
	data = ((adc[1]&3) << 8) + adc[2]
	return data
	
def GetRainfall():
	rainfall = ReadChannel(1)
	rRef = ReadChannel(2)
	# Calculates the resistance of the ETape water strip
	# Vout = (R2 * Vin) / (R1+R2)
	rainfall = (rainfall * 470) / (1023 - rainfall)
	rRef = (rRef * 470) / (1023 - rRef)
	return rainfall, rRef
	
def GetTempHumid():
	humid, temp = Adafruit_DHT.read(dht, 22)
	
	# If either reading returns null/none the value is assigned to be -1
	if humid is None:
		humid = -1
	if temp is None:
		temp = -1
		
	# Returns humidity in percentage and temp in Celsius 
	return humid, temp
	
def GetWindSpeed():
	speed = ReadChannel(0)
	# Determine voltage
	voltage = (speed * 3.3)/float(1023)
	# Use voltage to determine speed
	# voltage/meters per second ratio: 
	# 0.47V = 0 m/s -- 2V = 32.4 m/s 	
	speed = ((voltage - 0.47) * 16.2)
	#Disregard voltage if not necessary 
	#Returns speed in meters per second
	return round(speed, 2), round(voltage, 2)

def SpeedToMPH(speed):
	"""Converts meters per second windspeed to miles per hour"""
	
	# 1 mile = 1609.34 meters
	speed = (speed * 3600) / 1609.3
	return round(speed, 2)
	
def TempToFahrenheit(temp):
	temp = ((temp * 9) / 5) + 32
	return round(temp, 2)

def RainfallToInches(rainfall, rRef):
	rainfall = (rainfall - previous) / float(150)
	rainfall = round(rainfall, 2)
	return rainfall
	
def RainfallToCentimeters(rainfall, rRef):
	rainfall = (rainfall - previous) / float(60)
	rainfall = round(rainfall, 2)
	return rainfall
	
delay = 6

while True:

	#Read in data
	humid, temp = GetTempHumid()
	speed, voltage = GetWindSpeed()
	rainfall, rRef = GetRainfall()
	
	#Example print
	print "----------------------"
	print("Speed: {} ({}V)".format(speed,voltage))
	print("Temp: {} Humidity {}".format(round(temp, 2), round(humid, 2)))
	print("Rainfall {} - {}".format(rainfall, rRef))
	
	time.sleep(delay)
