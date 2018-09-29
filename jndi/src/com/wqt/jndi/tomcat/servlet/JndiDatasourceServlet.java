package com.wqt.jndi.tomcat.servlet;

import com.wqt.jndi.tomcat.MysqlDemo;
import com.wqt.jndi.tomcat.bean.Human;
import org.apache.naming.NamingContext;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;

/**
 * Created by iuShu on 18-9-27
 */
public class JndiDatasourceServlet extends HttpServlet {

    private static final String TOMCAT_RESOURCE_CONTEXT = "java:comp/env";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter out = resp.getWriter();
        try {
            Context context = new InitialContext();
            NamingContext envCtx = (NamingContext) context.lookup(TOMCAT_RESOURCE_CONTEXT);

            // org.apache.tomcat.dbcp.dbcp2.BasicDataSource
            DataSource dataSource = (DataSource) envCtx.lookup("jdbc/patriot");
            // org.apache.tomcat.dbcp.dbcp2.PoolingDataSource.PoolGuardConnectionWrapper
            Connection conn = dataSource.getConnection();

            ResultSet rs = conn.prepareStatement("select * from human where id=2").executeQuery();
            Human human = MysqlDemo.processResultSet(rs, Human.class);
            out.println(human);
            out.println();
            out.println(dataSource.getClass().getCanonicalName());
            out.println(conn.getClass().getCanonicalName());

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (out != null)
                out.close();
        }
    }
}
