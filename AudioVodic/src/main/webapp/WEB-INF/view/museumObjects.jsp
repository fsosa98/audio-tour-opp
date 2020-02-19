<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html lang="en">
<head>

    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width = device-width, initial- scale = 1">
    <link rel="stylesheet" type="text/css" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">


<style>


.bgColor{
 	background:#c2ccc7;
}



.img-responsive{
	width: 100%;
	height: 225;
}

</style>

</head>
<body>

<nav class="navbar navbar-light" style="background-color: #e3f2fd;">
	<h1>Najbolji muzej</h1>
	
	
	<c:choose>
		<c:when test="${not empty sessionScope['current.user.nick']}">
		
			<!-- <a class="brand">${sessionScope['current.user.nick']}</a> -->
		
			<a class="p-2 text-dark" href="showProfile">Profil</a>
		    <a class="p-2 text-dark" href="https://drive.google.com/open?id=1myLyhaZShUWsJIoAAPgS0t8Oh3HCiwqg" target="_blank" download="newfilename">Promo materijal</a>
										
		</c:when>
	</c:choose>
	
	<c:choose>
		<c:when test="${sessionScope['admin']}">
		
			<a class="p-2 text-dark" href="museumObjectStatistics">Statistika muzejskog objekta</a>
			<a class="p-2 text-dark" href="onlineUsers">Statistika korisnika</a>
	      										
		</c:when>
	</c:choose>
	
	<c:choose>
		<c:when test="${sessionScope['vlasnik']}">
		
			<a class="p-2 text-dark" href="museumObjectStatistics">Statistika muzejskog objekta</a>
			<a class="p-2 text-dark" href="onlineUsers">Statistika korisnika</a>
			
			<a class="p-2 text-dark" href="creatingAdmin">Definiraj administratora</a>
			<a class="p-2 text-dark" href="editUsers">Upravljanje korisnicima</a>
	      										
		</c:when>
	</c:choose>
	
	
	
	<c:choose>
		<c:when test="${not empty sessionScope['current.user.nick']}">				
			<form action="logInForm" method="POST">				
				<div class="formControls">
				  <span class="formLabel">&nbsp;</span>
				  <input class="btn btn-outline-primary" type="submit" name="metoda" value="Logout">
				</div>				
			</form>			
		</c:when>
		
		<c:otherwise>
			<a href="logInForm" class="btn btn-outline-primary" type="button">LogIn</a>	
		</c:otherwise>
	</c:choose>
	
</nav>

<c:choose>
	<c:when test="${sessionScope['admin']}">
		<div class="container"> 
      		<a href="addMuseumObject" class="btn btn-secondary" type="button">Dodaj muzejski objekt</a>	
      	</div>							
	</c:when>
</c:choose>
	
<c:choose>
	<c:when test="${sessionScope['vlasnik']}">
		<div class="container" color="#FAFAD2"> 
	      <a href="addMuseumObject" class="btn btn-secondary" type="button">Dodaj muzejski objekt</a>		
	    </div>							
	</c:when>
</c:choose>



<c:forEach var="groupsOfObject" items="${groupsOfObjects}" varStatus="status">

<div class="bgColor">
	<div class="container">
	
	${groupNames[status.index]}
	</div></div>

	<div class="album py-5">
	    <div class="container">
	    	<div class="row">

				<c:forEach var="museumObject" items="${groupsOfObject}">
				
					<div class="col-md-4">
			          <div class="card mb-4 shadow-sm">
			          	<a href="museumObject/${museumObject.id}"><img class="img-responsive" src="${contextPath}/images/${museumObject.imageName}"></a>
			          	<div class="card-body">
                  			<p class="card-text">${museumObject.name}</p>
                  		</div>
			          </div>
			        </div>

				</c:forEach>
	
	
			</div>
		</div>
	</div>	
	
</c:forEach>
	

	
	<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
    
</body>
</html>