function sendReim(){
    console.log("sendReim() started")
    let reimForm = document.reimForm;
    let userid = document.getElementById('userid').value;
    let amount = document.getElementById('amount').value;
    let description = document.getElementById('description').value;
    let type = document.getElementById('type').value;

    let reimtemplate={
        userid: userid,
        amount: amount,
        description: description,
        type: type
    }

    let xhr = new XMLHttpRequest();
    
    xhr.onreadystatechange = function(){
        if(this.readyState === 4 && this.status === 200){
            console.log("Success");
            sessionStorage.setItem('currentUser',this.responseText);
            window.location = "http://localhost:8080/project-1/Ehome.html"
            console.log(sessionStorage.getItem('currentUser'))
        }
        console.log("Processing")
    }
    
    xhr.open("POST", "http://localhost:8080/project-1/reim");
    
    xhr.send(JSON.stringify(reimtemplate));

}

function home(){
    window.location = "http://localhost:8080/project-1/Ehome.html"
}