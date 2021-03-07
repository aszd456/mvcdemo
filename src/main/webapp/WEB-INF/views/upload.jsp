<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <!--注意文件上传请求是 POST 请求，enctype 一定是 multipart/form-data-->
    <form action="/upload" method="post" enctype="multipart/form-data">
        <input type="file" name="file">
        <input type="submit" value="上传">
    </form>

    <!--多文件上传 key 相同的文件-->
    <form action="/upload2" method="post" enctype="multipart/form-data">
        <input type="file" name="files" multiple>
        <input type="submit" value="上传">
    </form>

    <!--  key 不同的文件-->
    <form action="/upload3" method="post" enctype="multipart/form-data">
        <input type="file" name="file1">
        <input type="file" name="file2">
        <input type="submit" value="上传">
    </form>

</body>
</html>
