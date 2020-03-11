package com.changgou.goods.dao;
import com.changgou.goods.pojo.Brand;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/****
 * @Author:sz.itheima
 * @Description:Brand的Dao
 * @Date 2019/6/14 0:12
 *****/
@Component
public interface BrandMapper extends Mapper<Brand> {
    /**
     * 根据分类ID查询品牌集合
     * @param categoryid 分类id
     */
    @Select("SELECT * from tb_brand tb,tb_category_brand tcb WHERE tb.id = tcb.brand_id AND tcb.category_id = #{categoryid}")
    List<Brand> findByCategory(Integer categoryid);
}
