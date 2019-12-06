package engine.Servlet;

import com.google.api.client.http.HttpStatusCodes;
import com.google.appengine.repackaged.com.google.gson.Gson;
import engine.Direction;
import engine.GamesContainer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "PostMove",urlPatterns = "/PostMove")
public class PostMove extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        String direction= req.getParameter("Direction");
        String userID= req.getParameter("UserID");
        boolean turboFlag=false;
        if(req.getParameter("TurboFlag").equals("true")){
            turboFlag=true;
        }
        if(direction.equals("UP")){

        }
        Direction eDirection;
        switch (direction.toUpperCase()){
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
                eDirection=Direction.UP;
        }

        GamesContainer container=GamesContainer.getInstance();
        container.postMove(eDirection,userID,turboFlag);
        Gson gson=new Gson();
        out.write(gson.toJson(HttpStatusCodes.STATUS_CODE_OK));
        out.flush();
    }
}
