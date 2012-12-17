<%@ include file="/WEB-INF/jsp/include.jsp" %>

<html>
<head><title>欢迎使用查一把应用</title></head>
<body>
<h1>Hello, ${model.user.name}</h1>

<br>

${model.user}

<br>

<a href="${model.authorizeUrl}">新用户请点击</a>

</body>
</html>