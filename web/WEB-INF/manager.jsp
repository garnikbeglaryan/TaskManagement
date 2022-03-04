<%@ page import="model.User" %>
<%@ page import="java.util.List" %>
<%@ page import="model.Task" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<% List<User> users = (List<User>) request.getAttribute("users");%>
<% List<Task> tasks = (List<Task>) request.getAttribute("tasks");%>
<a href="/logout">logout</a>
<div style="width: 800px;">
    <div style="width: 50%; float: left">
        Add User:<br>
        <form action="/userRegister" method="post" enctype="multipart/form-data">
            <input type="text" name="name" placeholder="name"><br>
            <input type="text" name="surname" placeholder="surname"><br>
            <input type="text" name="email" placeholder="email"><br>
            <input type="password" name="password" placeholder="password"><br>
            <input type="file" name="image">
            <select name="type">
                <option value="USER">USER</option>
                <option value="MANAGER">MANAGER</option>
            </select><br>
            <input type="submit" value="OK">
        </form>
    </div>
    <div style="width: 50%; float: left">
        Add Task:<br>
        <form action="/addTask" method="post">
            <input type="text" name="name" placeholder="name"><br>
            <textarea name="description" placeholder="description"></textarea><br>
            <input type="date" name="date"><br>

            <select name="status">
                <option value="NEW">NEW</option>
                <option value="IN_PROGRESS">IN_PROGRESS</option>
                <option value="FINISHED">FINISHED</option>
            </select><br>
            <select name="user_id">
                <%
                    for (User user : users) { %>
                <option value="<%=user.getId() %>"><%=user.getName()%><%=user.getSurname()%>
                </option>
                <%
                    }
                %>
            </select><br>

            <input type="submit" value="Add">
        </form>
    </div>
</div>
<div>
    All Users:<br>
    <table border="1">
        <tr>
            <th>name</th>
            <th>surname</th>
            <th>email</th>
            <th>type</th>
            <th>action</th>
        </tr>
        <%
            for (User user : users) { %>
        <tr>
            <td><%=user.getName()%>
            </td>
            <td><%=user.getSurname()%>
            </td>
            <td><%=user.getEmail()%>
            </td>
            <td><%=user.getType().name()%>
            </td>
            <td><a href="/deleteUser?id=<%=user.getId()%>">Delete</a>
            </td>
            <td><% if (user.getPictureUrl() != null) {%>
                <img src="/image?path=<%=user.getPictureUrl()%>" width="50"/> <%}%> <br>
            </td>
        </tr>
        <%
            }
        %>
    </table>
</div>
<div>
    All Tasks:<br>
    <table border="1">
        <tr>
            <th>name</th>
            <th>description</th>
            <th>deadline</th>
            <th>status</th>
            <th>user</th>
            <th>action</th>
            <th>update user</th>
        </tr>
        <%
            for (Task task : tasks) { %>
        <tr>
            <td><%=task.getName()%>
            </td>
            <td><%=task.getDescription()%>
            </td>
            <td><%=task.getDeadline()%>
            </td>
            <td><%=task.getTaskStatus().name()%>
            <td><%=task.getUser().getName() + " " + task.getUser().getSurname()%>
            </td>
            <td><a href="/deleteTask?id=<%=task.getId()%>">Delete</a></td>
            </td>
            <td>
                <form action="/changeTaskUser" method="post">
                    <input type="hidden" name="userId" value="<%=task.getId()%>">
                    <select name="user">
                        <%
                            for (User user : users) { %>
                        <option value="<%=user.getId() %>"><%=user.getName()%><%=user.getSurname()%>
                        </option>
                        <%
                            }
                        %>
                    </select><br> <input type="submit" value="OK">
                </form>
            </td>
        </tr>
        <%
            }
        %>
    </table>

</div>
</body>
</html>
