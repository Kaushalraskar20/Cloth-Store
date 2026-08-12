<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">

<header>

    <div class="navbar">

        <div class="logo">
            Cloth<span>Store</span>
        </div>

        <ul class="nav-links">

            <li><a href="index.jsp">Home</a></li>

            <li><a href="#">Men</a></li>

            <li><a href="#">Women</a></li>

            <li><a href="ProductServlet">Products</a></li>

            <li>
    <a href="${pageContext.request.contextPath}/CartDisplayServlet">
        Cart
    </a>
</li>

        </ul>

        <div class="search">

            <input type="text" placeholder="Search Products">

        </div>

        <div>

            <c:choose>

                <c:when test="${empty sessionScope.userName}">

                    <a href="login.jsp" class="btn">Login</a>

                    <a href="signup.jsp" class="btn">Signup</a>
                    
                    <a href="LogoutServlet" class="btn">Logout</a>

                </c:when>

                <c:otherwise>

                    <span style="font-weight:bold;color:#e91e63;margin-right:15px;">
                        Welcome ${sessionScope.userName}
                    </span>

                    <a href="LogoutServlet" class="btn">Logout</a>

                </c:otherwise>

            </c:choose>

        </div>

    </div>

</header>