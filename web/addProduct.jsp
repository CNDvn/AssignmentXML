<%-- 
    Document   : addProduct
    Created on : Apr 15, 2022, 8:57:44 PM
    Author     : CND
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Add New Product</title>
    </head>
    <body>
        <style>
            input, select {
                margin: 10px
            }
        </style>

        <h1>Add New</h1>
        <form>
            <input name="id" value="" placeholder="Enter id product..." /></br>
            <select name="idCategory">
                <option>Choose category</option>
                <c:forEach var="category" items="${categories}">
                    <option value="${category.id}">${category.name}</option>
                </c:forEach>
            </select>
            </br>
            <input name="name" value="" placeholder="Enter name product..." /></br>
            <input name="image" value="" placeholder="Enter image product..." /></br>
            <input name="price" value="" placeholder="Enter price product..." /></br>
            <input name="dateCreate" value="" placeholder="Enter date create product..." /></br>
            <input name="description" value="" placeholder="Enter description..." /></br>
            <button name="btnAction" value="Add Product">Add</button>
        </form>

    </body>
</html>
