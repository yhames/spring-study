# 스프링 입문을 윙한 자바 객체지향의 원리와 이해 - 부록 A : 스프링 MVC를 이용한 게시판 구축

<details>

<summary>✅ HSQLDB 연동하는데 BeanCreationException 발생</summary>

-> dataSourceScriptDatabaseInitializer bean 생성을 하는데 'schema.sql' 경로에서 schema script를 찾을 수 업어서 오류가 남.

```shell
Error creating bean with name 'dataSourceScriptDatabaseInitializer' defined in class path resource [org/springframework/boot/autoconfigure/sql/init/DataSourceInitializationConfiguration.class]: Invocation of init method failed; nested exception is java.lang.IllegalStateException: No schema scripts found at location 'schema.sql'
```

책에서 설명하는 `root-context.xml` 파일의 설정은 다음과 같다.

```xml

<jdbc:embedded-database id="dataSource" type="HSQL">
    <jdbc:script location="classpath:BoardSchema.sql"/>
    <jdbc:script location="classpath:BoardData.sql"/>
</jdbc:embedded-database>
```

위의 xml파일을 `application.properties`로 옮긴 최초 코드는 다음과 같다.

```properties
spring.datasource.embedded-database-connection=hsqldb
spring.sql.init.mode=always
spring.sql.init.schema-locations=schema.sql
spring.sql.init.data-locations=data.sql
```

처음에는 뭐가 다른건지 몰라서 한참 찾다가 다시 오류 메시지를 들여다 보는데 `No schema scripts found at location 'schema.sql`가 눈에 띄어서 다음과 같이 수정했다.

```properties
spring.datasource.embedded-database-connection=hsqldb
spring.sql.init.mode=always
spring.sql.init.schema-locations=classpath:schema.sql
spring.sql.init.data-locations=classpath:data.sql
```

해결되었다..

</details>