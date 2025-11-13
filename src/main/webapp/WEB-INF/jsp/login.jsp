<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login</title>

    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://rsms.me/inter/inter.css" rel="stylesheet">

    <style>
        body {
            background: linear-gradient(135deg, #eef2ff, #e0e7ff);
            font-family: 'Inter', sans-serif;
        }
        .card-modern {
            border-radius: 18px;
            padding: 28px;
            background: white;
            box-shadow: 0 8px 24px rgba(0,0,0,0.07);
        }
        .brand-title {
            font-weight: 700;
            font-size: 30px;
        }
        .subtitle {
            color: #5a5f75;
        }
    </style>
</head>

<body>

<div class="container d-flex justify-content-center align-items-center" style="min-height: 100vh;">
    <div class="col-md-5">

        <div class="card-modern">

            <div class="text-center mb-4">
                <div class="brand-title">TaskManager</div>
                <div class="subtitle">Welcome back — please sign in</div>
            </div>

            <c:if test="${not empty error}">
                <div class="alert alert-danger">${error}</div>
            </c:if>

            <form action="${pageContext.request.contextPath}/login" method="post">
                <div class="mb-3">
                    <label class="form-label fw-semibold">Email</label>
                    <input type="email" name="email" class="form-control form-control-lg" required>
                </div>

                <div class="mb-4">
                    <label class="form-label fw-semibold">Password</label>
                    <input type="password" name="password" class="form-control form-control-lg" required>
                </div>

                <button type="submit" class="btn btn-primary btn-lg w-100 rounded-pill">
                    Login
                </button>
            </form>

            <div class="text-center mt-4">
                <a href="${pageContext.request.contextPath}/register" class="text-decoration-none fw-semibold">
                    Create an account →
                </a>
            </div>

        </div>

    </div>
</div>

</body>
</html>
