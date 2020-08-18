<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="bo" 		uri="/WEB-INF/tlds/board.tld" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page" %>
<%@ include file="../common/head.jsp" %>

<body>
	<div class="main-content">
		<h1 class="title"><i class="fa fa-check-square-o mr5"></i>${rParam.board.boardName}</h1>
		
		<form id="listForm" action="/community/${rParam.username}/${rParam.boardId}">
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
			<input type="hidden" id="orderBy" name="orderBy" value="${rParam.orderBy}" />
			<page:pagination-input-hidden/>
			<div class="right">
				<input type="text" id="totSrch" name="totSrch" value="${rParam.totSrch}" placeholder="검색어를 입력하세요."/>
				<a href="javascript:Common.goPage(1);" class="btn small">검색</a>
			</div>
		</form>
		
		<ul class="tab order-tab">
			<li><a href="javascript:void(0);" class="${empty rParam.orderBy || rParam.orderBy eq 'new' ? 'selected' : ''}" data-value="new">최신순</a></li>
			<li><a href="javascript:void(0);" class="${rParam.orderBy eq 'comm' ? 'selected' : ''}" data-value="comm">댓글순</a></li>
			<li><a href="javascript:void(0);" class="${rParam.orderBy eq 'view' ? 'selected' : ''}" data-value="view">조회순</a></li>
		</ul>
		
		<table class="gray-table w100p center">
			<colgroup>
				<col>
				<col width="70px">
				<col width="140px">
				<col width="110px">
				<col width="140px">
				<col width="110px">
			</colgroup>
			<thead>
				<tr>
					<th>제목</th>
					<th>조회수</th>
					<th>작성자 IP</th>
					<th>작성일자</th>
					<th>수정자 IP</th>
					<th>수정일자</th>
				</tr>
			</thead>
			<tbody>
				<c:choose>
					<c:when test="${not empty list}">
						<c:forEach var="item" items="${list}">
							<tr>
								<td class="al-left">
									<c:if test="${item.noticeYn eq 'Y'}">
										<i class="fa fa-exclamation-circle mr5"></i>
									</c:if>
									<a href="/community/${rParam.username}/${rParam.boardId}/view?contentId=${item.contentId}">${item.title}</a>
									<c:if test="${rParam.board.commentYn eq 'Y'}">
										<p class="gray-txt">(${bo:toCommas(item.commentCnt)})</p>
									</c:if>
								</td>
								<td>${bo:toCommas(item.viewCnt)}</td>
								<td>${item.regIp}</td>
								<td>${bo:getFormattedDate(item.regDt)}</td>
								<td>${item.udtIp}</td>
								<td>${bo:getFormattedDate(item.udtDt)}</td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr><td colspan="6">게시글이 없습니다.</td></tr>
					</c:otherwise>
				</c:choose>
			</tbody>
		</table>
		<page:pagination-manager/>
		<div class="btn-wrap">
			<a href="/community/${rParam.username}/${rParam.boardId}/form" class="btn right">게시글 등록</a>
		</div>
	</div>
</body>