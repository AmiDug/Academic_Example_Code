package examples;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.ServletContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.sql.DataSource;

@WebServlet(name = "Controller", urlPatterns = {"/Controller"})
public class Controller extends HttpServlet {
 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                /*// Creating the session object has been moved to getpost (compared to the lecture)
                RequestDispatcher rd = request.getRequestDispatcher("/index.html");
                rd.forward(request, response);*/
                
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("Connecting to DB.");
        try{
            Context initContext = new InitialContext();
            Context envContext  = (Context)initContext.lookup("java:/comp/env");
            DataSource ds = (DataSource)envContext.lookup("jdbc/mysql");
            Connection conn = ds.getConnection();
        }
        catch(Exception e){
            out.println(e.getMessage());
        }
	/*out.println("Finished.");*/
        RequestDispatcher rd = request.getRequestDispatcher("/index.html");
        rd.forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
                response.setContentType("text/html;charset=UTF-8");
                PrintWriter out = response.getWriter();
                HttpSession session = request.getSession(true);
                User u;
                if (session.isNew()){
                    u = new User();
                    u.setUsername(request.getParameter("username"));
                    u.setPassword(request.getParameter("password"));
                    session.setAttribute("user", u);
                }else{
                    // retrieve the already existring user
                    u = (User)session.getAttribute("user");
                }
                ServletContext application = request.getServletContext();
                DbHandler dbh = (DbHandler)application.getAttribute("dbh");
                if(dbh==null)
                    dbh = new DbHandler();
                User[] users = dbh.getUsers();
                application.setAttribute("users", users);
                if("login".equals(request.getParameter("action"))){
                    // check if user is authorized with the "dbh" object
                    if(dbh.validate(u))
                    {
                        RequestDispatcher rd = request.getRequestDispatcher("/users.jsp");
                        rd.forward(request, response);
                    }
                    else
                    {
                        RequestDispatcher rd = request.getRequestDispatcher("/index.html");
                        rd.forward(request, response);
                    }
                    }
                }
                
    }
