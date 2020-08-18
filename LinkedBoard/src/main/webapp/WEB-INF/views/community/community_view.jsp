<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="bo" 		uri="/WEB-INF/tlds/board.tld" %>
<%@ taglib prefix="page" 	tagdir="/WEB-INF/tags/page" %>
<%@ include file="../common/head.jsp" %>

<body>
	<div class="main-content">
		<h1 class="title"><i class="fa fa-check-square-o mr5"></i>게시글 상세</h1>
		<table class="gray-table w100p">
			<colgroup>
				<col>
				<col width="140px">
			</colgroup>
			<tbody>
				<tr>
					<td>${content.title}</td>
					<td>${bo:getFormattedDate2(content.regDt, 'yyyy.MM.dd HH:mm')}</td>
				</tr>
				<tr>
					<td class="h500 editor" colspan="2">${content.content}</td>
				</tr>
				<c:if test="${not empty content.fileOrgName}">
					<tr>
						<td colspan="2">
							<a href="javascript:void(0)" class="download-file" data-file-path="${content.filePath}" data-file-name="${content.fileOrgName}">
								<i class="fa fa-file-o mr5"></i>
								${content.fileOrgName}
							</a>
						</td>
					</tr>
				</c:if>
			</tbody>
		</table>
		<div class="btn-wrap">
			<a href="javascript:toUpdateForm();" class="btn right">수정</a>
			<a href="javascript:deleteBoardContent('#deleteForm');" class="btn right">삭제</a>
			<a href="/community/${rParam.username}/${rParam.boardId}" class="btn right">목록</a>
		</div>
		
		<form id="updateForm" action="/community/${rParam.username}/${rParam.boardId}/form">
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
			<input type="hidden" id="contentId" name="contentId" value="${param.contentId}"/>
			<input type="hidden" id="contentPassword" name="contentPassword" value=""/>
		</form>
		
		<form id="deleteForm" action="/community/${rParam.username}/${rParam.boardId}/delete">
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
			<input type="hidden" id="contentId" name="contentId" value="${param.contentId}"/>
			<input type="hidden" id="contentPassword" name="contentPassword" value=""/>
		</form>
		
		<c:if test="${rParam.board.commentYn eq 'Y'}">
			<form id="deleteCommentForm" action="/community/${rParam.username}/${rParam.boardId}/delete-comment">
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
				<input type="hidden" id="contentPassword" name="commentPassword" value=""/>
				<input type="hidden" id="contentId" name="contentId" value="${param.contentId}"/>
				<input type="hidden" id="commentId" name="commentId" value=""/>
			</form>
			
			<form id="commentForm" action="/community/${rParam.username}/${rParam.boardId}/insert-comment">
				<input type="hidden" id="contentId" name="contentId" value="${param.contentId}"/>
				<table class="gray-table w100p mt25">
					<colgroup>
						<col>
						<col width="200px">
					</colgroup>
					<thead>
						<tr><th colspan="2">댓글(${fn:length(commentList)})</th></tr>
					</thead>
					<tbody>
						<c:choose>
							<c:when test="${not empty commentList}">
								<c:forEach items="${commentList}" var="item" varStatus="idx">
									<tr class="${idx.count > 10 ? 'folding' : ''}">
										<td>
											<pre class="${item.adminYn eq 'Y' ? 'admin' : ''}">${item.comment}</pre>
										</td>
										<td class="al-center">
											${bo:getFormattedDate2(item.regDt, 'yyyy.MM.dd HH:mm')}
											<c:if test="${item.adminYn eq 'N'}">
												<a href="javascript:deleteCommContent('#deleteCommentForm', '${item.commentId}')" class="btn small">삭제</a>
											</c:if>
										</td>
									</tr>
									<c:if test="${fn:length(commentList) == idx.count and idx.count > 10}">
										<tr class="bg-l-brown"><td colspan="2" class="al-center"><a href="javascript:void(0)" onclick="moreComm(this);">더보기<i class="fa fa-arrow-down ml5"></i></a></td></tr>
									</c:if>					
								</c:forEach>
							</c:when>
							<c:otherwise>
								<tr>
									<td colspan="3" class="al-center">등록된 댓글이 없습니다.</td>
								</tr>
							</c:otherwise>
						</c:choose>
						<tr>
							<td><textarea rows="2" id="comment" name="comment" data-limit-rows="true" class="w100p" _required="true" title="내용"></textarea></td>
							<td>
								<input type="text" class="w130" id="commentPassword" name="commentPassword" placeholder="삭제 비밀번호" _required="true" title="비밀번호"/>
								<a href="javascript:save('#commentForm')" class="btn small">저장</a>
							</td>
						</tr>
					</tbody>
				</table>
			</form>
		</c:if>
	</div>
</body>

<script type="text/javascript" src="/content/board/js/form-event.js"></script>
<script type="text/javascript">
	$( function() {
		$('textarea[data-limit-rows=true]').bind('paste', function (e) {
			var clipboardData = e.originalEvent.clipboardData.getData('text');
			var matchedDate = clipboardData.match(/\n/g);
			// 줄바꿈 문자가 있는경우 붙여넣기 불가
			if( matchedDate) return false;
			return limitTextarea($(this), e);
		});
	});
</script>