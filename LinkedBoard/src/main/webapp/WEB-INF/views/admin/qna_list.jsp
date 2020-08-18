<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="../common/header.jsp" %>

<body>
	<div class="main-content">
		<%@ include file="../admin/menu.jsp" %>
		
		<h1 class="title mt0"><i class="fa fa-check-square-o mr5"></i>나의 문의 목록</h1>
		
		<table class="gray-table w100p center">
			<colgroup>
				<col>
				<col width="150px">
				<col width="150px">
				<col width="110px">
			</colgroup>
			<thead>
				<tr>
					<th>제목</th>
					<th>답변여부</th>
					<th>작성자</th>
					<th>작성일자</th>
				</tr>
			</thead> 
			<tbody>
				<c:choose>
					<c:when test="${not empty list}">
						<c:forEach var="item" items="${list}">
							<tr>
								<td><a href="/admin/qna/view?qnaId=${item.qnaId}">${item.title}</a></td>
								<td>
									<c:choose>
										<c:when test="${not empty item.replyDt}">
											답변완료<br><p class="gray-txt">(${bo:getFormattedDate(item.replyDt)})</p>
										</c:when>
										<c:otherwise>미답변</c:otherwise>
									</c:choose>
								</td>
								<td>${item.regUser}</td>
								<td>${bo:getFormattedDate(item.regDt)}</td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr><td colspan="3">문의사항이 없습니다.</td></tr>
					</c:otherwise>
				</c:choose>
			</tbody>
		</table>
	</div>
</body>