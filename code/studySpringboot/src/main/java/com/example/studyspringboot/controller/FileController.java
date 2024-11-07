/**
 * 功能：
 * 作者：
 * 日期：2024/5/4 下午4:58
 */
package com.example.studyspringboot.controller;

import cn.hutool.core.io.FileUtil;
import com.example.studyspringboot.common.AuthAccess;
import com.example.studyspringboot.common.Result;
import com.example.studyspringboot.entity.User;
import com.example.studyspringboot.service.FileService;
import com.example.studyspringboot.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/file")
public class FileController {

    @Value("${ip:localhost}")
    String ip;

    @Value("${server.port}")
    String port;

    private static final String ROOT_PATH = System.getProperty("user.dir")+
            File.separator+"files";

    @Autowired
    private FileService fileService;

    @AuthAccess
    @PostMapping("/upload")
    public Result upload(MultipartFile file,String dirName) throws IOException {
        System.out.println(dirName);
        if (dirName.isEmpty()){
            dirName = "error";
        }
        String originalFilename = file.getOriginalFilename();//文件的原始名称
        //aaa.png
        String mainName = FileUtil.mainName(originalFilename);//aaa
        String extName = FileUtil.extName(originalFilename);//png

        File parentFile = new File(ROOT_PATH+File.separator + dirName);
        if(!parentFile.exists()){
            parentFile.mkdirs();//如果当前父级目录不存在，则创建
        }

        String fileRealPath = ROOT_PATH + File.separator + dirName + File.separator + originalFilename;
        File saveFile = new File(fileRealPath);
        if(saveFile.exists()){//当前文件已存在，则重命名
            originalFilename=System.currentTimeMillis()+"_"+mainName+"."+extName;
            saveFile = new File(ROOT_PATH+File.separator+dirName+File.separator+originalFilename);
        }
        file.transferTo(saveFile);
        String url = "http://"+ip+":"+port+"/file/download/"+dirName+"/"+originalFilename;

        //需要补充！
        //调用一个service 将originalFilename 存入数据库人物的image里面（即存的是图片名字）
        //返回的url 只返回这个originalFilename，前端再组装ip port /file/download/等等,然后下面那个方法自动会返回存在本地的图片

        return Result.success("200","请求成功",url);
    }

    @AuthAccess
    @GetMapping("/download/{path}/{fileName}")
    public void download(@PathVariable("path") String path,
                         @PathVariable("fileName") String fileName,
                         HttpServletResponse response) throws IOException {
        String filePath=ROOT_PATH+File.separator+path+File.separator+fileName;
        if(!new File(filePath).exists()){
            return;
            //response.sendError(404);
        }
        byte[] bytes = FileUtil.readBytes(filePath);
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(bytes);
        outputStream.flush();
        outputStream.close();
    }

    @AuthAccess
    @PostMapping("/snapshot")
    public Result snapshot(@RequestParam("image") MultipartFile image) throws IOException {
        User user = TokenUtils.getCurrentUser();
        if(user==null){
            return Result.error("请先登录");
        }
        Result result = upload(image,"snapshot");
        fileService.saveSnapshot(user.getUsername(),(String) result.getData());
        return Result.success("快照保存成功",result.getData());
    }

    @AuthAccess
    @GetMapping("/select")
    public Result selectAllSnapshot(){
        User user = TokenUtils.getCurrentUser();
        if(user==null){
            return Result.error("请先登录");
        }
        Map<LocalDate, List<String>> data = fileService.selectAllSnapshot(user.getUsername());
        System.out.println(data);
        return Result.success(data);
    }
}
