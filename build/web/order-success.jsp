<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>

<head>

    <title>Order Successful</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/style.css">

    <style>

        .success-container {

            width: 500px;
            margin: 80px auto;
            background: white;
            padding: 40px;
            text-align: center;
            border-radius: 10px;
            box-shadow: 0 5px 15px rgba(0,0,0,0.15);

        }

        .success-icon {

            font-size: 60px;
            color: #4CAF50;

        }

        .success-container h1 {

            color: #4CAF50;
            margin: 20px 0;

        }

        .continue-btn {

            display: inline-block;
            margin-top: 20px;
            padding: 12px 25px;
            background: #e91e63;
            color: white;
            text-decoration: none;
            border-radius: 5px;

        }

    </style>

</head>

<body>

<%@include file="header.jsp"%>

<div class="success-container">

    <div class="success-icon">
        ✓
    </div>

    <h1>Order Placed Successfully!</h1>

    <p>
        Thank you for shopping with Cloth Store.
    </p>

    <p>
        Order ID:
        <strong>${order.orderId}</strong>
    </p>

    <p>
        Product:
        <strong>${product.name}</strong>
    </p>

    <p>
        Total:
        <strong>₹ ${order.totalAmount}</strong>
    </p>

    <a href="ProductServlet"
       class="continue-btn">

        Continue Shopping

    </a>

</div>

<%@include file="footer.jsp"%>

</body>

</html>