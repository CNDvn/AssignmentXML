/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieubao.controller;

import hieubao.category.CategoryDAO;
import hieubao.category.CategoryDTO;
import hieubao.product.ProductDAO;
import hieubao.product.ProductDTO;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author CND
 */
@WebServlet(name = "AddProductController", urlPatterns = {"/AddProductController"})
public class AddProductController extends HttpServlet {

    private final String MANAGE_PRODUCT = "ManageProductController";
    private final String ERROR_PAGE = "error.html";
    private final String NEW_PRODUCT_PAGE = "addProduct.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String xmlPath = request.getServletContext().getRealPath("/xml/application_db.xml");
        String url = ERROR_PAGE;
        String action = request.getParameter("btnAction");

        try {

            if ("addNew".equals(action)) {
                List<CategoryDTO> categories = new CategoryDAO().getAll(xmlPath);
                request.setAttribute("categories", categories);
                url = NEW_PRODUCT_PAGE;
            } else {
                String id = request.getParameter("id");
                String idCategory = request.getParameter("idCategory");
                String name = request.getParameter("name");
                String image = request.getParameter("image");
                String dateCreate = request.getParameter("dateCreate");
                String description = request.getParameter("description");
                double price = Double.parseDouble(request.getParameter("price"));
                ProductDTO dto = new ProductDTO(id, idCategory, name, image, price, dateCreate, description);
                ProductDAO dao = new ProductDAO();
                dao.addNew(xmlPath, dto);
                url = MANAGE_PRODUCT;
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            request.getRequestDispatcher(url).forward(request, response);
        }

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
