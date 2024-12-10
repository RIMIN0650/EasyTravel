<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Post</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css" integrity="sha384-xOolHFLEh07PJGoPkLv1IbcEPTNtaed2xpHsD9ESMhqIYd0nLMwNLD69Npy4HI+N" crossorigin="anonymous">
<link rel="stylesheet" href="/static/css/style.css" type="text/css">
</head>
<body>
	
	
	<div id="wrap" class="bg-warning">
	
	<c:import url="/WEB-INF/jsp/include/header.jsp" />
	
	<section class="d-flex justify-content-center">
		<div class="mt-5">	
			<div id="post-input-form" class="bg-white">
				<div class="d-flex ml-3 pt-3">
					<input type="text" class="form-control col-7" placeholder="제목" id="title">
					<input type="text" class="form-control col-2 ml-5" placeholder="지역" id="region">
				</div>
				<div class="mt-5 ml-3">
					<textarea class="mb-2" placeholder="Detail" rows="20" id="input-text"></textarea>
				</div>
				
				<div class="d-flex justify-content-end my-5">
					<button type="button" class="btn btn-danger mr-4" id="cancel_btn">취소</button>
					<button type="button" class="btn btn-info mr-4" id="register_btn">등록</button>
				</div>
			</div>
		</div>
		
		
	</section>
	
	

	</div>
	
	
	
</body>

	<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
	<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.min.js" integrity="sha384-+sLIOodYLS7CIrQpBjl+C7nPvqq+FbNUBDunl/OZv93DB7Ln/533i8e/mZXLi/P+" crossorigin="anonymous"></script>
	
	
	<script>
		$(document).ready(function(){
			$("#register_btn").on("click", function(){
				alert("등록");
			});
			
			
			
			
		});
	</script>
	
	
	
	
</html>