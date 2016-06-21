class Student:
	def __init__(self, units):
		self.units = units
		
	def getUnits(self):
		return self.units
		
	def getStress(self):
		return self.units * 10
		
patrick = Student(15)

patrick.getUnits()
patrick.getStress()

def setUnits(self, units):
        if (units < 0 or units > 20):
            return
        self.units = units

Student.setUnits = setUnits

patrick.setUnits(8)

setUnits(patrick,8)
