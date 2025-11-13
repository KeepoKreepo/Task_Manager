<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Create Task</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>

<body class="bg-light">

<div class="container mt-5">
    <div class="card shadow-sm p-4">

        <h3 class="mb-4">Create New Task</h3>

        <form action="${pageContext.request.contextPath}/tasks/new" method="post">

            <!-- Title -->
            <div class="mb-3">
                <label class="form-label">Task Title</label>
                <input type="text" name="title" class="form-control" required>
            </div>

            <!-- Description -->
            <div class="mb-3">
                <label class="form-label">Description</label>
                <textarea name="description" class="form-control" rows="3"></textarea>
            </div>

            <!-- Priority -->
            <div class="mb-3">
                <label class="form-label">Priority</label>
                <select name="priority" class="form-select">
                    <option value="LOW">LOW</option>
                    <option value="MEDIUM" selected>MEDIUM</option>
                    <option value="HIGH">HIGH</option>
                </select>
            </div>

            <!-- Project -->
            <div class="mb-3">
                <label class="form-label">Project</label>
                <input type="text" name="project" class="form-control" placeholder="e.g. Mobile App, Website, College Project">
            </div>

            <!-- Assign to user -->
            <div class="mb-4">
                <label class="form-label">Assign To</label>
                <select name="assignedTo" class="form-select">
                    <option value="">Unassigned</option>
                    <c:forEach items="${users}" var="u">
                        <option value="${u.id}">${u.name}</option>
                    </c:forEach>
                </select>
            </div>

            <!-- Submit -->
            <button type="submit" class="btn btn-primary w-100">Create Task</button>

        </form>

        <a href="${pageContext.request.contextPath}/dashboard" class="btn btn-link mt-3">
            ← Back to Dashboard
        </a>

    </div>
</div>

</body>
</html>