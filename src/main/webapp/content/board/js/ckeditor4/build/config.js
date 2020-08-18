CKEDITOR.editorConfig = function( config ) {
	config.toolbar = [
		{ name: 'document', items: [ 'ExportPdf', 'Print'] },
		{ name: 'clipboard', items: [ 'Undo', 'Redo' ] },
		{ name: 'basicstyles', items: [ 'Bold', 'Italic', 'Underline', 'Strike'] },
		{ name: 'paragraph', items: [ 'NumberedList', 'BulletedList', '-', 'Outdent', 'Indent', '-', 'JustifyLeft', 'JustifyCenter', 'JustifyRight', 'JustifyBlock'] },
		{ name: 'styles', items: [ 'Styles', 'Font', 'FontSize' ] }, '/',
		{ name: 'colors', items: [ 'TextColor', 'BGColor' ] },
		{ name: 'links', items: [ 'Link'] },
		{ name: 'insert', items: [ 'Image', 'Table', 'HorizontalRule', 'Youtube' ] },
	];
};