package cn.kgc.tangcco.service.bill;

import cn.kgc.tangcco.dao.bill.BillMapper;
import cn.kgc.tangcco.pojo.Bill;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class BillServiceImpl implements BillService {


    @Resource
    private BillMapper billMapper;

    @Override
    public List<Bill> findBillByProId(int proId) {


        return billMapper.getBillByProId(proId);
    }

    @Override
    public List<Bill> findBillByProductNameAndProviderAndisPPayment(String queryProductName, Integer queryProviderId, Integer queryIsPayment) {
        return billMapper.getBillByProductNameAndProviderAndisPPayment(queryProductName,queryProviderId,queryIsPayment);
    }

    @Override
    public int addBill(Bill bill) {

        return billMapper.addBill(bill);
    }

    @Override
    public Bill findBillById(Integer id) {
        return billMapper.getBillById(id);
    }

    @Override
    public int updateBill(Bill bill) {
        return billMapper.updateBill(bill);
    }

    @Override
    public int delBill(Integer id) {
        return billMapper.delBill(id);
    }
}
