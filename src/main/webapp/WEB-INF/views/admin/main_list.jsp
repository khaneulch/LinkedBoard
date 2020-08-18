<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page" %>
<%@ include file="../common/header.jsp" %>

<body>
	<div class="main-content">
		<%@ include file="menu.jsp" %>
	
		<h1 class="title mt0"><i class="fa fa-check-square-o mr5"></i>게시판 목록</h1>
		
		<table class="gray-table w100p center">
			<colgroup>
				<col>
				<col width="90px">
				<col width="110px">
				<col width="100px">
				<col width="90px">
				<col width="65px">
			</colgroup>
			<thead>
				<tr>
					<th>게시판 이름</th>
					<th>접근설정</th>
					<th>생성자</th>
					<th>생성일자</th>
					<th>사용여부</th>
					<th>이동</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${not empty list}">
						<c:forEach var="item" items="${list}">
							<tr>
								<td>
									<c:if test="${item.blockYn eq 'Y'}">
										<p class="warning inline"><i class="fa fa-exclamation-circle mr5"></i></p>
									</c:if>
									<a href="/admin/view?username=${item.username}&boardId=${item.boardId}">${item.boardName}</a>
								</td>
								<td>${item.accessType eq 'O' ? 'OTP' : item.accessType eq 'P' ? '비밀번호' : '-'}</td>
								<td>${item.username}</td>
								<td>${bo:getFormattedDate(item.regDt)}</td>
								<td>${item.dpYn}</td>
								<td><a href="/admin/board/list?username=${item.username}&boardId=${item.boardId}" class="btn small">이동</a></td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr><td colspan="6">생성된 게시판이 없습니다.</td></tr>
					</c:otherwise>
				</c:choose>
			</tbody>
		</table>
		<page:pagination-manager/>
	</div>
</body>