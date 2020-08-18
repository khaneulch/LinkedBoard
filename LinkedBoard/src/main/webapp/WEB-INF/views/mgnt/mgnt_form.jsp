<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="../common/header.jsp" %>

<body> 
	<div class="main-content">
		<h1 class="title"><i class="fa fa-check-square-o mr5"></i>게시글 ${not empty rParam.contentId ? '수정' : '생성'}</h1>
		<form action="/board/mgnt/insert" id="mgntBoardForm" method="POST" enctype="multipart/form-data">
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
			<table class="gray-table w100p">
				<colgroup>
					<col width="150px">
					<col>
				</colgroup>
				<tbody>
					<tr>
						<th>제목</th>
						<td>
							<input type="checkbox" id="noticeYn" name="noticeYn" value="Y" ${content.noticeYn eq 'Y' ? 'checked' : ''}/>
							<label for="noticeYn">공지사항</label>
							<input type="text" class="w100p-95" id="title" name="title" _required="true" title="제목" value="${content.title}"/>
						</td>
					</tr>
					<tr>
						<th>내용</th>
						<td>
							<input type="hidden" class="eiditor-data" id="content" name="content" _required="true" title="내용" value=""/>
							<div id="editor-box">${content.content}</div>
						</td>
					</tr>
					<tr>
						<th>첨부파일<br><p class="gray-txt">(최대 : ${bo:toUnit(board.fileLimit)})</p></th>
						<td>
							<input type="file" id="mgntFile" name="mgntFile" data-max="${board.fileLimit}"/>
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
			<input type="hidden" id="boardId" name="boardId" value="${rParam.boardId}"/>
			<c:if test="${not empty rParam.contentId}">
				<input type="hidden" id="contentId" name="contentId" value="${rParam.contentId}"/>
				<input type="hidden" id="contentKey" name="contentKey" value="${content.contentKey}"/>
			</c:if>
			<div class="btn-wrap">
				<a href="javascript:save('#mgntBoardForm')" class="btn right">저장</a>
				<a href="javascript:history.back(-1);" class="btn right">취소</a>
			</div>
		</form>
	</div>
</body>
<script type="text/javascript" src="/content/board/js/form-event.js"></script>
<script type="text/javascript">
	$( function() {
		Common.editor.options.boardId = '${rParam.boardId}';
		Common.editor.init();
	});
</script>