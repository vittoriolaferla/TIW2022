{ 
	    pageOrchestrator = new PageOrchestrator(); // main controller
	
	  window.addEventListener("load", () => {
	    if (sessionStorage.getItem("username") == null) {
	      window.location.href = "index.html";
	      
	    } else {
	      pageOrchestrator.start(); 
	      pageOrchestrator.refresh();
	    } 
	  }, false);

		
	  // Constructors of view components

	  function PersonalMessage(_username, messagecontainer) {
	    this.username = _username;
	    this.show = function(_alert, _listcontainer, _listcontainerbody) {
	      messagecontainer.textContent = this.username;
	    }
	  }
	  
	     function GetAlbumUser(_alert, _listcontainer, _listcontainerbody){
		 this.alert = _alert;
	    this.listcontainer = _listcontainer;
	    this.listcontainerbody = _listcontainerbody;
	    
	      this.reset = function() {
	      this.listcontainer.style.visibility = "hidden";
	    }
	    
	       this.show = function(next) {
	      var self = this;
	      makeCall("GET", "GetAlbumUser", null,
	        function(req) {
	          if (req.readyState == 4) {
	            var message = req.responseText;
	            if (req.status == 200) {
	              var albumsUsers = JSON.parse(req.responseText);
	              if (albumsUsers.length == 0) {
	                self.alert.textContent = "No albums owned by you!";
	                return;
	              }
	              self.update(albumsUsers); // self visible by closure
	             // if (next) next(); // show the default element of the list if present
	            
	          } else if (req.status == 403) {
                  window.location.href = req.getResponseHeader("Location");
                  window.sessionStorage.removeItem('username');
                  }
                  else {
	            self.alert.textContent = message;
	          }}
	        }
	      );
	    };
	    
	     this.update = function(albumsUsers) {
	      var elem, i, row, destcell, datecell, linkcell, anchor;
	      this.listcontainerbody.innerHTML = ""; // empty the table body
	      // build updated list
	      var self = this;
	      albumsUsers.forEach(function(album) { // self visible here, not this
		        row = document.createElement("tr");
		        destcell = document.createElement("td");
		        destcell.textContent = album.title;
		        row.appendChild(destcell);
		        datecell = document.createElement("td");
		        datecell.textContent = album.nameCreator
		        row.appendChild(datecell);
		        datecell = document.createElement("td");
				datecell.innerText=album.creationDate
		        row.appendChild(datecell);
		        linkcell = document.createElement("td");
		        anchor = document.createElement("a");
		        linkcell.appendChild(anchor);
		        linkText = document.createTextNode("Show");
		        anchor.appendChild(linkText);
		        anchor.setAttribute('albumid', album.id);
		        anchor.addEventListener("click", (e) => {
			        albumImages.show(e.target.getAttribute("albumid"))
			        albumInfo.show(e.target.getAttribute("albumid")) // the list must know the details container
		        }, false);
		        anchor.href = "#";
		        row.appendChild(linkcell);
		        self.listcontainerbody.appendChild(row);
	      });
	      this.listcontainer.style.visibility = "visible";
	       }
		}
		
		
		
		
		
	  
	   function GetAlbumOtherUsers(_alert, _listcontainer, _listcontainerbody){
		  this.alert = _alert;
	      this.listcontainer = _listcontainer;
	      this.listcontainerbody = _listcontainerbody;
	      this.reset = function() {
	      this.listcontainer.style.visibility = "hidden";
	    }
	      this.show = function(next) {
	      var self = this;
	      makeCall("GET", "GetAlbumOtherUsers", null,function(req) {
	          if (req.readyState == 4) {
	             var message = req.responseText;
	             if (req.status == 200) {
	              	var albumsOtherUsers = JSON.parse(req.responseText);
	              	if (albumsOtherUsers.length == 0) {
	                	self.alert.textContent = "No albums owned by other users!";
	                	return;
	             	 }
	                 self.update(albumsOtherUsers);
	             }else if (req.status == 403) {
                  	window.location.href = req.getResponseHeader("Location");
                  	window.sessionStorage.removeItem('username');
                  }
                 else {
	           		self.alert.textContent = message;
	          }}
	        }
	      );
	    };
	    
	     this.update = function(albumsOtherUsers) {
		      var elem, i, row, destcell, datecell, linkcell, anchor;
		      this.listcontainerbody.innerHTML = ""; // empty the table body
		      // build updated list
		      var self = this;
		      albumsOtherUsers.forEach(function(album) { // self visible here, not this
			        row = document.createElement("tr");
			        destcell = document.createElement("td");
			        destcell.textContent = album.title;
			        row.appendChild(destcell);
			        datecell = document.createElement("td");
			        datecell.textContent = album.nameCreator
			        row.appendChild(datecell);
			        datecell = document.createElement("td");
					datecell.innerText=album.creationDate
			        row.appendChild(datecell);
			        linkcell = document.createElement("td");
			        anchor = document.createElement("a");
			        linkcell.appendChild(anchor);
			        linkText = document.createTextNode("Show");
			        anchor.appendChild(linkText);
			        anchor.setAttribute('albumid', album.id); // set a custom HTML attribute
			        anchor.addEventListener("click", (e) => {
				         albumImages.show(e.target.getAttribute("albumid"))
				         albumInfo.show(e.target.getAttribute("albumid")) // the list must know the details container
			        }, false);
			       anchor.href = "#";
			       row.appendChild(linkcell);
			       self.listcontainerbody.appendChild(row);    
		      	});
		      this.listcontainer.style.visibility = "visible";
		       }
		}
		
	  
	  function GetNewAlbum(_alert,form, _listcontainer, _listcontainerbody,titleAlbumNew){
		 this.alert = _alert;
		 this.form=form;
	     this.listcontainer = _listcontainer;
	     this.listcontainerbody = _listcontainerbody;
	     this.titleAlbumNew=titleAlbumNew
	     this.reset = function() {
	     this.listcontainer.style.visibility = "hidden";
	   }

	   this.show = function(next) {
	      var self = this;
	      makeCall("GET", "GetNewAlbum", null,
	        function(req) {
	          if (req.readyState == 4) {
	            var message = req.responseText;
	            if (req.status == 200) {
	              var imagesToShow = JSON.parse(req.responseText);
	              if (imagesToShow.length == 0) {
	                self.alert.textContent = "No images!";
	                self.reset()
	                return;
	              }
	              self.update(imagesToShow); // self visible by closure
	             // if (next) next(); // show the default element of the list if present
	            
	          } else if (req.status == 403) {
                  window.location.href = req.getResponseHeader("Location");
                  window.sessionStorage.removeItem('username');
                  }
                  else {
	            self.alert.textContent = message;
	          }}
	        }
	      );
	    };
	    
	     this.update = function(arrayImages) {
		      var elem, i, row, destcell, datecell,input,div;
		      this.listcontainerbody.innerHTML = ""; // empty the table body
		      // build updated list
		      var self = this;
	      	  arrayImages.forEach(function(image) { // self visible here, not this
			        row = document.createElement("tr");
			        destcell = document.createElement("td");
			        destcell.textContent = image.title;
			        row.appendChild(destcell);
			        datecell = document.createElement("td");
			        datecell.textContent = image.textDescription;
			        row.appendChild(datecell);
			        destcell = document.createElement("td");
			        destcell.setAttribute("class","tdImageCreate");
			        div= document.createElement("div");
			        div.setAttribute("align","center");
			        div.setAttribute("class","divImageCreate");
			        datecell = document.createElement("img")
			        datecell.src= "ImagesServer/"+image.filePath
			        datecell.setAttribute("class","imageCreate");
					div.appendChild(datecell);
			        destcell.appendChild(div);
			        row.appendChild(destcell);
			        datecell = document.createElement("td");
			        input = document.createElement("INPUT");
			        input.setAttribute("align","center");
					input.setAttribute("type", "checkbox")
					input.setAttribute("value",image.id)
					input.setAttribute("name","id")
					datecell.appendChild(input);
			        row.appendChild(datecell);
			        self.listcontainerbody.appendChild(row);
			   });
	      	   this.listcontainer.style.visibility = "visible";
	       }
	       
	      
	       
	         this.registerEvents = function(orchestrator) {
				var params=[]
				//Get all the checkbox checked which each one from the form has the idImage as value 
  			 	this.form.querySelector("input[type='button'].submit").addEventListener('click', (e) => {
  					let values = Array.from(document.querySelectorAll('input[type=checkbox]:checked'))
    				.map(item => item.value);
	    			for(let i=0;i<values.length;i++){
						params[i]="id="+parseInt(values[i]);
					}
					var str;
					for(let n=0;n<params.length;n++){
						if(n==0){
							str=params[n]+"&";
						}
						else{
							str+=params[n]+"&";
						}
					}
	        		var formToSend = e.target.closest("form");
			       if (formToSend.checkValidity()) {
			          var self = this;
		 			  makeCall("POST", "CreateAlbum?title=" + titleAlbumNew.value + "&" + str, formToSend,function(req) {
			              if (req.readyState == XMLHttpRequest.DONE) {
			                var message = req.responseText; //error messagge 
			              if (req.status == 200) {
			                   imagesNewAlbum.show()
			                   albumsOwned.show();
			              }else if (req.status == 403) {
		                       window.location.href = req.getResponseHeader("Location");
		                       window.sessionStorage.removeItem('username');
		                  }else {
			                   self.alert.textContent = message;
			                   self.show();
				                }
				              }
				            }
				          );
				  }else{
				     this.alert.textContent = "Required parameters for form missing";
				}
		     	 }); 
		     }   
	   	}     
	
		 
		function showImages(_alert, _listcontainer, _listcontainerbody,next,prev,hide){
			this.alert = _alert;
		    this.listcontainer = _listcontainer;
		    this.listcontainerbody = _listcontainerbody;
		    this.next=next;
		    this.prev=prev;
		    this.hide=hide
		    this.reset = function() {
			    this.listcontainer.style.visibility = "hidden";
			    this.next.style.visibility = "hidden";
			    this.prev.style.visibility = "hidden";
			    this.hide.style.visibility="hidden"
		   	 }
	        var imagesAlbum
	        this.show = function(albumid) {
		        var self = this;
		        makeCall("GET", "SelectAlbum?albumId=" + albumid, null, function(req) {
		         	 if (req.readyState == 4) {
		           		 var message = req.responseText;
			             if (req.status == 200) {
			                 imagesAlbum = JSON.parse(req.responseText);
				              if (imagesAlbum.length == 0) {
				                self.alert.textContent = "No images for this album!";
				                return;
				              }
			              self.update(imagesAlbum,0);//Pass the index 0, initialize the table
			            
			             }else if (req.status == 403) {
			                  window.location.href = req.getResponseHeader("Location");
			                  window.sessionStorage.removeItem('username');
		                 }else {
			            	  self.alert.textContent = message;
			          			}
		            }
		        }
		      	);
	       };
		    
		   var imagesToShow=[];
		   var currentIndex
		   
		   //Update the table contenet the images of the album selected  
		   this.update = function(imagesAlbum, index) {
		   		var elem, i, row, destcell, datecell, linkcell, anchor;
		   		this.listcontainerbody.innerHTML = ""; // empty the table body
			    var self = this;
			    var indexMax=index+5
			    imagesToShow=Array.from(imagesAlbum);
			    for(var h;index<imagesToShow.length && index<indexMax ;index++ ){ 
				        row = document.createElement("tr");
				        destcell = document.createElement("td");
				        destcell.textContent = imagesToShow[index].title;
				        row.appendChild(destcell);
				        datecell = document.createElement("td");
				        datecell.textContent = imagesToShow[index].textDescription;
				        row.appendChild(datecell);
				        destcell = document.createElement("td");
				        destcell.setAttribute("class","tdImageToShow");
				        div= document.createElement("div");
				        div.setAttribute("align","center");
				        div.setAttribute("class","divImageToShow");
				        datecell = document.createElement("img");
				        datecell.setAttribute("class","imageToShow");
				        datecell.src= "ImagesServer/"+imagesToShow[index].filePath;
				        datecell.setAttribute("idImage",imagesToShow[index].id)
				        datecell.setAttribute("class","imageToShow");
				        datecell.setAttribute("align","center");
			    	    datecell.addEventListener('mouseenter', function(e) {
					    showImage.show(e.target.getAttribute("idImage"))
					    });
					    destcell.appendChild(datecell);
				        row.appendChild(destcell);  
			      		 
			      		 self.listcontainerbody.appendChild(row);
			 	 }
			     currentIndex=index
			     this.listcontainer.style.visibility = "visible";
			     this.hide.style.visibility="visible"
			     if(indexMax<imagesToShow.length){
			        this.next.style.visibility = "visible";
			     }else{
					this.next.style.visibility = "hidden";
				 }
					
			     if(currentIndex>5){
			        this.prev.style.visibility = "visible";
			     }else{
				 	this.prev.style.visibility = "hidden";
				}
			}
	      //Add an event listenr to next and prev button
	        this.registerEvents = function(orchestrator) {
		   		this.next.addEventListener('click', (e) => { 
				this.update(imagesAlbum,currentIndex)
		    	});
		    	//Check if the last index is divisble per 5, if not bring the index to the closeset multiple of 5 greater then currentIndex
				this.prev.addEventListener('click', (e) => {	
					if(currentIndex%5!=0){
						while(currentIndex%5!=0){
							  currentIndex++;
						}
					 }
					currentIndex=currentIndex-10
					this.update(imagesAlbum,currentIndex)
					});
				}
				this.hide.addEventListener('click', (e) => { 
					this.reset();
					albumInfo.reset();
				});
			
			
		}
		
		
		//Show the Abum info when is click show an album in the album users or ohert users album
		function ShowAlbumInfo(_alert, _listcontainer, firstRow,secondRow,thirdRow){
			this.alert = _alert;
		    this.listcontainer = _listcontainer;
		    this.firstRow = firstRow;
		    this.secondRow=secondRow;
		    this.thirdRow=thirdRow;
		    
		    this.reset = function() {
			    this.listcontainer.style.visibility = "hidden";
			    this.firstRow.style.visibility = "hidden";
			    this.secondRow.style.visibility = "hidden";
			    this.thirdRow.style.visibility = "hidden";
		     }
	    
	       this.show = function(albumid) {
		      var self = this;
		      makeCall("GET", "SelectAlbumInfo?albumId=" + albumid, null,function(req) {
		          if (req.readyState == 4) {
		          	var message = req.responseText;
		            if (req.status == 200) {
		              var albumInfo = JSON.parse(req.responseText);
		              self.update(albumInfo); // self visible by closure
		            
		          	  
		          	}else if (req.status == 403) {
	                  window.location.href = req.getResponseHeader("Location");
	                  window.sessionStorage.removeItem('username');
	                }else {
		           	  self.alert.textContent = message;
		          	}
		          }
		          }
		      );
		    
	    };
	    //Update the album Info
	      this.update = function(albumInfo) {
		      var elem, i
		      var self = this;
		      firstRow.innerText = albumInfo.title;
		      secondRow.innerText = albumInfo.nameCreator;
		      thirdRow.innerText=albumInfo.creationDate
		      this.listcontainer.style.visibility = "visible";
		      this.firstRow.style.visibility = "visible";
		      this.secondRow.style.visibility = "visible";
		      this.thirdRow.style.visibility = "visible";
	       }
		}
		
		
		
		
	//Show the image when the mouse enter on the thumbnail, by calling show	
	function ShowImage(_alert,listContainer,listContainerBody,listContainerComments,listContainerCommentsBody,image,noComments,form,containerForm,hide) {
		  this.alert = _alert;
		  this.listContainerBody=listContainerBody;
		  this.listContainer=listContainer;
		  this.listContainerComments=listContainerComments;
		  this.listContainerCommentsBody=listContainerCommentsBody;
		  this.image=image;
	 	  this.noComments=noComments;
	 	  this.form=form;
	 	  this.containerForm=containerForm;
	 	  this.imageId;
	 	  this.hide=hide;
		  //At the start of the page, we hide the 
		 this.reset= function(){
			 this.listContainer.style.visibility = "hidden";
			this.listContainerComments.style.visibility = "hidden";
			this.image.style.visibility = "hidden";
			this.noComments.innerHtml="";
			this.containerForm.style.visibility="hidden";
			this.form.style.visibility="hidden";
			this.hide.style.visibility="hidden";
			document.getElementById("divNoComments").style.visibility="hidden";
			
		 }
		 
		//Get the image and the comments for that image, and create the table and the show the form
	    this.show = function(idImage) {
	     	var self = this;
	     	self.imageId=idImage
	      	makeCall("GET","Image?imageId="+ idImage, null,function(req) {
	          if (req.readyState == 4) {
	          	var message = req.responseText;
	          	if (req.status == 200) {
	               currentImageShow = JSON.parse(req.responseText);
	               makeCall("GET","Comments?imageId="+ idImage, null,function(req) {
				   		if (req.readyState == 4) {
				            	var message = req.responseText;
				            if (req.status == 200) {
				             	 var comments = JSON.parse(req.responseText);
				            if(comments.length==0){
								 showImage.noComments(currentImageShow);
							}else{
				             	 showImage.update(currentImageShow,comments);
				                }
				            }else if (req.status == 403) {
			                  	window.location.href = req.getResponseHeader("Location");
			                 	window.sessionStorage.removeItem('username');
			                }else {
				            	self.alert.textContent = message;
				          	}
				        }
				    });   
	           }else if (req.status == 403) {
                  window.location.href = req.getResponseHeader("Location");
                  window.sessionStorage.removeItem('username');
               }else {
	              self.alert.textContent = message;
	          	}
	          }
	        });
	      }
	       
	    //Function called if there is no comment and we want to show the image
	    this.noComments= function(currentImageShow) {
		    var elem, i, row, destcell, datecell;
			this.noComments.textContent="";
			this.listContainerBody.innerHTML = ""; 
	        this.listContainerCommentsBody.innerHTML = "";
	        
	      // build updated list
	        var self = this;
	        row = document.createElement("tr");
	        destcell = document.createElement("td");
	        destcell.textContent = currentImageShow.title;
	        row.appendChild(destcell);
	        datecell = document.createElement("td");
	        datecell.textContent = currentImageShow.textDescription;
	        row.appendChild(datecell);
	        self.listContainerBody.appendChild(row);
	        
	     	image.src="ImagesServer/"+currentImageShow.filePath
			noComments.textContent="There are no comments for this image";
	     	this.listContainerComments.style.visibility = "hidden";
	     	document.getElementById("divNoComments").style.visibility="visible";
	     	this.listContainer.style.visibility = "visible";
	     	this.image.style.visibility = "visible";
	     	   this.containerForm.style.visibility="visible";
		   this.form.style.visibility="visible";
		   this.hide.style.visibility="visible";
			}

		//Update the image if there is at least one comment
	    this.update = function(currentImageShow,comments) {
	        var elem, i, row, destcell, datecell;
	        this.listContainerBody.innerHTML = ""; 
	        this.listContainerCommentsBody.innerHTML = "";
	       this.noComments.textContent="";
	        var self = this;
	        row = document.createElement("tr");
	        destcell = document.createElement("td");
	        
	        destcell.textContent = currentImageShow.title;
	        destcell.setAttribute("class","rowAlbum");
	        row.appendChild(destcell);
	        datecell = document.createElement("td");
	        datecell.textContent = currentImageShow.textDescription;
	        datecell.setAttribute("class","rowAlbum");
	        row.appendChild(datecell);
	        self.listContainerBody.appendChild(row);
	     	image.src="ImagesServer/"+currentImageShow.filePath
	      	comments.forEach(function(comment) { // self visible here, not this
		        row = document.createElement("tr");
		        destcell = document.createElement("td");
		        destcell.textContent = comment.nameUser;
		        row.appendChild(destcell);
		        datecell = document.createElement("td");
		        datecell.textContent = comment.text;
		        row.appendChild(datecell);
		        self.listContainerCommentsBody.appendChild(row);
	     	});
	       this.listContainer.style.visibility = "visible";
		   document.getElementById("divNoComments").style.visibility="hidden";
		   this.listContainerComments.style.visibility = "visible";
		   this.image.style.visibility = "visible";
		   this.containerForm.style.visibility="visible";
		   this.form.style.visibility="visible";
		   this.hide.style.visibility="visible";
	    }
	    
	    this.registerEvents = function(orchestrator) {
		 	this.form.querySelector("input[type='button'].submit").addEventListener('click', (e) => {
	        	var formToSend = e.target.closest("form");
	        	var text= document.getElementById("textArea")
			    if (formToSend.checkValidity() && text.value.length!=0 && text.value.length<180) {
				
		          var self = this;
		          
	 			  makeCall("POST", "CreateComment?idImage=" + this.imageId +"&text=" + text.value , formToSend,function(req) {
		              if (req.readyState == XMLHttpRequest.DONE) {
		                var message = req.responseText; //error messagge 
		              if (req.status == 200) {
						  self.alert.textContent="";
		                  self.show(self.imageId)
		              }else if (req.status == 403) {
	                       window.location.href = req.getResponseHeader("Location");
	                       window.sessionStorage.removeItem('username');
	                  }else {
		                   self.alert.textContent = message;
		                   self.show(self.imageId);
			                }
			              }
			            }
			          );
			        }else{
						if(text.value.length==0)
							this.alert.textContent="You cannot add a blank comment";
						else
							this.alert.textContent="The comment size must be under 180";
							
							//this.show(this.imageId)
					}
	     	}); 
	     	
	     	this.hide.addEventListener('click', (e) => {
				showImage.reset();
			});
	   
	     		 
		     }   
	   	     
	    
	  }
		
		//Handle the buildind of the page
	  function PageOrchestrator() {
	     var alertContainer = document.getElementById("id_alert");
	    
	     this.start = function() {
		      personalMessage = new PersonalMessage(sessionStorage.getItem('username'),
		      document.getElementById("id_username"));
		      personalMessage.show();
		
		 imagesNewAlbum= new GetNewAlbum(
			  alertContainer,
			  document.getElementById("createAlbum"),
			  document.getElementById("listImagesContainer"),
			  document.getElementById("listContainersImagesBody"),
			  document.getElementById("titleAlbumNew"));

			 
		 albumsNotOwned= new GetAlbumOtherUsers(
		 	  alertContainer,
			  document.getElementById("listContainerALbumOtherUsers"),
			  document.getElementById("listContainerBodyAlbumOtherUsers"));
			 
		 albumsOwned= new GetAlbumUser(
			  alertContainer,
			  document.getElementById("listContainerAlbumUser"),
			  document.getElementById("listContainerBodyAlbumUser"));
		
		 albumImages= new showImages(
			  alertContainer,
			  document.getElementById("albumShowImage"),
			  document.getElementById("showImages"),
			  document.getElementById("next"),
			  document.getElementById("prev"),
			  document.getElementById("hideAlbum"));
			
		
		 albumInfo=new ShowAlbumInfo(
			  alertContainer,
			  document.getElementById("albumInfoShowed"),
			  document.getElementById("albumInfoFirstRow"),
			  document.getElementById("albumInfoSecondRow"),
			  document.getElementById("albumInfoThirdRow"));
			  
		 showImage= new ShowImage(
			  document.getElementById("alertComment"),
			  document.getElementById("showCompleteImageContainer"),
			  document.getElementById("showCompleteImageContainerBody"),
			  document.getElementById("showComments"),
			  document.getElementById("showComment"),
			  document.getElementById("img"),
			  document.getElementById("noComments"),
			  document.getElementById("commentForm"),
			    document.getElementById("containerForm"),
			  document.getElementById("hideImage")  
			  );
			  
		  imagesNewAlbum.registerEvents(this);
		  albumImages.registerEvents(this);
		  showImage.registerEvents(this);
		  document.querySelector("a[href='Logout']").addEventListener('click', () => {
		        window.sessionStorage.removeItem('username');
		        })
		   };

	    this.refresh = function() { // currentMission initially null at start
		      alertContainer.textContent = "";        // not null after creation of status change
		    //  missionsList.reset();
		      imagesNewAlbum.show()
		    //  missionDetails.reset();
		      albumsNotOwned.show()
		      albumsOwned.show();
		      albumImages.reset();
		      albumInfo.reset();
		      showImage.reset();
	         };
	     }
	};
