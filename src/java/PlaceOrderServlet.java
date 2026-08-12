import POJO.Orders;
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
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

@WebServlet("/PlaceOrderServlet")
public class PlaceOrderServlet extends HttpServlet {

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
                    "Hibernate initialization failed", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request,
                           HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        HttpSession httpSession = request.getSession(false);

        // Check login
        if (httpSession == null ||
            httpSession.getAttribute("userId") == null) {

            response.sendRedirect("login.jsp");
            return;
        }

        Integer userId =
                (Integer) httpSession.getAttribute("userId");

        Session session = null;
        Transaction tx = null;

        try {

            // Get product ID
            String productIdString =
                    request.getParameter("productId");

            // Get quantity
            String quantityString =
                    request.getParameter("quantity");

            System.out.println("User ID = " + userId);
            System.out.println("Product ID = " + productIdString);
            System.out.println("Quantity = " + quantityString);

            if (productIdString == null ||
                quantityString == null) {

                response.getWriter().println(
                        "<h2>Product ID or Quantity is missing.</h2>"
                );

                return;
            }

            int productId =
                    Integer.parseInt(productIdString);

            int quantity =
                    Integer.parseInt(quantityString);

            session = factory.openSession();

            tx = session.beginTransaction();

            // Get product
            Products product =
                    (Products) session.get(
                            Products.class,
                            productId
                    );

            if (product == null) {

                response.getWriter().println(
                        "<h2>Product not found.</h2>"
                );

                return;
            }

            System.out.println(
                    "Product = " + product.getName()
            );

            // Calculate total
            double total =
                    product.getPrice() * quantity;

            System.out.println(
                    "Total = " + total
            );

            // Create order
            Orders order = new Orders();

            order.setUserId(userId);
            order.setProductId(productId);
            order.setQuantity(quantity);
            order.setPrice(product.getPrice());
            order.setTotalAmount(total);
            order.setStatus("PLACED");

            // Save order
            session.save(order);

            tx.commit();

            System.out.println(
                    "Order saved successfully!"
            );

            // Send data to success page
            request.setAttribute(
                    "order",
                    order
            );

            request.setAttribute(
                    "product",
                    product
            );

            request.getRequestDispatcher(
                    "order-success.jsp"
            ).forward(request, response);

        } catch (Exception e) {

            if (tx != null) {
                try {
                    tx.rollback();
                } catch (Exception rollbackException) {
                    rollbackException.printStackTrace();
                }
            }

            // Print actual error in NetBeans console
            e.printStackTrace();

            // Show actual error in browser temporarily
            response.getWriter().println(
                    "<h2>Order Placement Failed</h2>"
            );

            response.getWriter().println(
                    "<h3>Error:</h3>"
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