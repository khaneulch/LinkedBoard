/* 모든 문자열 치환 */
String.prototype.replaceAll = function(org, dest) {
    return this.split(org).join(dest);
}

/* 메뉴 클릭시 스크롤 */
$(document).on('click', 'li.menu-li > a', function() {
	var targetId = $(this).attr('id');
	$('body').animate({
		scrollTop: $('div#' + targetId).offset().top - 69
	}, 800);
});

/* _number 클래스인 경우 숫자만 입력되도록 */
$(document).on('keyup ', '._number', function(e) {
    var obj = $(this);

    if (!(e.which && (e.which > 47 && e.which < 58) || (e.which > 95 && e.which < 106) || e.which ==8 || e.which == 13 || e.which == 37 || e.which == 39 || e.which == 46 || e.which ==9|| e.which ==0 || (e.ctrlKey && e.which ==86) ) ) {
        e.preventDefault();
    }

    var pattern = /^[0-9]+/g;
    var matchValue = obj.val().match(pattern);

    if (!pattern.test(obj.val())) {
        obj.val('');
    }

    if (obj.val() != matchValue) {
        obj.val(matchValue);
    }
});

/* _number_eng 클래스인 경우 숫자/영어만 입력되도록 */
$(document).on('keyup ', '._number_eng', function(e) {
	var obj = $(this);
	
	if (!(e.which && (e.which > 47 && e.which < 58) || (e.which > 95 && e.which < 106) || e.which ==8 || e.which == 13 || e.which == 37 || e.which == 39 || e.which == 46 || e.which ==9|| e.which ==0 || (e.ctrlKey && e.which ==86) ) ) {
		e.preventDefault();
	}
	
	var pattern = /^[A-Za-z0-9+]*$/;
	var matchValue = obj.val().match(pattern);
	
	if (!pattern.test(obj.val())) {
		obj.val('');
	}
	
	if (obj.val() != matchValue) {
		obj.val(matchValue);
	}
});

/* 숫자 + px 패턴만 입력되도록 */
$(document).on('change ', '._px', function(e) {
    var $el = $(this);
    var pattern = /^[0-9]+px$/g;

	if( !$el.val().match(pattern)) {
		Hms.alert('너비값을 정확히 입력해주세요.(ex:1200px)');
		$el.val('');
	}
});

/* rgb패턴 체크 */
$(document).on('change ', '._rgb', function(e) {
	var colorPattern = /^\#([a-fA-F0-9]{6}|[a-fA-F0-9]{3})$/;
	var $el = $(this);
	
	if( !$el.val().match(colorPattern)) {
		Hms.alert('RGB 컬러코드를 정확히 입력해주세요.');
		$el.val('');
	}
});

/* 비밀번호 체크 */
$(document).on('change ', '[type=password]', function(e) {
	var pwd = $('#password').val();
	var pwdChk = $('#password-chk').val();
	if( pwd && pwd !== pwdChk) {
		$('#password-chk').siblings('.warning').removeClass('folding');
	} else {
		$('#password-chk').siblings('.warning').addClass('folding');
	}
});

/* 아이디 중복 */
$(document).on('change ', '#username', function(e) {
	$('#username').siblings('.warning').removeClass('folding');
});

/* 전체선택 체크박스 */
$(document).on('click ', '.check-all', function(e) {
	var $self = $(this);
	var checked = $(this).is(':checked');
	if( checked) {
		$self.parents('div').find('input:checkbox').prop('checked', true);
	} else {
		$self.parents('div').find('input:checkbox').prop('checked', false);
	}
});

/* textarea row 제한 */
$(document).on('keydown', 'textarea[data-limit-rows=true]', function (e) {
	return limitTextarea($(this), e);
});

/* textarea row 제한 이벤트 */
function limitTextarea( $self, e) {
	var text = $self.val();
	var numberOfLines = text.match(/\n/g) ? text.match(/\n/g).length + 1 : 1;
	var maxRows = Number( $self.attr('rows'));
	if ((e.type === 'paste' || e.which === 13) && numberOfLines >= maxRows ) {
		return false;
	} 
	return true;
}

/* 정렬순서 변경 */
$(document).on('click', 'ul.order-tab li a', function() {
	var $self = $(this);
	$('#listForm').find('#orderBy').val($self.attr('data-value'));
	Common.goPage('1');
});

/* 파일 다운로드 */
$(document).on('click', 'a.download-file', function() {
	var filePath = $(this).attr('data-file-path');
	var fileName = $(this).attr('data-file-name');
	
	location.href = '/file/download?filePath=' + filePath + '&fileName=' + fileName;
});

/* 파일 용량제한 */
$(document).on('change', 'input[type=file]', function( e) {
	// 파일 사이즈 제한
	var maxSize = $(e.target).attr('data-max');
	var fileEl = this; 
	if( maxSize) {
		if( fileEl.files[0].size > maxSize) {
			Common.alert('첨부가능 용량을 확인해주세요.');
			$(fileEl).val('');
		}
	}
	
	// 실행파일 제한
	if( fileEl.files[0]) {
		console.log(fileEl.files[0].type);
		if( fileEl.files[0].type === 'application/x-msdownload') {
			Common.alert('실행파일은 첨부할 수 없습니다.');
			$(fileEl).val('');
		}
	}
});