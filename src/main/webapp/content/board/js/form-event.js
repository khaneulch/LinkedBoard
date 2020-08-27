/* form 저장 액션 */
function save( formSelector) {
	Common.confirm('저장하시겠습니까?', function() {
		if( !formSelector) formSelector = 'form';
			if( Common.editor.options.isInit == true) {
				$( formSelector).find('.eiditor-data').val( CKEDITOR.instances["editor-box"].getData());
			}
		if( Common.formValidator( formSelector)) {
			$( formSelector).submit();
		}
	});
}

/* 파일 추가 */
function fileAdd() {
	$('#sliderImg').change( function() {
		var $file = $(this);
		var $ul = $(this).parents('ul');
		
		var $clone = $file.clone();
		var $li = $('<li></li>');
		$li.append($clone);
		$li.append('<i class="fa fa-times file-delete" aria-hidden="true"></i>');
		  
		$file.before($li);
		$clone.attr('name', 'sliderImg');
		$file.val('');
	});
	
	$(document).on('click', '.file-delete', function() {
		var fileLength = $('.file-delete').length;
		var $delete = $(this);
		
		if( fileLength == 1) {
			Common.alert('파일은 하나이상 필수입니다.');
		} else {
			$delete.parents('li').remove();
		}
	});
	
	$('.li-delete').click( function() {
		var $delete = $(this);
		var deleteFiles = $('#deleteFiles').val();
		
		$('#deleteFiles').val(deleteFiles + ',' + $delete.parents('li').attr('data-file-name'));
		$delete.parents('li').remove();
	});
}

/* ID 중복체크 */
function checkDuplicateId( selector) {
	if( !selector) selector = '#username';
	var value = $( selector).val();
	if( value.trim()) {
		var resp = Common.getAjaxCall('/user/duplicate', {username : value});
		if( resp && resp.isSuccess) {
			Common.alert('사용가능한 아이디입니다.');
			$( selector).siblings('.warning').addClass('folding');
		} else {
			Common.alert('이미 사용중인 아이디입니다.', function() {
				$( selector).val('');
			});
			$( selector).siblings('.warning').removeClass('folding');
		}
	} else {
		Common.alert('아이디를 입력해주세요.');
	}
}
