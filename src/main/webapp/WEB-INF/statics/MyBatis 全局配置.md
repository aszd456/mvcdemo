## 6. 全局配置
全局配置中的文件非常多，主要有如下几方面：
 * properties（属性）
 * settings（全局配置参数）
 * typeAliases（类型别名）
 * typeHandlers（类型处理器）
 * objectFactory（对象工厂）
 * plugins（插件）
 * environments（环境集合属性对象）
 * environment（环境子属性对象）
 * transactionManager（事务管理）
 * dataSource（数据源）
 * mappers（映射器）

### 6.1 properties
利用 mybatis-config.xml 配置文件中的 properties 属性，引入这个配置文件，然后在 DataSource 中使用这个配置文件
```xml
<configuration>
    <properties resource="db.properties"/>
    <environments default="development">
        <environment id="development">
            <transactionManager type="JDBC"/>
            <dataSource type="POOLED">
                <property name="driver" value="{db.driver}"/>
                <property name="url" value="{db.url}"/>
                <property name="username" value="{db.username}"/>
                <property name="password" value="{db.password}"/>
            </dataSource>
        </environment>
    </environments>
    <mappers>
        <package name="org.javaboy.mybatis.mapper"/>
    </mappers>
</configuration>
```
### 6.2 settings
```xml
<configuration>
    <settings>
        <setting name="cacheEnabled" value="false" />
    </settings>
</configuration>

```
### 6.3 typeAliases
```xml
<configuration>
    <properties resource="db.properties"/>
    <typeAliases>
        <typeAlias type="org.test.entity.User" alias="javaboy"/>
    </typeAliases>
</configuration>
```
```xml
<select id="getAllUser" resultType="javaboy">
    select * from user;
</select>
```
```xml
<typeAliases>
    <package name="org.javaboy.mybatis.model"/>
</typeAliases>
```
这个配置就表示给 org.javaboy.mybatis.model 包下的所有类取别名，默认的别名就是类名首字母小写

在最新版中，批量定义的别名，类名首字母也可以不用小写，在实际开发中，我们一般使用第二种方式（批量定义的方式）

### 6.4 typeHandlers
在 MyBatis 映射中，能够自动将 Jdbc 类型映射为 Java 类型
自定义类型处理器

如：List 集合中的数据存入到 VARCHAR 中，我们需要自定义一个类型转换器
* 首先在这个自定义的类型转换器上添加 @MappedJdbcTypes 注解指定要处理的 Jdbc 数据类型，另外还有一个注解是 @MappedTypes 指定要处理的 Java 类型，这两个注解结合起来，就可以锁定要处理的字段了。
* setParameter 方法看名字就知道是设置参数的，这个设置过程由我们手动实现，我们在这里，将 List 集合中的每一项，用一个 , 串起来，组成一个字符串。
* getResult 方法，有三个重载方法，其实都是处理查询的

```xml
<insert id="addUser" parameterType="org.javaboy.mybatis.model.User">
    insert into user (username,address,favorites) values (#{username},#{address},#{favorites,typeHandler=org.javaboy.mybatis.typehandler.List2VarcharHandler});
</insert>
```

### 6.5 Mapper
Mapper 配置的几种方法：
* 使用相对于类路径的资源，即 XML 的定位，从 classpath 开始写。
```xml
<mapper resource="mapping/User.xml" />
```
* 使用完全限定路径，相当于使用绝对路径，这种方式使用非常少。
```xml
<mapper url="file:///D:\demo\xxx\User.xml" />
```
* 使用 mapper 接口类路径，注意：此种方法要求 mapper 接口名称和 mapper 映射文件名称相同，且放在同一个目录中
```xml
<mapper class="org.sang.mapper.UserMapper"/>
```
* 注册指定包下的所有 mapper 接口
```xml
<package name="org.sang.mybatis.mapper"/>
```