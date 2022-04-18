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
import hieubao.utils.DBHelper;
import hieubao.utils.XmlUtil;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.xml.sax.SAXException;

/**
 *
 * @author CND
 */
@WebServlet(name = "ImportXMLToDBController", urlPatterns = {"/ImportXMLToDBController"})
@MultipartConfig
public class ImportXMLToDBController extends HttpServlet {

    private final static String SUCCESS_PAGE = "importDataSuccess.html";
    private final static String ERROR_PAGE = "error.html";
    private final String CHECK_FILE_FAIL_PAGE = "checkFileFail.jsp";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        Part filePart = request.getPart("data");
        String url = ERROR_PAGE;
        try {
            String xmlPath = request.getServletContext().getRealPath("/xml/importFile.xml");
            String xsdPath = request.getServletContext().getRealPath("/xml/schema.xml");
            String xmlPathDB = request.getServletContext().getRealPath("/xml/application_db.xml");
            XmlUtil.writeFile(request, filePart, xmlPath);

            if (XmlUtil.ValidationXMLSchame(xsdPath, xmlPath)) {
                if (DBHelper.getConnect() != null) {
                    ProductDAO productDAO = new ProductDAO();
                    CategoryDAO categoryDAO = new CategoryDAO();

                    List<ProductDTO> products = productDAO.getProducts(xmlPath, null, null);
                    List<CategoryDTO> categories = categoryDAO.getAll(xmlPath);

                    productDAO.importXMLFIleToDatabase(xmlPathDB, products);
                    categoryDAO.importXMLFIleToDatabase(xmlPathDB, categories);

                    FileInputStream fis = new FileInputStream(xmlPathDB);
                    BufferedReader br = new BufferedReader(new InputStreamReader(fis));
                    response.setHeader("Content-disposition", "attachment; filename=application_db.xml");

                    while (true) {
                        String s = br.readLine();
                        if (s == null) {
                            break;
                        }
                        out.println(s);
                        out.flush();
                    }
                    fis.close();
                }
            } else {
                url = CHECK_FILE_FAIL_PAGE;
                request.getRequestDispatcher(url).forward(request, response);

            }
        } catch (SAXException e) {
            e.printStackTrace();
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher(url).forward(request, response);

        } catch (IOException e) {
            e.printStackTrace();
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher(url).forward(request, response);

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", e.getMessage());
            request.getRequestDispatcher(url).forward(request, response);
        } finally {
            out.close();
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
