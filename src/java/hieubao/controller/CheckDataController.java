/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hieubao.controller;

import hieubao.utils.XmlUtil;
import java.io.File;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author CND
 */
@WebServlet(name = "CheckDataController", urlPatterns = {"/CheckDataController"})
public class CheckDataController extends HttpServlet {

    
    private final String INDEX_PAGE = "index.html";
    private final String ERROR_PAGE = "error.html";
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String url = INDEX_PAGE;
        try{
            String xmlPath = "D:\\FU\\CN_VIII\\PRX\\NikeShop\\xml\\application_db.xml";
            String xsdPath = "D:\\FU\\CN_VIII\\PRX\\NikeShop\\xml\\schema.xml";
            if(XmlUtil.ValidationXMLSchame(xsdPath, xmlPath)){
                url = INDEX_PAGE;
            }else{
                url = ERROR_PAGE;
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally{
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
