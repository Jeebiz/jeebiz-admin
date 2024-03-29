package io.hiwepy.admin.extras.dict.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.common.collect.Lists;

import io.hiwepy.admin.extras.dict.dao.DictRegionMapper;
import io.hiwepy.admin.extras.dict.dao.entities.DictRegionEntity;
import io.hiwepy.admin.extras.dict.service.IDictRegionService;
import io.hiwepy.boot.api.dao.entities.PairModel;
import io.hiwepy.boot.api.service.BaseServiceImpl;

/**
 * <p>
 * 国家地区代码 http://doc.chacuo.net/iso-3166-1 服务实现类
 * </p>
 *
 * @author wandl
 * @since 2022-01-22
 */
@Service
public class DictRegionServiceImpl extends BaseServiceImpl<DictRegionMapper, DictRegionEntity> implements IDictRegionService {

    @Override
    public List<PairModel> getPairList() {
        List<DictRegionEntity>  regions = getBaseMapper().selectList(new QueryWrapper<DictRegionEntity>()
                .orderByAsc("id"));
        if (CollectionUtils.isEmpty(regions)){
            return  Lists.newArrayList();
        }
        return regions.stream().map(region -> new PairModel(region.getCode2(), region.getCnName())).collect(Collectors.toList());
    }
}
