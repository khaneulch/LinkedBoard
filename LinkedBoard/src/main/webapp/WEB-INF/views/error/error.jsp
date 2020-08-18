<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="bo" 		uri="/WEB-INF/tlds/board.tld" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page" %>
<%@ include file="../common/head.jsp" %>
<body>
	<div class="main-content">
		<h1 class="error"><i class="fa fa-exclamation-triangle mr5"></i>ERROR</h1>
		${status}
		<a href="javascript:history.back(-1)">이전 페이지로</a>
	</div>
</body>