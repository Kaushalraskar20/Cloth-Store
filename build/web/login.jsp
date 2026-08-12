<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>

    <title>Login - Cloth Store</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/css/style.css">

    <style>

        body{
            background:#f5f5f5;
        }

        .login-container{

            width:400px;
            margin:50px auto;
            background:white;
            padding:30px;
            border-radius:10px;
            box-shadow:0px 5px 15px rgba(0,0,0,.2);

        }

        .login-container h2{

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

        .form-group input{

            width:100%;
            padding:10px;
            border:1px solid #ccc;
            border-radius:5px;

        }

        .btn-login{

            width:100%;
            padding:12px;
            background:#e91e63;
            color:white;
            border:none;
            border-radius:5px;
            cursor:pointer;
            font-size:16px;

        }

        .btn-login:hover{

            background:#c2185b;

        }

        .signup-link{

            text-align:center;
            margin-top:20px;

        }

    </style>

</head>

<body>

<%@include file="header.jsp"%>

<div class="login-container">

<h2>Login</h2>

<form action="LoginServlet" method="post">

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

<button type="submit"
        class="btn-login">

Login

</button>

</form>

<div class="signup-link">

Don't have an account?

<a href="signup.jsp">

Signup

</a>

</div>

</div>

<%@include file="footer.jsp"%>

</body>
</html>