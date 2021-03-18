### 使用xml中的字符实体
```roomsql
SELECT * FROM test WHERE 1 = 1 AND start_date <= CURRENT_DATE AND end_date >= CURRENT_DATE
```
替换成
```roomsql
SELECT * FROM test WHERE 1 = 1 AND start_date &lt;= CURRENT_DATE AND end_date &gt;= CURRENT_DATE
```
使用<\![CDATA[ < ]]>
```roomsql
and (t1.status <![CDATA[ >= ]]> 1  and  t1.status <![CDATA[ <= ]]> 2)
上述代码其实对应的sql：
and (t1.status > =1 andt1.status <= 2)
```

