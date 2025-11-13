<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Login - Task Manager</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">

</head>
<body class="bg-light d-flex justify-content-center align-items-center vh-100">

<div class="card shadow-sm" style="width: 400px;">
    <div class="card-header bg-dark text-white text-center">
        <h4>Login</h4>
    </div>
    <div class="card-body">
        <c:if test="${not empty error}">
            <div class="alert alert-danger text-center">${error}</div>
        </c:if>

        <form method="post" action="${pageContext.request.contextPath}/login">
            <div class="mb-3">
                <label class="form-label">Email</label>
                <input type="email" name="email" class="form-control" required>
            </div>

            <div class="mb-3">
                <label class="form-label">Password</label>
                <input type="password" name="password" class="form-control" required>
            </div>

            <button type="submit" class="btn btn-dark w-100">Login</button>
        </form>

        <div class="text-center mt-3">
            <p class="mb-0">Don't have an account?
                <a href="${pageContext.request.contextPath}/register" class="text-decoration-none">Register here</a>
            </p>
        </div>
    </div>
</div>

</body>
</html>