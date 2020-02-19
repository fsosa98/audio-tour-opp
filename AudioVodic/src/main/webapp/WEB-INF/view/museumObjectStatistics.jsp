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
</style>

</head>
<body>


				<a href="museumObjects" class="btn btn-info btn-md" role="button">Pocetna</a>



<div class="container">
				
	<h3>Statistika muzejskih objekata</h3>
		
		<table class="table table-hover">
			<thead>
			  <tr>
			    <th scope ="col">Naziv</th>
			    <th scope="col">Broj pregleda <a href="?sort=pregledi"><span class="glyphicon glyphicon-sort-by-attributes"></span></a></th>
			    <th scope="col">Provedeno vrijeme <a href="?sort=vrijeme"><span class="glyphicon glyphicon-sort-by-attributes"></span></a></th>
			    <th scope="col">Broj pokretanja zvučnog zapisa</th>
			  </tr>
			</thead>
			<tbody>
				<c:forEach var="museumObject" items="${museumObjects}">
					  <tr>
					    <td>${museumObject.name}</td>
					    <td>${museumObject.statistics.pageTraffic}</td>
					    <td>${museumObject.statistics.printTime}</td>
					    <td>${museumObject.statistics.audioCounter}</td>
					  </tr>
				</c:forEach>
			</tbody>
			</table>
			
		<h3>Ukupna posjećenost web stranice je ${ukupno}</h3>
				
	
</div>	
	

</body>
</html>