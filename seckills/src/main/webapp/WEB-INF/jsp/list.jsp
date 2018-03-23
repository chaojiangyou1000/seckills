<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!-- 引入JSTL标签库 -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>秒杀列表页</title>

<link rel="stylesheet" href="<%=basePath%>/bootstrap-3.3.7/css/bootstrap.min.css">
<script src="<%=basePath%>/Jquery/jquery-3.1.1.js"></script>
<script src="<%=basePath%>/bootstrap-3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
	<div class="container">
		<div class="panel panel-default">
			<div class="panel-heading text-center">
				<h2>秒杀列表</h2>
			</div>

			<div class="panel-body">
				<table class="table table-hover">
					<thead>
						<tr>
							<th>名称</th>
							<th>库存</th>
							<th>开始时间</th>
							<th>结束时间</th>
							<th>创建时间</th>
							<th>详情页</th>
						</tr>
					</thead>

					<tbody>
						<c:forEach var="sk" items="${list }">
							<tr>
								<td>${sk.name }</td>
								<td>${sk.number }</td>
								<td>
									<!-- 时间格式化 --> <fmt:formatDate value="${sk.startTime }"
										pattern="yyyy-MM-dd HH:mm:ss" />
								</td>
								<td>
									<fmt:formatDate value="${sk.endTime }"
										pattern="yyyy-MM-dd HH:mm:ss" />
								</td>
								<td>
								    <fmt:formatDate value="${sk.createTime }"
										pattern="yyyy-MM-dd HH:mm:ss" />
								</td>
								<td>
									<a class="btn btn-info"
									href="${sk.seckillId}/detail" target="_blank">link</a>
								</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>
</body>
</html>