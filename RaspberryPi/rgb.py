
import time, sys
import RPi.GPIO as GPIO

class RGB:
    def __init__(self, red, green, blue):
        self.redPin = red
        self.greenPin = green
        self.bluePin = blue
        
    def blink(self,pin):
        
        GPIO.setmode(GPIO.BCM)
        
        GPIO.setup(pin, GPIO.OUT)
        GPIO.output(pin, GPIO.HIGH)
        
    def turnOff(self,pin):
                
        GPIO.setup(pin, GPIO.OUT)
        GPIO.output(pin, GPIO.LOW)
        
    def redOn(self):
        self.blink(self.redPin)

    def redOff(self):
        self.turnOff(self.redPin)

    def greenOn(self):
        self.blink(self.greenPin)

    def greenOff(self):
        self.turnOff(self.greenPin)

    def blueOn(self):
        self.blink(self.bluePin)
    
    def blueOff(self):
        self.turnOff(self.bluePin)

    def yellowOn(self):
        self.blink(self.redPin)
        self.blink(self.greenPin)

    def yellowOff(self):
        self.turnOff(self.redPin)
        self.turnOff(self.greenPin)

    def cyanOn(self):
        self.blink(self.greenPin)
        self.blink(self.bluePin)

    def cyanOff(self):
        self.turnOff(self.greenPin)
        self.turnOff(self.bluePin)

    def magentaOn(self):
        self.blink(self.redPin)
        self.blink(self.bluePin)

    def magentaOff(self):
        self.turnOff(self.redPin)
        self.turnOff(self.bluePin)

    def whiteOn(self):
        self.blink(self.redPin)
        self.blink(self.greenPin)
        self.blink(self.bluePin)

    def whiteOff(self):
        self.turnOff(self.redPin)
        self.turnOff(self.greenPin)
        self.turnOff(self.bluePin)
        




