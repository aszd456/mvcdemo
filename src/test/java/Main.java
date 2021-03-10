import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;
import org.test.entity.User;
import org.test.mapper.UserMapper;
import org.test.utils.SqlSessionFactoryUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: zhouliansheng
 * @Date: 2021/3/10 0:04
 */
public class Main {
    public static void main(String[] args) throws IOException {
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream("mybatis-config.xml"));
        SqlSession sqlSession = factory.openSession();
        User user = sqlSession.selectOne("org.test.mapper.UserMapper.getUserById", 1);
        System.out.println(user.getUsername());
        sqlSession.close();
    }

    SqlSessionFactory sqlSessionFactory;

    @Before
    public void getSqlSessionFactory() {
        sqlSessionFactory = SqlSessionFactoryUtils.getInstance();
    }

    /**
     * 主键自增长
     * SQL 插入完成后，一定要提交，即 sqlSession.commit()
     */
    @Test
    public void addUser() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        User user = new User();
        user.setUsername("江南一点雨");
        user.setAddress("北京");
        int insert = sqlSession.insert("org.test.mapper.UserMapper.addUser", user);
        //int delete = sqlSession.delete("org.test.mapper.UserMapper.deleteUserById", 1);
        System.out.println(insert);
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void deleteUserById(){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        int delete = sqlSession.delete("org.test.mapper.UserMapper.deleteUserById", 2);
        System.out.println(delete);
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void updateUser(){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        User user = new User();
        user.setId(2);
        user.setUsername("javaboy");
        int update = sqlSession.update("org.test.mapper.UserMapper.updateUser", user);
        System.out.println(update);
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void getAllUser(){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        //List<User> list = sqlSession.selectList("org.test.mapper.UserMapper.getAllUser");
        List<User> list = mapper.getAllUser();
        System.out.println(list.get(2).getFavorites());
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void getUserByPage(){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<User> list = mapper.getUserByPage(null, null);
        System.out.println(list);
        list = mapper.getUserByPage(2, 2);
        System.out.println(list);
        sqlSession.close();
    }

    @Test
    public void getUserByUsernameAndId(){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<User> list = mapper.getUserByUsernameAndId(2, "java");
        System.out.println(list);
        list = mapper.getUserByUsernameAndId(null, "javaboy");
        System.out.println(list);
        list = mapper.getUserByUsernameAndId(5, null);
        System.out.println(list);
        list = mapper.getUserByUsernameAndId(null, null);
        System.out.println(list);
        sqlSession.close();
    }

    @Test
    public void batchInsertUser(){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<User> users = new ArrayList<>();
        User u1 = new User();
        u1.setUsername("zhangsan");
        u1.setAddress("shenzhen");
        users.add(u1);
        User u2 = new User();
        u2.setUsername("lisi");
        u2.setAddress("广州");
        users.add(u2);
        mapper.batchInsertUser(users);
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void addUser3(){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User user = new User();
        user.setUsername("风气");
        user.setAddress("上海");
        List<String> favorites = new ArrayList<String>();
        favorites.add("足球");
        favorites.add("篮球");
        favorites.add("乒乓球");
        user.setFavorites(favorites);
        mapper.addUser3(user);
        sqlSession.commit();
        sqlSession.close();
    }


}
