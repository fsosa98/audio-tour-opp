<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html lang="en">
<head>

	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width = device-width, initial- scale = 1">
	<link rel="stylesheet" type="text/css"
		href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
		integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T"
		crossorigin="anonymous">
	<link rel="stylesheet" type="text/css" href="style.css" />	
	
<style>

.bg{
	background: url('../images/bg3.jpg') no-repeat;
	width: 100%;
	height: 100vh;
	background-size: 100%;
}

	.form-container{
		position: absolute
		top: 15vh;
		background: #fff;
		padding: 30px;
		border-radius: 10px;
		box-shadow: 0px 0px 10px 0px #000;
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
				<a href="/museumObjects" class="btn btn-info btn-md" role="button">Pocetna</a>
			</p>
		</u1>
	</nav>
	
	<div class="mx-left m-5" style="width: 600px;">
		<div class="page-header">

			<h1>Uredi muzejski objekt</h1>

		</div>
	</div>
	
	<section class="container-fluid bg m-5">
		<section class="row justify-content-left">
			<section class="col-12 col-sm-6 col-md-5">
	
	<form class="form-container" action="" method="post" enctype="multipart/form-data">
				
				
				
				<div>
				 <div>
				  <span class="formLabel" ">Naslov</span><br><input type="text" name=title size="20" value='${title}' >
				  
				 </div>
				 <br>
				 <div>
				 <c:if test="${titleHasError}">
					<div class="greska">
						<c:out value="${titleError}" />
					</div>
				</c:if>
				 </div>
				</div>
				
				
				
				
				
				
				
		
				<div>
				 <div>
				 <span class="formLabel">Tekst<br><textarea rows="4" cols="65" name="text">${text}</textarea></span>
				 </div>
				 <br>
				 <div>
				 <c:if test="${descriptionHasError}">
					<div class="greska">
						<c:out value="${descriptionError}" />
					</div>
				 </c:if>
				 </div>
				</div>
				
				
				
				<div class="form-group">
				    <label for="exampleFormControlSelect1">Naziv grupe</label>
				    <select class="form-control" id="exampleFormControlSelect1" name="groupSelect">
				    	<c:forEach var="groupName" items="${groupNames}">
				    		<option>${groupName}</option>
				    	</c:forEach>
				    </select>
				    <input type="checkbox" class="form-check-input" id="exampleCheck1" name="checkBox">
				    <input type="text" name=group size="20" value='${group}'>
				  	<div>
					 <c:if test="${groupHasError}">
						<div class="greska">
							<c:out value="${groupError}" />
						</div>
					</c:if>
					</div>
				</div>
				
				
			    
			    <div class="form-group row">
					<label for="inputKey" class="col-md-4 control-label">Izaberi sliku izloška</label>
					<div class="col-md-6">
						<input type="file" name="file1" accept="image/*">
					</div>
				</div>
				
				
				
				
				<div class="form-group row">
					<label for="inputKey" class="col-md-4 control-label">Izaberi zvučni zapis</label>
					<div class="col-md-6">
						<input type="file" name="file2" accept="audio/*">
					</div>
				</div>
				<div>
				 <c:if test="${imageAndAudioHasError}">
					<div class="greska">
						<c:out value="${imageAndAudioError}" />
					</div>
				 </c:if>
				 </div>
				 
				 
				 
				 
				
				<br>
				
				
				
				
				<div class="formControls">
				  <span class="formLabel">&nbsp;</span>
				  <input type="submit" class="btn btn-success" name="metoda" value="Potvrdi">
				  <input type="submit" class="btn btn-danger" name="metoda" value="Odustani">
				</div>
				<br>
				
				
				</form>
				
				</section>
			</section>
		</section>
		<br>
		
	<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
		integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
		crossorigin="anonymous"></script>
	<script
		src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
		integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
		crossorigin="anonymous"></script>
	<script
		src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
		integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
		crossorigin="anonymous"></script>


</body>

</html>