<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- 引入JSTL标签库 -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>秒杀详情页</title>
<link rel="stylesheet" href="<%=basePath%>/bootstrap-3.3.7/css/bootstrap.min.css">
<script src="<%=basePath%>/Jquery/jquery-3.1.1.js"></script>
<script src="<%=basePath%>/bootstrap-3.3.7/js/bootstrap.min.js"></script>
<!-- 引入Jquery-cookie的插件 -->
<script src="<%=basePath%>/Jquery-cookie/jquery.cookie.js"></script>
<!-- 引入Jquery-countdown的插件倒计时 -->
<script src="<%=basePath%>/jquery.countdown-2.1.0/jquery.countdown.min.js"></script>
<!-- 引入seckill.js文件 -->
<script src="<%=basePath%>/js/seckill.js"></script>
</head>
<body>
	<div class="container">
		<div class="panel panel-default ">
			<div class="panel-heading text-center">
				<h2>${seckill.name }</h2>
			</div>
		</div>

		<div class="panel-body text-center">
			<h2 class="text-danger">
				<%--显示time图标--%>
				<span class="glyphicon glyphicon-time"></span>
				<%--展示倒计时--%>
				<span class="glyphicon" id="seckill-box"></span>
			</h2>
		</div>
	</div>

	<%--登录弹出层 输入电话--%>
	<div id="killPhoneModal" class="modal fade">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h3 class="modal-title text-center">
						<span class="glyphicon glyphicon-phone"> </span>秒杀电话:
					</h3>
				</div>

				<div class="modal-body">
					<div class="row">
						<div class="col-xs-8 col-xs-offset-2">
							<input type="text" name="killPhone" id="killPhoneKey"
								placeholder="填写手机号^o^" class="form-control">
						</div>
					</div>
				</div>

				<div class="modal-footer">
					<%--验证信息--%>
					<span id="killPhoneMessage" class="glyphicon"> </span>
					<button type="button" id="killPhoneBtn" class="btn btn-success">
						<span class="glyphicon glyphicon-phone"></span> Submit
					</button>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
		$(function(){
			//使用EL表达式传入参数
			seckill.detail.init({
				seckillId : ${seckill.seckillId},
				startTime : ${seckill.startTime.time},
				endTime : ${seckill.endTime.time}
			});		
		});
		
	</script>
</html>