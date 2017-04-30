<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<sec:authentication var="user" property="principal" />

<t:genericpage>
	<jsp:attribute name="header">
      <jsp:include page="../common/header.jsp" />
    </jsp:attribute>
    <jsp:attribute name="footer">
      <jsp:include page="../common/footer.jsp" />
    </jsp:attribute>
    <jsp:body>
        <div>Welcome to the user section.</div>
        <sec:authorize access="hasRole('ROLE_USER') and isAuthenticated()">
        <div>
	        Name: ${user.name}<br/>
	        Email: ${user.github}<br/>
        </div>
        </sec:authorize>
        
    </jsp:body>
</t:genericpage>