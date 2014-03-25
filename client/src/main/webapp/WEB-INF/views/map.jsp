<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ page session="false" %>
<html>
<head>
	<title>Map Operations</title>
</head>
<body>
<h1>
	Map Operations 
</h1>

	<form:form commandName="keyValue">
		<table>
			<tr>
				<td><form:label path="key">Key: </form:label></td>
				<td><form:input path="key" /></td>
			</tr>
			<tr>
				<td><form:label path="value">Value</form:label></td>
				<td><form:input path="value" /></td>
			</tr>
		    <tr>
		        <td colspan="2">
		            <input type="submit" value="Submit"/>
		        </td>
		    </tr>			
		</table>
	</form:form>
<div>
	-------------------------------------------------------------------
	-------------------------------------------------------------------
	All the Key-Values from the services: <br/>
	<table>	
	<c:forEach var="entry" items="${keyValues}">
	

			<tr>
				<td>Key:</td>
				<td><c:out value="${entry.key}"/></td>
				<td/>
				<td>Value:</td>
				<td><c:out value="${entry.value}"/></td>
			</tr>
	</c:forEach>
	</table>
</div>	
	
	
</body>
</html>
