<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<div id="wrapper">
<div id="content">

	<section id="new-entry">
		<form id="new-entry-form" action="<%= request.getContextPath() %>/user/publish" method="post">
			<textarea id="new-entry-content" name="content"></textarea>
			<input type="submit" id="new-entry-submit" value="提交" />
		</form>
	</section>

	<div class="clearfix"></div>

	<section id="timeline">
		<s:iterator value="entries">
			<div class="entry">
				<s:property value="authorName" />:
				<s:property value="content" />
			</div>
		</s:iterator>
	</section>

</div><!-- content -->
</div><!-- wrapper -->
