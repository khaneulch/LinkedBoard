<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="../common/header.jsp" %>

<body>
	<div class="main-content">
		<h1 class="title"><i class="fa fa-check-square-o mr5"></i>문의${empty qna ? ' 생성' : ' 수정'}</h1>
		
		<form action="/board/qna/insert" id="qnaForm">
			<input type="hidden" id="qnaId" name="qnaId" value="${qna.qnaId}"/>
			<table class="gray-table w100p">
				<colgroup>
					<col width="250px">
					<col>
				</colgroup>
				<tbody>
					<tr>
						<th>제목</th>
						<td><input type="text" class="w100p" id="title" name="title" value="${qna.title}" _required="true" title="문의 제목"/></td>
					</tr>
					<tr>
						<th>내용</th>
						<td>
							<textarea rows="20" class="w100p" id="content" name="content" _required="true" title="문의 내용">${qna.content}</textarea>
						</td>
					</tr>
				</tbody>
			</table>
			<div class="btn-wrap">
				<a href="/board/qna/list" class="btn right">목록</a>
				<a href="javascript:save('#qnaForm')" class="btn right">저장</a>
			</div>
		</form>
	</div>
</body>
<script type="text/javascript" src="/content/board/js/form-event.js"></script>