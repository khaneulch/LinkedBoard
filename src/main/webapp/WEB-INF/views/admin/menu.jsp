<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions" %>

<div class="inline mt10">
	<c:set var="currURI" value="${requestScope['javax.servlet.forward.request_uri']}"/>
	<c:if test="${not empty menu}">
		<ul class="menu">
			<c:forEach items="${menu}" var="item">
				<li class="${fn:indexOf(currURI, item.menuUrl) ne -1 ? ' selected' : ''}"><a href="${item.menuUrl}">${item.menuName}</a></li>
			</c:forEach>
		</ul>
	</c:if>
</div>