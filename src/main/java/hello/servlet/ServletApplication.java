package hello.servlet;

import hello.servlet.web.springmvc.v1.SpringMemberFormControllerV1;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@ServletComponentScan // 서블릿 자동 등록
@SpringBootApplication
public class ServletApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServletApplication.class, args);
	}

	// 뷰 리졸버 강의
	// application.properties에서 viewResolver을 설정한 것이 부트에서 아래와 같은 기능을 한 것과 동일하다
//	@Bean
//	ViewResolver internalResourceViewResolver(){
//		return new InternalResourceViewResolver("/WEB-INF/views/", ".jsp");
//	}

	// 스프링 MVC 시작하기 강의
	// 지정한 클래스에서 컴포넌트 스캔을 하지않고 @RequestMapping만 쓰고 메인에서 빈등록을 하는 방식이다
//	@Bean
//	SpringMemberFormControllerV1 SpringMemberFormControllerV1(){
//		return new SpringMemberFormControllerV1();
//	}

}
