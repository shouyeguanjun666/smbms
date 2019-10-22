package cn.kgc.tangcco.controller;

import cn.kgc.tangcco.pojo.Bill;
import cn.kgc.tangcco.pojo.Provider;
import cn.kgc.tangcco.service.bill.BillService;
import cn.kgc.tangcco.service.provider.ProviderService;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/provider")
public class ProviderController {

    @Resource
    private ProviderService providerService;
    @Resource
    private BillService billService;

    /*去供应商页面*/
    @RequestMapping(value = "toProView")
    public String toProviderView(Model model, @RequestParam(value = "queryProCode",required = false) String queryProCode, @RequestParam(value = "queryProName",required = false) String queryProName, @RequestParam(value = "pageIndex",required = false) String pageIndex){

        int pIndex=0;
        if(pageIndex!=null && pageIndex!=""){
            pIndex=Integer.valueOf(pageIndex);
        }else{
            pIndex=1;
        }


        PageHelper.startPage(pIndex,5);
        List<Provider> providerList=providerService.findProviderByCodeAndName(queryProCode,queryProName);

        PageInfo<Provider> pageInfo=new PageInfo<>(providerList);

        model.addAttribute("providerList",providerList);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("currentPageNo",pIndex);

        return "providerlist";


    }


    /*去添加页面*/
    @RequestMapping(value = "toaddpro")
    public String toaddpro(@ModelAttribute("provider")Provider provider){

        return "provideradd";
    }

    /*添加供应商*/
    @RequestMapping(value = "doaddpro")
    public String doaddpro(Provider provider){
    provider.setCreationDate(new Date());
    int result=providerService.saveProvider(provider);
    if(result>0){
        return "redirect:/provider/toProView";
    }
        return "redirect:/provider/toaddpro";

    }


    /*去查看页面*/
    @RequestMapping(value = "viewProvider")
    public String viewProvider(Integer id,Model model){

        Provider provider=providerService.getProById(id);
        model.addAttribute("provider",provider);
        return "providerview";
    }


    /*去修改页面*/
    @RequestMapping(value = "upProvider")
    public String upProvider(Integer id,Model model){

        Provider provider=providerService.getProById(id);
        model.addAttribute("provider",provider);

        return "providermodify";

    }


        /*修改*/
    @RequestMapping(value = "doupprovider")
    public String doupprovider(Provider provider){

        int result=providerService.updatePro(provider);
        if(result>0){
            return "redirect:/provider/toProView";
        }else{
            return "redirect:/provider/upProvider";
        }

    }


    /*删除供应商*/
    @RequestMapping(value = "delPro")
    @ResponseBody
    public JSON delPro(Integer id){
        Map<String,String> resultMap=new HashMap<>();
        //根据供应商Id查询订单,如果能查到就不能删
        List<Bill> billList=billService.findBillByProId(id);
        if(billList.size()==0){//没查到订单,可以删除
            int result=providerService.delPro(id);
            if(result!=0){
                resultMap.put("result","true");
            }

        }else{
            resultMap.put("result","false");
        }
        return (JSON) JSON.toJSON(resultMap);

    }






}
