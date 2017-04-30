<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<sec:authentication var="user" property="principal" />
<c:url value="/logout" var="logoutUrl" />

<sec:authorize access="hasRole('ROLE_USER') and isAuthenticated()">
	Welcome : <img src="${user.avatarUrl}" alt="" style="width:30px;height:30px" /> ${user.name} | <a href="javascript:formSubmit()"> Logout</a>
</sec:authorize>

<!-- csrt for log out-->
<form action="${logoutUrl}" method="post" id="logoutForm">
	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
</form>

<script>
	function formSubmit() {
		document.getElementById("logoutForm").submit();
	}
</script>
