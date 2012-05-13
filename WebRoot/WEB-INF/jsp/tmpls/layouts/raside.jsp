<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<!DOCTYPE HTML>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title><tiles:insertAttribute name="title" ignore="true" /></title>
	<meta http-equiv="keywords"
		content="<tiles:insertAttribute name="keywords" ignore="true" />">
	<meta http-equiv="description"
		content="<tiles:insertAttribute name="description" ignore="true" />">
	<link rel="stylesheet" type="text/css" media="screen"
		href="<%= request.getContextPath() %>/resources/stylesheets/default.css" />
	<script type="text/javascript"
		src="<%= request.getContextPath() %>/resources/javascripts/jquery-1.7.2.min.js"></script>
</head>

<body>
	<header>
		<tiles:insertAttribute name="header" />
	</header>
	<div id="outer-wrapper">
		<div id="inner-wrapper">
			<section id="content" class="content-left">
				<tiles:insertAttribute name="content" />
			</section>
			<aside id="sidebar" class="sidebar-right">
				<tiles:insertAttribute name="sidebar" />
			</aside>
		</div>
	</div>
	<script type="text/javascript">
	function adjustFooterPosition() {
		// Actually it will change min-height of #wrapper-outer.
		$('#outer-wrapper').css('min-height',
			$(window).height() - 196);
	}
	var resizeTimer = null;
	$(window).bind('resize', function() {
		if (resizeTimer)
			clearTimeout(resizeTimer);
		resizeTimer = setTimeout(adjustFooterPosition, 100);
	});
	adjustFooterPosition();
	</script>
	<div class="clearfix"></div>
	<footer>
		<tiles:insertAttribute name="footer" />
	</footer>
</body>
</html>
