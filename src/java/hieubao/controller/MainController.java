/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieubao.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author CND
 */
public class MainController extends HttpServlet {

    private final String INDEX_PAGE = "index.html";
    private final String GENERATE_DATA = "GenerateDataController";
    private final String CHECK_DATA = "CheckDataController";
    private final String ADD_CATEGORY = "AddCategoryController";
    private final String ADD_PRODUCT = "AddProductController";
    private final String MANAGE_PRODUCT = "ManageProductController";
    private final String DELETE_PRODUCT = "DeleteProductController";
    private final String UPDATE_PRODUCT = "UpdateProductController";
    private final String EXPORT_DATA = "ExportXmlToExcel";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String action = request.getParameter("btnAction");
        String url = INDEX_PAGE;
        try {
            if ("Generate Data".equals(action)) {
                url = GENERATE_DATA;
            }
            if ("Check Data".equals(action)) {
                url = CHECK_DATA;
            }

            if ("New Category".equals(action)) {
                url = ADD_CATEGORY;
            }

            if ("addNew".equals(action) || "Add Product".equals(action)) {
                url = ADD_PRODUCT;
            }

            if ("Manage Product".equals(action) || "search".equals(action)) {
                url = MANAGE_PRODUCT;
            }
            if ("deleteProduct".equals(action)) {
                url = DELETE_PRODUCT;
            }

            if ("updateProduct".equals(action)) {
                url = UPDATE_PRODUCT;
            }

            if ("export".equals(action)) {
                url = EXPORT_DATA;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
