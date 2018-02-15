import boto3
from datetime import datetime
from time import sleep
import picamera
import os
import tinys3
import glob
import RPi.GPIO as GPIO
import time
import sys
from hx711 import HX711
from rgb import RGB
#Button Settings

import subprocess

GPIO.setwarnings(False)

wifi_ip = subprocess.check_output(['hostname', '-I'])
if wifi_ip is None:
    subprocess.call(['sudo service networking restart'],shell = True)

os.system('modprobe w1-gpio')
os.system('modprobe w1-therm')
base_dir = '/sys/bus/w1/devices/'

device_folder = glob.glob(base_dir + '28*')[0]
device_file = device_folder + '/w1_slave'

# S3 Properties
access_key_id = '****'  # your access key id here
secret_access_key = '*****'  # your secret access key here
bucket_name = '****'  # bucket name here
aws_region = '****'# region of aws account
dbTable = '****'#dB table name


def read_temp_raw():
    f = open(device_file, 'r')
    lines = f.readlines()
    f.close()

    return lines

def read_temp():
    lines = read_temp_raw()

    while lines[0].strip()[-3:] != 'YES':
        time.sleep(0.2)
        lines = read_temp_raw()

    equals_pos = lines[1].find('t=')

    if equals_pos != -1:
        temp_string = lines[1][equals_pos + 2:]
        temp_c = float(temp_string) / 1000.0

        return temp_c

def cleanAndExit():
    print "Cleaning GPIO..."
    GPIO.cleanup()
    sys.exit()

# setHX711
hx = HX711(5, 6)
hx.set_reading_format("LSB", "MSB")
hx.set_reference_unit(580)  #Calibration
hx.reset()
hx.tare()

#DynamoDB settings

dynamodb = boto3.resource('dynamodb',
    aws_access_key_id=access_key_id,
    aws_secret_access_key=secret_access_key,
    region_name= aws_region)

table = dynamodb.Table(dbTable)

debug = True

# image settings
image_width = 640  # horizontal resolution
image_height = 480  # vertical_res
file_extension = '.jpg'  # file extension
photo_interval = 60  # Interval between photo (in seconds)
image_folder = 'images'  # folder name

# camera setup
camera = picamera.PiCamera()
camera.resolution = (image_width, image_height)
camera.awb_mode = 'auto'  # ['awb_mode']
camera.vflip = True

# verify image folder exists and create if it does not
if not os.path.exists(image_folder):
    os.makedirs(image_folder)
#For testing  
send_data = True

#Button Settings
GPIO.setup(12, GPIO.IN, pull_up_down=GPIO.PUD_UP)
led = RGB(21,20,16)

currentTime = datetime.now()
print(currentTime)
led.redOn()
sleep(0.5)
led.redOff()

tryNumber = table.item_count + 1

print(str(tryNumber))
print("waiting for button")
while True:

    try:
           
        button_state = GPIO.input(12)
        if button_state == False:
            print('Button Pressed.')
            
            weight = hx.get_weight(5)
            led.yellowOn()
            sleep(0.1)
            led.yellowOff()
            sleep(4)
            
            weight1 = hx.get_weight(5)
            led.yellowOn()
            sleep(0.1)
            led.yellowOff()
            
            led.blueOn()
            sleep(1)
            led.blueOff()
            print(str(weight1))
            currentTime = datetime.now()
            if weight1 > -1:
                print str(weight) + ' gramx1. Sicaklik: ' + str(read_temp())
                print str(weight1) + ' gramx2. Sicaklik: ' + str(read_temp())
                if send_data :
                    led.greenOn()
                    print('Change in weight detected')
                    file_name = 'test' + str(tryNumber) # file name
                    filepath = image_folder + '/' + file_name + file_extension
                    #Take Photo
                    camera.capture(filepath)
                    
                    #DynamoDB
                    table.put_item(
                        Item={
                            'device_id': 'kap1',  # Kap Id olmali
                            'timestamp': str(tryNumber) ,  # Deneme numarasi
                            'Temperature': int(read_temp()),
                            'Food Weight': int(weight1),
                            'Year':currentTime.year,
                            'Month':currentTime.month,
                            'Day':currentTime.day,
                            'Hour':currentTime.hour,
                            'Minute':currentTime.minute,
                            
                        }
                    )
                    tryNumber = tryNumber + 1
                    
                    # Upload to S3
                    conn = tinys3.Connection(access_key_id, secret_access_key)
                    f = open(filepath, 'rb')
                    conn.upload(filepath, f, bucket_name,
                                headers={
                                    'x-amz-meta-cache-control': 'max-age=60'
                                })
                    
                    print 'Image Uploaded to s3 '
                    # Cleanup
                    if os.path.exists(filepath):
                        os.remove(filepath)

                    led.greenOff() 
            hx.power_down()
            hx.power_up()
            time.sleep(1)
            print('Ready for next one')
    except (KeyboardInterrupt, SystemExit):
        cleanAndExit()






