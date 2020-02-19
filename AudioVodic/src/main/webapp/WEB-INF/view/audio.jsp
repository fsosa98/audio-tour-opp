<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html lang="en">
<head>

	<meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width = device-width, initial- scale = 1">
    <link rel="stylesheet" href="style.css">
    <link rel="stylesheet" type="text/css" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
	
	<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">

<style>



.music-container{
	width: 350px;
	display: block;
	position: absolute;
	left: 50%;
	top: 50%;
	transform: translate(-50%,-50%);
}

.music-img{
	height: 350px;
	background-size: cover;
	background-position: center;
	background-image: linear-gradient(rgba(0,0,0,0.5), rgba(0,0,0,0.5)), url('${contextPath}/images/${museumObject.imageName}');
	color: #fff;	
}

.music-header{
	border-bottom: 1px solid #555;
	height: 50px;
}

.music-header .fa-angle-left{
	float: left;
	margin-top: 15px;
	margin-left: 10px;
	cursor: pointer;
}

.music-header .fa-list-ul{
	float: right;
	margin-top: 15px;
	margin-left: 10px;
	cursor: pointer;
}

.music-header .title{
	width: 250px;
	height: 30px;
	position: absolute;
	margin-left: 30px;
	margin-top: 10px;
	text-align: center;
	padding-left: 10px;
}

.inner-img{
	padding-top: 60px;
}

.inner-img img{
	border-radius: 50%;
	animation: rotate 5s linear infinite;
	margin-bottom: 30px;
}

.img-fluid {
  width: 40%;
  height: 40%;
}

@keyframes rotate{
	100%{transform: rotate(360deg);}
}

marquee{
	font-size: 12px;
	margin-top: -10px;
}

.music-control{
	background-image: linear-gradient(#F0E68C, #222);
}

.music-bar .progress{
	height: 5px !important;
	border-radius: 0 !important;
	background-color: #000;
}

.music-bar .progress-bar{
	width: 0%;
	background-color: #fff;
	animation: width 10s infinite;
}

@keyframes{
	100%{width: 100%;}
}

.social-bar{
	border-bottom: 1px solid #FFF8DC;
	height: 50px;
}

.social-bar .fa{
	padding: 15px 26px;
	color: #999;
	font-size: 15px;
	cursor: pointer;
}

.control-bar .fa{
	display: inline-block;
	margin: 0 5px;
	cursor: pointer;
}

.control-bar .fa-play{
	width: 60px;
	height: 60px;
	padding-top: 14px;
	color: #fff;
	background-color: #delala;
	border-radius: 50%;
	font-size: 30px;
}


	
</style>

</head>
<body>
	<div class="music-container">
		<div class="music-img text-center">
			<div class="music-header">
			<i class="fa fa-angle-left"></i>
			<div class="title"><p>Trenutno slušate</p></div>
			<i class="fa fa-list-ul"></i>
			</div>
			
			<div class="inner-img">
			<img src="${contextPath}/images/${museumObject.imageName}" class="img-fluid" alt="Responsive image">
			</div>
			
			<p><b>${museumObject.name}</b></p>
			<marquee>${museumObject.description}</marquee>
			
			<div class="music-control">
			<div class="music-bar">
				<div class="progress">
					<div class="progress-bar"></div>
				</div>
			</div>
			
			<div class="social-bar text-center">
			<i class="fa fa-university"></i>
			</div>
			
			<div class="control-bar text-center">
			<audio controls>
			  <source src="${contextPath}/audio/${museumObject.audioName}" type="audio/ogg">
			  <source src="${contextPath}/audio/${museumObject.audioName}" type="audio/mpeg">
			</audio>
			
			</div>
			
			</div>
		</div>
	</div>


	<nav class="navbar navbar-light" style="background-color: #e3f2fd;">
		<h1>Najbolji muzej</h1>
		<ul class="nav navbar-nav navbar-right">
			<p>
				<a href="/museumObjects" class="btn btn-info btn-md" role="button">Pocetna</a>
			</p>
		</u1>
	</nav>
	    
	    
	<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
    
	
</body>
</html>
