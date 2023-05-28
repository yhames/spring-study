# 스프링 입문을 윙한 자바 객체지향의 원리와 이해 - 부록 A : 스프링 MVC를 이용한 게시판 구축

<details>

<summary>✅ HSQLDB 연동하는데 `BeanCreationException` 발생</summary>

-> dataSourceScriptDatabaseInitializer bean 생성을 하는데 'schema.sql' 경로에서 schema script를 찾을 수 업어서 오류가 남.

```shell
$ Error creating bean with name 'dataSourceScriptDatabaseInitializer' defined in class path resource [org/springframework/boot/autoconfigure/sql/init/DataSourceInitializationConfiguration.class]: Invocation of init method failed; nested exception is java.lang.IllegalStateException: No schema scripts found at location 'schema.sql'
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


<details>

<summary>✅ MyBatis Mapper XML 파일이 연동이 안됨</summary>

-> Mapper XML 파일의 default 위치가 다른 곳으로 설정되어있는 것 같아서 `application.properties`에 `mapper-locations` 경로를 지정함.
```properties
mybatis.mapper-locations=classpath:sqlmap/**/*.xml
```

</details>

<details>

<summary>✅ `@ModelAttribute` 객체 바인딩 안됨</summary>

-> 책 내용을 진행하던 중 다음과 같은 에러가 나왔다.
```shell
$ Neither BindingResult nor plain target object for bean name 'boardVO' available as request attribute
```

이는 `write.jsp`에서 스프링이 제공하는 form tag의 modelAttribute 속성에서 발생한 오류이다.

```html
<form:form modelAttribute="boardVO" method="post">
```

실제로 Controller에서 어떠한 Model도 전달하지 않고 있다.

```java
@GetMapping("/write")
public String write() {
    return "/board/write";
}
```

이를 위해 Model 객체를 추가하고 BoardVO 객체를 전달하는데에서 문제가 발생했다.
무분별한 객체 생성을 제한하기 위해 기본 생서자를 PROTECTED로 설정해서 객체 생성이 불가능하게 된 것이다.

```java
@GetMapping("/write")
public String write(Model model) {
    model.addAttribute("boardVO", new BoardVO());
    return "/board/write";
}
```

```java
@Alias("boardVO")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardVO {
    private int seq;
    private String title;
    private String content;
    // ...
```

어쩔 수 없는 것이라고 생각하고 @NoArgsConstructor 속성값을 default로 바꾸었더니,
`DataIntegrityViolationException`(데이터 무결성 위반 오류)가 발생했다.

```java
@Alias("boardVO")
@Getter
@NoArgsConstructor
public class BoardVO {
    private int seq;
    private String title;
    private String content;
    // ...
```

```shell
$ Servlet.service() for servlet [dispatcherServlet] in context with path [] threw exception [Request processing failed; nested exception is org.springframework.dao.DataIntegrityViolationException:
```

<details>

<summary>에러 메시지를 자세히 보면 다음과 같다.</summary>

```shell
### Error updating database.  Cause: java.sql.SQLIntegrityConstraintViolationException: integrity constraint violation: NOT NULL check constraint; SYS_CT_10093 table: BOARD column: TITLE
### The error may exist in file [/Users/yhames/spring/oopinspring-mvc/build/resources/main/sqlmap/sqlmap-board.xml]
### The error may involve com.oopinspring.mvc.dao.BoardDao.insert-Inline
### The error occurred while setting parameters
### SQL: INSERT INTO BOARD (title, content, writer, password, regDate, cnt)         VALUES (?, ?, ?, ?, SYSDATE, 0);
### Cause: java.sql.SQLIntegrityConstraintViolationException: integrity constraint violation: NOT NULL check constraint; SYS_CT_10093 table: BOARD column: TITLE; integrity constraint violation: NOT NULL check constraint; SYS_CT_10093 table: BOARD column: TITLE; nested exception is java.sql.SQLIntegrityConstraintViolationException: integrity constraint violation: NOT NULL check constraint; SYS_CT_10093 table: BOARD column: TITLE] with root cause

org.hsqldb.HsqlException: integrity constraint violation: NOT NULL check constraint; SYS_CT_10093 table: BOARD column: TITLE
	at org.hsqldb.error.Error.error(Unknown Source) ~[hsqldb-2.5.2.jar:2.5.2]
	at org.hsqldb.Table.enforceRowConstraints(Unknown Source) ~[hsqldb-2.5.2.jar:2.5.2]
	at org.hsqldb.Table.generateAndCheckData(Unknown Source) ~[hsqldb-2.5.2.jar:2.5.2]
	at org.hsqldb.Table.insertSingleRow(Unknown Source) ~[hsqldb-2.5.2.jar:2.5.2]
	at org.hsqldb.StatementDML.insertSingleRow(Unknown Source) ~[hsqldb-2.5.2.jar:2.5.2]
	at org.hsqldb.StatementInsert.getResult(Unknown Source) ~[hsqldb-2.5.2.jar:2.5.2]
	at org.hsqldb.StatementDMQL.execute(Unknown Source) ~[hsqldb-2.5.2.jar:2.5.2]
	at org.hsqldb.Session.executeCompiledStatement(Unknown Source) ~[hsqldb-2.5.2.jar:2.5.2]
	at org.hsqldb.Session.execute(Unknown Source) ~[hsqldb-2.5.2.jar:2.5.2]
	at org.hsqldb.jdbc.JDBCPreparedStatement.fetchResult(Unknown Source) ~[hsqldb-2.5.2.jar:2.5.2]
	at org.hsqldb.jdbc.JDBCPreparedStatement.execute(Unknown Source) ~[hsqldb-2.5.2.jar:2.5.2]
	at com.zaxxer.hikari.pool.ProxyPreparedStatement.execute(ProxyPreparedStatement.java:44) ~[HikariCP-4.0.3.jar:na]
	at com.zaxxer.hikari.pool.HikariProxyPreparedStatement.execute(HikariProxyPreparedStatement.java) ~[HikariCP-4.0.3.jar:na]
	at org.apache.ibatis.executor.statement.PreparedStatementHandler.update(PreparedStatementHandler.java:47) ~[mybatis-3.5.11.jar:3.5.11]
	at org.apache.ibatis.executor.statement.RoutingStatementHandler.update(RoutingStatementHandler.java:74) ~[mybatis-3.5.11.jar:3.5.11]
	at org.apache.ibatis.executor.SimpleExecutor.doUpdate(SimpleExecutor.java:50) ~[mybatis-3.5.11.jar:3.5.11]
	at org.apache.ibatis.executor.BaseExecutor.update(BaseExecutor.java:117) ~[mybatis-3.5.11.jar:3.5.11]
	at org.apache.ibatis.session.defaults.DefaultSqlSession.update(DefaultSqlSession.java:194) ~[mybatis-3.5.11.jar:3.5.11]
	at org.apache.ibatis.session.defaults.DefaultSqlSession.insert(DefaultSqlSession.java:181) ~[mybatis-3.5.11.jar:3.5.11]
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method) ~[na:na]
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62) ~[na:na]
	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) ~[na:na]
	at java.base/java.lang.reflect.Method.invoke(Method.java:566) ~[na:na]
	at org.mybatis.spring.SqlSessionTemplate$SqlSessionInterceptor.invoke(SqlSessionTemplate.java:425) ~[mybatis-spring-2.1.0.jar:2.1.0]
	at com.sun.proxy.$Proxy59.insert(Unknown Source) ~[na:na]
	at org.mybatis.spring.SqlSessionTemplate.insert(SqlSessionTemplate.java:272) ~[mybatis-spring-2.1.0.jar:2.1.0]
	at com.oopinspring.mvc.dao.BoardDaoMyBatis.insert(BoardDaoMyBatis.java:38) ~[main/:na]
	at com.oopinspring.mvc.dao.BoardDaoMyBatis$$FastClassBySpringCGLIB$$e40820de.invoke(<generated>) ~[main/:na]
	at org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218) ~[spring-core-5.3.27.jar:5.3.27]
	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:793) ~[spring-aop-5.3.27.jar:5.3.27]
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163) ~[spring-aop-5.3.27.jar:5.3.27]
	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:763) ~[spring-aop-5.3.27.jar:5.3.27]
	at org.springframework.dao.support.PersistenceExceptionTranslationInterceptor.invoke(PersistenceExceptionTranslationInterceptor.java:137) ~[spring-tx-5.3.27.jar:5.3.27]
	at org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186) ~[spring-aop-5.3.27.jar:5.3.27]
	at org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:763) ~[spring-aop-5.3.27.jar:5.3.27]
	at org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:708) ~[spring-aop-5.3.27.jar:5.3.27]
	at com.oopinspring.mvc.dao.BoardDaoMyBatis$$EnhancerBySpringCGLIB$$1d86e569.insert(<generated>) ~[main/:na]
	at com.oopinspring.mvc.service.BoardServiceImpl.write(BoardServiceImpl.java:33) ~[main/:na]
	at com.oopinspring.mvc.controller.BoardController.write(BoardController.java:50) ~[main/:na]
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method) ~[na:na]
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62) ~[na:na]
	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) ~[na:na]
	at java.base/java.lang.reflect.Method.invoke(Method.java:566) ~[na:na]
	at org.springframework.web.method.support.InvocableHandlerMethod.doInvoke(InvocableHandlerMethod.java:205) ~[spring-web-5.3.27.jar:5.3.27]
	at org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:150) ~[spring-web-5.3.27.jar:5.3.27]
	at org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:117) ~[spring-webmvc-5.3.27.jar:5.3.27]
	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandlerMethod(RequestMappingHandlerAdapter.java:895) ~[spring-webmvc-5.3.27.jar:5.3.27]
	at org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:808) ~[spring-webmvc-5.3.27.jar:5.3.27]
	at org.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapter.handle(AbstractHandlerMethodAdapter.java:87) ~[spring-webmvc-5.3.27.jar:5.3.27]
	at org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:1072) ~[spring-webmvc-5.3.27.jar:5.3.27]
	at org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:965) ~[spring-webmvc-5.3.27.jar:5.3.27]
	at org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:1006) ~[spring-webmvc-5.3.27.jar:5.3.27]
	at org.springframework.web.servlet.FrameworkServlet.doPost(FrameworkServlet.java:909) ~[spring-webmvc-5.3.27.jar:5.3.27]
	at javax.servlet.http.HttpServlet.service(HttpServlet.java:555) ~[tomcat-embed-core-9.0.75.jar:4.0.FR]
	at org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:883) ~[spring-webmvc-5.3.27.jar:5.3.27]
	at javax.servlet.http.HttpServlet.service(HttpServlet.java:623) ~[tomcat-embed-core-9.0.75.jar:4.0.FR]
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:209) ~[tomcat-embed-core-9.0.75.jar:9.0.75]
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:153) ~[tomcat-embed-core-9.0.75.jar:9.0.75]
	at org.apache.tomcat.websocket.server.WsFilter.doFilter(WsFilter.java:51) ~[tomcat-embed-websocket-9.0.75.jar:9.0.75]
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:178) ~[tomcat-embed-core-9.0.75.jar:9.0.75]
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:153) ~[tomcat-embed-core-9.0.75.jar:9.0.75]
	at org.springframework.web.filter.RequestContextFilter.doFilterInternal(RequestContextFilter.java:100) ~[spring-web-5.3.27.jar:5.3.27]
	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:117) ~[spring-web-5.3.27.jar:5.3.27]
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:178) ~[tomcat-embed-core-9.0.75.jar:9.0.75]
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:153) ~[tomcat-embed-core-9.0.75.jar:9.0.75]
	at org.springframework.web.filter.FormContentFilter.doFilterInternal(FormContentFilter.java:93) ~[spring-web-5.3.27.jar:5.3.27]
	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:117) ~[spring-web-5.3.27.jar:5.3.27]
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:178) ~[tomcat-embed-core-9.0.75.jar:9.0.75]
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:153) ~[tomcat-embed-core-9.0.75.jar:9.0.75]
	at org.springframework.web.filter.CharacterEncodingFilter.doFilterInternal(CharacterEncodingFilter.java:201) ~[spring-web-5.3.27.jar:5.3.27]
	at org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:117) ~[spring-web-5.3.27.jar:5.3.27]
	at org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:178) ~[tomcat-embed-core-9.0.75.jar:9.0.75]
	at org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:153) ~[tomcat-embed-core-9.0.75.jar:9.0.75]
	at org.apache.catalina.core.StandardWrapperValve.invoke(StandardWrapperValve.java:167) ~[tomcat-embed-core-9.0.75.jar:9.0.75]
	at org.apache.catalina.core.StandardContextValve.invoke(StandardContextValve.java:90) ~[tomcat-embed-core-9.0.75.jar:9.0.75]
	at org.apache.catalina.authenticator.AuthenticatorBase.invoke(AuthenticatorBase.java:481) ~[tomcat-embed-core-9.0.75.jar:9.0.75]
	at org.apache.catalina.core.StandardHostValve.invoke(StandardHostValve.java:130) ~[tomcat-embed-core-9.0.75.jar:9.0.75]
	at org.apache.catalina.valves.ErrorReportValve.invoke(ErrorReportValve.java:93) ~[tomcat-embed-core-9.0.75.jar:9.0.75]
	at org.apache.catalina.core.StandardEngineValve.invoke(StandardEngineValve.java:74) ~[tomcat-embed-core-9.0.75.jar:9.0.75]
	at org.apache.catalina.connector.CoyoteAdapter.service(CoyoteAdapter.java:343) ~[tomcat-embed-core-9.0.75.jar:9.0.75]
	at org.apache.coyote.http11.Http11Processor.service(Http11Processor.java:390) ~[tomcat-embed-core-9.0.75.jar:9.0.75]
	at org.apache.coyote.AbstractProcessorLight.process(AbstractProcessorLight.java:63) ~[tomcat-embed-core-9.0.75.jar:9.0.75]
	at org.apache.coyote.AbstractProtocol$ConnectionHandler.process(AbstractProtocol.java:926) ~[tomcat-embed-core-9.0.75.jar:9.0.75]
	at org.apache.tomcat.util.net.NioEndpoint$SocketProcessor.doRun(NioEndpoint.java:1791) ~[tomcat-embed-core-9.0.75.jar:9.0.75]
	at org.apache.tomcat.util.net.SocketProcessorBase.run(SocketProcessorBase.java:52) ~[tomcat-embed-core-9.0.75.jar:9.0.75]
	at org.apache.tomcat.util.threads.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1191) ~[tomcat-embed-core-9.0.75.jar:9.0.75]
	at org.apache.tomcat.util.threads.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:659) ~[tomcat-embed-core-9.0.75.jar:9.0.75]
	at org.apache.tomcat.util.threads.TaskThread$WrappingRunnable.run(TaskThread.java:61) ~[tomcat-embed-core-9.0.75.jar:9.0.75]
	at java.base/java.lang.Thread.run(Thread.java:829) ~[na:na]
```
</details>

요약하자면 TITLE 컬럼에 대하여 널체크를 하지 않아서 무결성 위반되었다는 것이다.
로그를 출력해보면 request parameter는 잘 넘어 오는데,
`@ModelAttribute`에서 `BoardVO` 객체가 바인딩 되지 않고 모두 `null` 값으로 되어있었다.
 
```java
@PostMapping("/write")
public String write(HttpServletRequest req, @ModelAttribute BoardVO boardVO, BindingResult bindingResult) {
    log.info("HttpServletRequest.request : title={}, content={}, writer={}, password={}",
            req.getParameter("title"), req.getParameter("content"),
            req.getParameter("writer"), req.getParameter("password"));
    log.info("@ModelAttribute boardVO : title={}, content={}, writer={}, password={}",
            boardVO.getTitle(), boardVO.getContent(), boardVO.getWriter(), boardVO.getPassword());
```
```shell
$ HttpServletRequest.request : title=123, content=123, writer=123, password=123
$ @ModelAttribute boardVO : seq=0, title=null, content=null, writer=null, password=0
```

로그를 찍어보니 `NoArgsConstructor`가 있는 경우에는 `PartialArgsConstructor`가 아닌
`NoArgsConstructor`가 호출된다는 것을 알 수 있었다.

```java
public BoardVO() {
    log.info("NoArgsConstructor execute");
}

public BoardVO(String title, String content, String writer, int password) {
    log.info("PartialArgsConstructor execute");
    this.title = title;
    this.content = content;
    this.writer = writer;
    this.password = password;
    this.cnt = 0;
}
```

`@ModelAttribute`가 객체를 생성하는 순서는 다음과 같다.
1. 객체 생성 및 초기화
2. 데이터 바인딩
3. Validation

여기서 두번째 단계인 데이터 바인딩은 `getter/setter` 메서드를 사용하여 바인딩을 처리한다.

즉, 요청 파라미터는 정상인데 객체의 필드값이 전부 `null` 혹은 0인 이유는
`BoardVO` 클래스에 `setter` 메서드를 설정하지 않았기 때문이다.  
실제로 `@Setter`를 설정하면 정상적으로 작동한다.

```java
@Alias("boardVO")
@Getter
//@Setter
@NoArgsConstructor
public class BoardVO {
    private int seq;
    private String title;
    private String content;
    // ...
```

정확한 내용을 알기 위해 `@ModelAttribute`가 내부적으로 어떻게 요청 파라미터를 객체에 바인딩하는지 알아봤다.  

`@ModelAttribute`가 객체를 바인딩할 떄 `ModelAttributeMethodProcessor`라는 ArgumentResolver를 사용한다.
`ModelAttributeMethodProcessor` 내부에서는 `createAttribute()`와 `constructAttribute()` 메서드가 호출된다.  

먼저 `createAttribute()`를 살펴보면 `getResolvableConstructor()`를 통해 적절한 생성자를 찾아서
`constructAttribute()`를 통해 객체를 생성한다.

```java
protected Object createAttribute(String attributeName, MethodParameter parameter,
    WebDataBinderFactory binderFactory, NativeWebRequest webRequest) throws Exception {
    
    MethodParameter nestedParameter = parameter.nestedIfOptional();
    Class<?> clazz = nestedParameter.getNestedParameterType();

    Constructor<?> ctor = BeanUtils.getResolvableConstructor(clazz);
    Object attribute = constructAttribute(ctor, attributeName, parameter, binderFactory, webRequest);
    if (parameter != nestedParameter) {
        attribute = Optional.of(attribute);
    }
    return attribute;
}
```

`constructAttribute()`는 파라미터 개수가 0이면 인스턴스를 바로 반환하고
그렇지 않은 경우에는 request parameter를 통해 생성자의 파라미터를 처리한다. 

```java
protected Object constructAttribute(Constructor<?> ctor, String attributeName, MethodParameter parameter,
    WebDataBinderFactory binderFactory, NativeWebRequest webRequest) throws Exception {

    if (ctor.getParameterCount() == 0) {
    // A single default constructor -> clearly a standard JavaBeans arrangement.
    return BeanUtils.instantiateClass(ctor);
    }

    // A single data class constructor -> resolve constructor arguments from request parameters.
    String[] paramNames = BeanUtils.getParameterNames(ctor);
    Class<?>[] paramTypes = ctor.getParameterTypes();
    Object[] args = new Object[paramTypes.length];
    WebDataBinder binder = binderFactory.createBinder(webRequest, null, attributeName);
    String fieldDefaultPrefix = binder.getFieldDefaultPrefix();
    String fieldMarkerPrefix = binder.getFieldMarkerPrefix();
    boolean bindingFailure = false;
    Set<String> failedParams = new HashSet<>(4);
```

`@ModelAttribute`가 어떤 방식으로 생성자를 선택하는지 확인하려면  
적절한 생성자를 찾아주는 `getResolvableConstructor()`를 확인해야한다.

```java
public static <T> Constructor<T> getResolvableConstructor(Class<T> clazz) {
    Constructor<T> ctor = findPrimaryConstructor(clazz);
    if (ctor != null) {
        return ctor;
    }

    Constructor<?>[] ctors = clazz.getConstructors();
    if (ctors.length == 1) {
        // A single public constructor
        return (Constructor<T>) ctors[0];
    }
    else if (ctors.length == 0) {
        // No public constructors -> check non-public
        ctors = clazz.getDeclaredConstructors();
        if (ctors.length == 1) {
            // A single non-public constructor, e.g. from a non-public record type
        return (Constructor<T>) ctors[0];
        }
    }

    // Several constructors -> let's try to take the default constructor
    try {
        return clazz.getDeclaredConstructor();
    }
    catch (NoSuchMethodException ex) {
        // Giving up...
    }
```

현재 생성자는 2개이기 때문에 바로 `try...catch...`문이 실행된다.
바로 위 주석에 의하면 여러 생성자가 있는 경우 **기본 생성자**, 즉 `NoArgsConstructor`가 사용된다.

따라서 `@ModelAttribute`에서 요청 파라미터가 객체에 바인딩되지 않은 이유는
`PartialArgsConstructor`가 아니라 `NoArgsConstructor`가 사용되었고,
`BoardVO` 클래스에 `setter` 메서드가 없기 때문인 것이다.

이에 대한 해결방법으로 3가지를 생각했다.
1. `setter` 메서드를 사용한다.
2. `BoardVO.builder().build()`로 객체를 생성한다.
3. `static factory method`를 구현한다.

여기서 3번째 방법을 선택했는데, 이유는 책을 진행하면서 객체를 또 생성해야할 수도 있기 때문에
미리 정적 팩토리 메서드로 구현해놓으면 편할것같다고 생각했다.  

```java
public static BoardVO newInstance() {
    return new BoardVO();
}
```
```java
@GetMapping("/write")
public String write(Model model) {
    model.addAttribute("boardVO", BoardVO.newInstance());
    return "/board/write";
}
```

추가로 고민해야봐야할 혹은 공부가 필요한 부분은 다음과 같다.
1. 책에서 설명하는 `VO`는 `DTO`를 의미하는데, `DTO`에서 `setter` 메서드를 무조건적으로 지양해야하는지
2. `Entity`와 `DTO`에 대한 명확한 개념정리
3. `Entity`에 `static factory method`를 적용해도 되는지

> 참고자료  
> https://breakcoding.tistory.com/m/404  
> https://hyeon9mak.github.io/model-attribute-without-setter/  
> https://minchul-son.tistory.com/546  
> https://sedangdang.tistory.com/304  

</details>




<details>
<summary>✅ `@SessionAttributes`를 적용하면 `@ModelAttribute` 객체 바인딩이 안됨</summary>

-> `@SessionAttributes`를 사용하면 수정 기능 뿐만 아니라 등록 기능도 같이 오류가 나타난다는 것을 발견함.
왜 안될까 하고 로그를 하나씩 찍어봤는데 **객체 바인딩이 안된다**는 것을 확인함.
등록 기능에서 파라미터를 `form` 데이터로 보내면 객체가 바인딩 되지 않고 모두 `null` 혹은 `0`으로 되어있음 
```java
public String write(HttpServletRequest request, @ModelAttribute @Valid BoardVO boardVO, BindingResult bindingResult) {
    log.info("HttpServletRequest.getParameter.title={}", request.getParameter("title"));
    log.info("HttpServletRequest.getParameter.content={}", request.getParameter("content"));
    log.info("HttpServletRequest.getParameter.writer={}", request.getParameter("writer"));
    log.info("HttpServletRequest.getParameter.password={}", request.getParameter("password"));
    log.info("write().boardVO.getTitle()={}", boardVO.getTitle());
    log.info("write().boardVO.getContent()={}", boardVO.getContent());
    log.info("write().boardVO.getWriter()={}", boardVO.getWriter());
    log.info("write().boardVO.getPassword()={}", boardVO.getPassword());
    // ...
```
```shell
HttpServletRequest.getParameter.title=title
HttpServletRequest.getParameter.content=content
HttpServletRequest.getParameter.writer=writer
HttpServletRequest.getParameter.password=1234
write().boardVO.getTitle()=null
write().boardVO.getContent()=null
write().boardVO.getWriter()=null
write().boardVO.getPassword()=0
```

이전 문제점과 마찬가지로 내부적으로 `NoArgConstructor`를 사용할지도 모른다는 생각에
일단 `setter`와 `NoArgConstructor`를 설정함

```java
// ...
@Setter
@NoArgsConstructor
public class BoardVO {

    private int seq;
    // ...
```

그리고 재실행 결과 역시나 `PartialArgsConstructor`이 아니라 `NoArgsConstructor`이 사용된다는 것을 확인함.

`@SessionAttributes`는 `@ModelAttribute`를 통해 Key 값으로 지정한 이름에 해당하는 `Model` 정보를 자동으로 `Session`에 넣어줌.
즉, `@SessionAttributes`에 지정된 `Key`(혹은 `Model`)와 동일한 `Key`의 값을 수정하면
`@ModelAttribute`를 통해 바인딩되고 이를 자동으로 `Session`에 저장함.

<h3>원인 분석</h3>

이전의 코드를 살펴보면, `/write GET 요청`이 오면 `boardVO` 객체를 `Model`에 추가하여 `response`을 보낸다.

```java
@GetMapping("/write")
public String write(Model model) {
    model.addAttribute("boardVO", BoardVO.newInstance());
    return "/board/write";
}
```

그리고 `/write POST 요청`시 `@ModelAttribute`를 통해 `boardVO` 객체를 바인딩한다. 

```java
@PostMapping("/write")
public String write(@ModelAttribute @Valid BoardVO boardVO, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
        return "/board/write";
    }
    boardService.write(boardVO);
    return "redirect:/board/list";
}
```

여기까지만 보면 전혀 문제가 없어보이지만 `@SessionAttributes` 유무에 따른 상황을 확인하게 위해
테스트 케이스를 작성하고 각 객체에 대해 `toString()`을 출력했다.

<h3>`@SessionAttributes`이 없는 경우</h3>

```java
@Slf4j
@WebMvcTest(controllers = BoardController.class)
class BoardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BoardService boardService;

    @Test
    @DisplayName("저장 기능 - SessionAttributes와 ModelAttribute 디버깅")
    void writeDebug() throws Exception {
        // given
        BoardVO boardVO = BoardVO.newInstance();
        log.info("user.boardVO={}", boardVO.toString());

        MockHttpSession session = new MockHttpSession();

        // when
        mockMvc.perform(post("/board/write")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .session(session)
                .sessionAttr("boardVO", boardVO)
                .characterEncoding("UTF-8")
                .param("title", "t1")
                .param("content", "c1")
                .param("writer", "w1")
                .param("password", "1234")
        ).andExpect(status().is3xxRedirection());

        // then
        assertThat(boardVO.getTitle()).isEqualTo("t1");
        assertThat(boardVO.getContent()).isEqualTo("c1");
        assertThat(boardVO.getWriter()).isEqualTo("w1");
        assertThat(boardVO.getPassword()).isEqualTo(1234);
    }
}
```

```java
@PostMapping("/write")
public String write(@ModelAttribute @Valid BoardVO boardVO, BindingResult bindingResult,
        HttpServletRequest request) {
    log.info("session.boardVO={}", request.getSession().getAttribute("boardVO").toString());
    log.info("controller.boardVO={}", boardVO.toString());
    if (bindingResult.hasErrors()) {
        return "/board/write";
    }
    boardService.write(boardVO);
    return "redirect:/board/list";
}
```

```shell
2023-05-28 16:14:58.493  INFO 96851 --- [    Test worker] com.oopinspring.mvc.domain.BoardVO       : static factory method
2023-05-28 16:14:58.493  INFO 96851 --- [    Test worker] com.oopinspring.mvc.domain.BoardVO       : NoArgsConstructor
2023-05-28 16:14:58.493  INFO 96851 --- [    Test worker] c.o.mvc.controller.BoardControllerTest   : user.boardVO=com.oopinspring.mvc.domain.BoardVO@13004dd8
2023-05-28 16:14:58.517  INFO 96851 --- [    Test worker] com.oopinspring.mvc.domain.BoardVO       : PartialArgsConstructor
2023-05-28 16:14:58.547  INFO 96851 --- [    Test worker] c.o.mvc.controller.BoardController       : session.boardVO=com.oopinspring.mvc.domain.BoardVO@13004dd8
2023-05-28 16:14:58.547  INFO 96851 --- [    Test worker] c.o.mvc.controller.BoardController       : controller.boardVO=com.oopinspring.mvc.domain.BoardVO@108b121f
```

`user`와 `session` 객체는 동일한데 `controller`에서 바인딩 된 객체는 전혀 다른 객체인 것을 확인했다.
이는 `/write POST 요청`시 `@ModelAttribute`가 `PartialArgsConstructor`를 이용하여 새로운 객체를 만들었기 때문이다.
당연히 `session`에서 사용하는 객체는 바인딩 되지 않아 모두 `null`로 들어가 있다.
```shell
expected: "t1"
 but was: null
org.opentest4j.AssertionFailedError: 
expected: "t1"
 but was: null
```

<h3>`@SessionAttributes`가 있는 경우</h3>

다음으로 `@SessionAttributes`가 정상적으로 동작할 때 로그를 확인했다.
정상동작을 위해 `BoardVO` 클래스에 `Setter`를 추가했다.

```shell
2023-05-28 16:40:52.660  INFO 98597 --- [    Test worker] com.oopinspring.mvc.domain.BoardVO       : static factory method
2023-05-28 16:40:52.660  INFO 98597 --- [    Test worker] com.oopinspring.mvc.domain.BoardVO       : NoArgsConstructor
2023-05-28 16:40:52.660  INFO 98597 --- [    Test worker] c.o.mvc.controller.BoardControllerTest   : user.boardVO=com.oopinspring.mvc.domain.BoardVO@2c99c8d
2023-05-28 16:40:52.712  INFO 98597 --- [    Test worker] c.o.mvc.controller.BoardController       : session.boardVO=com.oopinspring.mvc.domain.BoardVO@2c99c8d
2023-05-28 16:40:52.712  INFO 98597 --- [    Test worker] c.o.mvc.controller.BoardController       : controller.boardVO=com.oopinspring.mvc.domain.BoardVO@2c99c8d
```

로그를 출력해보니 이전과는 다르게 모두 같은 객체를 공유하고 있다는 것을 알 수 있다.
즉, `NoArgsConstructor`이 없더라도 `@SessionAttributes`를 사용하면 `@ModelAttribute`는 `PartialArgsConstructor`를 통해 객체를 생상하는 것이 아니라
`user`에서 생성하여 `session`에 저장한 그 객체를 사용한다는 것이다. 이 때 `NoArgsConstructor`과 마찬가지로 내부적으로 `setter`메서드를 이용하게 된다.

아직 `setter` 메서드를 작성하지 않았기 때문에 객체의 값이 모두 `null` 혹은 `0`인 상태에서
`@Valid`를 통해 검증 절차로 진행되어 `return "/board/write";` 구분이 실행되기 때문에 테스트는 실패하게 된다.

이는 처음에 발생했던 문제점과 동일하게 form 데이터로 파라미터를 넘겨도 객체에 바인딩 되지 않고 넘어가는 상황과 동일함.

```shell
Range for response status value 200 expected:<REDIRECTION> but was:<SUCCESSFUL>
Expected :REDIRECTION
Actual   :SUCCESSFUL
```

<h3>해결방법</h3>

`@SessionAttributes`를 적용하면 바인딩할 객체를 따로 생성하지 않고 `/write GET 요청`시 세션에 추가한 `boardVO` 객체에 파라미터를 바인딩한다.

`PartialArgsConstructor`를 사용하지 못하고 `NoArgsConstructor`를 사용해야하는데
이 때 객체를 바인딩하기 위해서는 `setter`가 필요하다.
따라서 객체 바인딩을 위해 `setter` 메서드를 작성하기로 결정했다.
그리고 정적 팩토리 메서드(`newInstance()`)은 관련 내용을 학습하고 리펙토링하기 위해 일단 남겨놨다. 


다음으로 추가적으로 학습해야할 내용은 다음과 같다.
1. Entity와 DAO, DTO에 대한 명확한 개념정리
2. DTO 클래스에 setter를 사용해도 되는지  
2.1. 사용하면 안된다면 setter를 대체할만한 패턴이 있는지
3. @Data 어노테이션


추가로 이번 문제를 해결하면서
객체 바인딩에서 에러가 발생했을 때 `@Valid`를 통한 validation이 동작을 하지 않던 문제도 같이 해결되어서
관련내용 다음으로 학습하려고 한다.

> 참고자료  
> https://developer-joe.tistory.com/226
> https://goodgid.github.io/Spring-MVC-SessionAttributes/

</details>

<details>
<summary>✅ `bindingResult.hasErrors()`가 발생하면 `@Valid`는 안보임 </summary>

-> validation을 적용하고 나서 테스트를 해보니, `int`형인 `password`에 문자열을 바인딩 하려고 하면
`typemismatch` 에러가 발생하는데, 그러면 나머지 `@Valid`를 통해 검증하는 로직들이 실행하지 않는 것을 발견함.

또한 `typemismatch`가 발생하지 않으면 @Valid 또한 문제없이 동작함.

처음에는 @ModelAttribute가 객체를 생성할때 `객체 초기화 → 데이터 바인딩 → 검증` 순서로 진행되기 떄문에 그저 데이터 바인딩과 검증이 순차적으로 실행되는 것인 줄 알았다.
그래서 데이터 바인딩 이후에 검증을 동작하게 할 수 있는 방법을 찾아보려고 했다.

그런데, 위의 `SessionAttributes` 문제에서 정적팩토리메서드 지우고
`setter`와 `NoArgConstructor` 사용하니 문제가 갑자기 해결되어버려서
그 이유를 확인하려고 한다.


</details>



<details>

<summary>✅ 수정/삭제 기능에서 `int`형 매개변수에 대한 validation 필요 </summary>

-> 수정 기능에서 `int pwd`에 대한 validation이 없으니까 문자열이 들어가면 오류메시지가 나오는 것이 아니라
아예 400 오류 페이지가 나와버림.



</details>
