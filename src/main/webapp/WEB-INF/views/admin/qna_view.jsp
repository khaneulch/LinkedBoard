<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="../common/header.jsp" %>

<body>
	<div class="main-content">
		<h1 class="title"><i class="fa fa-check-square-o mr5"></i>문의 상세</h1>
		<table class="gray-table w100p">
			<colgroup>
				<col>
				<col width="140px">
			</colgroup>
			<tbody>
				<tr>
					<td>${qna.title}</td>
					<td>${bo:getFormattedDate2(qna.regDt, 'yyyy.MM.dd HH:mm')}</td>
				</tr>
				<tr>
					<td class="h500 editor" colspan="2">${qna.content}</td>
				</tr>
			</tbody>
		</table>
		<div class="btn-wrap">
			<a href="/admin/qna/list" class="btn right">목록</a>
		</div>
		
		<form action="/admin/qna/reply" id="replyForm">
			<input type="hidden" id="qnaId" name="qnaId" value="${qna.qnaId}"/>
			<table class="gray-table w100p">
				<colgroup>
					<col>
					<col width="140px">
				</colgroup>
				<thead>
					<tr><th colspan="2">답변</th></tr>
				</thead>
				<tbody>
					<c:choose>
						<c:when test="${not empty qna.replyDt}">
							<tr>
								<td><pre>${qna.reply}</pre></td>
								<td>${bo:getFormattedDate2(qna.regDt, 'yyyy.MM.dd HH:mm')}</td>
							</tr>	
						</c:when>
						<c:otherwise>
							<tr>
								<td colspan="2"><textarea rows="10" class="w100p" id="reply" name="reply" _required="true" title="답변"></textarea></td>
							</tr>
						</c:otherwise>
					</c:choose>
				</tbody>
			</table>
			<div class="btn-wrap">
				<c:choose>
					<c:when test="${not empty qna.replyDt}">
						<a href="javascript:save('#replyForm')" class="btn right">수정</a>
					</c:when>
					<c:otherwise>
						<a href="javascript:save('#replyForm')" class="btn right">저장</a>
					</c:otherwise>
				</c:choose>
			</div>
		</form>
	</div>
</body>

<script type="text/javascript" src="/content/board/js/form-event.js"></script>