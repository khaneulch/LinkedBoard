<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page" %>
<%@ include file="../common/header.jsp" %>

<body>
	<div class="main-content">
		<h1 class="title"><i class="fa fa-check-square-o mr5"></i>현재 게시판 : ${board.boardName}</h1>
		
		<c:if test="${board.blockYn eq 'Y'}">
			<p class="warning mb5"><i class="fa fa-exclamation-circle mr5"></i>해당 게시판은 관리자에 의해 차단되었습니다.</p>
			<p class="warning mb5"><i class="fa fa-exclamation-circle mr5"></i>차단 사유 : ${board.blockReason}</p>
		</c:if>
		
		<form id="listForm" action="/board/mgnt/list">
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
			<input type="hidden" name="boardId" value="${param.boardId}" />
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
				<col width="40px">
				<col>
				<col width="70px">
				<col width="140px">
				<col width="110px">
				<col width="140px">
				<col width="110px">
			</colgroup>
			<thead>
				<tr>
					<th><input type="checkbox" class="check-all" /></th>
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
								<td><input type="checkbox" id="contentId" name="contentId" value="${item.contentId}"/></td>
								<td class="al-left">
									<c:if test="${item.noticeYn eq 'Y'}">
										<i class="fa fa-exclamation-circle mr5"></i>
									</c:if>
									<a href="/board/mgnt/view?boardId=${param.boardId}&contentId=${item.contentId}">${item.title}</a>
									<c:if test="${board.commentYn eq 'Y'}">
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
						<tr><td colspan="7">게시글이 없습니다.</td></tr>
					</c:otherwise>
				</c:choose>
			</tbody>
		</table>
		<page:pagination-manager/>
		<div class="btn-wrap">
			<a href="javascript:deleteContent();" class="btn left">삭제</a>
			<a href="/board/main" class="btn right">목록</a>
			<a href="javascript:toCreateForm();" class="btn right">게시글 등록</a>
		</div>
		<form id="createForm" action="/board/mgnt/form">
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
			<input type="hidden" id="boardId" name="boardId" value="${param.boardId}"/>
		</form>
	</div>
</body>