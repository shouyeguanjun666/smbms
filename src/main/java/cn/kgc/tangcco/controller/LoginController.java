package cn.kgc.tangcco.controller;


import cn.kgc.tangcco.pojo.User;
import cn.kgc.tangcco.service.user.UserService;
import cn.kgc.tangcco.tools.Constants;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class LoginController {

    @Resource
    private UserService userService;

    /**
     * 登录
     * @param userCode
     * @param userPassword
     * @param model
     * @param session
     * @return
     */
    @RequestMapping(value = "login.html",method=RequestMethod.POST)
    public String Login(String userCode, String userPassword, Model model, HttpSession session){
        String userName=userCode;
        String userPwd=userPassword;

        User user=userService.login(userName,userPwd);
        if(user!=null){
            session.setAttribute(Constants.USER_SESSION,user);
            return "frame";
        }else{
            model.addAttribute("mess","登录失败");
        }

        return "login";

    }


    /**
     * 退出
     */
    @RequestMapping(value = "logout.html",method = RequestMethod.GET)
    public String LoginOut(Model model, HttpSession session){

        session.removeAttribute(Constants.USER_SESSION);

        return "login";




    }



    @RequestMapping(value = "exlogin.html",method=RequestMethod.POST)
    public String exLogin(String userCode, String userPassword, Model model, HttpSession session){
        String userName=userCode;
        String userPwd=userPassword;

        User user=userService.login(userName,userPwd);
        if(user!=null){
            session.setAttribute(Constants.USER_SESSION,user);
            return "frame";
        }else{
           /* model.addAttribute("mess","登录失败");*/
            throw new RuntimeException("登录失败,用户名或密码错误");
        }



    }

    @ExceptionHandler(value = {RuntimeException.class})
    public String handlerException(RuntimeException e, HttpServletRequest request){
        request.setAttribute("e",e);
        return "error";
    }




}
