package cn.kgc.tangcco.service.provider;

import cn.kgc.tangcco.pojo.Provider;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;


public interface ProviderService {

    List<Provider>  findAllProvider();


    //根据供应商编码和供应商名称查询
    List<Provider> findProviderByCodeAndName(@Param(value = "proCode") String proCode, @Param(value = "proName")String proName);

    //添加供应商
    int saveProvider(Provider provider);


    //根据id查询供应商
    Provider getProById(Integer id);


    //修改
    int updatePro(Provider provider);


    //删除供应商
    int delPro(Integer id);



}
