package net.jeebiz.admin.extras.dict.service.impl;

import net.jeebiz.admin.extras.dict.dao.entities.DictRegionEntity;
import net.jeebiz.admin.extras.dict.dao.DictRegionMapper;
import net.jeebiz.admin.extras.dict.service.IDictRegionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 国家地区代码 http://doc.chacuo.net/iso-3166-1 服务实现类
 * </p>
 *
 * @author wandl
 * @since 2022-01-22
 */
@Service
public class DictRegionServiceImpl extends ServiceImpl<DictRegionMapper, DictRegionEntity> implements IDictRegionService {

}
