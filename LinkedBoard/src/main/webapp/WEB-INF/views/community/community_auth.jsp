<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="bo" 		uri="/WEB-INF/tlds/board.tld" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page" %>
<%@ include file="../common/head.jsp" %>

<body>
	<div class="main-content">
		<h1 class="title"><i class="fa fa-check-square-o mr5"></i>인증</h1>
		<div class="auth-form">
			<form id="authForm" action="/community/${rParam.username}/${rParam.boardId}/auth">
				<input type="hidden" id="auth" name="auth" value="${rParam.auth}"/>
				<input type="text" class="mt6" id="authCode" name="authCode" placeholder="인증번호를 입력하세요."/>
				<a href="javascript:save('#authForm');" class="btn right">수정</a>
			</form>
		</div>
	</div>
</body>

<script type="text/javascript" src="/content/board/js/form-event.js"></script>
<script type="text/javascript">
</script>