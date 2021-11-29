package com.example.demo.controller;


import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.example.demo.pojo.Result;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

@RestController
@RequestMapping("/files")
public class FileController {
    @Value("${server.port}")
    private String port;
    private static final String ip="http://localhost";
    @PostMapping("/editor/upload")
    public JSON editorUpload(MultipartFile file) throws IOException {
        String filename = file.getOriginalFilename();
        //定义文件唯一标识
        String flag= IdUtil.fastSimpleUUID();
        String filePath=System.getProperty("user.dir")+"/src/main/resources/files/"+flag+filename;
        FileUtil.writeBytes(file.getBytes(),filePath);
        String url=ip+":"+port+"/files/"+flag;
        JSONObject json=new JSONObject();
        json.set("errno",0);
        JSONArray arr=new JSONArray();
        JSONObject data=new JSONObject();
        data.set("url",url);
        arr.add(data);
        json.set("data",arr);
        return json;
    }

    @PostMapping("/upload")
    public Result<?> upload(MultipartFile file) throws IOException {
        String filename = file.getOriginalFilename();
        //定义文件唯一标识
        String flag= IdUtil.fastSimpleUUID();
        String filePath=System.getProperty("user.dir")+"/src/main/resources/files/"+flag+filename;
        FileUtil.writeBytes(file.getBytes(),filePath);
        return Result.success(ip+":"+port+"/files/"+flag);
    }
    @GetMapping("/{flag}")
    public void getFiles(@PathVariable String flag, HttpServletResponse response){
        OutputStream os;//新建一个输出流对象
        String basePath = System.getProperty("user.dir")+"/src/main/resources/files/";
        List<String> fileNames =FileUtil.listFileNames(basePath);
        String filename = fileNames.stream().filter(name->name.contains(flag)).findAny().orElse("");//找到跟参数一致的文件
        try{
            if (StrUtil.isNotEmpty(filename)) {
                response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "utf-8"));
                response.setContentType("application/octet-stream");
                byte[] bytes = FileUtil.readBytes(basePath+filename);
                os=response.getOutputStream();
                os.write(bytes);
                os.flush();
                os.close();
            }
        }catch(Exception e){
            System.out.println("文件下载失败");
        }
    }


}
