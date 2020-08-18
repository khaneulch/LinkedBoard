<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="../common/header.jsp" %>
<body>
	<div class="main-content">
		<h1 class="title"><i class="fa fa-check-square-o mr5"></i>로그인</h1>
		<div>
			<form action="/user/loginProc" method="post">
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
				<table class="al-left login-form">
					<colgroup>
						<col width="120px">
						<col>
					</colgroup>
					<tr>
						<th><i class="fa fa-arrow-right mr5"></i>ID</th>
						<td>
							<input type="text" id="username" name="username"/> 
						</td>
					</tr>
					<tr>
						<th><i class="fa fa-arrow-right mr5"></i>PASSWORD</th>
						<td>
							<input type="password" id="password" name="password"/>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							<button class="btn right" type="submit">확인</button>    
						</td>
					</tr>
				</table>
			</form>
		</div>
		<div class="btn-wrap mt10 al-center">
			<a class="btn" href="/user/register">회원가입</a>
			<a class="btn">비밀번호 찾기</a>
		</div>
	</div>
</body>
