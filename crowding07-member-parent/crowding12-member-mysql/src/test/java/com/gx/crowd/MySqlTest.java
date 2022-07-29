package com.gx.crowd;

import com.gx.crowd.entity.vo.ProjectTypeVO;
import com.gx.crowd.mapper.ProjectPOMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author FL
 * @version 1.0
 * @date 2022/6/13 8:54
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class MySqlTest {
    @Resource
    private ProjectPOMapper projectPOMapper;
    @Test
    public void testSql(){
        List<ProjectTypeVO> projectTypeVOList = projectPOMapper.selectProjectTypeVO();
    }
}
