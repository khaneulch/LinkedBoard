<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="../common/header.jsp" %>
<body>
	<div class="main-content">
		<h1 class="title"><i class="fa fa-check-square-o mr5"></i>회원가입</h1>
		<form id="registerForm" action="/user/registerProc" method="post">
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
			<table class="gray-table">
				<colgroup>
					<col width="120px">
					<col width="500px">
				</colgroup>
				<tr>
					<th>아이디</th>
					<td>
						<input type="text" class="_number_eng" id="username" name="username" _required="true" title="아이디"/>
						<a href="javascript:checkDuplicateId();" class="btn">중복체크</a>
						<p class="warning folding">아이디 중복체크 하세요.</p>
					</td>
				</tr>
				<tr>
					<th>비밀번호</th>
					<td><input type="password" id="password" name="password" _required="true" title="비밀번호" autocomplete="off" /></td>
				</tr>
				<tr>
					<th>비밀번호확인</th>
					<td>
						<input type="password" id="password-chk" autocomplete="off"/>
						<p class="warning folding">비밀번호를 정확히 입력하세요.</p>
					</td>
				</tr>
				<tr>
					<th>이름</th>
					<td><input type="text" id="name" name="name" _required="true" title="이름" /></td>
				</tr>
				<tr>
					<th>이메일주소</th>
					<td><input type="text" id="email" name="email" /></td>
				</tr>
				<tr>
					<th>핸드폰번호</th>
					<td><input type="text" id="mobile" name="mobile" /></td>
				</tr>
			</table>
			<a href="javascript:save('#registerForm');" class="btn right">저장</a>
		</form>
	</div>
</body>

<script type="text/javascript" src="/content/board/js/form-event.js"></script>