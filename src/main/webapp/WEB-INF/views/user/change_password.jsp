<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="../common/header.jsp" %>
<body>
	<div class="main-content">
		<h1 class="title"><i class="fa fa-check-square-o mr5"></i>비밀번호 변경</h1>
		<div>
			<c:choose>
				<c:when test="${not empty expired}">
					${expired}
				</c:when>
				<c:otherwise>
					<form id="passwordForm" action="/user/updateUser" method="post">
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
						<input type="hidden" id="data" name="data" value="${rParam.data}"/>
						<table class="al-left login-form">
							<colgroup>
								<col width="120px">
								<col>
							</colgroup>
							<tr>
								<th><i class="fa fa-arrow-right mr5"></i>비밀번호</th>
								<td>
									<input type="password" id="password" name="password" _required="true" title="비밀번호" autocomplete="off" />
								</td>
							</tr>
							<tr>
								<th><i class="fa fa-arrow-right mr5"></i>비밀번호확인</th>
								<td>
									<input type="password" id="password-chk" autocomplete="off"/>
								</td>
							</tr>
							<tr>
								<td colspan="2">
									<a class="btn right" href="javascript:pwdSave();">저장</a>    
								</td>
							</tr>
						</table>
					</form>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</body>
<script type="text/javascript" src="/content/board/js/login-event.js"></script>