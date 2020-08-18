<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="../common/header.jsp" %>

<body>
	<div class="main-content">
		<h1 class="title"><i class="fa fa-check-square-o mr5"></i>게시판 상세</h1>
		<c:if test="${board.blockYn eq 'Y'}">
			<p class="warning mb5"><i class="fa fa-exclamation-circle mr5"></i>해당 게시판은 관리자에 의해 차단되었습니다.</p>
			<p class="warning mb5"><i class="fa fa-exclamation-circle mr5"></i>차단 사유 : ${board.blockReason}</p>
		</c:if>
		<table class="gray-table w100p">
			<colgroup>
				<col width="250px">
				<col>
			</colgroup>
			<tbody>
				<tr>
					<th>게시판 이름</th>
					<td>${board.boardName}</td>
				</tr>
				<tr>
					<th>접근 설정</th>
					<td>${board.accessType eq 'P' ? '비밀번호' : board.accessType eq 'O' ? 'Google OTP' : '없음'}</td>
				</tr>
				<tr>
					<th>댓글 사용</th>
					<td>${board.commentYn eq 'Y' ? '사용' : '미사용'}</td>
				</tr>
				<tr>
					<th>파일 용량 제한</th>
					<td>${bo:toUnit(board.fileLimit)}</td>
				</tr>
			</tbody>
		</table>
		<input type="hidden" id="username" name="username" value="${board.username}"/>
		<input type="hidden" id="boardId" name="boardId" value="${board.boardId}"/>
		<div class="btn-wrap">
			<a href="/admin/main" class="btn right">목록</a>
			<c:choose>
				<c:when test="${board.blockYn eq 'Y'}">
					<a href="javascript:void(0)" onclick="unblockBoard()" class="btn right">차단 해제</a>
				</c:when>
				<c:otherwise>
					<a href="javascript:void(0)" onclick="blockBoard()" class="btn right">차단</a>
				</c:otherwise>
			</c:choose>
		</div>
	</div>
</body>
<script type="text/javascript" src="/content/board/js/form-event.js"></script>
<script type="text/javascript">
	$( function() {
		accessTypeClick();
	});
</script>