package com.jjlb.dao.product;

import com.jjlb.model.entity.product.DrProductPic;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lslnx0307
 */
@Repository
public interface DrProductPicDAO {
	
	
 	/**
 	 * 根据条件得到产品图片
 	 * @param pid
 	 * @return List<DrProductPic>
 	 */
     public List<DrProductPic> getDrProductPicByPid(Integer pid);
     
}
