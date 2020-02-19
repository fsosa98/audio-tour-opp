<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html lang="en">
<head>

   <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.0/js/bootstrap.min.js"></script>

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
.bg{
		background: url('../images/bg3.jpg') no-repeat;
		width: 100%;
		height: 100vh;
		background-size: 100%;
	}
</style>

</head>
<body>

				<a href="museumObjects" class="btn btn-info btn-md" role="button">Pocetna</a>


<div class="container">
	
			<ul>
			<table class="table table-hover">
			
				<h3>Vlasnik sustava</h3>
  				<thead>
  				  <tr>
	 			     <th scope="col">Ime</th>
	                 <th scope="col">Prezime</th>
	  			    <th scope="col">Korisničko ime</th>
	  			    <th scope="col">Email</th>
				   </tr>
				  </thead>
				  <tbody>
				    <tr>
				      <td>${vlasnik.firstName}</td>
					  <td>${vlasnik.lastName}</td>
					  <td>${vlasnik.username}</td>
				      <td>${vlasnik.email}</td>
				    </tr>
		
		<table class="table table-hover">
			
				<h3>Administratori</h3>
  				<thead>
  				  <tr>
 			     <th scope="col">Ime</th>
                 <th scope="col">Prezime</th>
  			    <th scope="col">Korisničko ime</th>
  			    <th scope="col">Email</th>
  			    <th scope="col">Opcija</th>
  			    <th scope="col">Izbriši</th>
    </tr>
  </thead>
  <tbody>
  <c:forEach var="admin" items="${admins}">
    <tr>
      <td>${admin.firstName}</td>
					    <td>${admin.lastName}</td>
					    <td>${admin.username}</td>
					    <td>${admin.email}</td>
					    <td><a href="changeUser/${admin.id}" type="button"> <span class="glyphicon glyphicon-circle-arrow-down" aria-hidden="true"></span> Promijeni ovlasti</a>	</td>
					    <td><a href="deleteUser/${admin.id}" type="button"> <span class="glyphicon glyphicon-remove-sign" aria-hidden="true"></span> Izbriši</a>	</td>
		
		</tr>
				
				
				</c:forEach>
				</table>
				
				<table class="table table-hover">
			
				<h3>Registrirani korisnici</h3>
  				<thead>
  				  <tr>
 			     <th scope="col">Ime</th>
                 <th scope="col">Prezime</th>
  			    <th scope="col">Korisničko ime</th>
  			    <th scope="col">Email</th>
  			    <th scope="col">Opcija</th>
  			    <th scope="col">Izbriši</th>
    </tr>
  </thead>
  <tbody>
				<c:forEach var="registeredUser" items="${registeredUsers}">
					
					  <tr>
					    <td>${registeredUser.firstName}</td>
					    <td>${registeredUser.lastName}</td>
					    <td>${registeredUser.username}</td>
					    <td>${registeredUser.email}</td>
					    <td><a href="changeUser/${registeredUser.id}" type="button"> <span class="glyphicon glyphicon-circle-arrow-up" aria-hidden="true"></span> Promijeni ovlasti</a>	</td>
					    <td><a href="deleteUser/${registeredUser.id}" type="button"> <span class="glyphicon glyphicon-remove-sign" aria-hidden="true"></span> Izbriši</a>	</td>    
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