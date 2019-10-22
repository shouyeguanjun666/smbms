package cn.kgc.tangcco.controller;

import cn.kgc.tangcco.pojo.Bill;
import cn.kgc.tangcco.pojo.Provider;
import cn.kgc.tangcco.service.bill.BillService;
import cn.kgc.tangcco.service.provider.ProviderService;
import cn.kgc.tangcco.tools.Constants;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/bill")
public class BillController {


    @Resource
    BillService billService;
    @Resource
    ProviderService providerService;

    /*去订单页面*/
    @RequestMapping(value = "tobill")
    public String tobill(Model model, @RequestParam(value = "queryProductName",required = false) String queryProductName, @RequestParam(value = "queryProviderId",required = false) String queryProviderId, @Param("queryIsPayment") String queryIsPayment, @RequestParam(value = "pageIndex",required = false) String pageIndex){
        int pIndex=0;
        if(pageIndex!=null && pageIndex!=""){
            pIndex=Integer.valueOf(pageIndex);
        }else{
            pIndex=1;
        }


        System.out.println(queryProviderId+"aaaaaaaaaaaaaaaaaaaaaaaaa");


        Integer _queryIsPayment=0;
        Integer _queryProviderId=0;

        if(queryIsPayment!=null&&queryIsPayment!=""){
            _queryIsPayment=Integer.valueOf(queryIsPayment);
        }

        if(queryProviderId!=null&&queryProviderId!=""){
            _queryProviderId=Integer.valueOf(queryProviderId);
        }

        PageHelper.startPage(pIndex,5);
        List<Bill> billList=billService.findBillByProductNameAndProviderAndisPPayment(queryProductName,_queryProviderId,_queryIsPayment);

        PageInfo<Bill> pageInfo=new PageInfo<>(billList);

        List<Provider> providerList=providerService.findAllProvider();

        model.addAttribute("providerList",providerList);
        model.addAttribute("billList",billList);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("currentPageNo",pIndex);



        return "billlist";


    }



    /*去添加*/
    @RequestMapping(value = "toaddbill")
    public String toaddbill(@ModelAttribute("Bill") Bill bill,Model model){
        List<Provider> providerList=providerService.findAllProvider();
        model.addAttribute("providerList",providerList);
        return "billadd";

    }

    /*添加订单*/
    @RequestMapping(value = "doaddbill")
    public String doaddbill(Bill bill){


       int result=billService.addBill(bill);
        if(result>0){
            return "redirect:/bill/tobill";
        }

        return "redirect:/bill/toaddbill";


    }


    /*查看订单*/
    @RequestMapping(value = "billview")
    public String billview(Integer id,Model model){

        Bill bill=billService.findBillById(id);
        model.addAttribute("bill",bill);

        return "billview";

    }


    /*去修改页面*/
    @RequestMapping(value = "gobillup")
    public String gobillup(Integer id,Model model){
        Bill bill=billService.findBillById(id);
        List<Provider> providerList=providerService.findAllProvider();
        model.addAttribute("providerList",providerList);
        model.addAttribute("bill",bill);

        return "billmodify";
    }

    /*修改*/
    @RequestMapping(value = "doupbill")
    public String doupbill(Bill bill, HttpSession session){


        System.out.println(bill.getId());


        int result=billService.updateBill(bill);
        if(result>0){
            return "redirect:/bill/tobill";
        }

        return "redirect:/bill/gobillup";
    }




    /*删除*/
    @RequestMapping(value = "delBill")
    @ResponseBody
    public JSON delBill(Integer id){
        Map<String,String> resultMap=new HashMap<>();
        int result=billService.delBill(id);
        if(result>0){
            resultMap.put("result","true");

        }else{
            resultMap.put("result","false");
        }

        return (JSON) JSON.toJSON(resultMap);

    }






}
