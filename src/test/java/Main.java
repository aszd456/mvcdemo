import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.test.entity.User;

import java.io.IOException;

/**
 * @Author: zhouliansheng
 * @Date: 2021/3/10 0:04
 */
public class Main {
    public static void main(String[] args) throws IOException {
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream("mybatis-config.xml"));
        SqlSession sqlSession = factory.openSession();
        User user = (User) sqlSession.selectOne("org.test.mapper.UserMapper.getUserById", 3);
        System.out.println(user.getUsername());
        sqlSession.close();
    }
}
