<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width = device-width, initial- scale = 1">
    <link rel="stylesheet" type="text/css" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
   

<style>
	.form-container{
		position: absolute
		top: 15vh;
		background: #fff;
		padding: 30px;
		border-radius: 10px;
		box-shadow: 0px 0px 10px 0px #000;
	}
	
	.bg{
		background: url('../images/bg3.jpg') no-repeat;
		width: 100%;
		height: 100vh;
		background-size: 100%;
	}
</style>


<style type="text/css">
	.greska {
		  font-family: fantasy;
		  font-size: 0.9em;
		  color: #FF0000;
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
	
	<div class="mx-left m-5" style="width: 600px;">
		<div class="page-header">

			<h1>Definiraj administratora</h1>

		</div>
	</div>


			<section class="container-fluid bg">
		<section class="row justify-content-left">
			<section class="col-12 col-sm-6 col-md-3">
		
			<form class="form-container" action="creatingAdmin" method="POST">
			
			<table>
			
				<tr><div class="form-group">
					<td>Korisničko ime:</td>
					<td><input type="text" placeholder="Korisničko ime" name="username" value='<c:out value="${username}"/>'>
					<c:if test="${usernameHasError}">
							<div class="greska">
								<c:out value="${usernameError}" />
							</div>
						</c:if>
					</td>
				</tr></div>
				
				<tr><div class="form-group">
					<td>Lozinka:</td>
					<td><input type="password" placeholder="Lozinka" name="password">
					<c:if test="${passwordHasError}">
							<div class="greska">
								<c:out value="${passwordError}" />
							</div>
						</c:if>
					</td>
				</tr></div>
				
			</table>
				
			<button type="submit" class="btn btn-success" value="Login" name="metoda">Dodaj</button>
			</form>
		</section>
	</section>
</section>



	<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
    
    
</body>
</html>