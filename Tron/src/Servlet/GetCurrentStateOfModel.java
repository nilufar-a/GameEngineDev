package engine.Servlet;

import engine.GamesContainer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "GetCurrentStateOfModel",urlPatterns = "/GetCurrentStateOfModel")
public class GetCurrentStateOfModel extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        int GameID=Integer.parseInt(req.getParameter("GameID"));
        GamesContainer container=GamesContainer.getInstance();
        String json=container.getCurrentStateOfModel(GameID);
        out.write(json);
        out.flush();
    }
}
