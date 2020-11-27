let welcome = document.getElementById('welcome');
let userString = sessionStorage.getItem('currentUser');
let currentUser = JSON.parse(userString);

let firstname = document.getElementById('firstname');
let lastname = document.getElementById('lastname');
let email = document.getElementById('email');
let username = document.getElementById('username');

if (userString === null) {
	window.location = "http://localhost:8080/project-1/index.html"
} else {
	console.log(currentUser);
	if (currentUser != null) {
		welcome.innerHTML = "Welcome to Updates " + currentUser.username;
		firstname.value = currentUser.firstname;
		lastname.value = currentUser.lastname;
		username.value = currentUser.username;
		email.value = currentUser.email;
	}
}

function home() {
	window.location = "http://localhost:8080/project-1/employee/Ehome.html"
}

function profile() { //DONE
	console.log("profile() started")
	console.log(sessionStorage.getItem('currentUser'))
	window.location = "http://localhost:8080/project-1/employee/profile.html"
}

function update() {
	console.log("update() started")
	let updateForm = document.updateForm;

	let firstname = document.getElementById('firstname').value;
	let lastname = document.getElementById('lastname').value;
	let email = document.getElementById('email').value;
	let username = document.getElementById('username').value;
	let password = document.getElementById('password').value;
	let repassword = document.getElementById('repassword').value;

	const updatetemplate = {
		updater: currentUser.username,
		firstname: firstname,
		lastname: lastname,
		email: email,
		username: username,
		password: password,
		repassword: repassword
	}

	let xhr = new XMLHttpRequest();

	xhr.onreadystatechange = function() {
		console.log("Processing")
		if (this.readyState === 4 && this.status === 200) {
			console.log("Success");
			profile();
		}

		if (this.readyState === 4 && this.status === 204) {
			console.log("Failed");
			let childDiv = document.getElementById("warningText")
			childDiv.textContent = "Failed"
		}
	}

	xhr.open("POST", "http://localhost:8080/project-1/update");
	xhr.send(JSON.stringify(updatetemplate));
}
