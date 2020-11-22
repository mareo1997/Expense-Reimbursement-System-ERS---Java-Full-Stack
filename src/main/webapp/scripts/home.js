function reim(){
    window.location = "http://localhost:8080/project-1/reim.html"
}

function logout(){
		let xhr = new XMLHttpRequest();
		
		xhr.open("POST", "http://localhost:8080/project-1/logout");
		xhr.send();
		
		sessionStorage.removeItem('currentUser');
		window.location = "http://localhost:8080/project-1/";
}