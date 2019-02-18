
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
    <title>Meals</title>
</head>
<body>
<section>
    <h3><a href="index.html">Home</a></h3>
    <h2>MEALS</h2>
    <hr>
    <a href="meals?action=create">Add Meal</a>
    <hr/>
    <table border="1" cellpadding="8" cellspacing="0">
        <tr>
            <th>Date</th>
            <th>Meal name</th>
            <th>Calories</th>
            <th></th>
            <th></th>
        </tr>

        <c:forEach items="${meals}" var="meal">
            <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.model.MealTo"/>
            <tr style="${meal.excess ? 'color: green':'color: red'}">
                <fmt:parseDate value="${meal.dateTime}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime" type="date"/>
                <fmt:formatDate pattern="dd.MM.yyyy" var="formattedDate" value="${parsedDateTime}" type="date"/>

                <td><c:out value="${formattedDate}"/></td>
                <td><c:out value="${meal.description}"/></td>
                <td><c:out value="${meal.calories}"/></td>
                <td><a href="meals?action=update&id=${meal.id}">Update</a></td>
                <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
            </tr>
        </c:forEach>


    </table>
</section>
</body>
</html>
</html>