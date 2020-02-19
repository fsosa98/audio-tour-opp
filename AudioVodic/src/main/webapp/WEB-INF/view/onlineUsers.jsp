<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html lang="en">
<head>

    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width = device-width, initial- scale = 1">
    <link rel="stylesheet" type="text/css" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">


<style>

.bg{
	background: url('../images/bg3.jpg') no-repeat;
	width: 100%;
	height: 100vh;
	background-size: 100%;
}

table {
  font-family: arial, sans-serif;
  border-collapse: collapse;
  width: 100%;
}

td, th {
  border: 1px solid #dddddd;
  text-align: left;
  padding: 8px;
}

tr:nth-child(even) {
  background-color: #dddddd;
}
</style>

</head>
<body>

	<nav class="navbar navbar-light" style="background-color: #e3f2fd;">
		<h1>Najbolji muzej</h1>
		<ul class="nav navbar-nav navbar-right">
			<p>
				<a href="museumObjects" class="btn btn-info btn-md" role="button">Pocetna</a>
			</p>
		</u1>
	</nav>
	



<div class="container">

			<ul>
			  <table class="table table-hover">
				<h3>Broj trenutno aktivnih administratora je ${onlineAdmins.size()}</h3>
				<thead>
				  <tr>
				    <th scope ="col">Ime</th>
				    <th scope="col">Prezime</th>
				    <th scope="col">Korisničko ime</th>
				  </tr>
				</thead>
                <tbody>
				<c:forEach var="admin" items="${onlineAdmins}">
					  <tr>
					    <td>${admin.firstName}</td>
					    <td>${admin.lastName}</td>
					    <td>${admin.username}</td>
					    
					  </tr>
				</c:forEach>
				<table class="table table-hover">
				
				<h3>Broj trenutno aktivnih registriranih korisnika je ${onlineRegisteredUsers.size()}</h3>
				<thead>
				  <tr>
				    <th scope="col">Ime</th>
				    <th scope="col">Prezime</th>
				    <th scope="col">Korisničko ime</th>
				  </tr>
				</thead>
                <tbody>  
				<c:forEach var="registeredUser" items="${onlineRegisteredUsers}">
					  <tr>
					    <td>${registeredUser.firstName}</td>
					    <td>${registeredUser.lastName}</td>
					    <td>${registeredUser.username}</td>
					    
					  </tr>
				</c:forEach>
				</table>
			</ul>
	
</div>	
	
	<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
    
</body>
</html>