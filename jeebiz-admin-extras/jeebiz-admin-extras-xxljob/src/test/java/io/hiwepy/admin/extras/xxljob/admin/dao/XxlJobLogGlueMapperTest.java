package io.hiwepy.admin.extras.xxljob.admin.dao;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import io.hiwepy.admin.extras.xxljob.dao.XxlJobLogGlueMapper;
import io.hiwepy.admin.extras.xxljob.dao.entities.XxlJobLogGlue;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class XxlJobLogGlueMapperTest {

    @Resource
    private XxlJobLogGlueMapper xxlJobLogGlueMapper;

    @Test
    public void test(){
        XxlJobLogGlue logGlue = new XxlJobLogGlue();
        logGlue.setJobId(1);
        logGlue.setGlueType("1");
        logGlue.setGlueSource("1");
        logGlue.setGlueRemark("1");

        logGlue.setAddTime(new Date());
        logGlue.setUpdateTime(new Date());
        int ret = xxlJobLogGlueMapper.save(logGlue);

        List<XxlJobLogGlue> list = xxlJobLogGlueMapper.findByJobId(1);

        int ret2 = xxlJobLogGlueMapper.removeOld(1, 1);

        int ret3 =xxlJobLogGlueMapper.deleteByJobId(1);
    }

}
