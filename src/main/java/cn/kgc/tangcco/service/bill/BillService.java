package cn.kgc.tangcco.service.bill;

import cn.kgc.tangcco.pojo.Bill;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BillService {

    //根据供应商id查询订单
    List<Bill> findBillByProId(int proId);






    //根据供应商,商品名称,是否付款查询订单
    List<Bill> findBillByProductNameAndProviderAndisPPayment(@Param("queryProductName") String queryProductName, @Param("queryProviderId") Integer queryProviderId, @Param("queryIsPayment") Integer queryIsPayment);



    //添加订单
    int addBill(Bill bill);



    //根据id查询订单
    Bill findBillById(Integer id);



    /*修改订单*/
    int updateBill(Bill bill);


    /*删除*/
    int delBill(Integer id);




}
