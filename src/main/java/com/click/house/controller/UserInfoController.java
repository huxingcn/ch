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
 * demo����ch���ݿ��������
 * <һ�仰���ܼ���>
 * <������ϸ����>
 * 
 * @author  huxing
 * @version  [�汾��, 2020��3��3��]
 * @see  [�����/����]
 * @since  [��Ʒ/ģ��汾]
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
                return "id������";
            String path = ResourceUtils.getURL("classpath:").getPath();
            System.out.println(path);
            File file = new File(path + id + ".docx");
            if (!file.exists())
                return "�ļ�������";
            response.setHeader("content-type", "application/octet-stream");
            response.setContentType("application/octet-stream");
            // �����ļ���������ʾ����
            response.setHeader("Content-Disposition",
                "attachment;filename=" + URLEncoder.encode("�����ļ���.docx", "UTF-8"));
            // ʵ���ļ�����
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
                return "���س���";
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
