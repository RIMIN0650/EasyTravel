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
	
	
	<div id="wrap">
	
	<c:import url="/WEB-INF/jsp/include/header.jsp" />
	
	
	<div>
		<div class="mt-3 bg-white">
			<h3 class="mt-5 ml-3">지역별 게시판</h3>
			
			<div class="d-flex mt-3 mx-4 justify-content-between">
				<c:forEach var="region" items="${regionList }" varStatus="status">
					<div id="region-card" class="text-center ml-3"><a href="#" class="text-white">${region.name }</a></div>
				</c:forEach>
			</div>
			
			<div class="d-flex justify-content-center mt-5">
				<div id="region-preview">
					<h5 class="my-3 ml-2">부산 후기</h5>
					<div class="mt-2 ml-4">
						<b>[1]</b> 부산의 매력을 느낄 수 있는 여행지 탐방기 <br>
					</div>
					<div class="mt-2 ml-4">
						<b>[2]</b> 달음산 자연 속 힐링 여행 후기 <br>
					</div>
					<div class="mt-2 ml-4">
						<b>[3]</b> 부산 바다와 함께한 여행, 최고의 휴식처 <br>
					</div>
				</div>
			</div>
			
		</div>
	</div>
	</div>
	
	
	
</body>

	<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
	<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.min.js" integrity="sha384-+sLIOodYLS7CIrQpBjl+C7nPvqq+FbNUBDunl/OZv93DB7Ln/533i8e/mZXLi/P+" crossorigin="anonymous"></script>
	
	
	
	
	
	
	
</html>