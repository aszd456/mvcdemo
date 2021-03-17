import org.apache.ibatis.datasource.pooled.PooledDataSourceFactory;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import org.junit.Before;
import org.junit.Test;
import org.test.entity.Clazz;
import org.test.entity.Person;
import org.test.entity.TbStudent;
import org.test.entity.User;
import org.test.mapper.ClazzMapper;
import org.test.mapper.PersonMapper;
import org.test.mapper.StudentMapper;
import org.test.mapper.UserMapper;
import org.test.utils.SqlSessionFactoryUtils;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * @Author: zhouliansheng
 * @Date: 2021/3/10 0:04
 */
public class Main {
    /**
     * MyBatis 初始化基本过程：
     * SqlSessionFactoryBuilder  根据传入的输入流生成Configuration  对
     * 象，然后根据Configuration对象创建默认的SqlSessionFactory实例。
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream("mybatis-config.xml"));
        SqlSession sqlSession = factory.openSession();
        User user = sqlSession.selectOne("org.test.mapper.UserMapper.getUserById", 1);
        System.out.println(user.getUsername());
        sqlSession.close();
    }

    @Test
    public void main2() {
        SqlSession sqlSession = null;
        try {
            sqlSession = SqlSessionFactoryUtils.getInstance().openSession();
            User user = new User();
            user.setUsername("傻逼");
            user.setAddress("123456");
            user.setFavorites(Arrays.asList("11", "22"));
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            userMapper.addUser3(user);
            sqlSession.commit();
        } catch (Exception e) {
            if (sqlSession != null) {
                sqlSession.rollback();
            }
            e.printStackTrace();
        } finally {
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
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
    public void deleteUserById() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        int delete = sqlSession.delete("org.test.mapper.UserMapper.deleteUserById", 2);
        System.out.println(delete);
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void updateUser() {
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
    public void getAllUser() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        //List<User> list = sqlSession.selectList("org.test.mapper.UserMapper.getAllUser");
        List<User> list = mapper.getAllUser();
        System.out.println(list.get(2).getFavorites());
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void getUserByPage() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<User> list = mapper.getUserByPage(null, null);
        System.out.println(list);
        list = mapper.getUserByPage(2, 2);
        System.out.println(list);
        sqlSession.close();
    }

    @Test
    public void getUserByUsernameAndId() {
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
    public void batchInsertUser() {
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
    public void addUser3() {
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
    @Test
    public void ontToOne(){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        PersonMapper personMapper = sqlSession.getMapper(PersonMapper.class);
        Person person = personMapper.selectPersonById(1);
        System.out.println(person);
        sqlSession.close();
    }

    @Test
    public void testSelectClazzById(){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        ClazzMapper clazzMapper = sqlSession.getMapper(ClazzMapper.class);
        Clazz clazz = clazzMapper.selectClazzById(1);
        System.out.println(clazz);
        sqlSession.close();
    }

    @Test
    public void testSelectStudentById(){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        StudentMapper studentMapper = sqlSession.getMapper(StudentMapper.class);
        TbStudent student = studentMapper.selectStudentById(1);
        System.out.println(student);
        sqlSession.close();
    }

    /**
     * 不使用 XML 构建 SqlSessionFactory
     */
    public void build() {
        Properties properties = new Properties();
        properties.setProperty("driver", "com.mysql.jdbc.Driver");
        properties.setProperty("url", "jdbc:mysql://127.0.0.1:3306/mybatisdemo");
        properties.setProperty("username", "root");
        properties.setProperty("password", "123456");
        PooledDataSourceFactory pooledDataSourceFactory = new PooledDataSourceFactory();
        pooledDataSourceFactory.setProperties(properties);
        DataSource dataSource = pooledDataSourceFactory.getDataSource();
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("development", transactionFactory, dataSource);
        Configuration configuration = new Configuration(environment);
        configuration.addMapper(UserMapper.class);
        SqlSessionFactory sqlSessionFactory2 = new SqlSessionFactoryBuilder().build(configuration);

//        从 SqlSessionFactory 中获取 SqlSession
        try (SqlSession session = sqlSessionFactory2.openSession()) {
            User user = session.selectOne("org.test.mapper.UserMapper.getUserById", 2);

        }
//        诚然，这种方式能够正常工作，对使用旧版本 MyBatis 的用户来说也比较熟悉。
//        但现在有了一种更简洁的方式——使用和指定语句的参数和返回值相匹配的接口（比如 BlogMapper.class），
//        现在你的代码不仅更清晰，更加类型安全，还不用担心可能出错的字符串字面值以及强制类型转换。
        try (SqlSession session = sqlSessionFactory.openSession()) {
            UserMapper mapper = session.getMapper(UserMapper.class);
            User user = mapper.getUserById(2);
        }
    }


}
