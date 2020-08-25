const Common = {};

/* 공통 alert */
Common.alert = function(message,callback) {
	var $div = '';
	$div +='<div id="myModal" class="modal-wrap is-visible">';
	$div +='	<div class="modal">';
	$div +='		<div><a href="javascript:void(0);" class="modal-close"><i class="fa fa-times" aria-hidden="true"></i></a></div>';
	$div +='		<div class="txt-wrap center">';
	$div +=`			<p>${message}</p>`;
	$div +='			<a href="javascript:void(0);" class="btn modal-ok right m0">확인</a>';
	$div +='		</div>';
	$div +='	</div><br><br>';
	$div +='</div>';
	$('body').append($div);
	
	$('body').css('overflow', 'hidden');
	
	$('#myModal .modal-ok').focus();

	$('#myModal .modal-ok').on('click', function(e) {
		$('body').off('scroll touchmove mousewheel');
		$('body').off('keypress');
		$('body').css('overflow', 'auto');
		$('#myModal').remove();
		if ($.isFunction(callback)) {
			callback();
		}
	});
	
	$('#myModal .modal-close').on('click', function() {
		$('#myModal').remove();
	});
}

/* 공통 confirm */
Common.confirm = function(message, confirmFunction, cancelFunction) {
	var $div = '';
	$div +='<div id="myModal" class="modal-wrap is-visible">';
	$div +='	<div class="modal">';
	$div +='		<div><a href="javascript:void(0);" class="modal-close"><i class="fa fa-times" aria-hidden="true"></i></a></div>';
	$div +='		<div class="txt-wrap center">';
	$div +=`			<p>${message}</p>`;
	$div +='			<div class="btn-wrap">';
	$div +='				<a href="javascript:void(0);" class="modal-yes btn left">예</a>';
	$div +='				<a href="javascript:void(0);" class="modal-no btn right">아니오</a>';
	$div +='			</div>';
	$div +='		</div>';
	$div +='	</div><br><br>';
	$div +='</div>';
	$('body').append($div);

	$('body').css('overflow', 'hidden');
	
	$('#myModal .modal-yes').focus();

	$('#myModal .modal-yes').on('click', function(e) {
		$('body').off('scroll touchmove mousewheel');
		$('body').off('keypress');
		$('body').css('overflow', 'auto');
		$('#myModal').remove();
		if ($.isFunction(confirmFunction)) {
			confirmFunction();
		}
	});

	$('#myModal .modal-no').on('click', function(e) {
		$('body').off('scroll touchmove mousewheel');
		$('body').off('keypress');
		$('body').css('overflow', 'auto');
		$('#myModal').remove();
		if ($.isFunction(cancelFunction)) {
			cancelFunction();
		}
	});
	
	$('#myModal .modal-close').on('click', function() {
		$('#myModal').remove();
	});
}

/* 공통 ajax call */
Common.getAjaxCall = function(url, params, successCallback, async) {
	var retData;
	if( typeof params === 'undefined') {
		params = {};
	}
	if( typeof async === 'undefined') {
		async = false;
	}
	
	params._csrf = $('meta[name=_csrf]').attr('content');
	
	var ajaxOptions = {
		type: 'post',
		async: async,
		url: url,
		data: params,
		success:function( data) {
			if ( $.isFunction(successCallback)) {
				successCallback();
			}
			else {
				retData = data;
			}
			return retData;
		},
		error:function(xhr,status,error) {
			if ( xhr.status == 999 ) {
				Mms.alert(xhr.responseText);
			}
		}
	};
	
	if( params instanceof FormData) {
		ajaxOptions.processData = false;
		ajaxOptions.contentType = false;
	}
	$.ajax(ajaxOptions);
		
	return retData;
}

/* form sumbit시 필수값 체크 */
Common.formValidator = function( selector) {
	if( !selector) selector = 'form';
	var flag = true;
	$(selector).find('input, textarea, input:checkbox, input:radio').each( function() {
		var $self = $(this);
		if( $self.hasClass('folding') == false) {
			if( $self.attr('_required') && $self.attr('_required') == 'true') {
				if( $self.is('input, textarea')) {
					var v = $self.val().trim();
					if( v == null || v == '') {
						var title = $self.attr('title');
						if( !title) title = '해당';
						Common.alert(`${title} 값은 필수입니다.`, function() {
							$self.focus();
						});
						flag = false;
						return false;
					}
				} else if( $self.is('checkbox') || $self.is('radio')) {
					var v = $self.val();
				}
			}
		}
		if( $self.is('input:text, input.eiditor-data, textarea')) {
			Word.filter( $self);
		}
	});
	
	if( flag === true) {
		var $warn = $(selector).find('.warning:not(.folding)');
		if( $warn.length > 0) {
			if( $warn.siblings().attr('id') === 'password-chk') {
				Common.alert('비밀번호가 올바르지 않습니다.', function() {
					$('#password').focus();
				});
				flag = false;
				return false;
			} else if( $warn.siblings().attr('id') === 'username') {
				Common.alert('아이디 중복체크가 되지않았습니다.', function() {
					$('#username').focus();
				});
				flag = false;
				return false;
			}
		}
	}
	return flag;
}

/* 페이지 이동 액션 */
Common.goPage = function( page) {
	$('#listForm #currentPage').val(page);
	$('#listForm').submit();
}

/* ckeditor 관련 js */
Common.editor = {
	options : {
		isInit : false
	}, 
	init : function( selector) {
		CKEDITOR.replace( 'editor-box', {
		    filebrowserUploadUrl: '/file/ckImageUpload'
		    , width:'100%'
		 	, height: 500
		}).on( 'fileUploadRequest', function( evt ) {
		    var fileLoader = evt.data.fileLoader,
	        formData = new FormData(),
	        xhr = fileLoader.xhr;

		    xhr.open( 'POST', fileLoader.uploadUrl, true );
		    formData.append( 'upload', fileLoader.file, fileLoader.fileName );
		    formData.append( '_csrf', $('meta[name=_csrf]').attr('content') );
		    if( Common.editor.options.username) {
		    	formData.append( 'username', Common.editor.options.username);
		    }
		    formData.append( 'boardId', Common.editor.options.boardId);
		    fileLoader.xhr.send( formData );
	
		    evt.stop();
		}, null, null, 4 );
		
		CKEDITOR.on("instanceReady", function(event) {Common.editor.options.isInit = true;});
	}
}

/* 윈도우 팝업 */
Common.pop = function( url) {
	var specs = 'menubar=no,status=no,location=no,left=1,top=1';
	window.open(url, '_blank', specs);
}

/* 클립보드에 복사 */
Common.copyToClip = function( self) {
	var txt = $(self).html();
	
	var tempTextArea = document.createElement("textarea");
	document.body.appendChild(tempTextArea);
	tempTextArea.value = txt;
	tempTextArea.select();
	
	document.execCommand('copy');
	document.body.removeChild(tempTextArea);
}