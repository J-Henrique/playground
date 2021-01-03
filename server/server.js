const express = require("express");
const app = express();

app.use(express.json());

const countries = {
	1: {
		id: 1,
		name: 'Brazil',
		language: 'Portuguese',
		continent: 'South America',
		capital: 'Brasilia'
	},

	2: {
		id: 2,
		name: 'Canada',
		language: 'English, French',
		continent: 'North America',
		capital: 'Ottawa'
	},

	3: {
		id: 3,
		name: 'Germany',
		language: 'German',
		continent: 'Europe',
		capital: 'Berlin'
	},

	4: {
		id: 4,
		name: 'Japan',
		language: 'Japanese',
		continent: 'East Asia',
		capital: 'Tokyo'
	},

	5: {
		id: 5,
		name: 'Israel',
		language: 'Hebrew',
		continent: 'Middle East',
		capital: 'Jerusalem'
	}
}

const parseToArray = (map) => {
	let list = []
	Object.keys(map).forEach(key => list.push(map[key]))
	return list
}

app.get("/country", function(req, res) {
	setTimeout(function() {
		res.json(parseToArray(countries));
	  }, 3000);
});

app.listen(3000, function() {
  console.log("Server is running");
});