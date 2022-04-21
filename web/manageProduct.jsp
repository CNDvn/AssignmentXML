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
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css" integrity="sha512-KfkfwYDsLkIlwQp6LFnl8zNdLGxu9YAA1QvwINks4PhcElQSvqcyVLLD9aMhXd13uQjoXtEKNosOWaZqXgel0g==" crossorigin="anonymous" referrerpolicy="no-referrer" />
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
        <style>
            .wrapper {
                width: 100%;
                height: 100%;
                display: flex;
                /*                align-items: center;
                                justify-content: center;*/
            }
            .wrapper .file-upload {
                height: 200px;
                width: 200px;
                border-radius: 100px;
                position: relative;
                display: flex;
                justify-content: center;
                align-items: center;
                border: 4px solid #fff;
                overflow: hidden;
                background-image: linear-gradient(to bottom, #2590eb 50%, #fff 50%);
                background-size: 100% 200%;
                transition: all 1s;
                color: #fff;
                font-size: 100px;
            }
            .wrapper .file-upload input[type='file'] {
                height: 200px;
                width: 200px;
                position: absolute;
                top: 0;
                left: 0;
                opacity: 0;
                cursor: pointer;
            }
            .wrapper .file-upload:hover {
                background-position: 0 -100%;
                color: #2590eb;
            }

        </style>
    </head>
    <body>
        <div style="display: flex;justify-content: center;gap: 10px">
            <div style="margin-top: 10px">
                <!--                <form method="post" action="MainController">
                                    <input type="submit" name="btnAction" value="Generate Data"  type="button" class="btn btn-primary"/>
                                </form-->
                <button id="generateData"  class="btn btn-success" >Generate Data</button>
            </div>

            <div style="margin-top: 10px">
                <!--                <form method="post" action="CheckDataController" enctype="multipart/form-data" >
                                    <div class="wrapper">
                                        <div class="file-upload">
                                            <input type="file" name="data"/>
                                            <i class="fa fa-arrow-up"></i>
                                        </div>
                                    </div>
                                    <input type="submit" name="btnAction" value="Check Data" class="btn btn-dark"/>
                                </form>-->
                <button id="checkData"  class="btn btn-success" >Check Data</button>
            </div>
            <div style="margin-top: 10px">
                <form method="get" action="MainController">
                    <input type="submit" name="btnAction" value="Manage Product"  class="btn btn-success" />
                </form>
            </div>
            <div style="margin-top: 10px">
                <!--                <form method="post" action="ImportXMLToDBController" enctype="multipart/form-data">
                                    <div class="wrapper">
                                        <div class="file-upload">
                                            <input type="file" name="data"/>
                                            <i class="fa fa-arrow-up"></i>
                                        </div>
                                    </div>
                                    <input type="submit" name="btnAction" value="Import data" class="btn btn-dark"/>
                                </form>-->
                <button id='importData'  class="btn btn-success" >Import Data</button>
            </div>
            <button style="margin-top: 10px"  class="btn btn-success" ><a href="MainController?btnAction=export" style="    text-decoration: none;
                                                                          color: white" >Export</a></button>
        </div>
        <style>
            td{
                padding: 5px  
            }
            table{
                margin-top: 5px
            }
        </style>
        <div style="display: flex;justify-content: space-between;padding-right: 267px;
             padding-left: 267px;
             align-items: center">
            <form action="MainController" method="POST" style="display: flex;justify-content: space-between;gap:8px;
                  align-items: center">
                <div class="input-group">
                    <input name="searchValue" value="${filter}" placeholder="Search..." style="padding: 4px"/>
                </div>
                <select name="idCategory" style="padding: 5px;">
                    <c:forEach var="category" items="${categories}">
                        <c:if test="${requestScope.idCategory eq category.id}">
                            <option selected value="${category.id}">${category.name}</option>
                        </c:if>
                        <c:if test="${requestScope.idCategory != category.id}">
                            <option value="${category.id}">${category.name}</option>
                        </c:if>
                    </c:forEach>
                </select>
                <button name="btnAction" value="search" class="btn btn-success" >Search</button>
            </form>
            <button style="margin-top: 10px"  class="btn btn-success" >  <a href="MainController?btnAction=addNew" style="    text-decoration: none;
                                                                            color: white">Add New</a></button>
        </div>
        <c:if test="${not empty products}">
            <div style="display: flex;justify-content: center;align-items: center">
                <table border >
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
                        <form action="MainController" method="POST">
                            <tr>
                                <td>${counter.count}</td>
                                <td id="nameProduct${product.id}">${product.name}</td>
                                <td id="imageProduct${product.id}">
                                    <img src="https://picsum.photos/200" />
                                </td>
                                <td id="priceProduct${product.id}">$${product.price}</td>
                                <td id="dateCreateProduct${product.id}">${product.dateCreate}</td>
                                <td id="descriptionProduct${product.id}">
                                    ${product.description}
                                </td>
                                <td><a href="MainController?btnAction=deleteProduct&id=${product.id}"class="btn btn-success">Delete</a></td>
                            <input type="hidden" name="id" value="${product.id}" />
                            <input type="hidden" name="name" value="${product.name}" />
                            <input type="hidden" name="price" value="${product.price}" />
                            <input type="hidden" name="dateCreate" value="${product.dateCreate}" />
                            <input type="hidden" name="description" value="${product.description}" />
                            <input type="hidden" name="idCategory" value="${product.idCategory}" />
                            <input type="hidden" name="image" value="${product.image}" />
                            <td>  <button name="btnAction" value="detailProduct" class="btn btn-success" >view detail</button></td>

                            </tr>
                        </form>

                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:if>
        <c:if test="${empty products}">
            <h3 style="color:red">Don't have product</h3>
        </c:if>
        <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
        <script src="//cdn.jsdelivr.net/npm/sweetalert2@11"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/axios/0.26.1/axios.min.js" integrity="sha512-bPh3uwgU5qEMipS/VOmRqynnMXGGSRv+72H/N260MQeXZIK4PG48401Bsby9Nq5P5fz7hy5UGNmC/W1Z51h2GQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
        <script>
            function postForm(path, params, method) {
                method = method || 'post';

                var form = document.createElement('form');
                form.setAttribute('method', method);
                form.setAttribute('action', path);

                for (var key in params) {
                    if (params.hasOwnProperty(key)) {
                        var hiddenField = document.createElement('input');
                        hiddenField.setAttribute('type', 'hidden');
                        hiddenField.setAttribute('name', key);
                        hiddenField.setAttribute('value', params[key]);

                        form.appendChild(hiddenField);
                    }
                }

                document.body.appendChild(form);
                form.submit();
            }

            function postFormMultipart(path, params, method, data) {
                method = method || 'post';

                var form = document.createElement('form');
                form.setAttribute('method', method);
                form.setAttribute('action', path);
                form.setAttribute('enctype', 'multipart/form-data')

                for (var key in params) {
                    if (params.hasOwnProperty(key)) {
                        var hiddenField = document.createElement('input');
                        hiddenField.setAttribute('type', 'hidden');
                        hiddenField.setAttribute('name', key);
                        hiddenField.setAttribute('value', params[key]);

                        form.appendChild(hiddenField);
                    }
                }
                var dataField = document.createElement('input');
                dataField.setAttribute('type', 'file');
                hiddenField.setAttribute('name', 'data');
                hiddenField.setAttribute('value', data);
                form.appendChild(dataField);

                document.body.appendChild(form);
                form.submit();
            }

            const generateData = document.getElementById("generateData").addEventListener("click", () => {
                Swal.fire({
                    title: 'Are you sure?',
                    text: "If you generate data then old data will remove!",
                    icon: 'warning',
                    showCancelButton: true,
                    confirmButtonColor: '#3085d6',
                    cancelButtonColor: '#d33',
                    confirmButtonText: 'Yes'
                }).then((result) => {
                    if (result.isConfirmed) {
                        postForm('MainController', {btnAction: 'Generate Data'});
                    }
                })
            }
            );

            const checkData = document.getElementById("checkData").addEventListener("click", () => {
                Swal.fire({
                    title: 'Submit file need check',
                    html:
                            '<form method="post" action="CheckDataController" enctype="multipart/form-data">\n\
                                <input type="file" name="data"/>\n\
                                <input style="margin-top:30px" type="submit" name="btnAction" value="Check Data" class="btn btn-dark"/>\n\
                            </form>',
                    showCloseButton: true,
                    showConfirmButton: false
                })
            });

            const importData = document.getElementById('importData').addEventListener('click', () => {
                Swal.fire({
                    title: 'Submit file need import',
                    html:
                            '<form  method="post" action="ImportXMLToDBController" enctype="multipart/form-data">\n\
                                <input type="file" name="data"/>\n\
                                <input style="margin-top:30px"  type="submit" name="btnAction" value="Import data" class="btn btn-dark"/>\n\
                            </form>',
                    showCloseButton: true,
                    showConfirmButton: false
                })
            })
        </script>
    </body>
</html>
