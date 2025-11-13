<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Dashboard - Task Manager</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.1/font/bootstrap-icons.css" rel="stylesheet">

</head>
<body class="bg-light">

<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container-fluid">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/dashboard">TaskManager</a>
        <div class="d-flex">
            <span class="navbar-text text-white me-3">
                Welcome, ${sessionScope.username}
            </span>
            <a href="${pageContext.request.contextPath}/logout" class="btn btn-outline-light btn-sm">Logout</a>
        </div>
    </div>
</nav>

<div class="container py-5">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2 class="fw-bold">Your Tasks</h2>
        <a href="${pageContext.request.contextPath}/tasks/new" class="btn btn-primary">
            <i class="bi bi-plus-circle"></i> New Task
        </a>
    </div>

    <c:if test="${not empty tasks}">
        <table class="table table-hover align-middle shadow-sm bg-white rounded">
            <thead class="table-dark">
            <tr>
                <th>Title</th>
                <th>Description</th>

            </tr>
            </thead>
            <tbody>
            <c:forEach var="t" items="${tasks}">
                <tr>
                    <!-- Task Title (editable) -->
                    <td>
                        <form action="${pageContext.request.contextPath}/tasks/update-details" method="post" style="display:flex; gap:5px;">
                            <input type="hidden" name="taskId" value="${t.id}" />
                            <input type="text" name="title" value="${t.title}" class="form-control" style="width:150px;" />
                        </form>
                    </td>

                    <!-- Task Description (editable) -->
                    <td>
                        <input type="text" name="description" value="${t.description}" class="form-control" style="width:200px;" />
                    </td>
                    <!-- Status Dropdown -->
                    <td>
                        <form action="${pageContext.request.contextPath}/tasks/update-status" method="post">
                            <input type="hidden" name="taskId" value="${t.id}">
                            <select name="status" onchange="this.form.submit()" class="form-select form-select-sm">
                                <option value="OPEN" ${t.status == 'OPEN' ? 'selected' : ''}>Open</option>
                                <option value="IN_PROGRESS" ${t.status == 'IN_PROGRESS' ? 'selected' : ''}>In Progress</option>
                                <option value="DONE" ${t.status == 'DONE' ? 'selected' : ''}>Done</option>
                            </select>
                        </form>
                    </td>

                    <!-- Edit and Delete -->
                    <td>
                        <button type="submit" class="btn btn-primary btn-sm">💾 Save</button>
                        </form>

                        <form action="${pageContext.request.contextPath}/tasks/delete" method="post" style="display:inline;">
                            <input type="hidden" name="taskId" value="${t.id}">
                            <button type="submit" class="btn btn-danger btn-sm">🗑 Delete</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>

    <c:if test="${empty tasks}">
        <div class="alert alert-secondary text-center">
            No tasks found. <a href="${pageContext.request.contextPath}/tasks/new" class="alert-link">Create one!</a>
        </div>
    </c:if>
</div>

</body>
</html>
