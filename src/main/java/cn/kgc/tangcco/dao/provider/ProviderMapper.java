package cn.kgc.tangcco.dao.provider;

import cn.kgc.tangcco.pojo.Provider;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProviderMapper {


    //查询所有供应商
    List<Provider> getAllProvider();

    //根据供应商编码和供应商名称查询
    List<Provider> getProviderByCodeAndName(@Param(value = "proCode") String proCode, @Param(value = "proName")String proName);


    //添加供应商
    int addProvider(Provider provider);


    //根据id查询供应商
    Provider getProById(Integer id);


    //修改
    int updatePro(Provider provider);


    //删除供应商
    int delPro(Integer id);



}
