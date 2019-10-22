package cn.kgc.tangcco.service.provider;

import cn.kgc.tangcco.dao.provider.ProviderMapper;
import cn.kgc.tangcco.pojo.Provider;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class ProviderServiceImpl implements ProviderService {

    @Resource
    ProviderMapper providerMapper;

    @Override
    public List<Provider> findAllProvider() {



        return providerMapper.getAllProvider();
    }

    @Override
    public List<Provider> findProviderByCodeAndName(@Param(value = "proCode") String proCode, @Param(value = "proName")String proName) {
        return providerMapper.getProviderByCodeAndName(proCode,proName);
    }

    @Override
    public int saveProvider(Provider provider) {
        return providerMapper.addProvider(provider);
    }

    @Override
    public Provider getProById(Integer id) {
        return providerMapper.getProById(id);
    }

    @Override
    public int updatePro(Provider provider) {
        return providerMapper.updatePro(provider);
    }

    @Override
    public int delPro(Integer id) {
        return providerMapper.delPro(id);
    }
}
