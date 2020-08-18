<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="../common/head.jsp" %>

<body>
	<div class="main-content">
		<h1 class="title"><i class="fa fa-check-square-o mr5"></i>게시글 ${not empty rParam.contentId ? '수정' : '생성'}</h1>
		<form action="/community/${rParam.username}/${rParam.boardId}/insert" id="commBoardForm">
			<table class="gray-table w100p">
				<colgroup>
					<col width="150px">
					<col>
				</colgroup>
				<tbody>
					<tr>
						<th>제목</th>
						<td>
							<input type="text" class="w100p" id="title" name="title" _required="true" title="제목" value="${content.title}"/>
						</td>
					</tr>
					<tr>
						<th>내용</th>
						<td>
							<input type="hidden" class="eiditor-data" id="content" name="content" _required="true" title="내용" value=""/>
							<div id="editor-box">${content.content}</div>
						</td>
					</tr>
					<c:if test="${empty rParam.contentId}">
						<tr>
							<th>게시글 비밀번호</th>
							<td>
								<input type="text" id="contentPassword" name="contentPassword" _required="true" title="게시글 비밀번호" autocomplete="off"/>
								<p class="warning">게시물 수정/삭제시 이용됩니다.</p>
							</td>
						</tr>
					</c:if>
				</tbody>
			</table>
			<c:if test="${not empty rParam.contentId}">
				<input type="hidden" id="contentId" name="contentId" value="${rParam.contentId}"/>
				<input type="hidden" id="contentKey" name="contentKey" value="${content.contentKey}"/>
			</c:if>
			<div class="btn-wrap">
				<a href="javascript:save('#commBoardForm')" class="btn right">저장</a>
				<a href="javascript:history.back(-1);" class="btn right">취소</a>
			</div>
		</form>
	</div>
</body>
<script type="text/javascript" src="/content/board/js/form-event.js"></script>
<script type="text/javascript">
	$( function() {
		Common.editor.options.username = '${rParam.username}';
		Common.editor.options.boardId = '${rParam.boardId}';
		Common.editor.init();
	});
</script>