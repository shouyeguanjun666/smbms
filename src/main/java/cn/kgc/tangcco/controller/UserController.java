package cn.kgc.tangcco.controller;


import cn.kgc.tangcco.pojo.Role;
import cn.kgc.tangcco.pojo.User;
import cn.kgc.tangcco.service.role.RoleService;
import cn.kgc.tangcco.service.user.UserService;
import cn.kgc.tangcco.tools.Constants;
import cn.kgc.tangcco.tools.PageSupport;
import com.alibaba.fastjson.JSON;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/user")
public class UserController {

    private Logger logger=Logger.getLogger(UserController.class);

    @Resource
    private UserService userService;
    @Resource
    private RoleService roleService;


    /**
     * 分页查询
     * @param model
     * @param queryUserName
     * @param queryUserRole
     * @param pageIndex
     * @return
     */
    @RequestMapping(value = "userList.html")
    public String showUserList(Model model
                                , @RequestParam(value = "queryname",required = false)String queryUserName
                                ,@RequestParam(value = "queryUserRole",required = false)String queryUserRole
                                ,@RequestParam(value = "pageIndex",required = false)String pageIndex) {

        logger.info("用户名"+queryUserName);
        logger.info("角色编码"+queryUserRole);
        logger.info("当前页码"+pageIndex);

        int _queryUserRole = 0;
        List<User> userList = null;
        //设置页面容量
        int pageSize = Constants.pageSize;
        //当前页码
        int currentPageNo = 1;

        if(queryUserName == null){
            queryUserName = "";
        }
        if(queryUserRole != null && !queryUserRole.equals("")){
            _queryUserRole = Integer.parseInt(queryUserRole);
        }

        if(pageIndex != null){
            try{
                currentPageNo = Integer.valueOf(pageIndex);
            }catch(NumberFormatException e){
                return "redirect:/user/syserror.html";
                //response.sendRedirect("syserror.jsp");
            }
        }
        //总数量（表）
        int totalCount	= userService.getUserCount(queryUserName,_queryUserRole);
        //总页数
        PageSupport pages=new PageSupport();
        pages.setCurrentPageNo(currentPageNo);
        pages.setPageSize(pageSize);
        pages.setTotalCount(totalCount);
        int totalPageCount = pages.getTotalPageCount();
        //控制首页和尾页
        if(currentPageNo < 1){
            currentPageNo = 1;
        }else if(currentPageNo > totalPageCount){
            currentPageNo = totalPageCount;
        }

        int uu= (currentPageNo-1)*pageSize;

        userList = userService.getUserList(queryUserName,_queryUserRole,uu,pageSize);
        model.addAttribute("userList", userList);
        List<Role> roleList = null;
        roleList = roleService.findRoleList();
        model.addAttribute("roleList", roleList);
        model.addAttribute("queryUserName", queryUserName);
        model.addAttribute("queryUserRole", queryUserRole);
        model.addAttribute("totalPageCount", totalPageCount);
        model.addAttribute("totalCount", totalCount);
        model.addAttribute("currentPageNo", currentPageNo);


        return "userlist";




    }







    /**
     * 去添加页面
     * @param user
     * @return
     */
    @RequestMapping(value = "touseradd.html",method = RequestMethod.GET)
    public String toUserAdd(@ModelAttribute("user") User user){

        return "useradd";
    }

   /* @RequestMapping(value = "saveuser",method = RequestMethod.POST)
    public String saveUser(User user, HttpSession session){
        user.setCreatedBy(((User)session.getAttribute(Constants.USER_SESSION)).getId());
        user.setCreationDate(new Date());
        //添加用户
        boolean result=userService.add(user);
        if(result){
            return "redirect:userList.html";
        }else{
            return "useradd";
        }

    }*/




    @RequestMapping(value = "saveuser",method = RequestMethod.POST)
    public String saveUser(User user, HttpSession session, HttpServletRequest request, @RequestParam(value = "attachs",required = false)MultipartFile[] attachs){

        /*如果你有文件上传,第一件事:将文件上传到服务器的指定路径*/
            //判断文件大小
            //文件的后缀
            //给上传的文件重新命名
        /*第二件事:给user对象的idpicpath属性赋值*/
        String errorInfo="";//作为错误信息的键
        if(null!=attachs){//attach不为null说明有文件上传

            for (int i=0;i<attachs.length;i++){
                MultipartFile attach=attachs[i];
                //设置文件上传的路径
                String path=request.getSession().getServletContext().getRealPath("statics"+ File.separator+"uploadfiles");
                logger.info("文件上传的路径为"+path);
                if(i==0){
                    errorInfo="uploadFileError";
                }else{
                    errorInfo="uploadWpError";
                }
                //获得原始文件名称
                String oldFileName=attach.getOriginalFilename();
                logger.info("上传的文件名称"+oldFileName);
                //获得文件名的后缀
                String suffix= FilenameUtils.getExtension(oldFileName);
                logger.info("文件后缀"+suffix);
                //限制上传文件的大小
                int fileSize=500000;
                logger.info("上传文件大小为"+attach.getSize());
                if(attach.getSize()>fileSize){//判断上传文件的大小
                    request.setAttribute("uploadFileError","文件大小不能超过500kb");
                    return "useraddd";
                }else if(suffix.equalsIgnoreCase("jpg")
                        ||suffix.equalsIgnoreCase("png")
                        ||suffix.equalsIgnoreCase("jpeg")
                        ||suffix.equalsIgnoreCase("peng")
                ){//后缀+大小均符合上传要求

                    //给上传文件重新命名
                    String fileName=System.currentTimeMillis()+ RandomUtils.nextInt(1000000)+attach.getName()+"_Personal.jpg";
                    logger.info("新的文件名为"+fileName);
                    //创建File对象
                    File targetFile=new File(path,fileName);
                    //如果上传文件路径不存在就创建
                    if(!targetFile.exists()){
                        targetFile.mkdirs();
                    }

                    try {
                        //上传
                        attach.transferTo(targetFile);
                        if(i==0){//说明是证件照
                            user.setIdPicPath(path+File.separator+fileName);

                        }else{//说明是工作照
                            user.setWorkPicPath(path+File.separator+fileName);
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                        request.setAttribute(errorInfo,"上传失败");
                        return "useradd";
                    }


                }else{//上传图片的格式不符合要求
                    request.setAttribute("uploadFileError","上传图片格式不正确");
                    return "useradd";
                }

            }



        }




        user.setCreatedBy(((User)session.getAttribute(Constants.USER_SESSION)).getId());
        user.setCreationDate(new Date());
        //添加用户
        boolean result=userService.add(user);
        if(result){
            return "redirect:userList.html";
        }else{
            return "useradd";
        }

    }









    @RequestMapping(value ="/view/{id}",method = RequestMethod.GET)
    @ResponseBody
    public JSON viewUser(@PathVariable Integer id){
        User user=null;
        try {
             user=userService.findUserById(id);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return (JSON) JSON.toJSON(user);

    }







    /*//根据id查询用户
    @RequestMapping(value = "viewUser",method = RequestMethod.GET)
    public String getUserId(Integer id,Model model){
        try {
            User user=userService.findUserById(id);
            model.addAttribute(user);
            return "userview";
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }

    }*/



    //删除用户
    @RequestMapping(value = "delUser",method = RequestMethod.POST)
    @ResponseBody/*只返回数据,不返回视图*/
    public JSON delUser(Integer id){
        System.out.println("6666666666666666666");
        boolean result=false;
        Map<String,String> resultMap=new HashMap<>();
        try {
            result=userService.remoUser(id);
            if(result){
                resultMap.put("result","true");
            }else{
                resultMap.put("result","false");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return (JSON) JSON.toJSON(resultMap);

    }



    //去修改页面
    @RequestMapping(value = "updateUser")
    public String doUpdate(Integer id,Model model){


        try {
            User user=userService.findUserById(id);
            List<Role> roleList=roleService.findRoleList();
            System.out.println(user.getUserRoleName());
            model.addAttribute("user",user);
            model.addAttribute("roleList",roleList);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return "usermodify";
    }


    //修改
    @RequestMapping(value = "toUpdate")
    public String toUpdate(User user,Model model){

        System.out.println(user.getId());
        System.out.println(user.getUserCode());

        //修改
        boolean result=userService.updateUser(user);
        if(result){
            return "redirect:/user/userList.html";
        }

        return "redirect:/user/updateUser";
    }



    /*去修改密码页面*/
    @RequestMapping(value = "upPwd")
    public String doPwd(){


        return "pwdmodify";
    }

    /*判断原密码是否正确*/
    @RequestMapping(value = "yuanPwd")
    @ResponseBody
    public JSON yuanPwd(String pwd,HttpSession session){
        Map<String,String> resultMap=new HashMap<>();
        User user= (User) session.getAttribute(Constants.USER_SESSION);
        System.out.println(user.getUserPassword());
        System.out.println(pwd);
        if(user.getUserPassword().equals(pwd)){
            resultMap.put("result","true");
        }else{
            resultMap.put("result","false");
        }

        return (JSON) JSON.toJSON(resultMap);
    }


    /*提交修改*/
    @RequestMapping(value = "dopwdmodify")
    public String submitUpdate(String newpassword,HttpSession session){
        System.out.println(newpassword);
        User user= (User) session.getAttribute(Constants.USER_SESSION);
        user.setUserPassword(newpassword);
        //修改密码
        boolean result=userService.updatePwd(user);
        if(result){
            session.removeAttribute(Constants.USER_SESSION);
            return "login";
        }else{
            return "redirect:/user/upPwd";
        }





    }












  /*  *//**
     * 判断userCode是否存在
     * @param userCode
     * @return
     *//*
    @RequestMapping("ucexists")
    @ResponseBody
    public JSON ucExists(String userCode){
        User user=userService.selectUserCodeExist(userCode);
        Map<String,String> resultMap=new HashMap<>();
        if(user==null){
            resultMap.put("mess","noexists");
        }else{
            resultMap.put("mess","exists");
        }
        return (JSON) JSON.toJSON(resultMap);
    }
*/


}
