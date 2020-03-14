package com.click.house.controller;

import com.click.house.entity.UserInfo;
import com.click.house.service.UserInfoService;

import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

/**
 * demo测试ch数据库基本操作
 * <一句话功能简述>
 * <功能详细描述>
 * 
 * @author  huxing
 * @version  [版本号, 2020年3月3日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
@RestController
@RequestMapping("/user")
public class UserInfoController
{
    @Resource
    private UserInfoService userInfoService;
    
    @RequestMapping("/download")
    public String download(HttpServletResponse response, @RequestParam(value = "id", required = false) Integer id)
    {
        try
        {
            if (id == null)
                return "id不存在";
            String path = ResourceUtils.getURL("classpath:").getPath();
            System.out.println(path);
            File file = new File(path + id + ".docx");
            if (!file.exists())
                return "文件不存在";
            response.setHeader("content-type", "application/octet-stream");
            response.setContentType("application/octet-stream");
            // 下载文件能正常显示中文
            response.setHeader("Content-Disposition",
                "attachment;filename=" + URLEncoder.encode("中文文件名.docx", "UTF-8"));
            // 实现文件下载
            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            OutputStream os = null;
            try
            {
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                os = response.getOutputStream();
                int len = 0;
                while ((len = bis.read(buffer)) > 0)
                {
                    os.write(buffer, 0, len);
                }
            }
            catch (Exception e)
            {
                return "下载出错";
            }
            finally
            {
                fis.close();
                bis.close();
            }
        }
        catch (Exception e)
        {
            return "err";
        }
        return "ok";
    }
    
    @RequestMapping("/saveData")
    public String saveData()
    {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(4);
        userInfo.setUserName("winter");
        userInfo.setPassWord("567");
        userInfo.setPhone("13977776789");
        userInfo.setEmail("winter");
        userInfo.setCreateDay("2020-02-20");
        userInfoService.saveData(userInfo);
        return "sus";
    }
    
    @RequestMapping("/selectById")
    public UserInfo selectById()
    {
        return userInfoService.selectById(1);
    }
    
    @RequestMapping("/selectList")
    public List<UserInfo> selectList()
    {
        return userInfoService.selectList();
    }
}
