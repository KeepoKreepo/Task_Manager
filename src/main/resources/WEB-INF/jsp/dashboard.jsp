<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Dashboard</title>

    <!-- Bootstrap + Inter Font -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://rsms.me/inter/inter.css" rel="stylesheet">

    <style>
        body {
            background: linear-gradient(135deg, #eef2ff, #e0e7ff);
            font-family: 'Inter', sans-serif;
        }

        .card-modern {
            border-radius: 18px;
            background: rgba(255, 255, 255, 0.85);
            backdrop-filter: blur(10px);
            padding: 24px;
            box-shadow: 0 8px 25px rgba(0,0,0,0.08);
        }

        .navbar-modern {
            background: rgba(255, 255, 255, .7) !important;
            backdrop-filter: blur(12px);
            border-bottom: 1px solid rgba(0,0,0,0.08);
        }

        .badge-status {
            padding: 6px 12px;
            font-size: 13px;
            border-radius: 12px;
        }
        .badge-priority {
            padding: 6px 12px;
            font-size: 13px;
            border-radius: 12px;
        }

        .badge-open       { background: #dbeafe; color: #1e40af; }
        .badge-progress   { background: #fef9c3; color: #854d0e; }
        .badge-done       { background: #dcfce7; color: #166534; }

        .badge-low        { background: #e0f2fe; color: #0369a1; }
        .badge-medium     { background: #fae8ff; color: #7e22ce; }
        .badge-high       { background: #fee2e2; color: #b91c1c; }

        select.modern-select {
            border-radius: 10px;
            padding: 4px 8px;
            font-size: 13px;
        }
    </style>
</head>

<body>

<!-- NAVBAR -->
<nav class="navbar navbar-expand-lg navbar-light navbar-modern shadow-sm py-3">
    <div class="container">

        <a class="navbar-brand fw-bold" href="#" style="font-size: 22px;">TaskManager</a>

        <div class="d-flex align-items-center">
            <span class="me-3 text-muted fw-semibold">Hi, ${username}</span>
            <a href="${pageContext.request.contextPath}/logout" class="btn btn-outline-danger rounded-pill">
                Logout
            </a>
        </div>

    </div>
</nav>

<!-- CONTENT -->
<div class="container mt-4">

    <div class="card-modern mb-4">

        <div class="d-flex justify-content-between align-items-center mb-3">
            <h3 class="fw-bold">Your Tasks</h3>
            <a href="${pageContext.request.contextPath}/tasks/new" class="btn btn-primary rounded-pill px-4">
                + New Task
            </a>
        </div>

        <!-- Project Filter -->
        <form method="get" class="mb-4">
            <label class="form-label fw-semibold">Filter by Project</label>
            <select name="project" class="form-select modern-select" onchange="this.form.submit()">
                <option value="">All Projects</option>

                <c:forEach items="${projects}" var="p">
                    <option value="${p}"
                            <c:if test="${param.project == p}">selected</c:if>>
                            ${p}
                    </option>
                </c:forEach>
            </select>
        </form>

        <!-- TASKS TABLE -->
        <div class="table-responsive">
            <table class="table align-middle">

                <thead class="table-light">
                <tr>
                    <th>Title</th>
                    <th>Status</th>
                    <th>Priority</th>
                    <th>Project</th>
                    <th>Assigned To</th>
                    <th class="text-end">Actions</th>
                </tr>
                </thead>

                <tbody>

                <c:forEach items="${tasks}" var="t">
                    <tr>
                        <!-- TITLE -->
                        <td class="fw-semibold">${t.title}</td>

                        <!-- STATUS -->
                        <td>
                            <form action="${pageContext.request.contextPath}/tasks/update-status" method="post">
                                <input type="hidden" name="id" value="${t.id}">
                                <select class="form-select modern-select" name="status" onchange="this.form.submit()">
                                    <option value="OPEN"        ${t.status=='OPEN' ? 'selected':''}>OPEN</option>
                                    <option value="IN_PROGRESS" ${t.status=='IN_PROGRESS' ? 'selected':''}>IN PROGRESS</option>
                                    <option value="DONE"        ${t.status=='DONE' ? 'selected':''}>DONE</option>
                                </select>
                            </form>
                        </td>

                        <!-- PROJECT — DISPLAY ONLY -->
                        <td>
            <span class="badge bg-secondary rounded-pill px-3 py-2">
                    ${t.project != null ? t.project : '—'}
            </span>
                        </td>

                        <!-- ASSIGNED TO -->
                        <td>
                            <form action="${pageContext.request.contextPath}/tasks/assign" method="post">
                                <input type="hidden" name="id" value="${t.id}">
                                <select name="assignedTo" class="form-select modern-select" onchange="this.form.submit()">
                                    <option value="">Unassigned</option>
                                    <c:forEach items="${users}" var="u">
                                        <option value="${u.id}"
                                                <c:if test="${t.assignedTo == u.id}">selected</c:if>>
                                                ${u.name}
                                        </option>
                                    </c:forEach>
                                </select>
                            </form>
                        </td>

                        <!-- ACTIONS -->
                        <td class="text-end">
                            <form action="${pageContext.request.contextPath}/tasks/delete"
                                  method="post"
                                  onsubmit="return confirm('Delete this task?');">
                                <input type="hidden" name="id" value="${t.id}">
                                <button class="btn btn-danger btn-sm rounded-pill px-3">Delete</button>
                            </form>
                        </td>

                    </tr>
                </c:forEach>

                </tbody>
            </table>
        </div>

    </div>

</div>

</body>
</html>