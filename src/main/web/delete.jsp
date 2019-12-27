<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:useBean id="user" class="ua.nure.kn.kyrylov.usermanagement.domain.User" scope="session"/>
<html>
<head><title>User management: delete</title></head>
<body>
<form action="<%=request.getContextPath()%>/delete" method="post">
    <input type="hidden" name="id" value="${user.id}">
    ${user.firstName}<br>
    ${user.lastName}<br>
    ${user.dateOfBirth}<br>
    <input type="submit" name="okButton" value="Ok">
    <input type="submit" name="cancelButton" value="Cancel">
</form>
<c:if test='${requestScope.error != null}'>
    <script type="text/javascript">
        alert('${requestScope.error}');
    </script>
</c:if>
</body>
</html>