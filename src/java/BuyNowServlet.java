import POJO.Products;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@WebServlet("/BuyNowServlet")
public class BuyNowServlet extends HttpServlet {

    private SessionFactory factory;

    @Override
    public void init() throws ServletException {

        try {

            factory = new Configuration()
                    .configure("hibernate.cfg.xml")
                    .buildSessionFactory();

        } catch (Exception e) {

            e.printStackTrace();

            throw new ServletException(
                    "Unable to create Hibernate SessionFactory", e);

        }
    }

    @Override
    protected void doGet(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession httpSession = request.getSession(false);

        // Check whether user is logged in
        if (httpSession == null ||
            httpSession.getAttribute("userId") == null) {

            response.sendRedirect("login.jsp");
            return;
        }

        String id = request.getParameter("id");

        if (id == null || id.trim().isEmpty()) {

            response.sendRedirect("ProductServlet");
            return;
        }

        try {

            Integer productId = Integer.parseInt(id);

            Session session = factory.openSession();

            try {

                Products product =
                        (Products) session.get(
                                Products.class,
                                productId
                        );

                if (product == null) {

                    response.sendRedirect("ProductServlet");
                    return;
                }

                // Send product to checkout page
                request.setAttribute("product", product);

                request.setAttribute("quantity", 1);

                request.getRequestDispatcher(
                        "checkout.jsp"
                ).forward(request, response);

            } finally {

                session.close();

            }

        } catch (NumberFormatException e) {

            response.sendRedirect("ProductServlet");

        } catch (Exception e) {

            e.printStackTrace();

            response.getWriter().println(
                    "Unable to process Buy Now."
            );
        }
    }

    @Override
    public void destroy() {

        if (factory != null) {
            factory.close();
        }
    }
}