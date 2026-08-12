<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>

    <title>Signup - Cloth Store</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/style.css">

    <style>

        body{
            background:#f5f5f5;
        }

        .signup-container{

            width:450px;
            margin:50px auto;
            background:#ffffff;
            padding:30px;
            border-radius:10px;
            box-shadow:0px 5px 15px rgba(0,0,0,0.2);

        }

        .signup-container h2{

            text-align:center;
            color:#e91e63;
            margin-bottom:25px;

        }

        .form-group{

            margin-bottom:15px;

        }

        .form-group label{

            display:block;
            margin-bottom:5px;
            font-weight:bold;

        }

        .form-group input,
        .form-group textarea{

            width:100%;
            padding:10px;
            border:1px solid #ccc;
            border-radius:5px;
            font-size:15px;

        }

        .btn-register{

            width:100%;
            padding:12px;
            background:#e91e63;
            color:white;
            border:none;
            border-radius:5px;
            font-size:16px;
            cursor:pointer;

        }

        .btn-register:hover{

            background:#c2185b;

        }

        .login-link{

            text-align:center;
            margin-top:20px;

        }

        .login-link a{

            text-decoration:none;
            color:#e91e63;
            font-weight:bold;

        }

    </style>

</head>

<body>

<%@ include file="header.jsp" %>

<div class="signup-container">

    <h2>Create Account</h2>

    <form action="SignupServlet" method="post">

        <div class="form-group">

            <label>Full Name</label>

            <input type="text"
                   name="name"
                   required>

        </div>

        <div class="form-group">

            <label>Email</label>

            <input type="email"
                   name="email"
                   required>

        </div>

        <div class="form-group">

            <label>Password</label>

            <input type="password"
                   name="password"
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

        <button type="submit" class="btn-register">

            Register

        </button>

    </form>

    <div class="login-link">

        Already have an account?

        <a href="login.jsp">

            Login

        </a>

    </div>

</div>

<%@ include file="footer.jsp" %>

</body>
</html>