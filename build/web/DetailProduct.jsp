<%-- 
    Document   : DetailProduct
    Created on : Apr 21, 2022, 1:44:17 PM
    Author     : CND
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css" integrity="sha512-KfkfwYDsLkIlwQp6LFnl8zNdLGxu9YAA1QvwINks4PhcElQSvqcyVLLD9aMhXd13uQjoXtEKNosOWaZqXgel0g==" crossorigin="anonymous" referrerpolicy="no-referrer" />
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
        <title>Detail Product</title>
    </head>
    <body>
        <h1 style="text-align: center">Detail Product</h1>
        <div style="display:  flex;justify-content: center; align-items: center">

            <div class="card" style="width: 18rem;">
                <img class="card-img-top" style="height: 100px" src="https://picsum.photos/seed/picsum/200/300
                     " alt="Card image cap">
                <div class="card-body">
                    <form method="POST">
                        <input type="hidden" name="id" value="${requestScope.detailProduct.id}" />
                        <h5 class="card-title"><input type="text" name="name" value="${requestScope.detailProduct.name}" /> </h5>
                        <p class="card-text"> <input type="text" name="price" value="${requestScope.detailProduct.price}" /> </p>
                        <p class="card-text"> <input type="text" name="dateCreate" value="${requestScope.detailProduct.dateCreate}" /> </p> 
                        <p class="card-text"> <input type="text" name="image" value="${requestScope.detailProduct.image}" /> </p> 
                        <select name="idCategory">
                            <option>Choose category</option>
                            <c:forEach var="category" items="${requestScope.categories}">
                                <option value="${category.id}" selected="${requestScope.detailProduct.idCategory == category.id}">${category.name}</option>
                            </c:forEach>
                        </select>
                        <p class="card-text">  <textarea class="form-control" id="exampleFormControlTextarea1" rows="3" name="description"> ${requestScope.detailProduct.description}</textarea></p>
                        <button value="updateProduct" name="btnAction" >Update Product</button>
                    </form>
                </div>
            </div>
        </div>
        <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
        <script src="//cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/axios/0.26.1/axios.min.js" integrity="sha512-bPh3uwgU5qEMipS/VOmRqynnMXGGSRv+72H/N260MQeXZIK4PG48401Bsby9Nq5P5fz7hy5UGNmC/W1Z51h2GQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
    </body>
</html>
