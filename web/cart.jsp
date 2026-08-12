<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>

<html>

<head>

    <title>My Shopping Cart - Cloth Store</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/style.css">

    <style>

        body {
            margin: 0;
            background: #f5f5f5;
            font-family: Arial, sans-serif;
        }

        .cart-container {
            width: 92%;
            margin: 30px auto 60px auto;
        }

        .cart-title {
            text-align: center;
            font-size: 32px;
            margin-bottom: 30px;
            color: #111;
        }

        .cart-table {
            width: 100%;
            border-collapse: collapse;
            background: white;
            box-shadow: 0 3px 15px rgba(0, 0, 0, 0.10);
        }

        .cart-table th {
            padding: 20px;
            border: 1px solid #ddd;
            background: #f8f8f8;
            text-align: center;
            font-size: 18px;
            color: #111;
        }

        .cart-table td {
            padding: 20px;
            border: 1px solid #ddd;
            text-align: center;
            vertical-align: middle;
            font-size: 17px;
        }

        .cart-image {
            width: 100px;
            height: 100px;
            object-fit: contain;
        }

        .product-name {
            font-weight: bold;
            font-size: 18px;
        }

        .product-price {
            font-weight: bold;
            color: #e91e63;
        }

        .quantity {
            font-weight: bold;
        }

        .remove-form {
            margin: 0;
            padding: 0;
        }

        .remove-btn {
            background-color: red;
            color: white;
            border: none;
            padding: 12px 25px;
            font-size: 16px;
            font-weight: bold;
            border-radius: 5px;
            cursor: pointer;
        }

        .remove-btn:hover {
            background-color: darkred;
        }

        .empty-cart {
            width: 500px;
            max-width: 90%;
            margin: 80px auto;
            background: white;
            padding: 40px;
            text-align: center;
            border-radius: 10px;
            box-shadow: 0 3px 15px rgba(0, 0, 0, 0.10);
        }

        .empty-cart h2 {
            color: #333;
        }

        .empty-cart p {
            color: #777;
            font-size: 17px;
        }

        .shop-btn {
            display: inline-block;
            margin-top: 20px;
            padding: 12px 25px;
            background-color: #e91e63;
            color: white;
            text-decoration: none;
            border-radius: 5px;
            font-weight: bold;
        }

        .shop-btn:hover {
            background-color: #c2185b;
        }

    </style>

</head>

<body>

<%@include file="header.jsp"%>


<div class="cart-container">

    <h2 class="cart-title">
        My Shopping Cart
    </h2>


    <c:choose>


        <c:when test="${not empty cartList}">


            <table class="cart-table">

                <tr>

                    <th>
                        Image
                    </th>

                    <th>
                        Name
                    </th>

                    <th>
                        Price
                    </th>

                    <th>
                        Quantity
                    </th>

                    <th>
                        Action
                    </th>

                </tr>


                <c:forEach
                    var="product"
                    items="${products}"
                    varStatus="status">


                    <tr>


                        <!-- PRODUCT IMAGE -->

                        <td>

                            <img
                                src="${pageContext.request.contextPath}/images/${product.imageUrl}"
                                alt="${product.name}"
                                class="cart-image">

                        </td>


                        <!-- PRODUCT NAME -->

                        <td class="product-name">

                            ${product.name}

                        </td>


                        <!-- PRODUCT PRICE -->

                        <td class="product-price">

                            ₹${product.price}

                        </td>


                        <!-- QUANTITY -->

                        <td class="quantity">

                            ${cartList[status.index].quantity}

                        </td>


                        <!-- REMOVE BUTTON -->

                        <td>

                            <form
                                action="${pageContext.request.contextPath}/RemoveCartServlet"
                                method="post"
                                class="remove-form"
                                onsubmit="return confirm('Are you sure you want to remove this item?');">


                                <input
                                    type="hidden"
                                    name="cartId"
                                    value="${cartList[status.index].cartId}">


                                <button
                                    type="submit"
                                    class="remove-btn">

                                    Remove

                                </button>


                            </form>

                        </td>


                    </tr>


                </c:forEach>


            </table>


        </c:when>


        <c:otherwise>


            <div class="empty-cart">

                <h2>
                    Your Cart is Empty
                </h2>

                <p>
                    You have not added any products to your cart.
                </p>

                <a
                    href="${pageContext.request.contextPath}/ProductServlet"
                    class="shop-btn">

                    Continue Shopping

                </a>

            </div>


        </c:otherwise>


    </c:choose>


</div>


<%@include file="footer.jsp"%>

</body>

</html>