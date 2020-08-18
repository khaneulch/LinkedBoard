<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="bo" 		uri="/WEB-INF/tlds/board" %>
<%@ include file="head.jsp" %>

<script type="text/javascript" src="/content/board/js/mgnt-event.js"></script>

<div class="header-content">
	<div class="header-inside">
		<c:if test="${fn:indexOf(pageContext.request.contextPath, '/user') == -1}">
			<div class="left">
				<a href="/board/main"><i class="fa fa-home mr5 home-icon"></i></a>
			</div>
			<c:if test="${not empty username}">
				<div class="right">
					<i class="fa fa-user-circle mr5"></i>${username}<a href="/user/logout" class="btn small">로그아웃</a>
				</div>
			</c:if>
		</c:if>
		<c:set var="boardFullUrl" value="${pageContext.request.scheme}://${pageContext.request.serverName}:${pageContext.request.serverPort}"/>
	</div>
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
</div>