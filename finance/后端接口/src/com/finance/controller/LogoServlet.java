package com.finance.controller;

import com.alibaba.fastjson.JSON;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author:Wang
 * @className: LogoServlet
 * @description:
 * @date: 2021/6/1 21:18
 * @version:0.1
 * @since:1.8
 */
@WebServlet("/upload2/*")
public class LogoServlet extends BaseServlet {

    public void image(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("到了吗");
        //获取代码运行的在服务器上的本地路径
        String basePath = req.getServletContext().getRealPath("/");
        //拼接上指定的目录(文件夹)
        String folderPath = "mypicture/";
        //文件上传总路径
        String returnPath = "";
        //创建本地文件临时工厂
        DiskFileItemFactory dff = new DiskFileItemFactory();
        //解析输入流核心对象
        ServletFileUpload servletFileUpload = new ServletFileUpload(dff);
        System.out.println("到了吗2");
        String filename = null;
        try {
            //解析后封装成的文件对象
            List<FileItem> list = servletFileUpload.parseRequest(req);
            for (FileItem fi : list) {
                System.out.println("到了吗3");
                if (!fi.isFormField()) {
                    //处理文件
                    //文件名
                    filename = UUID.randomUUID().toString() + "_" + fi.getName();
                    File newfile = new File(basePath + folderPath + filename);
                    //从临时文件 写入到本地创建的新文件
                    System.out.println("到了吗4");
                    fi.write(newfile);
                    returnPath = newfile.getPath();
                    System.out.println("到了吗5");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("到了吗6");
        resp.setContentType("text/html;charset=utf-8");
        Map<String, Object> returnMap = new HashMap();
        System.out.println(folderPath + UUID.randomUUID().toString() + "_" + filename);
        System.out.println(filename + "--------");
        returnMap.put("imgsrc", "http://localhost:8080/finance/" + folderPath + filename);

        PrintWriter writer = resp.getWriter();
        writer.print(JSON.toJSONString(returnMap));
        System.out.println(returnPath);
        writer.flush();
        writer.close();
    }
}
