$( function() {
	urlPatternCheck();
});

/* 현재 url 패턴확인후 알럿창 생성 */
function urlPatternCheck() {
	var url = location.href;
	if( url.indexOf('login?error=1') > -1) {
		Common.alert('아이디 또는 비밀번호가 잘못되었습니다.<br>로그인 5번 실패시 계정은 잠금상태가 됩니다.');
	} else if( url.indexOf('login?error=2') > -1) {
		Common.alert('비밀번호를 5번 틀려 계정이 잠겼습니다.<br>비밀번호 변경후 다시 시도해주세요.');
	} else if( url.indexOf('login?error=3') > -1) {
		Common.alert('해당 계정은 관리자에 의해 차단되었습니다.');
	} else if( url.indexOf('/view?error=1') > -1) {
		Common.alert('게시글 비밀번호가 잘못되었습니다.');
	} else if( url.indexOf('error=99') > -1) {
		Common.alert('해당 페이지 권한이 없습니다.');
	}
}

/* 게시글 상세 페이지로 이동 */
function toCreateForm() {
	$('form#createForm').attr('method', 'POST');
	$('form#createForm').submit();
}

/* 게시글 수정 페이지로 이동 */
function toUpdateForm() {
	var _HTML = '';
	_HTML +='<div class="update-form-modal modal-wrap is-visible">';
	_HTML +='	<div class="modal">';
	_HTML +='		<div><a href="javascript:void(0);" class="modal-close"><i class="fa fa-times" aria-hidden="true"></i></a></div>';
	_HTML +='		<div class="txt-wrap center">';
	_HTML +='			<p>게시글 비밀번호&nbsp;:&nbsp;<input type="text" id="password" name="password"  autocomplete="off"/></p>';
	_HTML +='			<a href="javascript:void(0);" class="btn modal-ok right m0">확인</a>';
	_HTML +='		</div>';
	_HTML +='	</div><br><br>';
	_HTML +='</div>';
	$('body').append(_HTML);
	
	$('body').css('overflow', 'hidden');
	
	$('.update-form-modal .modal-ok').focus();

	$('.update-form-modal .modal-ok').click( function(e) {
		var pass = $('.update-form-modal #password').val().trim();
		
		if( !pass) {
			Common.alert('비밀번호를 입력해주세요.');
		} else {
			$('form#updateForm').find('#contentPassword').val( pass);
			$('form#updateForm').attr('method', 'POST');
			$('form#updateForm').submit();
		}
	});
	
	$('.update-form-modal .modal-close').click( function() {
		$('.update-form-modal').remove();
	});
}

/* 게시글 삭제 */
function deleteBoardContent( formSelector) {
	Common.confirm('삭제하시겠습니까?', function() {
		var _HTML = '';
		_HTML +='<div class="delete-form-modal modal-wrap is-visible">';
		_HTML +='	<div class="modal">';
		_HTML +='		<div><a href="javascript:void(0);" class="modal-close"><i class="fa fa-times" aria-hidden="true"></i></a></div>';
		_HTML +='		<div class="txt-wrap center">';
		_HTML +='			<p>게시글 비밀번호&nbsp;:&nbsp;<input type="text" id="password" name="password"  autocomplete="off"/></p>';
		_HTML +='			<a href="javascript:void(0);" class="btn modal-ok right m0">확인</a>';
		_HTML +='		</div>';
		_HTML +='	</div><br><br>';
		_HTML +='</div>';
		$('body').append(_HTML);
		
		$('body').css('overflow', 'hidden');
		
		$('.delete-form-modal .modal-ok').focus();
	
		$('.delete-form-modal .modal-ok').click( function(e) {
			var pass = $('.delete-form-modal #password').val().trim();
			
			if( !pass) {
				Common.alert('비밀번호를 입력해주세요.');
			} else {
				$('form' + formSelector).find('#contentPassword').val( pass);
				$('form' + formSelector).attr('method', 'POST');
				$('form' + formSelector).submit();
			}
		});
		
		$('.delete-form-modal .modal-close').click( function() {
			$('.delete-form-modal').remove();
		});
	});
}

/* 댓글 삭제 */
function deleteCommContent( formSelector, commentId) {
	$('form' + formSelector).find('#commentId').val(commentId);
	deleteBoardContent(formSelector);
}

/* 댓글 더보기 */
function moreComm( self) {
	var $self = $(self);
	var $tr = $self.parents('tbody').find('tr.folding');
	
	if( $tr.length <= 10) {
		$self.parents('tr').addClass('folding');
	}
	
	$tr.each( function( k, v) {
		if( k < 10) {
			$(v).removeClass('folding');
		}
	});
}