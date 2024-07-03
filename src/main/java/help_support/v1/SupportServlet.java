package help_support.v1;


import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class SupportServlet extends HttpServlet {

    private SupportService supportService;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try {
            supportService = new ApplicationContext().getInstance(SupportService.class);
        } catch (InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setStatus(200);
        response.setContentType("text/plain");
        response.getWriter().write(supportService.getPhrase());
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setStatus(200);
        response.setContentType("text/plain");
        response.getWriter().write(supportService.addPhrase(request.getParameter("phrase")));
    }

}