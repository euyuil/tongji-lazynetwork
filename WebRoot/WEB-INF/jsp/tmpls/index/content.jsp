<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<div id="wrapper">
<div id="content">

	<div id="authorization">
		<s:if test="currentUser eq null">
			您还未登录。
		</s:if>
		<s:if test="currentUser neq null">
			欢迎用户 <s:property value="currentUser.id" /> |
				<a href="<s:url action="user/logout" />">登出</a>
		</s:if>
	</div>

	<section id="description">
		Lazy Network 是您强大的信息聚合助手，只需登录下列任意一个社交账号，即可开始体验。
	</section>
	<div id="login-choices">
		<ul>
			<li class="login-choice" id="login-sina-weibo">
				<a href="login/sina" title="单击这里使用新浪微博登陆"></a>
			</li>
			<li class="login-choice" id="login-renren">
				<a href="login/renren" title="单击这里使用人人账号登陆"></a>
			</li>
			<li class="login-choice" id="login-qq-weibo">
				<a href="login/qq" title="单击这里使用腾讯微博登陆"></a>
			</li>
			<li class="login-choice" id="login-netease-weibo">
				<a href="login/netease" title="单击这里使用网易微博登陆"></a>
			</li>
			<li class="login-choice" id="login-openid">
				<input id="login-openid-url" value="使用 OpenID 登录" />
			</li>
		</ul>
	</div>

</div><!-- content -->
</div><!-- wrapper -->
