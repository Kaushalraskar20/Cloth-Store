import POJO.Cart;

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
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

@WebServlet("/CartServlet")
public class CartServlet extends HttpServlet {

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

        // Get logged-in user ID
        Integer userId =
                (Integer) httpSession.getAttribute("userId");

        // Get product ID
        String productIdString =
                request.getParameter("id");

        if (productIdString == null ||
            productIdString.trim().isEmpty()) {

            response.sendRedirect("ProductServlet");
            return;
        }

        Session session = null;
        Transaction transaction = null;

        try {

            Integer productId =
                    Integer.parseInt(productIdString);

            System.out.println("--------------------------------");
            System.out.println("ADD TO CART");
            System.out.println("User ID    : " + userId);
            System.out.println("Product ID : " + productId);

            session = factory.openSession();

            transaction = session.beginTransaction();

            // Check whether this product is already
            // present in this user's cart

            Query query = session.createQuery(
                    "from Cart " +
                    "where userId = :userId " +
                    "and productId = :productId"
            );

            query.setParameter("userId", userId);
            query.setParameter("productId", productId);

            Cart cart = (Cart) query.uniqueResult();

            if (cart == null) {

                // Product is not in cart
                // Create a new cart object

                cart = new Cart();

                cart.setUserId(userId);
                cart.setProductId(productId);
                cart.setQuantity(1);

                session.save(cart);

                System.out.println(
                        "New product added to cart."
                );

            } else {

                // Product already exists
                // Increase quantity

                cart.setQuantity(
                        cart.getQuantity() + 1
                );

                session.update(cart);

                System.out.println(
                        "Product already exists. Quantity increased."
                );
            }

            transaction.commit();

            System.out.println(
                    "Cart operation successful."
            );

            System.out.println("--------------------------------");

            // Go to cart
            response.sendRedirect("CartDisplayServlet");

        } catch (NumberFormatException e) {

            e.printStackTrace();

            if (transaction != null) {
                transaction.rollback();
            }

            response.sendRedirect("ProductServlet");

        } catch (Exception e) {

            e.printStackTrace();

            if (transaction != null) {

                try {
                    transaction.rollback();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            response.setContentType(
                    "text/html;charset=UTF-8"
            );

            response.getWriter().println(
                    "<h2>Add to Cart Failed</h2>"
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