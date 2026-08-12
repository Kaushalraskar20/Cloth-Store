import POJO.Cart;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;


public class RemoveCartServlet extends HttpServlet {


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
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {


        System.out.println(
                "================================"
        );

        System.out.println(
                "REMOVE CART SERVLET CALLED"
        );


        // Get current session

        HttpSession httpSession =
                request.getSession(false);


        // Check whether user is logged in

        if (httpSession == null ||
            httpSession.getAttribute("userId") == null) {


            System.out.println(
                    "User is not logged in."
            );


            response.sendRedirect(
                    "login.jsp"
            );

            return;
        }


        // Get logged-in user ID

        Integer userId =
                (Integer) httpSession.getAttribute(
                        "userId"
                );


        // Get cart ID from form

        String cartIdString =
                request.getParameter(
                        "cartId"
                );


        System.out.println(
                "User ID = " + userId
        );

        System.out.println(
                "Cart ID = " + cartIdString
        );


        // Check cart ID

        if (cartIdString == null ||
            cartIdString.trim().isEmpty()) {


            System.out.println(
                    "ERROR: Cart ID is missing."
            );


            response.sendRedirect(
                    "CartDisplayServlet"
            );

            return;
        }


        Session session = null;

        Transaction transaction = null;


        try {


            // Convert cart ID

            Integer cartId =
                    Integer.parseInt(
                            cartIdString
                    );


            // Open Hibernate session

            session =
                    factory.openSession();


            // Start transaction

            transaction =
                    session.beginTransaction();


            // Find cart item

            Cart cart =
                    (Cart) session.get(
                            Cart.class,
                            cartId
                    );


            if (cart == null) {


                System.out.println(
                        "Cart item NOT FOUND."
                );


                transaction.rollback();


            } else {


                System.out.println(
                        "Cart item FOUND."
                );


                System.out.println(
                        "Database Cart ID = "
                        + cart.getCartId()
                );


                System.out.println(
                        "Database User ID = "
                        + cart.getUserId()
                );


                /*
                 * Make sure the cart item belongs
                 * to the logged-in user.
                 */

                if (cart.getUserId() != null &&
                    cart.getUserId().equals(userId)) {


                    // Delete item

                    session.delete(cart);


                    // Commit deletion

                    transaction.commit();


                    System.out.println(
                            "ITEM REMOVED SUCCESSFULLY."
                    );


                } else {


                    System.out.println(
                            "ERROR: This cart item "
                            + "does not belong to this user."
                    );


                    transaction.rollback();

                }

            }


            System.out.println(
                    "================================"
            );


            // Go back to cart

            response.sendRedirect(
                    "CartDisplayServlet"
            );


        } catch (NumberFormatException e) {


            e.printStackTrace();


            if (transaction != null) {

                try {

                    transaction.rollback();

                } catch (Exception ex) {

                    ex.printStackTrace();

                }
            }


            response.sendRedirect(
                    "CartDisplayServlet"
            );


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
                    "<h2>Remove Item Failed</h2>"
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