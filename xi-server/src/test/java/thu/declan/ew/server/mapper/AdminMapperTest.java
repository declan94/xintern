package thu.declan.xi.server.mapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import thu.declan.xi.server.model.Admin;

/**
 *
 * @author declan
 */
@RunWith(SpringJUnit4ClassRunner.class) // 整合 
@ContextConfiguration(locations = "classpath:spring.xml") // 加载配置
public class AdminMapperTest {

    @Autowired
    private AdminMapper adminMapper;

	@Test
	public void skip() {
		
	}
	
//    @Test
    public void testAdminMapper() {
        System.out.println("-------------testAdminMapper begin------------");
        Admin admin = new Admin();
        admin.setUsername("admintest");
        admin.setPassword("test");
        admin.setName("declan");
        admin.setTel("19910201234");
        admin.setRole(Admin.Role.FINANCE);
        adminMapper.insert(admin);
        System.out.println("admin id: " + admin.getId());
        adminMapper.delete(admin.getId());
        System.out.println("-------------testAdminMapper end------------");
    }
}
