package zhangmuxin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.ResponseBody;
import zhangmuxin.pojo.Bill;
import zhangmuxin.pojo.Provider;
import zhangmuxin.pojo.Result;
import zhangmuxin.pojo.User;
import zhangmuxin.service.BillService;
import zhangmuxin.service.ProviderService;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;


/**
 * @author zhang muxin
 */
@Controller
public class ProviderController {
    @Autowired
    private ProviderService providerService;
    @Autowired
    private BillService billService;

    @RequestMapping("/getProviderLisByPage")
    public String getBillLisByPage(Model model, Provider provider,
                                   @RequestParam(value = "inputPage",defaultValue = "1",required = false) Integer currentpage) {
        QueryWrapper<Provider> wrapper = new QueryWrapper<>();
       if (provider.getProCode()!=null&&provider.getProCode()!=""){
            wrapper.like("proCode",provider.getProCode());
        }
        if (provider.getProName()!=null&&provider.getProName()!=""){
            wrapper.like("proName",provider.getProName());
        }

        //分页查询数据
        Page<Provider> providerPage = new Page<>(currentpage,5);
        Page<Provider> page = providerService.page(providerPage, wrapper);
        model.addAttribute("providerList",page.getRecords());
        //分页pageinfo
        model.addAttribute("page",page);
        model.addAttribute("pages",page.getPages());
        //标记分页条跳转
        model.addAttribute("mark","provider");
        //查询条件
        model.addAttribute("provider",provider);
        return "jsp/providerlist.jsp";
    }
    @RequestMapping("/queryProviderId")
    public String queryProviderId(Model model,Integer proid) {
        Provider provider = providerService.getById(proid);
        model.addAttribute("provider",provider);
        return "jsp/providerview.jsp";
    }
    @Transactional
    @RequestMapping("/modifyProvider")
    public String modifyProvider(Model model,Integer proid) {
        Provider provider = providerService.getById(proid);
        model.addAttribute("provider",provider);
        return "jsp/providermodify.jsp";
    }
    @Transactional
    @RequestMapping("/updateProvider")
    public String updateProvider(Provider provider,HttpServletRequest request) {
        User userSession = (User) request.getSession().getAttribute("USER_SESSION");
        UpdateWrapper<Provider> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("modifyDate",new Date());
        updateWrapper.set("modifyBy",userSession.getId());
        updateWrapper.eq("id",provider.getId());
        providerService.update(provider,updateWrapper);
        return "redirect:getProviderLisByPage";
    }
    @Transactional
    @RequestMapping("/deleteProviderById")
    @ResponseBody
    public Result deleteProviderById(Integer proid) {
        QueryWrapper<Provider> wrapper = new QueryWrapper<>();
        QueryWrapper<Bill> billQueryWrapper = new QueryWrapper<>();
        Result result = new Result();
        wrapper.eq("id",proid);
        billQueryWrapper.eq("providerId", proid);
        List<Bill> billList = billService.list(billQueryWrapper);
        Provider provider = providerService.getById(proid);
        if (billList!=null&&billList.size()>0){
            result.setResult( Integer.toString(billList.size()));
            return result;
        }else {
            if (provider != null) {
                boolean remove = providerService.remove(wrapper);
                if (remove) {
                    result.setResult("true");
                    return result;
                } else {
                    result.setResult("false");
                    return result;
                }
            } else {
                result.setResult("notexist");
                return result;
            }

        }
    }

    @Transactional
    @RequestMapping("/addProvider")
    public String addProvider(Provider provider, HttpServletRequest request) {
        User userSession = (User) request.getSession().getAttribute("USER_SESSION");

        Date date = new Date();
        provider.setCreatedBy(userSession.getId());
        provider.setCreationDate(date);

        providerService.save(provider);
        return "redirect:getProviderLisByPage";
    }
    @RequestMapping("selectProviderlist")
    @ResponseBody
    public  List<Provider> selectProviderlist(){
        List<Provider> providerList = providerService.list();
        return providerList;
    }
}
