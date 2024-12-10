<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>List</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css" integrity="sha384-xOolHFLEh07PJGoPkLv1IbcEPTNtaed2xpHsD9ESMhqIYd0nLMwNLD69Npy4HI+N" crossorigin="anonymous">
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.18/dist/css/bootstrap-select.min.css">
<link rel="stylesheet" href="/static/css/style.css" type="text/css">
</head>
<body>
	
	
	<div id="wrap" class="bg-warning">
	
		<c:import url="/WEB-INF/jsp/include/header.jsp" />
		
		
		<div id="post-box">
			
			<h2 class="ml-3 mt-3"> &#8251 여행 일정 추천 - 부산</h2>
			
			<div class="d-flex justify-content-center">
				<div id="search-form" class="d-flex">
					<input type="text" class="form-control">
					<button type="button" id="search-btn" class="btn btn-info">검색</button>
				</div>
				
				<div id="order-form" class="ml-3">
					<select name="order" id="order-select">  			
					  <option value="0">최신순</option>
					  <option value="1">조회수</option>
					  <option value="2">추천순</option>
					</select>
					</select>
					<button type="button" class="btn btn-success btn-sm	">정렬</button>
				</div>	
			</div>
			
			
			<table class="table text-center">
				<thead>
					<tr>
						<th>번호</th>
						<th>제목</th>
						<th>추천수</th>
						<th>조회수</th>
					</tr>
				</thead>
				<tbody>
					<tr>
						<td>4</td>
						<td>부산 꿀팁</td>
						<td>0</td>
						<td>2</td>
					</tr>
					<tr>
						<td>3</td>
						<td>부산 분좋카</td>
						<td>4</td>
						<td>13</td>
					</tr>
					<tr>
						<td>2</td>
						<td>부산 맛집 투어</td>
						<td>12</td>
						<td>46</td>
					</tr>
					<tr>
						<td>1</td>
						<td>부산의 숨겨진 여행지!</td>
						<td>31</td>
						<td>124</td>
					</tr>
				</tbody>
			
			
			
			</table>
		</div>
		
		
		<div>
		
		
		
		
		</div>
		
		
		
	</div>
	
	
	
</body>

	<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
	<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js" integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN" crossorigin="anonymous"></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.min.js" integrity="sha384-+sLIOodYLS7CIrQpBjl+C7nPvqq+FbNUBDunl/OZv93DB7Ln/533i8e/mZXLi/P+" crossorigin="anonymous"></script>
	<script src="https://cdn.jsdelivr.net/npm/bootstrap-select@1.13.14/dist/js/bootstrap-select.min.js"></script>
	<script>
	
	
 		
</script>
	
	
	
	
</html>