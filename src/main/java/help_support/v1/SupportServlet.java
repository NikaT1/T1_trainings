package help_support.v1;


import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SupportServlet extends HttpServlet {

    private final ArrayList<String> phrases = new ArrayList<>(List.of("У тебя все получиться", "Не сдавайся!"));
    private final Random random = new Random();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setStatus(200);
        response.setContentType("text/plain");
        int ind = random.nextInt(phrases.size());
        response.getWriter().write(phrases.get(ind));
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        phrases.add(request.getParameter("phrase"));
        response.setStatus(200);
        response.setContentType("text/plain");
        response.getWriter().write("Новая фраза добавлена!");
    }

}