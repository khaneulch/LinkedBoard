<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ taglib prefix="c" 		uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" 		uri="http://java.sun.com/jsp/jstl/functions" %> 
<head>
	<!-- Global site tag (gtag.js) - Google Analytics -->
	<script async src="https://www.googletagmanager.com/gtag/js?id=UA-175164061-1"></script>
	<script>
	  window.dataLayer = window.dataLayer || [];
	  function gtag(){dataLayer.push(arguments);}
	  gtag('js', new Date());
	
	  gtag('config', 'UA-175164061-1');
	</script>
	
	<meta charset="utf-8" />
	<meta http-equiv="Content-Script-Type" content="text/javascript" />
	<meta http-equiv="Content-Style-Type" content="text/css" />
	<meta name="_csrf" content="${_csrf.token}"/>
	<meta name="_csrf_header" content="${_csrf.headerName}"/>
	
	<title>LINKED BOARD</title>
</head>

<!-- css -->
<link rel="stylesheet" type="text/css" href="/content/board/css/font-awesome-4.7.0/font-awesome.min.css">
<link rel="stylesheet" type="text/css" href="/content/board/css/slick/slick.css">
<link rel="stylesheet" type="text/css" href="/content/board/css/slick/slick-theme.css">
<link rel="stylesheet" type="text/css" href="/content/board/css/common.css">

<!-- jquery js -->
<script type="text/javascript" src="/content/board/js/jquery/jquery-3.5.1.min.js"></script>

<!-- ckeditor4 -->
<script type="text/javascript" src="/content/board/js/ckeditor4/build/ckeditor.js"></script>

<!-- slick -->
<script type="text/javascript" src="/content/board/js/slick/slick.min.js"></script>

<!-- custom -->
<script type="text/javascript" src="/content/board/js/common.js"></script>
<script type="text/javascript" src="/content/board/js/dom-event.js"></script>
<script type="text/javascript" src="/content/board/js/community-event.js"></script>
<script type="text/javascript" src="/content/board/js/word-checker.js"></script>