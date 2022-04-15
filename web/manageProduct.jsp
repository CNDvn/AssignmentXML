<%-- 
    Document   : manageProduct
    Created on : Apr 15, 2022, 10:36:34 AM
    Author     : CND
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Manage</title>
    </head>
    <body>
        <style>
            td{
                padding: 5px  
            }
            table{
                margin-top: 5px
            }
        </style>
        <select onchange="((value) => {
                    window.location.href = 'MainController?btnAction=Manage Product&idCategory=' + value
                })(this.value)">
            <c:forEach var="category" items="${categories}">
                <c:if test="${requestScope.idCategory eq category.id}">
                    <option selected value="${category.id}">${category.name}</option>
                </c:if>
                <c:if test="${requestScope.idCategory != category.id}">
                    <option value="${category.id}">${category.name}</option>
                </c:if>
            </c:forEach>
        </select>
        <table border>
            <thead>
                <tr>
                    <th>No.</th>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Image</th>
                    <th>Price</th>
                    <th>Date Create</th>
                    <th>Description</th>
                    <th colspan="2">action</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="product" items="${products}" varStatus="counter">
                    <tr>
                <form action="MainController" method="POST">
                    <td>${counter.count}</td>
                    <td>${product.id}</td>
                    <td>${product.name}</td>
                    <td>${product.image}</td>
                    <td>$${product.price}</td>
                    <td>${product.dateCreate}</td>
                    <td>${product.description}</td>
                    <td><a href="MainController?btnAction=deleteProduct&id=${product.id}">Delete</a></td>
                    <td>Update</td>
                </form>

            </tr>
        </c:forEach>
    </tbody>
</table>
</body>
</html>
