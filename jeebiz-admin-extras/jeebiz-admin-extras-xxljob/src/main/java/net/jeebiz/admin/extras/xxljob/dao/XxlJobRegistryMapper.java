package net.jeebiz.admin.extras.xxljob.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import net.jeebiz.admin.extras.xxljob.dao.entities.XxlJobRegistry;

import java.util.Date;
import java.util.List;

/**
 * Created by xuxueli on 16/9/30.
 */
@Mapper
public interface XxlJobRegistryMapper {

    public List<Integer> findDead(@Param("timeout") int timeout,
                                  @Param("nowTime") Date nowTime);

    public int removeDead(@Param("ids") List<Integer> ids);

    public List<XxlJobRegistry> findAll(@Param("timeout") int timeout,
                                        @Param("nowTime") Date nowTime);

    public int registryUpdate(@Param("registryGroup") String registryGroup,
                              @Param("registryKey") String registryKey,
                              @Param("registryValue") String registryValue,
                              @Param("updateTime") Date updateTime);

    public int registrySave(@Param("registryGroup") String registryGroup,
                            @Param("registryKey") String registryKey,
                            @Param("registryValue") String registryValue,
                            @Param("updateTime") Date updateTime);

    public int registryDelete(@Param("registryGroup") String registryGroup,
                          @Param("registryKey") String registryKey,
                          @Param("registryValue") String registryValue);

}
