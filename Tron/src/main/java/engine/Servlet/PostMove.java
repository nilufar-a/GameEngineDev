package engine.Servlet;

import engine.Direction;
import engine.GamesContainer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "PostMove", urlPatterns = "/PostMove")
public class PostMove extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        String direction = req.getParameter("Direction");
        String userID = req.getParameter("UserID");
        boolean turboFlag = false;
        try {
            if (req.getParameter("TurboFlag").toLowerCase().equals("true")) {
                turboFlag = true;
            } else if (req.getParameter("TurboFlag").toLowerCase().equals("false")) {
                turboFlag = false;
            } else {
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException ex) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid turbo flag");
        }
        Direction eDirection;

        switch (direction.toUpperCase()) {
            case "UP":
                eDirection = Direction.UP;
                break;
            case "DOWN":
                eDirection = Direction.DOWN;
                break;
            case "LEFT":
                eDirection = Direction.LEFT;
                break;
            case "RIGHT":
                eDirection = Direction.RIGHT;
                break;
            default:
                eDirection = null;
        }
        try {
            if (eDirection == null) {
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException ex) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Illegal direction");
        }
        GamesContainer container = GamesContainer.getInstance();
        if (container.postMove(eDirection, userID, turboFlag)) {
            resp.setStatus(HttpServletResponse.SC_OK);
        } else {
          resp.sendError(HttpServletResponse.SC_NOT_FOUND,"No game/player found");
        }
        out.flush();
    }
}
