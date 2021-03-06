/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieubao.controller;

import hieubao.utils.XmlUtil;
import java.io.IOException;
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
@WebServlet(name = "CheckDataController", urlPatterns = {"/CheckDataController"})
@MultipartConfig
public class CheckDataController extends HttpServlet {

    private final String INDEX_PAGE = "index.html";
    private final String ERROR_PAGE = "error.html";
    private final String CHECK_FILE_FAIL_PAGE = "checkFileFail.jsp";
    private final String CHECK_FILE_SUCCESS_PAGE = "checkFileSuccess.html";

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = CHECK_FILE_FAIL_PAGE;
        Part filePart = request.getPart("data");
        try {
            String xmlPath = request.getServletContext().getRealPath("/xml/application_db_check.xml");
            String xsdPath = request.getServletContext().getRealPath("/xml/schema.xml");

            XmlUtil.writeFile(request, filePart, xmlPath);

            if (XmlUtil.ValidationXMLSchame(xsdPath, xmlPath)) {
                url = CHECK_FILE_SUCCESS_PAGE;
            }
        } catch (SAXException e) {
            e.printStackTrace();
            request.setAttribute("error", e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            request.setAttribute("error", e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", e.getMessage());
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
