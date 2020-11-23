function begin(){
	let welcome = document.getElementById('welcome');
	let userString = sessionStorage.getItem('currentUser');

	if(userString === null) {
	window.location = "http://localhost:8080/project-1/"
	} else {
		let currentUser = JSON.parse(userString);
		console.log(currentUser);
		if (currentUser != null) {
			welcome.innerHTML = "Welcome " + currentUser.username;
		}
	}
}

function reim() {
	console.log("reim() started")
	window.location = "http://localhost:8080/project-1/reim.html"
	console.log(sessionStorage.getItem('currentUser'))

}

function logout() {
	console.log("logout() started")
	let xhr = new XMLHttpRequest();

	xhr.open("POST", "http://localhost:8080/project-1/logout");
	xhr.send();

	sessionStorage.removeItem('currentUser');
	window.location = "http://localhost:8080/project-1/";
}

function pending() {
	console.log("pending() started")
	let xhr = new XMLHttpRequest();

	xhr.onreadystatechange = function() {
		if (this.readyState === 4 && this.status === 200) {
			let childDiv = document.getElementById("display")
			childDiv.textContent = "Pending"
		}

		if (this.readyState === 4 && this.status === 204) {
			console.log("Failed");
			let childDiv = document.getElementById("display")
			childDiv.textContent = "No pending requests"
		}
	}

	xhr.open("GET", "http://localhost:8080/project-1/pending");
	xhr.send(JSON.stringify(currentUser));
}

function resolved() {
	console.log("resolved() started")
	let xhr = new XMLHttpRequest();

	xhr.onreadystatechange = function() {
		if (this.readyState === 4 && this.status === 200) {
			let childDiv = document.getElementById("display")
			childDiv.textContent = "Resolved"
		}

		if (this.readyState === 4 && this.status === 204) {
			console.log("Failed");
			let childDiv = document.getElementById("display")
			childDiv.textContent = "No resolved requests"
		}
	}

	xhr.open("GET", "http://localhost:8080/project-1/resolved");
	xhr.send(JSON.stringify(currentUser));
}

function profile() {
	console.log("profile() started")
	window.location = "http://localhost:8080/project-1/profile.html"
}