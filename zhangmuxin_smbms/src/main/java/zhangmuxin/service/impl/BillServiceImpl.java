package zhangmuxin.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import zhangmuxin.pojo.Bill;
import zhangmuxin.mapper.BillMapper;
import zhangmuxin.service.BillService;

/**
 * @author zhang muxin
 */
@Service
public class BillServiceImpl extends ServiceImpl<BillMapper, Bill> implements BillService {
}
