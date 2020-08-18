<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page" %>
<%@ include file="../common/header.jsp" %>

<body>
	<div class="main-content">
		<%@ include file="menu.jsp" %>
	
		<h1 class="title mt0"><i class="fa fa-check-square-o mr5"></i>사용자 목록</h1>
		
		<form id="listForm" action="/admin/user/list">
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
			<page:pagination-input-hidden/>
			<div class="right mb10">
				<input type="text" id="totSrch" name="totSrch" value="${rParam.totSrch}" placeholder="계정/이름 검색"/>
				<a href="javascript:Common.goPage(1);" class="btn small">검색</a>
			</div>
		</form>
		
		<table class="gray-table w100p center">
			<colgroup>
				<col width="40px">
				<col width="160px">
				<col width="110px">
				<col width="150px">
				<col>
				<col width="86px">
				<col width="86px">
				<col width="100px">
			</colgroup>
			<thead>
				<tr>
					<th><input type="checkbox" class="check-all" /></th>
					<th>ID</th>
					<th>이름</th>
					<th>전화번호</th>
					<th>이메일</th>
					<th>게시판수</th>
					<th>계정상태</th>
					<th>생성일자</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${not empty list}">
						<c:forEach var="item" items="${list}">
							<tr>
								<td><input type="checkbox" id="username" name="username" value="${item.username}"/></td>
								<td>${item.username}</td>
								<td>${item.name}</td>
								<td>${item.mobile}</td>
								<td>${item.email}</td>
								<td>${item.boardCnt}</td>
								<td>
									<c:choose>
										<c:when test="${item.status eq 'L'}">
											<p class="warning"><i class="fa fa-lock mr5"></i>잠김</p>
										</c:when>
										<c:when test="${item.status eq 'B'}">
											<p class="warning"><i class="fa fa-ban mr5"></i>차단</p>
										</c:when>
										<c:when test="${item.status eq 'D'}">삭제</c:when>
										<c:otherwise>활성</c:otherwise>
									</c:choose>
								</td>
								<td>${bo:getFormattedDate(item.regDt)}</td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr><td colspan="8">사용자가 없습니다.</td></tr>
					</c:otherwise>
				</c:choose>
			</tbody>
		</table>
		<div class="btn-wrap">
			<a href="javascript:blockUser();" class="btn left">차단</a>
		</div>
		<page:pagination-manager/>
	</div>
</body>