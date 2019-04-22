/**
 * All rights Reserved, Designed By Suixingpay.
 *
 * @author: xiehongfei[xie_hf@suixingpay.com]
 * @date: 2019年04月22日 10:38
 * @Copyright ©2019 Suixingpay. All rights reserved.
 * 注意：本内容仅限于随行付支付有限公司内部传阅，禁止外泄以及用于其他的商业用途。
 */
package com.dusty.boring.demo.mapper;

import com.dusty.boring.demo.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * <pre>
 *
 *       <功能详细描述>
 *
 * @author xiehongfei[xie_hf@suixingpay.com]
 * @date: 2019年04月22日 10:38
 * @version V1.0
 * @review: xiehongfei[xie_hf@suixingpay.com]/2019年04月22日 10:38
 * </pre>
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MapperTest {
    
    @Resource
    private UserMapper userMapper;
    
    @Test
    public void test1() {
        final List<User> users = userMapper.selectList(null);
        System.out.println("\n-\t用户信息:");
        users.forEach(System.out::println);
    
    }
}
