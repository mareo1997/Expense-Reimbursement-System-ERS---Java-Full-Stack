let welcome = document.getElementById('welcome');
let input = document.getElementById('allrequests');
let userString = sessionStorage.getItem('currentUser');
let currentUser = JSON.parse(userString);

if (userString === null) {
    window.location = "http://localhost:8080/project-1/index.html"
} else {
    console.log(currentUser);
    if (currentUser != null) {
        welcome.innerHTML = "Welcome " + currentUser.username + " to all employee pending requests";
    }
}
allrequests()

function home() {
    window.location = "http://localhost:8080/project-1/manager/Mhome.html"
}

function allrequests() {
    console.log("allrequests() started");

    let xhr = new XMLHttpRequest();

    xhr.onreadystatechange = function () {
        console.log("Process");
        if (this.readyState === 4 && this.status === 200) {
			alert("Success");
			let data = JSON.parse(xhr.responseText);
			console.log(data);
			renderHTML(data);
        }

        if (this.readyState === 4 && this.status === 204) {
            console.log("Failed");
            let childDiv = document.getElementById("allrequests");
            childDiv.textContent = "No pending requests found";
        }
    }

    xhr.open("GET", "http://localhost:8080/project-1/pendingrequests");
    xhr.send();
}

function renderHTML(data) {
	for (var i = 0; i < data.length; i++) {
	
		input.append("ID: "+data[i].ersid);
		input.append(document.createElement("br"));

		input.append("Author: " + data[i].author.username);
		input.append(document.createElement("br"));

		input.append("Type: " + data[i].type.type);
		input.append(document.createElement("br"));

		input.append("Description: " + data[i].description);
		input.append(document.createElement("br"));

		input.append("Amount: $" + data[i].amt);
		input.append(document.createElement("br"));

		input.append("Submitted: " + data[i].submitted);
		input.append(document.createElement("br"));

		input.append(document.createElement("hr"));
	}
}