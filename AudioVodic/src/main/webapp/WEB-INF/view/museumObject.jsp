<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html lang="en">

<head>
		<link href="../css/style.css" rel="stylesheet" type="text/css">
		<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
	<meta charset="UTF-8">
	
	
<style>


</style>	
	
</head>
<body>
	<nav class="navbar navbar-light" style="background-color: #e3f2fd;">
		<h1>Najbolji muzej</h1>
		<ul class="nav navbar-nav navbar-right">
			<p>
				<a href="/museumObjects" class="btn btn-info btn-md" role="button">Pocetna</a>
			</p>
		</u1>
	</nav>
	
	<c:choose>
	<c:when test="${sessionScope['admin']}">
	
		<div class="container"> 
      		<a href="editMuseumObject/${museumObject.id}" class="btn btn-secondary" type="button">Uredi muzejski objekt</a>	
      		<a href="deleteMuseumObject/${museumObject.id}" class="btn btn-secondary" type="button">Izbriši muzejski objekt</a>
      	</div>	
      					
	</c:when>
	
	<c:when test="${sessionScope['vlasnik']}">
      
      	<div class="container"> 
      		<a href="editMuseumObject/${museumObject.id}" class="btn btn-secondary" type="button">Uredi muzejski objekt</a>	
      		<a href="deleteMuseumObject/${museumObject.id}" class="btn btn-secondary" type="button">Izbriši muzejski objekt</a>
      	</div>	
									
	</c:when>
</c:choose>
	
	
	<div class="container">
		<h2 class="text-center font-weight-light text-info mb-3">${museumObject.name}</h2>
		<img src="${contextPath}/images/${museumObject.imageName}" class="rounded"
			alt="primjerak" width=700px; height=500px>
		
		<p class="text-left mt-2">${museumObject.description}</p>
		
	</div>
	<div class="container">
		<a href="audio/${museumObject.id}" class="btn btn-outline-info mb-2" type="button">Audiozapis</a>	
	</div>

</body>
</html>

