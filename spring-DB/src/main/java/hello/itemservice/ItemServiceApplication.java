package hello.itemservice;

import hello.itemservice.config.JdbcTemplateV3Config;
import hello.itemservice.config.JpaConfig;
import hello.itemservice.config.MyBatisConfig;
import hello.itemservice.repository.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

@Slf4j
//@Import(MemoryConfig.class)
//@Import(JdbcTemplateV1Config.class)
//@Import(JdbcTemplateV2Config.class)
//@Import(JdbcTemplateV3Config.class)
//@Import(MyBatisConfig.class)
@Import(JpaConfig.class)
@SpringBootApplication(scanBasePackages = "hello.itemservice.web")    // 컨트롤러만 자동등록, 나머지 리포지토리와 서비스는 수동등록
public class ItemServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ItemServiceApplication.class, args);
	}

	@Bean
	@Profile("local")    // 프로필에 따라 설정정보 다르게 설정 가능(local(내 PC), 운영환경, 테스트실행 등), 지정하지 않을 시 "default" 프로필 설정
	public TestDataInit testDataInit(ItemRepository itemRepository) {
		return new TestDataInit(itemRepository);
	}

//	주석처리하고 application.properties 설정도 주석처리하면 자동으로 임베디드 DB 사용
//	@Bean	// 테스트 케이스에서 사용할 데이터소스를 빈으로 수동 등록
//	@Profile("test")	// 테스트 케이스에서만 사용
//	public DataSource dataSource() {
//		log.info("메모리 데이터베이스 초기화");
//		DriverManagerDataSource dataSource = new DriverManagerDataSource();
//		dataSource.setDriverClassName("org.h2.Driver");	// h2 드라이버 지정
//		// mem:db -> 메모리 BD = 임베디드 DB -> JVM 내 DB 생성
//		// DB_CLOSE_DELAY=-1 -> 임베디드 DB에서 데이터베이스 커넥션이 끊어지면 데이터베이스도 종료되는것을 방지
//		dataSource.setUrl("jdbc:h2:mem:db;DB_CLOSE_DELAY=-1");
//		dataSource.setUsername("sa");
//		dataSource.setPassword("");
//		return dataSource;
//	}
}
