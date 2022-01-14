package zhangmuxin.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
/**
 * @author zhang muxin
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "smbms_role")
public class Role {
	@TableId(type = IdType.AUTO)
	private Integer id;   //id
	private String roleCode; //角色编码
	private String roleName; //角色名称
	private Integer createdBy; //创建者
	private Date creationDate; //创建时间
	private Integer modifyBy; //更新者
	private Date modifyDate;//更新时间

	
}
