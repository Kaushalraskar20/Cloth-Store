import POJO.User;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    private SessionFactory factory;

    @Override
    public void init() throws ServletException {
        try {
            factory = new Configuration()
                    .configure("hibernate.cfg.xml")
                    .buildSessionFactory();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        Session session = factory.openSession();

        try {

            Query query = session.createQuery(
                    "from User where email = :email and password = :password");

            query.setParameter("email", email);
            query.setParameter("password", password);

            User user = (User) query.uniqueResult();

            if (user != null) {

                HttpSession httpSession = request.getSession();

                httpSession.setAttribute("userId", user.getUserId());
                httpSession.setAttribute("userName", user.getName());

                response.sendRedirect("ProductServlet");

            } else {

                response.getWriter().println("<h2>Invalid Email or Password</h2>");
                response.getWriter().println("<a href='login.jsp'>Try Again</a>");

            }

        } catch (Exception e) {

            e.printStackTrace();
            response.getWriter().println("Login Failed!");

        } finally {

            session.close();

        }

    }

    @Override
    public void destroy() {

        if (factory != null) {
            factory.close();
        }

    }
}