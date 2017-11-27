<%@ page contentType="text/html; charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css" integrity="sha384-PsH8R72JQ3SOdhVi3uxftmaW6Vc51MKb0q5P2rRUpPvrszuE4W1povHYgTpBfshb" crossorigin="anonymous">
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
    <div class="container">
        <form id="login-form" class="form-signin" method="post" action="<c:url value="/login"/>">
            <h2 class="form-signin-heading">Please sign in</h2>
            <input id="login-input" name="username" type="login" id="inputLogin" class="form-control" placeholder="Your login" required autofocus>
            <input name="password" type="password" id="inputPassword" class="form-control" placeholder="Your password" required>
            <button class="btn btn-lg btn-primary btn-block ps13-shadow" type="submit">Sign in</button>
        </form>
        <a href="<c:url value="/register"/>" class="btn btn-success btn-lg btn-block">Register</a>
    </div>
</body>
</html>