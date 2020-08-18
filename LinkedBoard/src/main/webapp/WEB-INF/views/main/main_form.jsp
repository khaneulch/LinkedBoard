<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="../common/header.jsp" %>

<body>
	<div class="main-content">
		<h1 class="title"><i class="fa fa-check-square-o mr5"></i>게시판 ${empty board ? '생성' : '수정'}</h1>
		
		<c:if test="${board.blockYn eq 'Y'}">
			<p class="warning mb5"><i class="fa fa-exclamation-circle mr5"></i>해당 게시판은 관리자에 의해 차단되었습니다.</p>
			<p class="warning mb5"><i class="fa fa-exclamation-circle mr5"></i>차단 사유 : ${board.blockReason}</p>
		</c:if>
		
		<form action="/board/main/insert" id="mainBoardForm">
			<input type="hidden" id="boardId" name="boardId" value="${board.boardId}"/>
			<table class="gray-table w100p">
				<colgroup>
					<col width="250px">
					<col>
				</colgroup>
				<tbody>
					<tr>
						<th>게시판 이름</th>
						<td><input type="text" class="w100p" id="boardName" name="boardName" value="${board.boardName}" _required="true" title="게시판 이름"/></td>
					</tr>
					<tr>
						<th>접근 설정</th>
						<td>
							<input type="radio" id="accessType0" name="accessType" value="X" ${empty board or board.accessType eq 'X' ? 'checked' : ''}/>
							<label for="accessType0">없음</label>
							<input type="radio" id="accessType1" name="accessType" value="P" ${board.accessType eq 'P' ? 'checked' : ''}/>
							<label for="accessType1">비밀번호</label>
							<input type="text" class="${board.accessType ne 'P' ? 'folding' : ''}" id="accessPass" name="accessPass" value="${board.accessPass}" placeholder="비밀번호를 입력하세요."  _required="true" title="비밀번호" />
							<input type="radio" id="accessType2" name="accessType" value="O" ${board.accessType eq 'O' ? 'checked' : ''}/>
							<label for="accessType2">Google OTP</label>
							<c:if test="${board.accessType eq 'O'}">
								<p class="gray-txt">( <a href="javascript:void(0)" onclick="Common.copyToClip(this);">${board.accessPass}</a> )</p>
							</c:if>
							<c:if test="${not empty board}">
								<input type="hidden" id="accessType_old" name="accessType_old" value="${board.accessType}"/>
							</c:if>
						</td>
					</tr>
					<tr>
						<th>댓글 사용</th>
						<td>
							<input type="radio" id="commentYn1" name="commentYn" value="N" ${empty board.commentYn or board.commentYn eq 'N' ? 'checked' : ''}/>
							<label for="commentYn1">미사용</label>
							<input type="radio" id="commentYn2" name="commentYn" value="Y" ${board.commentYn eq 'Y' ? 'checked' : ''}/>
							<label for="commentYn2">사용</label>
						</td>
					</tr>
					<tr>
						<th>파일 용량 제한</th>
						<td>
							<input type="radio" id="fileLimit1" name="fileLimit" value="${5*1024*1024}" ${empty board.fileLimit or board.fileLimit eq 5*1024*1024 ? 'checked' : ''}/>
							<label for="fileLimit1">5MB</label>
							<input type="radio" id="fileLimit2" name="fileLimit" value="${100*1024*1024}" ${board.fileLimit eq 100*1024*1024 ? 'checked' : ''}/>
							<label for="fileLimit2">100MB</label>
						</td>
					</tr>
					<tr>
						<th>게시판 숨김여부</th>
						<td>
							<input type="radio" id="dpYn1" name="dpYn" value="Y" ${empty board.dpYn or board.dpYn eq 'Y' ? 'checked' : ''}/>
							<label for="dpYn1">보이기</label>
							<input type="radio" id="dpYn2" name="dpYn" value="N" ${board.dpYn eq 'N' ? 'checked' : ''}/>
							<label for="dpYn2">숨기기</label>
						</td>
					</tr>
				</tbody>
			</table>
			<div class="btn-wrap">
				<a href="/board/main" class="btn right">목록</a>
				<a href="javascript:save('#mainBoardForm')" class="btn right">저장</a>
				<a href="javascript:deleteBoard('${board.boardId}')" class="btn right">삭제</a>
			</div>
		</form>
	</div>
</body>
<script type="text/javascript" src="/content/board/js/form-event.js"></script>
<script type="text/javascript">
	$( function() {
		accessTypeClick();
	});
</script>