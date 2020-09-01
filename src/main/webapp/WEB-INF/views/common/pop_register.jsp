<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>

<div class="modal-wrap pop-register is-visible">
	<div class="modal">
		<div><a href="javascript:void(0);" class="modal-close"><i class="fa fa-times" aria-hidden="true"></i></a></div>
		<h1 class="title"><i class="fa fa-check-square-o mr5"></i>회원가입</h1>
		<form id="registerForm" action="/user/registerProc" method="post">
			<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
			<table class="gray-table table-input-fit">
				<colgroup>
					<col width="200px">
					<col width="300px">
				</colgroup>
				<tr>
					<th>아이디<span class="red">*</span></th>
					<td>
						<input type="text" class="_number_eng not-fit" id="username" name="username" _required="true" title="아이디"/>
						<a href="javascript:checkDuplicateId('#registerForm #username');" class="btn">중복체크</a>
						<p class="warning folding">아이디 중복체크 하세요.</p>
					</td>
				</tr>
				<tr>
					<th>비밀번호<span class="red">*</span></th>
					<td><input type="password" id="password" name="password" _required="true" title="비밀번호" autocomplete="off" /></td>
				</tr>
				<tr>
					<th>비밀번호확인</th>
					<td>
						<input type="password" id="password-chk" autocomplete="off"/>
						<p class="warning folding">비밀번호를 정확히 입력하세요.</p>
					</td>
				</tr>
				<tr>
					<th>이름<span class="red">*</span></th>
					<td><input type="text" id="name" name="name" _required="true" title="이름" /></td>
				</tr>
				<tr>
					<th>이메일주소<span class="red">*</span></th>
					<td><input type="text" id="email" name="email" _required="true" title="이메일 주소"  /></td>
				</tr>
				<tr>
					<th>핸드폰번호</th>
					<td><input type="text" id="mobile" name="mobile" /></td>
				</tr>
			</table>
			<div class="btn-wrap mt10">
				<a href="javascript:save('#registerForm');" class="btn ml450">저장</a>
			</div>
		</form>
	</div>
</div>

