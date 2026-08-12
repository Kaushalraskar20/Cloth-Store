import POJO.Cart;
import POJO.Products;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

@WebServlet("/CartDisplayServlet")
public class CartDisplayServlet extends HttpServlet {

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
                    "Hibernate SessionFactory creation failed",
                    e
            );
        }
    }

    @Override
    protected void doGet(HttpServletRequest request,
                          HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession httpSession =
                request.getSession(false);

        // Check login
        if (httpSession == null ||
            httpSession.getAttribute("userId") == null) {

            response.sendRedirect("login.jsp");
            return;
        }

        Integer userId =
                (Integer) httpSession.getAttribute("userId");

        Session session = null;

        try {

            session = factory.openSession();

            System.out.println("--------------------------------");
            System.out.println("DISPLAY CART");
            System.out.println("User ID = " + userId);

            // Get cart items for current user

            Query query = session.createQuery(
                    "from Cart where userId = :userId"
            );

            query.setParameter(
                    "userId",
                    userId
            );

            List<Cart> cartList = query.list();

            System.out.println(
                    "Cart items found = "
                    + cartList.size()
            );

            // Product list

            List<Products> products =
                    new ArrayList<Products>();

            for (Cart cart : cartList) {

                Products product =
                        (Products) session.get(
                                Products.class,
                                cart.getProductId()
                        );

                if (product != null) {

                    products.add(product);

                }
            }

            System.out.println(
                    "Products found = "
                    + products.size()
            );

            System.out.println("--------------------------------");

            // Send data to JSP

            request.setAttribute(
                    "cartList",
                    cartList
            );

            request.setAttribute(
                    "products",
                    products
            );

            request.getRequestDispatcher(
                    "cart.jsp"
            ).forward(
                    request,
                    response
            );

        } catch (Exception e) {

            e.printStackTrace();

            response.setContentType(
                    "text/html;charset=UTF-8"
            );

            response.getWriter().println(
                    "<h2>Cart Display Failed</h2>"
            );

            response.getWriter().println(
                    "<pre>"
            );

            e.printStackTrace(
                    response.getWriter()
            );

            response.getWriter().println(
                    "</pre>"
            );

        } finally {

            if (session != null) {
                session.close();
            }
        }
    }

    @Override
    public void destroy() {

        if (factory != null) {
            factory.close();
        }
    }
}