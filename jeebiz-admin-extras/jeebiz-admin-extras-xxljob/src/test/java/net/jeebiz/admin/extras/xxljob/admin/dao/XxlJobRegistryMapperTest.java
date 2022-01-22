package net.jeebiz.admin.extras.xxljob.admin.dao;

import net.jeebiz.admin.extras.xxljob.dao.XxlJobRegistryMapper;
import net.jeebiz.admin.extras.xxljob.dao.entities.XxlJobRegistry;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class XxlJobRegistryMapperTest {

    @Resource
    private XxlJobRegistryMapper xxlJobRegistryMapper;

    @Test
    public void test(){
        int ret = xxlJobRegistryMapper.registryUpdate("g1", "k1", "v1", new Date());
        if (ret < 1) {
            ret = xxlJobRegistryMapper.registrySave("g1", "k1", "v1", new Date());
        }

        List<XxlJobRegistry> list = xxlJobRegistryMapper.findAll(1, new Date());

        int ret2 = xxlJobRegistryMapper.removeDead(Arrays.asList(1));
    }

}
