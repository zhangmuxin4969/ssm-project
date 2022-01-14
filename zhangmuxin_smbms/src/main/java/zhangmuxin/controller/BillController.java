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
public class BillController {
    @Autowired
    private BillService billService;
    @Autowired
    private ProviderService providerService;

    @RequestMapping("/getBillLisByPage")
    public String getBillLisByPage(Model model, Bill bill,
                                   @RequestParam(value = "inputPage", defaultValue = "1", required = false) Integer currentpage) {
        QueryWrapper<Bill> wrapper = new QueryWrapper<>();
        if (bill.getProductName() != null && bill.getProductName() != "") {
            wrapper.like("productName", bill.getProductName());
        }
        if (bill.getProviderId() != null && bill.getProviderId() != 0) {
            wrapper.eq("providerId", bill.getProviderId());
        }
        if (bill.getIsPayment() != null && bill.getIsPayment() != 0) {
            wrapper.eq("isPayment", bill.getIsPayment());
        }
        //分页查询数据
        Page<Bill> billPage = new Page<>(currentpage, 5);
        //List<Bill> billList = billService.list(wrapper);
        Page<Bill> page = billService.page(billPage, wrapper);
        List<Provider> providerList = providerService.list();
        model.addAttribute("billList", page.getRecords());
        //分页pageinfo
        model.addAttribute("page", page);
        model.addAttribute("pages", page.getPages());
        //查询条件
        model.addAttribute("bill", bill);
        model.addAttribute("providerList", providerList);
        model.addAttribute("mark", "bill");
        return "jsp/billlist.jsp";
    }

    @RequestMapping("/queryBillId")
    public String queryBillId(Model model, Integer billid) {
        Bill bill = billService.getById(billid);
        Provider provider = providerService.getById(bill.getProviderId());
        model.addAttribute("bill", bill);
        model.addAttribute("provider", provider);
        return "jsp/billview.jsp";
    }

    @Transactional
    @RequestMapping("/modifyBill")
    public String modifyBill(Model model, Integer billid) {
        Bill bill = billService.getById(billid);
        model.addAttribute("bill", bill);
        return "jsp/billmodify.jsp";
    }

    @Transactional
    @RequestMapping("/updateBill")
    public String updateBill(Bill bill, HttpServletRequest request) {
        User userSession = (User) request.getSession().getAttribute("USER_SESSION");
        UpdateWrapper<Bill> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("modifyBy", userSession.getId());
        updateWrapper.set("modifyDate", new Date());
        updateWrapper.eq("id", bill.getId());
        billService.update(bill, updateWrapper);
        return "redirect:getBillLisByPage";
    }

    @Transactional
    @RequestMapping("/deleteBillById")
    @ResponseBody
    public Result deleteBillById(Integer billid) {
        QueryWrapper<Bill> wrapper = new QueryWrapper<>();
        Result result = new Result();
        wrapper.eq("id", billid);
        Bill bill = billService.getById(billid);
        if (bill != null) {
            boolean remove = billService.remove(wrapper);
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

    @Transactional
    @RequestMapping("/addBill")
    public String addBill(Bill bill, HttpServletRequest request) {
        User userSession = (User) request.getSession().getAttribute("USER_SESSION");
        Date date = new Date();
        bill.setCreatedBy(userSession.getId());
        bill.setCreationDate(date);
        billService.save(bill);
        return "redirect:getBillLisByPage";
    }
}
