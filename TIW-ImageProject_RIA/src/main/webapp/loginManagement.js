
/**
 * Login management

*/
(function() {
	
	


  document.getElementById("loginbutton").addEventListener('click', (e) => {
	//Get the variables inside the form
    var form = e.target.closest("form");
    if (form.checkValidity()) {
	//Function that do a request to the server, useful if we want to refresh obly a part of the page
      makeCall("POST", 'CheckLogin', e.target.closest("form"),
        function(x) {
          if (x.readyState == XMLHttpRequest.DONE) {
            var message = x.responseText;
            switch (x.status) {
              case 200:
            	sessionStorage.setItem('username', message);
                window.location.href = "Home.html";
                break;
              case 400: // bad request
                document.getElementById("errormessageLogin").textContent = message;
                break;
              case 401: // unauthorized
                  document.getElementById("errormessageLogin").textContent = message;
                  break;
              case 500: // server error
            	document.getElementById("errormessageLogin").textContent = message;
                break;
            }
          }
        }
      );
    } else {
    	 form.reportValidity();
    }
  });
  

  document.getElementById("sign-up_button").addEventListener('click', (e) => {
	 var form = e.target.closest("form");
	 this.email=document.querySelector('input[name=email]').value
	 this.validateEmail=email.toLowerCase()
    .match(
      /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
    );
    this.repeatPwd=document.querySelector('input[name=repeatPwd]').value
	this.pwd=document.querySelector('input[name=pass]').value
	console.log(this.validateEmail && repeatPwd===pwd && pwd.length>5)
    if (this.validateEmail && repeatPwd===pwd && pwd.length>5) {
	  if (form.checkValidity()) {			
      makeCall("POST", 'CheckSignUp', e.target.closest("form"),
        function(x) {
          if (x.readyState == XMLHttpRequest.DONE) {
            var message = x.responseText;
            switch (x.status) {
              case 200:
             document.getElementById("errormessageSign-Up").textContent = "User created, now you can log-in";
                break;
              case 400: // bad request
                document.getElementById("errormessageSign-Up").textContent = message;
                break;
              case 401: // unauthorized
                  document.getElementById("errormessageSign-Up").textContent = message;
                  break;
              case 500: // server error
            	document.getElementById("errormessageSign-Up").textContent = message;
                break;
            }
          }
        }
      );
      }
    } else {
    	 document.getElementById("errormessageSign-Up").textContent = "Form required value are not correct";
    	 	

    }
  });

})();
