package engine.Servlet;


import engine.GamesContainer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "PostCreateGame", urlPatterns = "/PostCreateGame")
public class PostCreateGame extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        try {
            int gameID = Integer.parseInt(req.getParameter("GameID"));
            GamesContainer container = GamesContainer.getInstance();
            if (container.createGame(gameID)) {
                resp.setStatus(HttpServletResponse.SC_OK);
            } else {
                resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,"Error initializing the map and or players");
            }
        } catch (NumberFormatException ex) {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Invalid Game ID");
        }


        out.flush();

    }


}
