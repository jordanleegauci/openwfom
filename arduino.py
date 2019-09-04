import serial, time, json
from gui import Gui

class Arduino():
    """ Methods pertaining to Communication with the Arduino """

    def __init__(self, port):
        print("Attempting to Connect to Arduino at Serial Port: "+port)
        try:
            self.arduino = serial.Serial(
                port=port,\
                baudrate=115200,\
                parity=serial.PARITY_NONE,\
                stopbits=serial.STOPBITS_ONE,\
                bytesize=serial.EIGHTBITS,\
                    timeout=0)
            self.connected = 1
        except serial.SerialException as e:
            self.connected = 0
