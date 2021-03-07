package org.test.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @Author: zhouliansheng
 * @Date: 2021/3/7 23:23
 */

//SpringMVC 实现文件上传
//SpringMVC 中对文件上传做了封装，我们可以更加方便的实现文件上传。从 Spring3.1 开始，对于文件上传，提供了两个处理器：
// CommonsMultipartResolver
// StandardServletMultipartResolver
//第一个处理器兼容性较好，可以兼容 Servlet3.0 之前的版本，
// 但是它依赖了 commons-fileupload 这个第三方工具，所以如果使用这个，一定要添加 commons-fileupload 依赖
//第二个处理器兼容性较差，它适用于 Servlet3.0 之后的版本，它不依赖第三方工具，使用它，可以直接做文件上传

@Controller
public class FileUploadController {
    /**********CommonsMultipartResolver**************/
    private static final String FORMAT = "yyyy-MM-dd";

//    SimpleDateFormat sdf = new SimpleDateFormat("/yyyy/MM/dd/");

    @GetMapping("/fileUpload")
    public String fileUpload() {
        return "upload";
    }

    /**
     * 这个文件上传方法中，一共做了四件事：
     * 解决文件保存路径，这里是保存在项目运行目录下的 img 目录下，然后利用日期继续宁分类
     * 处理文件名问题，使用 UUID 做新的文件名，用来代替旧的文件名，可以有效防止文件名冲突
     * 保存文件
     * 生成文件访问路径
     *
     * @param file
     * @param req
     * @return
     */
    @RequestMapping("/upload")
    @ResponseBody
    public String upload(MultipartFile file, HttpServletRequest req) {
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT);
        String format = sdf.format(new Date());
        String realPath = req.getServletContext().getRealPath("/WEB-INF/img/") + format;
        File folder = new File(realPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String oldName = file.getOriginalFilename();
        String newName = UUID.randomUUID().toString() + oldName.substring(oldName.lastIndexOf("."));
        try {
            file.transferTo(new File(folder, newName));
            return req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + "/WEB-INF/img/" + format + "/" + newName;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "failed";
    }

    /************* 多文件上传****************/

    /**
     * 主要是 input 节点中多了 multiple 属性。后端用一个数组来接收文件即可
     *
     * @param files
     * @param req
     */
    @RequestMapping("/upload2")
    @ResponseBody
    public void upload2(MultipartFile[] files, HttpServletRequest req) {
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT);
        String format = sdf.format(new Date());
        String realPath = req.getServletContext().getRealPath("/img") + format;
        File folder = new File(realPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        try {
            for (MultipartFile file : files) {
                String oldName = file.getOriginalFilename();
                String newName = UUID.randomUUID().toString() + oldName.substring(oldName.lastIndexOf("."));
                file.transferTo(new File(folder, newName));
                String url = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + "/img" + format + newName;
                System.out.println(url);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/upload3")
    @ResponseBody
    public void upload3(MultipartFile file1, MultipartFile file2, HttpServletRequest req) {
        SimpleDateFormat sdf = new SimpleDateFormat(FORMAT);
        String format = sdf.format(new Date());
        String realPath = req.getServletContext().getRealPath("/img") + format;
        File folder = new File(realPath);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        try {
            String oldName = file1.getOriginalFilename();
            String newName = UUID.randomUUID().toString() + oldName.substring(oldName.lastIndexOf("."));
            file1.transferTo(new File(folder, newName));
            String url1 = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + "/img" + format + newName;
            System.out.println(url1);
            String oldName2 = file2.getOriginalFilename();
            String newName2 = UUID.randomUUID().toString() + oldName2.substring(oldName2.lastIndexOf("."));
            file2.transferTo(new File(folder, newName2));
            String url2 = req.getScheme() + "://" + req.getServerName() + ":" + req.getServerPort() + "/img" + format + newName2;
            System.out.println(url2);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
