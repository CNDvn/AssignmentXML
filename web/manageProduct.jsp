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
        <div>
            <form action="MainController" method="POST">
                <input name="searchValue" value="${filter}" placeholder="Search..."/>
                <select name="idCategory">
                    <c:forEach var="category" items="${categories}">
                        <c:if test="${requestScope.idCategory eq category.id}">
                            <option selected value="${category.id}">${category.name}</option>
                        </c:if>
                        <c:if test="${requestScope.idCategory != category.id}">
                            <option value="${category.id}">${category.name}</option>
                        </c:if>
                    </c:forEach>
                </select>
                <button name="btnAction" value="search">Search</button>
            </form>
            <a href="MainController?btnAction=addNew">Add New</a>
        </div>
        <c:if test="${not empty products}">
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
                        <input type="hidden" name="id" value="${product.id}"/>
                        <td>${product.id}</td>
                        <td><input name="name" value="${product.name}" /></td>
                        <td><input name="image" value="${product.image}" /></td>
                        <td>$ <input name="price" value="${product.price}" /></td>
                        <td><input name="dateCreate" value="${product.dateCreate}" /> </td>
                        <td>
                            <textarea name="description">${product.description}</textarea>
                        </td>
                        <td><a href="MainController?btnAction=deleteProduct&id=${product.id}">Delete</a></td>
                        <td><button type="submit" name="btnAction" value="updateProduct">Update</button></td>
                    </form>

                </tr>
            </c:forEach>
        </tbody>
    </table>
</c:if>
<c:if test="${empty products}">
    <h3 style="color:red">Don't have product</h3>
</c:if>
</body>
</html>
