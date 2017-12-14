# Code based on a great tutorial by Matt from raspberrypi-spy.co.uk 
# of how to use the MCP3008 IC chip with modifications by me, Bryce M
# for the specific type of weather sensors we are using

#DHT library from Adafruit for the temperature and humidity sensor

import spidev
import os
import Adafruit_DHT
import time
from subprocess import call
from datetime import datetime

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
	try:
		rainfall = (rainfall * 330) / (1023 - rainfall)
		rRef = (rRef * 330) / (1023 - rRef)
	except ZeroDivisionError:
		rainfall = 0
		rRef = 0
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
	speed = ((voltage - initial) * 16.2)
	#Disregard voltage if not necessary 
	#Returns speed in meters per second
	return speed, voltage
	
def RainfallDifference(rainfall, rRef):
	return (rRef - rainfall)
	
def WriteDataOut(filename, temp, humi, speed, rainfall):
	with open(filename, 'a') as file_obj:
		
		file_obj.write("{}:{},{},{},{},{}\n".format(
		str(datetime.now().hour), str(datetime.now().minute), 
		str(temp), str(humi), str(speed), str(rainfall)))

def TimeStamp(filename):
	with open(filename, 'a') as file_obj:
		file_obj.write(str(datetime.now().year) + "/" + 
		str(datetime.now().month) + "/" + str(datetime.now().day))

def Average(arrayIn):
	leng = len(arrayIn)
	if(leng == 0):
		leng = 1

	for numbs in arrayIn:
		if (numbs < 0):
			leng -= 1
		
	return sum(arrayIn) / float(leng)
				
initial = (ReadChannel(0) * 3.3)/float(1023)
temps = []
speeds = []
humidities = []
rainfalls = []
wrote = False
filename = '/home/pi/Documents/CSIS2450 Project/weatherdata.txt'

while True:	

	if datetime.now().hour == 0 and datetime.now().minute == 0 and datetime.now().second == 0:
		TimeStamp(filename)
	
	if datetime.now().second % 10 == 0:	
		humid, temp = GetTempHumid()
		speed, voltage = GetWindSpeed()
		rainfall, rRef = GetRainfall()
		
		if(temp >= 0):
			temps.append(temp)
						
		if(speed >= 0):
			speeds.append(speed)
			
		if(humid >= 0):
			humidities.append(humid)
			
		if(rRef - rainfall >= 0):
			rainfalls.append(RainfallDifference(rainfall, rRef))			
			
		print("{}:{},{},{},{},{}\n".format(
		str(datetime.now().hour), str(datetime.now().minute), 
		str(temp), str(humid), str(speed),
		str(RainfallDifference(rainfall, rRef))))
		
		time.sleep(0.8)
	
	if datetiem.now().minute % 10 > 7:
		wrote = False
				
	if datetime.now().minute % 10 == 0 and wrote != True:
		humid = Average(humidities)
		temp = Average(temps)
		speed = Average(speeds)
		rainfall = Average(rainfalls)
		
		WriteDataOut(filename, temp, humid, speed, rainfall)
		
		del humidities[:]
		del temps[:]
		del speeds[:]
		del rainfalls[:]
		
		#The directory may have to be changed before the following
		#command will work
		
		#call("java -cp . class args")
		
		wrote = True
