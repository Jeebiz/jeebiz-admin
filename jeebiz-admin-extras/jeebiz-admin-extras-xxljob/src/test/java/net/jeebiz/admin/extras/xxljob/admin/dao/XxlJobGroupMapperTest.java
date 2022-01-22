package net.jeebiz.admin.extras.xxljob.admin.dao;

import net.jeebiz.admin.extras.xxljob.dao.XxlJobGroupMapper;
import net.jeebiz.admin.extras.xxljob.dao.entities.XxlJobGroup;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class XxlJobGroupMapperTest {

    @Resource
    private XxlJobGroupMapper xxlJobGroupMapper;

    @Test
    public void test(){
        List<XxlJobGroup> list = xxlJobGroupMapper.findAll();

        List<XxlJobGroup> list2 = xxlJobGroupMapper.findByAddressType(0);

        XxlJobGroup group = new XxlJobGroup();
        group.setAppname("setAppName");
        group.setTitle("setTitle");
        group.setAddressType(0);
        group.setAddressList("setAddressList");
        group.setUpdateTime(new Date());

        int ret = xxlJobGroupMapper.save(group);

        XxlJobGroup group2 = xxlJobGroupMapper.load(group.getId());
        group2.setAppname("setAppName2");
        group2.setTitle("setTitle2");
        group2.setAddressType(2);
        group2.setAddressList("setAddressList2");
        group2.setUpdateTime(new Date());

        int ret2 = xxlJobGroupMapper.update(group2);

        int ret3 = xxlJobGroupMapper.remove(group.getId());
    }

}
