### 7.1 parameterType
7.1.1 $ 和 #

在 MyBatis 中，我们在 mapper 引用变量时，默认使用的是 #，像下面这样：
```xml
<select id="getUserById" resultType="org.javaboy.mybatis.model.User">
    select * from user where id=#{id};
</select>
```
除了使用 # 之外，我们也可以使用 $ 来引用一个变量：
```xml
<select id="getUserById" resultType="org.javaboy.mybatis.model.User">
    select * from user where id=${id};
</select>
```
在旧的 MyBatis 版本中，如果使用 $，变量需要通过 @Param 取别名，在最新的 MyBatis 中，无论是 # 还是 $，如果只有一个参数，可以不用取别名，如下：
```java
public interface UserMapper {
    User getUserById(Integer id);
}
```
一般来说，由于参数拼接的方式存在 SQL 注入的风险，因此我们使用较少，但是在一些特殊的场景下，又不得不使用这种方式。
有的 SQL 拼接实际上可以通过数据库函数来解决，例如模糊查询：
```xml
<select id="getUserByName" resultType="org.javaboy.mybatis.model.User">
    select * from user where username like concat('%',#{name},'%');
</select>
```
但是有的 SQL 无法使用 # 来拼接，例如传入一个动态字段进来，假设我想查询所有数据，要排序查询，但是排序的字段不确定，需要通过参数传入，这种场景就只能使用 $，例如如下方法：
```java 
List<User> getAllUser(String orderBy);
```
```xml
<select id="getAllUser" resultType="user">
    select * from user order by ${orderBy}
</select>
```
* Statement 和 PreparedStatement之间的关系和区别.
    * 关系：PreparedStatement继承自Statement,都是接口
    * 区别：PreparedStatement可以使用占位符，是预编译的，批处理比Statement效率高
    * PreparedStatement：表示预编译的 SQL 语句的对象 SQL 语句被预编译并存储在 PreparedStatement 对象中。然后可以使用此对象多次高效地执行该语句。
    * Statement:用于执行静态 SQL 语句并返回它所生成结果的对象。在默认情况下，同一时间每个 Statement 对象只能打开一个 ResultSet 对象。因此，如果读取一个 ResultSet 对象与另一个交叉，则这两个对象必须是由不同的 Statement 对象生成的。如果存在某个语句的打开的当前 ResultSet 对象，则 Statement 接口中的所有执行方法都会隐式关闭它。  

7.1.2 简单类型

通过给参数添加 @Param 来指定参数名（一般在又多个参数的时候，需要加），一旦用 @Param 指定了参数类型之后，可以省略掉参数类型，就是在 xml 文件中，不用定义 parameterType 了：
```java
Integer updateUsernameById(@Param("username") String username, @Param("id") Integer id);
``` 
7.1.3 对象参数
```xml
<insert id="addUser" parameterType="org.javaboy.mybatis.model.User">
    insert into user (username,address,favorites) values (#{username},#{address},#{favorites,typeHandler=org.javaboy.mybatis.typehandler.List2VarcharHandler});
</insert>
```
如果对象存在多个，我们也需要给对象添加 @Param 注解，如果给对象添加了 @Param 注解，那么对象属性的引用，会有一些变化。如下：
```java
Integer addUser(@Param("user") User user);
```
```xml
<insert id="addUser" parameterType="org.javaboy.mybatis.model.User">
    insert into user (username,address,favorites) values (#{user.username},#{user.address},#{user.favorites,typeHandler=org.javaboy.mybatis.typehandler.List2VarcharHandler});
</insert>
```
注意多了一个前缀，这个前缀不是变量名，而是 @Param 注解中定义名称

7.1.4 Map 参数
一般不推荐在项目中使用 Map 参数。
```java
Integer updateUsernameById(HashMap<String,Object> map);
```
```xml
<update id="updateUsernameById">
    update user set username = #{username} where id=#{id};
</update>
```
### 7.2 resultType
resultType 是返回类型，在实际开发中，如果返回的数据类型比较复杂，一般我们使用 resultMap，但是，对于一些简单的返回，使用 resultType 就够用了。

resultType 返回的类型可以是简单类型，可以是对象，可以是集合，也可以是一个 hashmap，如果是 hashmap，map 中的 key 就是字段名，value 就是字段的值。

输出 pojo 对象和输出 pojo 列表在 sql 中定义的 resultType 是一样的。
返回单个 pojo 对象要保证 sql 查询出来的结果集为单条，内部使用 sqlSession.selectOne 方法调用，mapper 接口使用 pojo 对象作为方法返回值。返回 pojo 列表表示查询出来的结果集可能为多条，内部使用 sqlSession.selectList 方法，mapper 接口使用 List 对象作为方法返回值。

### 7.3 resultMap
在实际开发中，resultMap 是使用较多的返回数据类型配置。因为实际项目中，一般的返回数据类型比较丰富，要么字段和属性对不上，要么是一对一、一对多的查询，等等，这些需求，单纯的使用 resultType 是无法满足的，因此我们还需要使用 resultMap，也就是自己定义映射的结果集。

```xml
<resultMap id="MyResultMap" type="org.javaboy.mybatis.model.User">
    <id column="id" property="id"/>
    <result column="username" property="username"/>
    <result column="address" property="address"/>
</resultMap>
```
```xml
<select id="getUserById" resultMap="MyResultMap">
    select * from user where id=#{id};
</select>
```
注意，在旧版的 MyBatis 中，要求实体类一定要有一个无参构造方法，新版的 MyBatis 没有这个要求。

当然，我们也可以在 resultMap 中，自己指定要调用的构造方法，指定方式如下：
```xml
<resultMap id="MyResultMap" type="org.javaboy.mybatis.model.User">
    <constructor>
        <idArg column="id" name="id"/>
        <arg column="username" name="username"/>
    </constructor>
</resultMap>
```
这个就表示使用两个参数的构造方法取构造一个 User 实例。注意，name 属性表示构造方法中的变量名，默认情况下，变量名是 arg0、arg1、、、、或者 param1、param2、、、，如果需要自定义，我们可以在构造方法中，手动加上 @Param 注解。
```java
public class User {
    private Integer id;
    private String username;
    private String address;
    private List<String> favorites;

    public User(@Param("id") Integer id, @Param("username") String username) {
        this.id = id;
        this.username = username;
        System.out.println("--------------------");
    }

    public User(Integer id, String username, String address, List<String> favorites) {
        this.id = id;
        this.username = username;
        this.address = address;
        this.favorites = favorites;
        System.out.println("-----------sdfasfd---------");
    }

    public List<String> getFavorites() {
        return favorites;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", address='" + address + '\'' +
                ", favorites=" + favorites +
                '}';
    }

    public void setFavorites(List<String> favorites) {
        this.favorites = favorites;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
```
### 7.4 动态 SQL
* 7.4.1 if

    if 是一个判断节点，如果满足某个条件，节点中的 SQL 就会生效。例如分页查询，要传递两个参数，页码和查询的记录数，如果这两个参数都为 null，那我就查询所有。
```java
List<User> getUserByPage(@Param("start") Integer start, @Param("count") Integer count);
```
```xml
<select id="getUserByPage" resultType="org.javaboy.mybatis.model.User">
    select * from user
    <if test="start !=null and count!=null">
        limit #{start},#{count}
    </if>
</select>
```
```java
public class Main2 {
    public static void main(String[] args) {
        SqlSessionFactory instance = SqlSessionFactoryUtils.getInstance();
        SqlSession sqlSession = instance.openSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<User> list = mapper.getUserByPage(null, null);
        System.out.println(list);
        list = mapper.getUserByPage(2, 2);
        System.out.println(list);
        sqlSession.close();
    }
}
```
* 7.4.2 where
```xml
<select id="getUserByUsernameAndId" resultType="org.javaboy.mybatis.model.User">
    select * from user
    <where>
        <if test="id!=null">
            and id>#{id}
        </if>
        <if test="name!=null">
            and username like concat('%',#{name},'%')
        </if>
    </where>
</select>
```
用 where 节点将所有的查询条件包起来，如果有满足的条件，where 节点会自动加上，如果没有，where 节点也将不存在，在有满足条件的情况下，where 还会自动处理 and 关键字。

* 7.4.3 foreach 
  
foreach 用来处理数组/集合参数。
```xml
<select id="getUserByIds" resultType="org.javaboy.mybatis.model.User">
    select * from user where id in
    <foreach collection="ids" open="(" close=")" item="id" separator=",">
        #{id}
    </foreach>
</select>
```
在 mapper 中，通过 foreach 节点来遍历数组，collection 表示数组变量，open 表示循环结束后，左边的符号，close 表示循环结束后，右边的符号，item 表示循环时候的单个变量，separator 表示循环的元素之间的分隔符。

注意，默认情况下，无论你的数组/集合参数名字是什么，在 XML 中访问的时候，都是 array，开发者可以通过 @Param 注解给参数重新指定名字。

```xml
<insert id="batchInsertUser">
    insert into user (username,address) values 
    <foreach collection="users" separator="," item="user">
        (#{user.username},#{user.address})
    </foreach>
</insert>
```

* 7.4.4 sql 片段

大家知道，在 SQL 查询中，一般不建议写 *，因为 select * 会降低查询效率。但是，每次查询都要把字段名列出来，太麻烦。这种使用，我们可以利用 SQL 片段来解决这个问题。

```xml
<sql id="Base_Column">
    id,usename,address
</sql>
```
```xml
<select id="getUserByIds" resultType="org.javaboy.mybatis.model.User">
    select <include refid="Base_Column" /> from user where id in
    <foreach collection="ids" open="(" close=")" item="id" separator=",">
        #{id}
    </foreach>
</select>
```

* 7.4.5 set

set 关键字一般用在更新中。因为大部分情况下，更新的字段可能不确定，如果对象中存在该字段的值，就更新该字段，不存在，就不更新。例如如下方法：

```xml
<update id="updateUser" parameterType="org.javaboy.mybatis.model.User">
    update user
    <set>
        <if test="username!=null">
            username = #{username},
        </if>
        <if test="address!=null">
            address=#{address},
        </if>
        <if test="favorites!=null">
            favorites=#{favorites},
        </if>
    </set>
    where id=#{id};
</update>
```