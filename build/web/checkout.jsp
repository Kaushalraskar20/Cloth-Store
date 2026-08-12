<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>

<head>

    <title>Checkout - Cloth Store</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/style.css">

    <style>

        .checkout-container {
            width: 90%;
            max-width: 900px;
            margin: 50px auto;
            display: flex;
            gap: 30px;
        }

        .product-box,
        .address-box {
            background: white;
            padding: 25px;
            border-radius: 10px;
            box-shadow: 0 4px 12px rgba(0,0,0,0.12);
        }

        .product-box {
            width: 45%;
        }

        .address-box {
            width: 55%;
        }

        .product-box img {
            width: 100%;
            height: 300px;
            object-fit: cover;
            border-radius: 8px;
        }

        .product-name {
            font-size: 24px;
            font-weight: bold;
            margin-top: 15px;
        }

        .price {
            color: #e91e63;
            font-size: 22px;
            font-weight: bold;
            margin-top: 10px;
        }

        .form-group {
            margin-bottom: 15px;
        }

        .form-group label {
            display: block;
            font-weight: bold;
            margin-bottom: 5px;
        }

        .form-group input,
        .form-group textarea {
            width: 100%;
            padding: 10px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }

        .place-order {
            width: 100%;
            padding: 13px;
            background: #e91e63;
            color: white;
            border: none;
            border-radius: 5px;
            font-size: 16px;
            cursor: pointer;
        }

        .place-order:hover {
            background: #c2185b;
        }

    </style>

</head>

<body>

<%@include file="header.jsp"%>

<div class="checkout-container">

    <!-- Product Details -->

    <div class="product-box">

        <img src="${pageContext.request.contextPath}/images/${product.imageUrl}"
             alt="${product.name}">

        <div class="product-name">
            ${product.name}
        </div>

        <p>
            Category: ${product.category}
        </p>

        <p>
            Size: ${product.size}
        </p>

        <div class="price">
            ₹ ${product.price}
        </div>

        <p>
            Quantity: 1
        </p>

    </div>


    <!-- Delivery Details -->

    <div class="address-box">

        <h2>Delivery Details</h2>

        <br>

        <form action="PlaceOrderServlet" method="post">

            <input type="hidden"
                   name="productId"
                   value="${product.id}">

            <input type="hidden"
                   name="quantity"
                   value="1">

            <div class="form-group">

                <label>Full Name</label>

                <input type="text"
                       name="name"
                       required>

            </div>

            <div class="form-group">

                <label>Mobile Number</label>

                <input type="text"
                       name="mobile"
                       required>

            </div>

            <div class="form-group">

                <label>Address</label>

                <textarea name="address"
                          rows="4"
                          required></textarea>

            </div>

            <div class="form-group">

                <label>Payment Method</label>

                <select name="paymentMethod"
                        style="width:100%;padding:10px;">

                    <option value="COD">
                        Cash on Delivery
                    </option>

                    <option value="ONLINE">
                        Online Payment
                    </option>

                </select>

            </div>

            <h3>
                Total: ₹ ${product.price}
            </h3>

            <br>

            <button type="submit"
                    class="place-order">

                Place Order

            </button>

        </form>

    </div>

</div>

<%@include file="footer.jsp"%>

</body>

</html>