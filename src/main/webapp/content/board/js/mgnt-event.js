/* 게시글 삭제 */
function deleteContent() {
	if( $('[name=contentId]:checked').length == 0) {
		Common.alert('게시글을 선택해주세요.');
		return false;
	}
	
	Common.alert('게시글을 삭제하시겠습니까?', function() {
		var param = {};
		param.boardId = $('#boardId').val();
		param.contentIds = $('[name=contentId]:checked').map(function(k, v) {return v.value}).get().join(',');
		if( $('#username').val()) param.username = $('#username').val();
		
		var resp = Common.getAjaxCall('/board/mgnt/delete', param);
		if( resp && resp.isSuccess) {
			location.reload();
		}
	});
}

/* 댓글 삭제 */
function deleteCommAll() {
	if( $('[name=commentId]:checked').length == 0) {
		Common.alert('댓글을 선택해주세요.');
		return false;
	}
	
	Common.alert('댓글을 삭제하시겠습니까?', function() {
		var param = {};
		param.boardId = $('#boardId').val();
		param.commentIds = $('[name=commentId]:checked').map(function(k, v) {return v.value}).get().join(',');
		if( $('#username').val()) param.username = $('#username').val();
		
		var resp = Common.getAjaxCall('/board/mgnt/delete-comment', param);
		if( resp && resp.isSuccess) {
			location.reload();
		}
	});
}

/* 접근설정(accessType) 클릭 이벤트 */
function accessTypeClick() {
	$('[name=accessType]').click( function() {
		var v = $(this).val();
		if( v === 'P') {
			$('#accessPass').removeClass('folding');
		} else {
			$('#accessPass').addClass('folding');
		}
	});
}

/* 게시판 차단 설정 */
function blockBoard() {
	var _HTML = '';
	_HTML +='<div class="block-form-modal modal-wrap is-visible">';
	_HTML +='	<div class="modal">';
	_HTML +='		<div><a href="javascript:void(0);" class="modal-close"><i class="fa fa-times" aria-hidden="true"></i></a></div>';
	_HTML +='		<div class="txt-wrap center">';
	_HTML +='			<p>해당 게시판을 차단하시겠습니까?</p>';
	_HTML +='			<p><input type="text" class="w100p" id="blockReason" name="blockReason" placeholder="차단 사유를 입력해주세요." autocomplete="off"/></p>';
	_HTML +='			<div class="btn-wrap">';
	_HTML +='				<a href="javascript:void(0);" class="modal-yes btn left">예</a>';
	_HTML +='				<a href="javascript:void(0);" class="modal-no btn right">아니오</a>';
	_HTML +='			</div>';
	_HTML +='		</div>';
	_HTML +='	</div><br><br>';
	_HTML +='</div>';
	$('body').append(_HTML);
	
	$('body').css('overflow', 'hidden');
	
	$('.block-form-modal #blockReason').focus();

	$('.block-form-modal .modal-yes').click( function(e) {
		var blockReason = $('.block-form-modal #blockReason').val().trim();
		
		if( !blockReason) {
			Common.alert('차단 사유를 입력해주세요.');
		} else {
			var param = {
				blockReason : blockReason
				, boardId : $('#boardId').val()
				, username : $('#username').val()
				, blockYn : 'Y'
			};
			
			var resp = Common.getAjaxCall('/admin/block', param);
			if( resp && resp.isSuccess) {
				$('.block-form-modal').remove();
				location.reload();
			}
		}
	});
	
	$('.block-form-modal .modal-no').click( function() {
		$('.block-form-modal').remove();
	});
}


/* 게시판 차단 해제 */
function unblockBoard() {
	Common.confirm('해당 게시판의 차단을 해제하시겠습니까?', function() {
			var param = {
				blockReason : ''
				, boardId : $('#boardId').val()
				, username : $('#username').val()
				, blockYn : 'N'
			};
			
			var resp = Common.getAjaxCall('/admin/block', param);
			if( resp && resp.isSuccess) {
				location.reload();
			}
	});
}

/* 게시판 삭제 */
function deleteBoard( boardId) {
	Common.confirm('게시판을 삭제하시겠습니까?', function() {
		location.href = '/board/main/delete?boardId=' + boardId;
	});
}

/* 사용자 차단 */
function blockUser() {
	if( $('[name=username]:checked').length == 0) {
		Common.alert('사용자를 선택해주세요.');
		return false;
	}
	Common.confirm('사용자를 차단하시겠습니까?', function() {
		var param = {};
		param.usernames = $('[name=username]:checked').map(function(k, v) {return v.value}).get().join(',');
		var resp = Common.getAjaxCall('/admin/user/block', param);
		console.log(resp);
		if( resp && resp.isSuccess) {
			location.reload();
		}
	});
}