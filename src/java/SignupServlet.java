import POJO.User;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

@WebServlet("/SignupServlet")
public class SignupServlet extends HttpServlet {

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

        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String mobile = request.getParameter("mobile");
        String address = request.getParameter("address");

        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();

        try {

            User user = new User();

            user.setName(name);
            user.setEmail(email);
            user.setPassword(password);
            user.setMobile(mobile);
            user.setAddress(address);

            session.save(user);

            tx.commit();

            response.sendRedirect("login.jsp");

        } catch (Exception e) {

            tx.rollback();
            e.printStackTrace();

            response.getWriter().println("Registration Failed!");

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