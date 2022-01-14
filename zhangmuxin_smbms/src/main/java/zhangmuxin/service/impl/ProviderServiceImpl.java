package zhangmuxin.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import zhangmuxin.pojo.Provider;
import zhangmuxin.mapper.ProviderMapper;
import zhangmuxin.service.ProviderService;

/**
 * @author zhang muxin
 */
@Service
public class ProviderServiceImpl  extends ServiceImpl<ProviderMapper, Provider> implements ProviderService {
}
