/* 비밀번호 찾기 */
function findPassword( formSelector) {
	var dialogHTML = $('#pop-password-change').html();
	$('body').append(dialogHTML);
	
	var $pop = $('.pop-password-change');
	
	$pop.find('.modal-ok').on('click', function() {
		var username = $pop.find('#username').val();
		if( username.trim()) {
			$pop.remove();
			var resp = Common.getAjaxCall('/user/changePwd', { username : username});
			
			if( resp && resp.isSuccess) {
				Common.alert('가입시 입력한 이메일로 비밀번호 변경 링크가 전송되었습니다.');
			} else {
				Common.alert('입력하신 아이디는 없는 아이디입니다.');
			}
		} else {
			Common.alert('아이디를 입력해주세요.');
		}
	});
	
	$pop.find('.modal-close').on('click', function() {
		$pop.remove();
	});
}

/* 회원가입 */
function registerPop() {
	var dialogHTML = $('#pop-register').html();
	$('body').append(dialogHTML);
	
	var $pop = $('.pop-register');
	
	$pop.find('[type=password]').change( function() {
		var pwd = $pop.find('#password').val();
		var pwdChk = $pop.find('#password-chk').val();
		if( pwd && pwd !== pwdChk) {
			$pop.find('#password-chk').siblings('.warning').removeClass('folding');
		} else {
			$pop.find('#password-chk').siblings('.warning').addClass('folding');
		}
	});
	
	$pop.find('[name=username]').change( function() {
		$(this).siblings('.warning').removeClass('folding');
	});

	
	$pop.find('.modal-close').on('click', function() {
		$pop.remove();
	});
}

/* 비밀번호 변경 저장 */
function pwdSave() {
	var pwd = $('#password').val();
	var pwdChk = $('#password-chk').val();
	
	if( !pwd.trim()) {
		Common.alert("비밀번호를 입력하세요.");
	} else if( pwd != pwdChk) {
		Common.alert("비밀번호를 잘못 입력했습니다.");
	} else {
		$('#passwordForm').submit();
	}
}
