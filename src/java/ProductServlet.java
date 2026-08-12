import POJO.Products;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@WebServlet("/ProductServlet")
public class ProductServlet extends HttpServlet {

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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Session session = factory.openSession();

        Query query = session.createQuery("from Products");
        List<Products> productList = query.list();

        System.out.println("Total Products = " + productList.size());

        for (Products p : productList) {
            System.out.println(p.getName() + " -> " + p.getImageUrl());
        }

        request.setAttribute("products", productList);

        session.close();

        request.getRequestDispatcher("products.jsp").forward(request, response);
    }

    @Override
    public void destroy() {
        if (factory != null) {
            factory.close();
        }
    }
}