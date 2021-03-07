<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="/doAdd4" method="post">
    <table>
        <tr>
            <td>书名：</td>
            <td><input type="text" name="name"></td>
        </tr>
        <tr>
            <td>作者姓名：</td>
            <td><input type="text" name="author.name"></td>
        </tr>
        <tr>
            <td>作者年龄：</td>
            <td><input type="text" name="author.age"></td>
        </tr>
        <tr>
            <td>价格：</td>
            <td><input type="text" name="price"></td>
        </tr>
        <tr>
            <td>出版时间：</td>
            <td><input type="text" name="time"></td>
        </tr>
        <tr>
            <td>是否上架：</td>
            <td>
                <input type="radio" value="true" name="ispublic">是
                <input type="radio" value="false" name="ispublic">否
            </td>
        </tr>
        <tr>
            <td>兴趣爱好：</td>
            <td>
                <input type="checkbox" name="favorites" value="足球">足球
                <input type="checkbox" name="favorites" value="篮球">篮球
                <input type="checkbox" name="favorites" value="乒乓球">乒乓球
            </td>
        </tr>
        <tr>
           <td colspan="2">
               <input type="submit" value="添加">
           </td>
        </tr>
    </table>
</form>
</body>
</html>
