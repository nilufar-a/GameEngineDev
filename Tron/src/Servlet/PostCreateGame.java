package engine.Servlet;


import com.google.api.client.http.HttpStatusCodes;
import com.google.appengine.repackaged.com.google.gson.Gson;
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
        int gameID= Integer.parseInt(req.getParameter("GameID"));
        GamesContainer container=GamesContainer.getInstance();
        container.createGame(gameID);
        Gson gson = new Gson();
        out.write(gson.toJson(HttpStatusCodes.STATUS_CODE_OK));
        out.flush();

    }


}
