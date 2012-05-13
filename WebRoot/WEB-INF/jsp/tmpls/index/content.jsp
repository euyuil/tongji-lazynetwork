<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<div id="wrapper">
<div id="content">

	<section id="description">
		Lazy Network 是您强大的信息聚合助手，只需登录下列任意一个社交账号，即可开始体验。
	</section>
	<div id="login-choices">
		<ul>
			<li class="login-choice" id="login-sina-weibo">
				<a href="login/sina" title="单击这里使用新浪微博登陆"></a>
			</li>
			<li class="login-choice" id="login-renren">
				<a href="login/oauth/request?service=renren" title="单击这里使用人人账号登陆"></a>
			</li>
			<li class="login-choice" id="login-qq-weibo">
				<a href="login/oauth/request?service=qq" title="单击这里使用腾讯微博登陆"></a>
			</li>
			<li class="login-choice" id="login-netease-weibo">
				<a href="login/oauth/request?service=netease" title="单击这里使用网易微博登陆"></a>
			</li>
			<li class="login-choice" id="login-openid">
				<input id="login-openid-url" value="使用 OpenID 登录" />
			</li>
		</ul>
	</div>

</div><!-- content -->
</div><!-- wrapper -->
