class Student:
	def __init__(self, units):
		self.units = units
		
	def getUnits(self):
		return self.units
		
	def getStress(self):
		return self.units * 10

class Dog:
    def __init__(self,units):
        self.units = units

molly = Dog(3)

f = Student.getUnits
f(molly)

Student.getUnits(molly)

