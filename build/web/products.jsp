<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
    <title>Cloth Store - Products</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/style.css">

    <style>

        .container{
            width:90%;
            margin:40px auto;
        }

        h1{
            text-align:center;
            margin-bottom:30px;
            color:#333;
        }

        .grid{
            display:grid;
            grid-template-columns:repeat(auto-fit,minmax(250px,1fr));
            gap:25px;
        }

        .card{
            background:white;
            border-radius:10px;
            overflow:hidden;
            box-shadow:0 5px 15px rgba(0,0,0,.15);
            transition:.3s;
        }

        .card:hover{
            transform:translateY(-8px);
        }

        .card img{
            width:100%;
            height:250px;
            object-fit:cover;
        }

        .card-body{
            padding:15px;
        }

        .card-body h3{
            margin-bottom:10px;
            color:#333;
        }

        .category{
            color:#777;
            margin-bottom:8px;
        }

        .size{
            margin-bottom:10px;
        }

        .price{
            color:#e91e63;
            font-size:22px;
            font-weight:bold;
            margin-bottom:15px;
        }

        .buttons{
            display:flex;
            justify-content:space-between;
        }

        .cart-btn,
        .buy-btn{

            text-decoration:none;
            color:white;
            padding:10px 15px;
            border-radius:5px;
            font-weight:bold;
        }

        .cart-btn{
            background:#2196F3;
        }

        .buy-btn{
            background:#4CAF50;
        }

        .cart-btn:hover{
            background:#1976D2;
        }

        .buy-btn:hover{
            background:#388E3C;
        }

    </style>

</head>

<body>

<%@ include file="header.jsp"%>

<div class="container">

    <h1>Our Collection</h1>

    <div class="grid">

        <c:forEach var="product" items="${products}">

            <div class="card">

                <img src="${pageContext.request.contextPath}/images/${product.imageUrl}"
                     alt="${product.name}">

                <div class="card-body">

                    <h3>${product.name}</h3>

                    <p class="category">
                        Category : ${product.category}
                    </p>

                    <p class="size">
                        Size : ${product.size}
                    </p>

                    <p class="price">
                        ₹ ${product.price}
                    </p>

                    <div class="buttons">

                        <a href="${pageContext.request.contextPath}/CartServlet?id=${product.id}"
                        class="cart-btn">
                             Add to Cart
                        </a>

                        <a href="BuyNowServlet?id=${product.id}"
                            accesskey=""class="buy-btn">
                            Buy Now
                        </a>

                    </div>

                </div>

            </div>

        </c:forEach>

    </div>

</div>

<%@ include file="footer.jsp"%>

</body>
</html>