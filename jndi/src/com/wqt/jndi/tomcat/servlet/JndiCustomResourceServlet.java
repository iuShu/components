package com.wqt.jndi.tomcat.servlet;

import com.wqt.jndi.tomcat.custom.CustomResource;
import org.apache.naming.NamingContext;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by iuShu on 18-9-27
 */
public class JndiCustomResourceServlet extends HttpServlet {

    private static final String TOMCAT_RESOURCE_CONTEXT = "java:comp/env";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter out = resp.getWriter();
        try {
            Context context = new InitialContext();
            NamingContext envCtx = (NamingContext) context.lookup(TOMCAT_RESOURCE_CONTEXT);

            CustomResource res = (CustomResource) envCtx.lookup("bean/customResourceFactory");
            out.println(res);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (out != null)
                out.close();
        }
    }
}
