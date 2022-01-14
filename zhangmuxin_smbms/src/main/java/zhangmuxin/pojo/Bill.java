package zhangmuxin.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
/**
 * @author zhang muxin
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "smbms_bill")
public class Bill {
	@TableId(type = IdType.AUTO)
	private Integer id;   //id 
	private String billCode; //账单编码 
	private String productName; //商品名称 
	private String productDesc; //商品描述 
	private String productUnit; //商品单位
	private BigDecimal productCount; //商品数量 
	private BigDecimal totalPrice; //总金额
	private Integer isPayment; //是否支付

	private Integer createdBy; //创建者
	private Date creationDate; //创建时间
	private Integer modifyBy; //更新者
	private Date modifyDate;//更新时间
	private Integer providerId; //供应商ID
	@TableField(exist = false)
	private String providerName;//供应商名称

	
}
