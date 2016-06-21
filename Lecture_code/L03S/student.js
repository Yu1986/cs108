function Student(units) {
  this.units = units;
  this.getUnits = getUnits;
  this.getStress = getStress;
}

function getUnits() {
	return this.units;
}

function getStress() {
	return 10*this.units;
}

patrick = new Student(16);
patrick.getStress();
getStress();

shayon = new Student(16);
shayon.name = "Shayon";
patrick.dog = "Molly";


function noStress() {
	return 0;
}

kasey = new Student(24);
kasey.getStress = noStress;

kasey.getStress();


dorm = {
    name: "FloMo",
	dog: "Molly the FloPup"
	};
	
dorm = {
    name: "FloMo",
	dog: "Molly the FloPup",
	printDogName: function() {
			document.writeln(this.dog);
		}
	};