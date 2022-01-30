# Before MVC



## Web Server & WAS



- #### Web Server

  - ####  HTTP 기반

  - #### 정적 리소스(HTML, CSS, JS, Image, Video) 제공, 기타 부가기능 제공

  - #### ex) `NGINX, APACHE`

- #### WAS (Web Application Server)

  - #### HTTP 기반

  - #### 웹 서버 기능 포함 + 정적 리소스 제공 가능

  - #### 프로그램 코드를 실행하여 어플리케이션 로직 수행 가능 (동적 HTML, HTTP API(JSON)(REST API), Servlet, JSP, Spring MVC)

  - #### ex) `Tomcat, Jetty, Undertow`

- #### 웹 서버도 프로그램을 실행하는 기능을 포함하기도 하며 WAS도 웹 서버의 기능을 제공하기도 한다.

- #### 서블릿 컨테이너 기능을 제공하며 어플리케이션 코드를 실행하는데 더 특화된 것은 WAS이다.

- #### WAS, DB만으로도 시스템을 구성하는 것은 가능하다.

- #### WAS가 너무 많은 역할을 담당하면 서버 과부하가 우려되며 값이 많이 나가는 어플리케이션 로직이 정적 리소스 때문에 수행이 어려울 수 있다. 또한, WAS 완전 장애 시 클라이언트가 서버에 접근 자체가 불가능해져서 오류 발생 화면을 표시하지 못하는 상황이 발생한다.

- #### 위 문제점을 보완하기 위해 정적 리소스는 웹 서버가 처리하며 어플리케이션 로직과 같은 동적인 처리가 필요하면 WAS에 웹 서버가 요청하게 된다.

- #### 웹 서버와 WAS로 계층을 나누면 효율적인 리소스 관리가 가능해진다. 즉, 정적 리소스가 많이 사용되면 웹 서버를 증설하면 되며 어플리케이션 리소스가 많이 사용되면 WAS를 증설하면 된다.

- #### 웹 서버는 정적 리소스를 처리하기 때문에 잘 죽지 않지만 어플리케이션 로직은 장애 요인이 많으므로 WAS는 잘 죽는다. 이러한 상태에서 웹 서버가 WAS에 접근하지 못하면 클라이언트 화면에 오류 화면을 표시해줄 수 있다.

- #### API 서버로 화면이 아닌 데이터만 제공한다면 웹 서버를 거치지 않고 WAS만으로 처리하는 것이 가능하다.



## Servlet



- #### Servlet은 백엔드 개발자가 하기 번거로운 영역을 처리해준다.

  - #### 서버 TCP/IP 연결 대기, 소켓 연결

  - #### HTTP 요청 메시지를 파싱해서 읽기

  - #### POST 방식, /save URL 인지

  - #### Content-Type 확인

  - #### HTTP 메시지 바디 내용 파싱

  - #### 저장 프로세스 실행

    ---

  - #### HTTP 응답 메시지 생성 시작

    - #### HTTP 시작 라인 생성

    - #### Header 생성

    - #### 메시지 바디에 HTML 생성에서 입력

  - #### TCP/IP에 응답 전달, 소켓 종료

- #### Servlet의 도움으로 개발자는 비즈니스 로직과 데이터 베이스 처리에 더 신경을 쓸 수 있다.

- #### HTTP 요청 시 WAS는 Request, Response 객체로 새로 생성하여 서블릿 객체를 호출한다. 그 후 Request에 있는 정보를 꺼내서 처리한 후 Response에 응답을 입력한다. WAS는 Response 객체에 담겨있는 내용으로 HTTP 응답 정보를 생성하여 클라이언트에게 전송한다.

- #### 서블릿 컨테이너는 서블릿 클래스를 생성, 호출, 생명주기 관리 등을 처리하는 컨테이너이다. Tomcat과 같은 서블릿을 지원하는 WAS를 서블릿 컨테이너라고 한다.

- #### 서블릿 객체는 싱글톤으로 관리한다. 그러므로 공유 변수 사용할 때 주의해야하며 사용하지 않는 것이 안전하다.



## Multi-Thread



- #### 서버 실행 시 스레드가 생성되고 클라이언트가 요청하면 스레드가 서블릿을 호출하게 된다.

- #### 요청마다 스레드를 생성하면 장점과 단점이 발생한다.

  - #### 장점 : 동시 요청을 처리할 수 있으며 리소스(CPU, Memory)가 허용될 때까지 처리하는 것이 가능하다. 또한 하나의 스레드가 지연되어도 나머지 스레드는 정상 작동한다.

  - #### 단점 : 스레드는 생성 비용이 매우 비싸서(CPU 많이 사용) 클라이언트 요청 시 응답 속도가 느려질 수 있다. 스레드는 컨텍스트 스위칭 비용이 발생하며 이 때 스레드 생성에 제한이 없어서 요청이 많으면 CPU, Memory 임계점을 넘어서 서버가 죽을 수 있다.

- #### 스레드 풀은 필요한 스레드를 스레드 풀에 미리 보관하고 관리하는 것이다. 

- #### 스레드 풀에서는 클라이언트 요청 시 스레드가 할당되며 스레드 사용 완료 시 다시 스레드 풀로 반납된다.

- #### 클라이언트 요청이 많아서 스레드 풀의 스레드가 모두 사용 중이라면 이후 요청에 대해서는 거절하거나 특정 숫자만큼만 대기하도록 설정할 수 있다.

- #### 스레드 풀을 사용하면 요청마다 스레드를 생성할 때 발생한 단점을 보완 가능하다.

  - #### 스레드가 미리 생성되어 있어서 스레드를 생성하고 종료하는 비용이 절약되고 응답 시간이 빠르다.

  - #### 생성 가능한 스레드의 최대치가 있으므로 너무 많은 요청이 들어와도 기존 요청을 안전하게 처리할 수 있으며 서버가 죽을 위험이 사라진다.

- #### 스레드 풀의 최대 스레드(max thread)가 적으면 동시 요청이 많을 때 서버 리소스는 여유롭지만 클라이언트에 대한 응답이 지연된다. 반면 최대 스레드가 너무 높다면 동시 요청 많을 때 CPU, Memory 리소스 임계점을 초과해서 서버가 다운된다.

- #### 장애가 발생했을 때 클라우드를 운영한다면 서버를 늘려서 문제를 해결한 뒤 튜닝한다(서버 증설 전에 최대 스레드를 늘려보는 것도 방법이다). 하지만 클라우드가 아니라면 장애가 발생하기 전에 미리 최대 스레드 개수를 잘 처리해야 한다.

- #### 최대 스레드를 결정하는 것은 어플리케이션 로직 복잡도, CPU, Memory, IO 리소스 등 많은 영향이 있어서 결정하기 힘들다.

- #### 반드시 성능 테스트로 최대한 실제 서비스와 유사한 조건으로 테스트를 해서 결정하는 것 좋다(툴 : 아파치 ab, 제이미터, nGrinder -> Naver에서 제공하는 오픈소스로 스크립트 작성 후 요청을 한번에 보낼 수 있다).

- #### 멀티 스레드에 대한 부분은 WAS가 알아서 처리하기 때문에 개발자가 신경쓰지 않아도 된다. 단, 멀티 스레드 환경이므로 싱글톤 객체(서블릿, 스프링 빈)는 주의해서 사용해야 한다.



## Response Resource



1. #### 정적 리소스

   - #### 고정된 HTML 파일, CSS, JS, 이미지, 영상 등을 제공한다.

   - #### 주로 웹 브라우저에서 요청한다.

2. #### HTML 페이지

   - #### 동적으로 필요한 HTML 파일을 생성해서 전달한다.

   - #### 웹 브라우저는 HTML을 해석하여 화면에 표시한다.

   - #### JSP, Thymeleaf(view template)을 사용하여 동적으로 HTML을 생성하여 클라이언트에게 전송한다.

3. #### HTTP API

   - #### HTML이 아니라 데이터를 전달한다.

   - #### 주로 JSON 형태로 데이터를 통신한다.

   - #### 앱, 웹 클라이언트, 서버 to 서버와 같은 다양한 시스템에서 호출한다(웹 클라이언트는 AJAX를 이용하여 자바스크립트로 호출 가능).

   - #### 앱 과 웹 클라이언트는 API로 받은 데이터를 처리하여 UI를 표시한다.

   - #### 서버 to  서버는 기업간 통신을 할때 이용한다. 예를 들어, 주문 서버와 결제 서버가 데이터를 서로 일관되게 주고 받아야 할 때 사용한다.

- #### SSR(Server Side Rendering)

  - #### 클라이언트 요청 후 HTML 최종 결과를 서버에서 만들어서 웹 브라우저에 전달하는 방식이다.

  - #### 주로 정적인 화면에 사용한다(동적으로 서버가 HTML을 생성하기는 한다).

  - #### 주로 JSP, Thymeleaf를 사용하며 백엔드 개발자의 역할이다.

- #### CSR(Client Side Rendering)

  - ####  클라이언트가 스스로 HTML 결과를 자바스크립트를 사용해 웹 브라우저에서 동적으로 생성하여 적용한다.

  - #### 주로 동적인 화면에 사용하며 웹 환경을 앱처럼 필요한 부분부분 변경 가능하다.

  - #### ex) `구글 지도, Gmail, 구글 캘린더`

    #### 구글 지도에서 위치를 바꿔도 URL이 그대로이며 화면이 변하는 이유는 클라이언트가 로직을 처리하여 화면에 표시하기 때문이다.

  - #### 주로 React, Vue.js를 사용하며 프론트엔드 개발자의 역할이다.

  - #### 웹 브라우저가 처음에 HTML 요청을 하면 서버는 내용없이 자바스크립트 링크를 응답한다. 그 후 웹 브라우저는 서버로 부터 받은 자바스크립트를 요청하고 서버는 클라이언트 로직,  HTML 렌더링 코드를 응답한다. 이후에 웹 브라우저는 서버에 HTTP API로 요청하여 데이터를 받고 이전에 응답으로 받은 클라이언트 로직과 HTML 렌더링 코드를 조합하여 동적 HTML을 만든다.

- #### React, Vue.js 를 CSR + SSR 동시에 지원하는 웹 프레임워크도 있으며 SSR을 사용해도 자바스크립트를 사용해서 화면 일부를 동적으로 변경할 수 있다.



## Current Java Web Technology



- #### Web Servlet - Spring MVC

  - ####  서블릿 기반으로 서블릿 위에  spring MVC를 올려서 동작하는 방식이다

  - #### HTTP servlet request, response를 모두 사용 가능하며 멀티 스레딩도 가능하다.

  - #### 어노테이션 기반으로 이전의 MVC 단점을 모두 보완하였다.

- #### Web Reactive - Spring WebFlux

  - #### 비동기 non-blocking 처리를 기반으로 한다.

  - #### 최소 스레드로 최대의 성능을 낸다. 즉, 코어 개수만큼이나 코어 개수 + 1 만큼만 스레드를 생성하여 컨텍스트 스위칭 횟수를 감소하여 비용 효율화를 이뤄냈다.

  - #### 함수형 스타일로 개발하여 여러 로직을 실행할 경우 동시 처리가 쉽게 가능하다. 즉, 동시 처리 코드가 효율화 되었다.

  - #### 서블릿 기술은 사용하지 않는다.

  - #### WebFlux는 아직 RDB 지원이 부족하며(NoSQL과 같은 다른 DB는 지원) 현재 일반 MVC의 스레드 모델도 충분히 빨라서 아직 많이 사용하지 않는다.

- #### Spring Boot

  - #### 스프링 부트는 서버를 내장하고 있다(WAS - Tomcat).

  - #### 스프링 부트는 스프링을 편리하게 사용할 수 있게하는 껍데기 느낌으로 볼 수 있다(껍데기지만 기능은 매우 많다).

  - #### 과거에는 서버에 WAS를 직접 설치하고(미리 서버를 실행 상태로 놓는다), 소스는 War 파일을 만들어서 설치한 WAS에 배포하는 방식으로 개발을 진행했다. 즉, 소스코드를 따로 만들어서 빌드해야 했다.

  - #### 스프링 부트는 빌드 결과(Jar)에 WAS 서버를 포함하여 빌드 배포를 단순화했다. 즉, 빌드 안에 tomcat 서버를 내장하여 서버에 WAS를 설치할 필요가 없어졌으며 빌드 결과(Jar)를 아무 서버에서 실행하는 것이 가능해졌다.

- #### View Template

  - #### JSP : 속도가 느리고 기능이 부족해서 잘 사용하지는 않지만 과거에 자바 코드를 입력할 수 있어서 많이 사용했다. HTML에 의존하여 코딩할 수 있는 장점이 있었다.

  - #### Freemarker, Velocity : JSP의 속도 문제를 개선하였고 다양한 기능을 지원하지만 잘 발전하지 않아서 많이 사용하지 않는다.

  - #### Thymeleaf : 내추럴 템플릿으로 HTML의 모양을 유지하면서 뷰 템플릿 적용 가능하다. 로직을 HTML에 포함할 때 JSP 코드가 보이는 경우가 발생하는데 타임리프는 HTML 속성에 tag를 넣어서 HTML 모양을 유지하여 문제를 해결한다. 스프링 MVC와 강력하게 기능을 통합할 수 있는 장점이 있다. 프리마커와 벨로시티가 더 빠르지만 타임리프를 사용하는 것이 최선의 선택이다.



# Servlet & MVC Setting



```groovy
plugins {
	id 'org.springframework.boot' version '2.6.2'
	id 'io.spring.dependency-management' version '1.0.11.RELEASE'
	id 'java'
	id 'war'
}

group = 'hello'
version = '0.0.1-SNAPSHOT'
sourceCompatibility = '11'

configurations {
	compileOnly {
		extendsFrom annotationProcessor
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation 'org.springframework.boot:spring-boot-starter-web'
	compileOnly 'org.projectlombok:lombok'
	annotationProcessor 'org.projectlombok:lombok'
	providedRuntime 'org.springframework.boot:spring-boot-starter-tomcat'
	testImplementation 'org.springframework.boot:spring-boot-starter-test'
}

test {
	useJUnitPlatform()
}

```

- #### Servlet 부터 Spring MVC까지 다뤄볼 것이므로 Jar이 아닌 War로 패키징 한다. War은 tomcat 내장 시에도 실행되며 별도로 WAS 설치 시에도 사용 가능하다.

- #### 간편한 환경 설정을 위해 스프링 부트를 이용한다.

- #### Intellij는 Gradle을 통해서 실행하는 것이 아닌 Intellij IDEA로 실행하는 것이 자바 직접 실행이므로 더 빠르다. 단, Intellij 무료 버전을 사용할 경우 War의 경우 tomcat이 정상 시작되지 않는 문제가 발생하여 Gradle로 설정하거나 build.gradle의 `providedRuntime 'org.springframework.boot:spring-boot-starter-tomcat'`을 지운 뒤 실행해야 한다.

- #### Dependency로는 Spring Web, Lombok을 추가하여 편리하게 서버와 기능을 사용할 수 있게 한다.



```properties
logging.level.org.apache.coyote.http11=debug
```

- #### Application.properties에 추가하여 모든 HTTP 요청 메시지를 로그로 출력하여 확인할 수 있다.

- #### 운영 서버에 모든 HTTP 로그를 출력하면 성능저하가 발생할 수 있으므로 개발 단계에서만 사용한다.



# Servlet Resquest & Response



## Request



### Header



```java
@WebServlet(name = "requestHeaderServlet", urlPatterns = "/request-header")
public class RequestHeaderServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        printStartLine(request);
        printHeaders(request);
        printHeaderUtils(request);
        printEtc(request);
        
    }

    // start line 정보
    private void printStartLine(HttpServletRequest request) {
        System.out.println("--- REQUEST-LINE - start ---");
        System.out.println("request.getMethod() = " + request.getMethod()); //GET
        System.out.println("request.getProtocal() = " + request.getProtocol()); //HTTP/1.1
        System.out.println("request.getScheme() = " + request.getScheme()); //http

        // http://localhost:8080/request-header
        System.out.println("request.getRequestURL() = " + request.getRequestURL());
        // /request-test
        System.out.println("request.getRequestURI() = " + request.getRequestURI());
        //username=hi
        System.out.println("request.getQueryString() = " +
                request.getQueryString());
        System.out.println("request.isSecure() = " + request.isSecure()); //https 사용 유무
        System.out.println("--- REQUEST-LINE - end ---");
        System.out.println();
    }

    //Header 모든 정보
    private void printHeaders(HttpServletRequest request) {
        System.out.println("--- Headers - start ---");

        // 옛날 방식이다
        /*Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            System.out.println(headerName + ": " + request.getHeader(headerName));
        }*/

        // 요즘 더 간결하게 만든 방식이다
        request.getHeaderNames().asIterator()
                .forEachRemaining(headerName -> System.out.println(headerName + ": "
                        + request.getHeader(headerName)));
        System.out.println("--- Headers - end ---");
        System.out.println();
    }

    //Header 편리한 조회
    private void printHeaderUtils(HttpServletRequest request) {
        System.out.println("--- Header 편의 조회 start ---");
        System.out.println("[Host 편의 조회]");
        System.out.println("request.getServerName() = " + request.getServerName()); //Host 헤더 // localhost
        System.out.println("request.getServerPort() = " + request.getServerPort()); //Host 헤더 // 8080
        System.out.println();
        System.out.println("[Accept-Language 편의 조회]");
        request.getLocales().asIterator()
                .forEachRemaining(locale -> System.out.println("locale = " + locale));
        System.out.println("request.getLocale() = " + request.getLocale()); // 가장 상위 협상 객체를 반환한다
        System.out.println();
        System.out.println("[cookie 편의 조회]");
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                System.out.println(cookie.getName() + ": " + cookie.getValue());
            }
        }
        System.out.println();
        System.out.println("[Content 편의 조회]");
        System.out.println("request.getContentType() = " + request.getContentType());
        System.out.println("request.getContentLength() = " +request.getContentLength());
        System.out.println("request.getCharacterEncoding() = " + request.getCharacterEncoding());
        System.out.println("--- Header 편의 조회 end ---");
        System.out.println();
    }

    //기타 정보
    private void printEtc(HttpServletRequest request) {
        System.out.println("--- 기타 조회 start ---");
        System.out.println("[Remote 정보]");
        System.out.println("request.getRemoteHost() = " + request.getRemoteHost()); //
        System.out.println("request.getRemoteAddr() = " + request.getRemoteAddr()); //
        System.out.println("request.getRemotePort() = " + request.getRemotePort()); //
        System.out.println();
        System.out.println("[Local 정보]");
        System.out.println("request.getLocalName() = " + request.getLocalName()); //
        System.out.println("request.getLocalAddr() = " + request.getLocalAddr()); //
        System.out.println("request.getLocalPort() = " + request.getLocalPort()); //
        System.out.println("--- 기타 조회 end ---");
        System.out.println();
    }
}
```

- #### @WebServlet 어노테이션을 설정하여 클래스를 서블릿으로 생성하고 이름과 URL을 부여하여 URL이 호출되면 service 메소드가 실행되게 할 수 있다.

- #### Service 메소드는 protected로 설정하는 것이 개발에 안전하다. Public은 접근에 취약하기 때문에 좋지 않다.

- #### Request, Response 를 출력하면 Facade가 적용되어 있는데 이것은 모두 인터페이스로 구현되어 다양한 WAS 서버가 서블릿 표준 스펙을 구현한 구현체라는 뜻이다.

- #### HTTPServletRequest는 개발자가 HTTP 요청 메시지를 편리하게 사용할 수 있도록 HTTP 요청 메시지를 자동으로 파싱하여 객체에 담은 것이다.

- #### Request는 임시 저장소 기능이 있어서 데이터를 담고 넘겨주는 모델로 역할을 수행할 수 있다.

- #### HTTP 요청 헤더를 조회할 때 request의 메소드를 이용하여 편리하게 조회하여 처리하는 것이 좋다.

- #### 헤더명을 getHeader()에 문자열로 넣어서 특정 헤더의 값을 가져오는 것이 가능하다.

- #### GET 메소드는 컨탠츠를 거의 보내지 않고 message body를 사용하지 않으므로 ContentType()이 null이다.

- #### 마지막 기타 정보는 클라이언트와 서버의 내부적인 HTTP 네트워크 커넥션 정보를 출력한다. 이때, IPv6에 대한 정보가 나오는데 IPv4를 확인하고 싶으면 VM options에 `-Djava.net.preferIPv4Stack=true`를 넣으면 된다.



### GET Method



```java
String username = request.getParameter("username"); //단일 파라미터 조회

Enumeration<String> parameterNames = request.getParameterNames(); //파라미터 이름들 모두 조회

Map<String, String[]> parameterMap = request.getParameterMap(); //파라미터를 Map 으로 조회

String[] usernames = request.getParameterValues("username"); //복수 파라미터 조회
```

- #### GET 메소드는 쿼리 파라미터를 전송하며 서버에서 각 쿼리 파라미터를 조회할 수 있다.

- #### 만약 하나의 파라미터 키에 여러 값을 중복하여 넘긴다면 getParameterValues()로 중복된 파라미터 값까지 모두 확인하여 사용해야 한다. GetParameter() 사용 시 내부 로직 순서에 따라 가장 앞 값만 조회한다. 하지만 파라미터 키를 중복하여 값을 저장하는 경우는 거의 없다.



### POST Method HTML Form



```html
<form action="/request-param" method="post">
    username: <input type="text" name="username" />
    age: <input type="text" name="age" />
    <button type="submit">전송</button>
</form>
```

- #### 단순하게 파라미터를 바디에 담아서 전송하는 것으로 쿼리 파라미터 형식이 메시지 바디에 들어간다고 생각하면 된다.

- #### GET 메소드 방식과 비슷하여 서버 입장에서 두 형식을 동일하게 인식하여 기존에 GET 메소드를 서버에서 받은 방식으로 동일하게 처리 가능하다.

- #### 폼으로 데이터를 저장하는 방식은 메시지 바디를 사용하기 때문에 `content-type: application/x-www-form-urlencoded`를 헤더에 넣어서 클라이언트가 전송한다.

- #### GET 메소드 와 POST HTML Form 방식을 요청 파라미터라고 명명하기도 한다.



### Body



#### TEXT



```java
@WebServlet(name = "requestBodyStringServlet", urlPatterns = "/request-body-string")
public class RequestBodyStringServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        System.out.println("messageBody = " + messageBody);

        response.getWriter().write("OK");
    }
}
```

- #### HTTP 메시지 바디에 데이터를 직접 담아서 요청할 때 사용하며 HTTP API(REST API)에서 사용한다.

- #### JSON, XML, TEXT 등 형식이 지원되지만 거의 표준인(defactor) JSON 데이터 형식을 이용한다.

- #### POST, PUT, PATCH 를 사용 가능하다.

- #### 위 코드는 문자열 형식으로 메시지 바디를 조회하는 것이다.

- #### GetInputStream()은 메시지 바디 내용을 바이트 코드로 바로 얻을 수 있게 해준다.

- #### StreamUtils는 스프링이 제공하는 편리 기능인 stream utility 중 하나이다.

- #### CopyToString()은 바이트를 문자로 바꾸는 메소드로 2번째 파라미터로 바이트가 인코딩 된 방식을 명시해줘야 한다(StandardCharsets.UTF_8).



#### JSON



```java
@WebServlet(name = "requestBodyJsonServlet", urlPatterns = "/request-body-json")
public class RequestBodyJsonServlet extends HttpServlet {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        System.out.println("messageBody = " + messageBody);

        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);

        System.out.println("helloData.username() = " + helloData.getUsername());
        System.out.println("helloData.age() = " + helloData.getAge());

        response.getWriter().write("OK");
    }
}
```

- #### HelloData는 JSON으로 들어오는 데이터를 형식에 맞춰서 저장하는 객체이다.

- #### JSON 결과를 파싱해서 사용할 수 있는 자바 객체로 변환하려면 Jackson, Gson 같은 JSON 변환 라이브러리를 추가하여 사용해야 한다.

- #### 스프링은 기본으로 JSON을 jackson이라는 라이브러리로 다루며 스프링 부트가 기본으로 jackson을 제공한다.

- #### Jackson 라이브러리는 getter, setter을 기본으로 호출하는 형식으로 작동하기 때문에 HelloData에 getter, setter 메소드를 생성해야 한다. 이러한 접근법을 java property 접근법, java bean 접근법이라고 한다.

- #### ObjectMapper()은 JSON을 읽는 jackson 라이브러리 객체이다.

- #### ReadValue() 메소드를 통해 JSON 메시지를 바로 지정한 클래스로 매핑하여 저장할 수 있다.

- #### 메시지 바디에 저장되는 형식도 JSON 형식의 키, 값으로 저장된다.

- #### HTML form 데이터도 jackson으로 읽을 수 있지만 편리 메소드가 제공되기 때문에 잘 이용하지 않는다.



## Response



### Header



```java
@WebServlet(name = "responseHeaderServlet", urlPatterns = "/response-header")
public class ResponseHeaderServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // [status - line]
        response.setStatus(HttpServletResponse.SC_OK);

        // [response - header]
        response.setHeader("Content-Type", "text/plain;charset=utf-8");
        // 캐시를 없애는 두 줄이다
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        // 새로 생성한 임의의 헤더
        response.setHeader("my-header", "hello");

        // [Header의 편의 메소스]
        content(response);
        cookie(response);
        redirect(response);

        // [message body]
        PrintWriter writer = response.getWriter();
        writer.println("OK");


    }

    private void content(HttpServletResponse response) {
        //Content-Type: text/plain;charset=utf-8
        //Content-Length: 2
        //response.setHeader("Content-Type", "text/plain;charset=utf-8");
        response.setContentType("text/plain");
        response.setCharacterEncoding("utf-8");
        //response.setContentLength(2); //(생략시 자동 생성)
    }

    private void cookie(HttpServletResponse response) {
        //Set-Cookie: myCookie=good; Max-Age=600;
        //response.setHeader("Set-Cookie", "myCookie=good; Max-Age=600");
        Cookie cookie = new Cookie("myCookie", "good");
        cookie.setMaxAge(600); //600초
        response.addCookie(cookie);
    }

    private void redirect(HttpServletResponse response) throws IOException {
        //Status Code 302
        //Location: /basic/hello-form.html
        //response.setStatus(HttpServletResponse.SC_FOUND); //302
        //response.setHeader("Location", "/basic/hello-form.html");
        response.sendRedirect("/basic/hello-form.html");
    }
}

```

- #### SetStatus() 메소드로 상태 코드를 설정할 때 이미 상태 코드값이 상수로 상생되어 있어서 설정되어 있는 변수를 사용하는 것이 좋다.

- #### Cache-Control 설정 시 여러 값을 ','로 나눠서 설정할 수 있다.

- #### SetHeader() 메소드로 자신이 직접 임의로 생성한 헤더를 설정할 수 있다.

- #### 메시지 바디에 getInputStream()으로 간편하게 데이터를 넣는 방식도 있으며 println()을 사용하면 엔터도 글자로 적용되어 텍스트 길이 + 1의 content-length가 설정된다.

- #### 쿠키와 redirect로 메소드로 편리하게 설정할 수 있다.



### Body



#### TEXT & HTML



```java
@WebServlet(name = "ResponseHtmlServlet", urlPatterns = "/response-html")
public class ResponseHtmlServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Content-Type: text/html;charset=utf-8
        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");

        PrintWriter writer = response.getWriter();
        writer.println("<html>");
        writer.println(("<body>"));
        writer.println("  <div>안녕?</div>");
        writer.println("</body>");
        writer.println("</html>");

    }
}
```

- #### HTML 응답 시 content-type을 `text/html`로 설정해서 보내야한다.

- #### 자바 코드로 작성하기 때문에 HTML을 동적으로 생성하는 것이 가능하다.



#### JSON



```java
@WebServlet(name = "responseJsonServlet", urlPatterns = "/response-json")
public class ResponseJsonServlet extends HttpServlet {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // Content-Type: application/json
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");

        HelloData helloData = new HelloData();
        helloData.setUsername("kim");
        helloData.setAge(20);

        // {"username":"kim", "age":20}
        String result = objectMapper.writeValueAsString(helloData);
        response.getWriter().write(result);

    }
}
```

- #### WriteValueAsString() 메소드를 이용하여 객체를 문자로 변환하여 JSON 형식을 맞춰서 응답할 수 있다.

- #### Application/json은 기본으로 UTF-8 형식을 사용하도록 정의되어 있어서 charset=utf-8 같은 추가 파라미터는 의미 없는 파라미터를 추가한 것이다.

- #### GetWriter()은 추가 파라미터를 자동으로 추가하는 기능이 있는데 getOutputStream()으로 출력하면 이러한 문제를 해결할 수 있다.



# Servlet & JSP & MVC Pattern



```java
/**
 * 동시성 문제가 고려되어 있지 않음, 실무에서는 ConcurrentHashMap, AtomicLong 사용 고려
 */
public class MemberRepository {

    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L;

    private static final MemberRepository instance = new MemberRepository();

    public static MemberRepository getInstance(){
        return instance;
    }

    private MemberRepository() {
    }

    public Member save(Member member){
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }

     public Member findById(Long id){
        return store.get(id);
     }

     public ArrayList<Member> findAll(){
        return new ArrayList<>(store.values());
     }

     public void clearStore(){
        store.clear();
     }
}
```

- #### Member 객체는 데이터를 저장하는 데이터 형식으로 id, username, age가 변수로 구성되어 있다.

- #### Store 객체는 해쉬맵으로 키로 아이디와 값으로 Member를 저장한다.

- #### Sequence 객체는 id값을 하나씩 늘려주는 시퀀스로 사용한다.

- #### Store 과 Sequence 객체는 static으로 설정하여 MemberRepository가 여러개 생성되어도 유일하게 존재하게 한다.

- #### 서블릿의 기능을 최대한 사용하기 위해 스프링의 빈 기능을 사용하지 않는다.

- #### 기본 생정자를 생성하고 MemberRepository 인스턴스를 static final로 생성하여 유일성을 확보하였으며 getInstance로 유일한 인스턴스를 사용할 수 있게 하였다(싱글톤 객체를 유지).

- #### FindAll() 메소드에서 store 객체를 변경 위험을 없애기 위해 리스트로 반환한다.

- #### Clear 메소드는 보통 테스트 할 때 여러 상황의 데이터를 입력하기 위해 사용한다.

  - ex) 

    ```java
    @AfterEach
    void afterEach(){
    	memberRepository.clearStore();
    }
    ```

- #### Form에 데이터를 입력 후 입력한 데이터를 Save하고 저장된 데이터를 출력하는 List 형태로 로직을 생성하였지만 편의상 가장 중요한 List만 다뤄볼 것이다.



## Servlet Pattern



```java
@WebServlet(name = "memberListServlet", urlPatterns = "/servlet/members")
public class MemberListServlet extends HttpServlet {

    private MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ArrayList<Member> members = memberRepository.findAll();

        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");

        PrintWriter w = response.getWriter();
        // 정적 영역
        w.write("<html>");
        w.write("<head>");
        w.write("   <meta charset=\"UTF-8\">");
        w.write("   <title>Title</title>");
        w.write("</head>");
        w.write("<body>");
        w.write("<a href=\"/index.html\">메인</a>");
        w.write("<table>");
        w.write("   <thead>");
        w.write("   <th>id</th>");
        w.write("   <th>username</th>");
        w.write("   <th>age</th>");
        w.write("   </thead>");
        w.write("   <tbody>");
        /*
        w.write("   <tr>");
        w.write("       <td>1</td>");
        w.write("       <td>userA</td>");
        w.write("       <td>10</td>");
        w.write("   </tr>");
        */
        // 동적 영역
        for (Member member : members) {
            w.write("   <tr>");
            w.write("       <td>" + member.getId() + "</td>");
            w.write("       <td>" + member.getUsername() + "</td>");
            w.write("       <td>" + member.getAge() + "</td>");
            w.write("   </tr>");
        }
        // 정적 영역
        w.write("   </tbody>");
        w.write("</table>");
        w.write("</body>");
        w.write("</html>");


    }
}
```

- #### 저장된 데이터를 HTML로 작성하여 클라이언트로 응답할 때 자바 코드에 의존하기 때문에 정적, 동적 데이터를 모두 다룰 수 있다.

- #### 자바 코드에 의존적으로 로직을 생성해야 하기 때문에 코드가 너무 길어지며 HTML을 자바 코드로 생성하기 때문에 오류가 발생하는 경우가 매우 많다.



## JSP Pattern



- #### 자바 코드에 의존적인 Servlet과 다르게 HTML에 자바 코드를 작성하여 HTML 의존적인 방법이다. 즉, HTML 영역에 동적으로 변경해야 하는 부분만 자바 코드를 넣는 것이다.

- #### 이러한 방법을 Template Engine 이라고 하며 JSP, Thymeleaf, Freemarker, Velocity 등이 있다.



```groovy
//JSP 추가 시작
implementation 'org.apache.tomcat.embed:tomcat-embed-jasper'
implementation 'javax.servlet:jstl'
//JSP 추가 끝
```

- #### JSP 사용 시 build.gradle에 dependency를 추가하여 빌드해야 한다.



```jsp
<%--members.jsp--%>

<%@ page import="java.util.List" %>
<%@ page import="hello.servlet.domain.member.MemberRepository" %>
<%@ page import="hello.servlet.domain.member.Member" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%

    MemberRepository memberRepository = MemberRepository.getInstance();
    List<Member> members = memberRepository.findAll();

%>

<html>
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<a href="/index.html">메인</a>
<table>
    <thead>
    <th>id</th>
    <th>username</th>
    <th>age</th>
    </thead>
    <tbody>
    <%
        for (Member member : members) {
            out.write("     <tr>");
            out.write("         <td>" + member.getId() + "</td>");
            out.write("         <td>" + member.getUsername() + "</td>");
            out.write("         <td>" + member.getAge() + "</td>");
            out.write("     </tr>");
        }
    %>
    </tbody>
</table>
</body>
</html>
```

- #### JSP는 자바 로직을 사용하기 때문에 사용하는 클래스를 import 해야 한다.

- #### `<%@ page contentType="text/html;charset=UTF-8" language="java" %>`는 JSP를 사용한다는 뜻으로 content-type과 charset을 설정한다.

- #### JSP는 실행 시 나중에 서블릿으로 자동 변환 되기 때문에 request, response를 그대로 사용하는 것이 가능하다. 즉, 서비스 로직이 이전과 동일하게 호출되는 것이다.

- #### `<% %>` 내부에 자바 코드를 입력할 수 있다.

- #### `<%= %>` 내부에 자바 코드를 출력할 수 있다.

- #### 보통 JSP 작성 시 로직을 먼저 넣고 HTML을 수정한다.

- #### JSP에서 사용가능한 예약어를 사용하여 비즈니스 로직을 실행할 수 있다(out 등). 이후에 예약어 사용 영역도 서블릿으로 변환되어 모두 write 된다.

- #### HTML 코드 부분은 이전과 같이 print로 전송된다.

- #### JSP는 서블릿 패턴의 단점을 보완하기는 했지만 다양한 코드가 모두 JSP에 노출되어 있다는 단점이 있다. 즉, 간단한 HTML 수정에도 코드까지 영향 받을 수 있다.

- #### JSP가 너무 많은 역할을 하게 되어 유지보수가 매우 힘들어진다.



## MVC Pattern



![MVC pattern](/media/mwkang/Klevv/Spring 일지/MVC1/01.09/MVC pattern.png)

- #### 비즈니스 로직은 서블릿처럼 다른 곳에서 처리하고 JSP는 목적에 맞게 HTML로 화면을 표시하게 나누는 패턴이다.

- #### UI와 비즈니스 로직을 수정하는 일은 각각 다르게 발생할 가능성이 매우 높고 대부분 서로에게 영향을 주지 않아서 라이프 사이플이 다르다. 그렇기 때문에 둘을 분리하여 관리하는 것이 좋다.

- #### MVC 패턴은 컨트롤러(Controller)와 뷰(View) 영역으로 나누는 것을 말한다.

- #### 컨트롤러는 HTTP 요청을 받아서 파라미터를 검증하고 비즈니스 로직을 실행한다. 그 후에 뷰에 전달할 결과 데이터를 조회하여 모델에 담는다.

- #### 모델은 뷰에 출력할 데이터를 담아둔다.

- #### 뷰는 모델에 담겨있는 데이터를 사용해서 화면을 그린다. 즉, HTML을 생성한다.



```java
@WebServlet(name = "mvcMemberListServlet", urlPatterns = "/servlet-mvc/members")
public class MvcMemberListServlet extends HttpServlet {

    private MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ArrayList<Member> members = memberRepository.findAll();

        request.setAttribute("members", members);

        String viewPath = "/WEB-INF/views/members.jsp";
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
        dispatcher.forward(request, response);



    }
}
```

```jsp
<%-- member.jsp --%>
<%-- /WEB-INF/views --%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<a href="/index.html">메인</a>
<table>
    <thead>
    <th>id</th>
    <th>username</th>
    <t h>age</th>
    </thead>
    <tbody>
    <c:forEach var="item" items="${members}">
            <tr><td>${item.id}</td>
            <td>${item.username}</td>
            <td>${item.age}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>

</body>
</html>
```

- #### 서블릿은 비즈니스 로직을 처리하는 역할만 담당한다.

- #### Request는 데이터 저장소의 기능을 수행하여 모델로 사용 가능하여 JSP에 넘겨줄 데이터를 request에 저장한 후 JSP로 전달한다. SetAttribute(), getAttribute() 메소드를 이용하여 request에 값을 저장하고 사용한다.

- #### Forward() 메소드를 사용하여 템플릿 엔진을 호출하여 HTML을 응답할 수 있다. 클라이언트에게 redirect하는 것이 아닌 서버에서 서버로 호출하는 것이다. 즉, 서버내 메소드를 호출하는 것과 같으며 URL이 변경되지 않는다. 클라이언트는 이러한 상황을 전혀 인지하지 못한다.

- #### 이제부터 JSP 파일은 /WEB-INF에 저장하는데 이 경로 안에 존재하는 것은 외부에서 직접 호출할 수 없다. 즉, 컨트롤러가 호출해야 하는 영역이다(WAS에 정해진 규칙).

- #### /WEB-INF 내부의 JSP는 상대 경로를 이용하게 했다. 즉, URL의 마지막 경로만 변경되게 설정한 것이다.

- #### Request에 키, 값 형식으로 데이터를 저장하면 뷰 템플릿에서 `${}`에 키값으로 값을 불러와서 동적 HTML을 생성할 수 있다. 원래는 반환타입이 Object인 경우 캐스팅 후 이용하여 데이터를 하나하나 뽑아야 하는 번거로움이 존재했다.

- #### JSTL의 특별한 기능으로 JSP에 쉽게 로직을 부여할 수 있다. `<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>` 등으로 JSTL 기능 사용을 선언한다. 보통 뷰 템플릿은 모두 이러한 기능이 있다.

- #### MVC 패턴에는 중복 호출이나 사용하지 않는 객체가 존재한다는 단점이 있다. 즉, 공통 처리가 어려운 문제점이 있는데 이 때 프론트 컨트롤러(Front Controller)패턴을 도입하여 문제를 해결한다.

- #### 프론트 컨트롤러는 대표 컨트롤러 하나를 배치하는 것으로 공통 처리를 하며 프론트 컨트롤러를 통해서만 다른 컨트롤러들을 호출할 수 있게 설계한다.



# Front Controller



## Version 1 - Front Controller Creation



![v1](/media/mwkang/Klevv/Spring 일지/MVC1/01.09/v1.png)

- #### 프론트 컨트롤러 서블릿 하나로 클라이언트의 요청을 받아서 나머지 컨트롤러는 서블릿을 사용하지 않아도 된다.



```java
public interface ControllerV1 {

    void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

}
```

```java
public class MemberListControllerV1 implements ControllerV1 {

    private MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<Member> members = memberRepository.findAll();

        request.setAttribute("members", members);

        String viewPath = "/WEB-INF/views/members.jsp";
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
        dispatcher.forward(request, response);
    }
}
```

```java
@WebServlet(name = "frontControllerServletV1", urlPatterns = "/front-controller/v1/*")
public class FrontControllerServletV1 extends HttpServlet {

    private Map<String, ControllerV1> controllerMap = new HashMap<>();

    public FrontControllerServletV1() {
        controllerMap.put("/front-controller/v1/members/new-form", new MemberFormControllerV1());
        controllerMap.put("/front-controller/v1/members/save", new MemberSaveControllerV1());
        controllerMap.put("/front-controller/v1/members", new MemberListControllerV1());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("FrontControllerServletV1.service");

        String requestURI = request.getRequestURI();

        ControllerV1 controller = controllerMap.get(requestURI);

        if(controller == null){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        controller.process(request, response);

    }
}
```

- #### 다형성을 위해 인터페이스를 생성한 후 모든 컨트롤러가 상속받게 한다. 다형성으로 각 객체에 접근하는 것이 쉬워진다.

- #### 다형성으로 로직의 일관성을 가질 수 있다.

- #### 프론트 컨트롤러 서블릿의 마지막 경로를 '*' 로 설정하여 마지막 경로에 어떠한 경로가 오면 이 서블릿이 작동할 수 있게한다.

- #### ControllerMap HashMap 객체에 키로 URI를 넣고 값에 컨트롤러 객체를 저장한다.

- #### GetRequestURI() 메소드를 통해 요청의 URI를 문자열로 받을 수 있다.

- #### 요청 URI로 controllerMap에서 컨트롤러를 찾은 후 객체의 로직을 실행하여 비즈니스 로직 수행을 완료하고 뷰 템플릿을 호출할 수 있다.

- #### 이전 MVC 패턴과 달라진 코드는 별로 없다.



## Version 2 - View Division



![v2](/media/mwkang/Klevv/Spring 일지/MVC1/01.09/v2.png)

- #### Version 1과 비슷하지만 MyView 객체의 도입으로 뷰를 호출하는 여역을 분리한다.



```java
public interface ControllerV2 {

    MyView process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;

}
```

```java
public class MemberListControllerV2 implements ControllerV2 {

    private MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    public MyView process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ArrayList<Member> members = memberRepository.findAll();

        request.setAttribute("members", members);

        return new MyView("/WEB-INF/views/members.jsp");
    }
}
```

```java
@WebServlet(name = "frontControllerServletV2", urlPatterns = "/front-controller/v2/*")
public class FrontControllerServletV2 extends HttpServlet {

    private Map<String, ControllerV2> controllerMap = new HashMap<>();

    public FrontControllerServletV2() {
        controllerMap.put("/front-controller/v2/members/new-form", new MemberFormControllerV2());
        controllerMap.put("/front-controller/v2/members/save", new MemberSaveControllerV2());
        controllerMap.put("/front-controller/v2/members", new MemberListControllerV2());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String requestURI = request.getRequestURI();

        ControllerV2 controller = controllerMap.get(requestURI);

        if(controller == null){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        MyView view = controller.process(request, response);
        view.render(request, response);

    }
}
```

```java
public class MyView {

   private String viewPath;

   public MyView(String viewPath){
       this.viewPath = viewPath;
   }

   public void render(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
       dispatcher.forward(request, response);

   }
}
```

- #### 뷰로 이동하는 것을 rendering이라고 부른다. 즉, 뷰를 만드는 행위 자체를 렌더링이라고 한다.

- #### MyView 생성으로 각 컨트롤러에 들어있던 공통 로직을 편리하게 분리하였다. 컨트롤러 코드가 간결해진 것을 볼 수 있다. 유지보수 시 쉽게 MyView만 수정하면 된다.

- #### 다형성을 위해 MyView도 인터페이스로 설정할 수 있다. 지금은 JSP만 렌더링 하기 때문에 필요 없지만 더 다양한 파일을 접근해야 한다면 인터페이스로 변환하여 사용하여 다형성을 이용하는 것이 좋다.



## Version 3 - Model Addition



![v3](/media/mwkang/Klevv/Spring 일지/MVC1/01.09/v3.png)

- #### ModelView 클래스를 생성하여 호출할 논리 이름, 뷰에 전달할 모델을 담는 형식의 설계이다.

- #### 프론트 컨트롤러에 viewResolver 메소드를 추가하여 컨트롤러에서 중복되는 prefix, suffix를 프론트 컨트롤러에서 처리할 수 있게 한다. 즉, 컨트롤러는 논리 이름만 입력하여 프론트 컨트롤러에 반환하면 된다.



```java
public interface ControllerV3 {

    ModelView process(Map<String, String> paramMap);
    
}
```

```java
public class MemberListControllerV3 implements ControllerV3 {

    private MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    public ModelView process(Map<String, String> paramMap) {

        ArrayList<Member> members = memberRepository.findAll();
        ModelView mv = new ModelView("members");
        mv.getModel().put("members", members);

        return mv;
    }
}
```

```java
@WebServlet(name = "frontControllerServletV3", urlPatterns = "/front-controller/v3/*")
public class FrontControllerServletV3 extends HttpServlet {

    private Map<String, ControllerV3> controllerMap = new HashMap<>();

    public FrontControllerServletV3() {
        controllerMap.put("/front-controller/v3/members/new-form", new MemberFormControllerV3());
        controllerMap.put("/front-controller/v3/members/save", new MemberSaveControllerV3());
        controllerMap.put("/front-controller/v3/members", new MemberListControllerV3());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String requestURI = request.getRequestURI();

        ControllerV3 controller = controllerMap.get(requestURI);

        if(controller == null){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // paramMap 생성해서 컨트롤러로 넘겨줘야 한다
        // 컨트롤러 핵심 비즈니스 완료 후 뷰 논리 이름이 반환되었다
        Map<String, String> paramMap = createParamMap(request);
        ModelView mv = controller.process(paramMap);

        // 뷰 논리이름을 물리이름으로 변환한다
        String viewName = mv.getViewName();
        MyView view = viewResolver(viewName);

        view.render(mv.getModel(), request, response);

    }

    private MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }

    private Map<String, String> createParamMap(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<>();
        request.getParameterNames().asIterator()
                .forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName)));
        return paramMap;
    }
}
```

```java
public class MyView {

   private String viewPath;

   public MyView(String viewPath){
       this.viewPath = viewPath;
   }
   
   // V3 에서 사용하는 것으로 model에 포함된 항목을 request로 넣어준 후 forward 한다
    public void render(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        modelToRequestAttribute(model, request);
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
        dispatcher.forward(request, response);
   }

    private void modelToRequestAttribute(Map<String, Object> model, HttpServletRequest request) {
        model.forEach((key, value) -> request.setAttribute(key, value));
    }
}
```

```java
@Getter
@Setter
public class ModelView {

    private String viewName;
    private Map<String, Object> model = new HashMap<>();

    public ModelView(String viewName) {
        this.viewName = viewName;
    }
    
}
```

- #### 프론트 컨트롤러는 paramMap HashMap 객체에 요청에 대한 키, 값을 저장 후 컨트롤러에 전달한다(이러한 특정 로직은 메소드로 추출하여 사용하는 것이 좋다). 즉, 모든 컨트롤러는 파라미터로 paramMap을 받고 원하는 데이터를 조회하여 처리한다.

- #### 컨트롤러는 ModelView에 호출할 논리 이름, 뷰에 전달할 데이터를 담은 객체를 저장하여 프론트 컨트롤러로 반환한다.

- #### 프론트 컨트롤러 ViewResolver로 ModelView에 있는 논리 이름을 prefix, suffix와 조합하여 물리 이름(절대 경로)를 만든 후 MyView 객체에 담고 렌더링을 시작한다. 이후에 경로가 바뀌면 viewResolver만 변경하면 되므로 유지보수가 쉬워진다.

- ####  MyView는 request 객체에 ModelView에 저장된 모델의 데이터를 저장한다. 그 후 렌더링을 시작한다.



## Version 4 - Practical Controller



![v4](/media/mwkang/Klevv/Spring 일지/MVC1/01.09/v4.png)

- #### 개발자가 편리하게 개발할 수  있게 Version 3를 리펙토링하는 작업을 수행한다.



```java
public interface ControllerV4 {
    /**
     *
     * @param paramMap
     * @param model
     * @return viewName
     */
    String process(Map<String, String> paramMap, Map<String, Object> model);
}
```

```java
public class MemberListControllerV4 implements ControllerV4 {

    private MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    public String process(Map<String, String> paramMap, Map<String, Object> model) {
        ArrayList<Member> members = memberRepository.findAll();

        model.put("members", members);

        return "members";
    }
}
```

```java
@WebServlet(name = "frontControllerServletV4", urlPatterns = "/front-controller/v4/*")
public class FrontControllerServletV4 extends HttpServlet {

    private Map<String, ControllerV4> controllerMap = new HashMap<>();

    public FrontControllerServletV4() {
        controllerMap.put("/front-controller/v4/members/new-form", new MemberFormControllerV4());
        controllerMap.put("/front-controller/v4/members/save", new MemberSaveControllerV4());
        controllerMap.put("/front-controller/v4/members", new MemberListControllerV4());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String requestURI = request.getRequestURI();

        ControllerV4 controller = controllerMap.get(requestURI);

        if(controller == null){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // paramMap 생성해서 컨트롤러로 넘겨줘야 한다
        // 컨트롤러 핵심 비즈니스 완료 후 뷰 논리 이름이 반환되었다
        Map<String, String> paramMap = createParamMap(request);
        Map<String, Object> model = new HashMap<>(); // V4 에서 추가된 무분으로 ModelView 사용하지 않고 바로 viewName과 model이라는 객체 이용한다.
        String viewName = controller.process(paramMap, model);

        // 뷰 논리이름을 물리이름으로 변환한다
        MyView view = viewResolver(viewName);

        view.render(model, request, response);

    }

    private MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }

    private Map<String, String> createParamMap(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<>();
        request.getParameterNames().asIterator()
                .forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName)));
        return paramMap;
    }
}
```

- #### 프론트 컨트롤러에서 컨트롤러에 paramMap과 비어있는 모델 객체를 파라미터로 전달한다.

- #### 컨트롤러에서 ModelView를 사용하지 않고 파라미터로 넘어온 모델에 뷰에 전달할 데이터를 저장 후 논리 이름을 반환한다. 이 때 모델의 키는 문자열로, 값은 Object로 모든 데이터를 저장할 수 있게 설정하는 것이 다양한 컨트롤러가 존재할 때 좋다.

- #### Version 4는 사실 Version 3와 거의 동일하다. 그저 개발자의 편의를 위해 ModelView를 제거하고 모델을 파라미터로 넘기고 뷰의 논리 이름을 반환하는 작은 변화만 가져온 것이다.



## Version 5 - Flexible Controller



![v5](/media/mwkang/Klevv/Spring 일지/MVC1/01.09/v5.png)

- #### 각 컨트롤러를 사용할 때 어떤 컨트롤러는 Version 3를 이용하고 어떤 컨트롤러는 Version 4를 이용하고자 할 때 이전 코드는 객체 타입이 정해져 있어서 반영하지 못 한다.

- #### 어댑터(adapter) 패턴을 사용하여 프론트 컨트롤러가 다양한 방식의 컨트롤러를 처리할 수 있도록 만들 수 있다.

- #### 핸들러 어댑터는 핸들러를 처리할 수 있는 어댑터이며 핸들러는 컨트롤러를 뜻한다. 어댑터의 사용으로 컨트롤러 뿐만 아니라 어떤 것이라도 URL에 매핑해서 사용할 수 있기 때문에 핸들러로 부르게 되었다.

- #### 핸들러는 핸들러 어댑터를 통해서만 호출하게 한다.



```java
public interface MyHandlerAdapter {

    boolean supports(Object handler);

    ModelView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException;

}
```

```java
public class ControllerV3HandlerAdapter implements MyHandlerAdapter {

    @Override
    public boolean supports(Object handler) {
        return (handler instanceof ControllerV3);
    }

    @Override
    public ModelView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException {
        ControllerV3 controller = (ControllerV3) handler;

        Map<String, String> paramMap = createParamMap(request);
        ModelView mv = controller.process(paramMap);

        return mv;
    }

    private Map<String, String> createParamMap(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<>();
        request.getParameterNames().asIterator()
                .forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName)));
        return paramMap;
    }
}
```

```java
public class ControllerV4HandlerAdapter implements MyHandlerAdapter {
    @Override
    public boolean supports(Object handler) {
        return (handler instanceof ControllerV4);
    }

    @Override
    public ModelView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException {
        ControllerV4 controller = (ControllerV4) handler;

        Map<String, String> paramMap = createParamMap(request);
        HashMap<String , Object> model = new HashMap<>();

        String viewName = controller.process(paramMap, model);

        ModelView mv = new ModelView(viewName);
        mv.setModel(model);

        return mv;
    }

    private Map<String, String> createParamMap(HttpServletRequest request) {
        Map<String, String> paramMap = new HashMap<>();
        request.getParameterNames().asIterator()
                .forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName)));
        return paramMap;
    }
}
```

```java
@WebServlet(name = "frontControllerServletV5", urlPatterns = "/front-controller/v5/*")
public class FrontControllerServletV5 extends HttpServlet {

    private final Map<String, Object> handlerMappingMap = new HashMap<>();
    private final List<MyHandlerAdapter> handlerAdapters = new ArrayList<>();

    public FrontControllerServletV5() {
        initHandlerMappingMap();
        inithandlerAdapters();
    }

    private void initHandlerMappingMap() {
        // V3
        handlerMappingMap.put("/front-controller/v5/v3/members/new-form", new MemberFormControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members/save", new MemberSaveControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members", new MemberListControllerV3());

        // V4
        handlerMappingMap.put("/front-controller/v5/v4/members/new-form", new MemberFormControllerV4());
        handlerMappingMap.put("/front-controller/v5/v4/members/save", new MemberSaveControllerV4());
        handlerMappingMap.put("/front-controller/v5/v4/members", new MemberListControllerV4());
    }
    private void inithandlerAdapters() {
        handlerAdapters.add(new ControllerV3HandlerAdapter());
        handlerAdapters.add(new ControllerV4HandlerAdapter());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // handler 찾아온다
        Object handler = getHandler(request);

        if(handler == null){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        // handler 에 대한 adapter 찾아온다
        MyHandlerAdapter adapter = getHandlerAdapter(handler);

        // adapter에서 핸들을 하여 핸들러를 호출한다. 호출한 결과를 ModelView 객체에 담아서 반환한다.
        ModelView mv = adapter.handle(request, response, handler);

        // 뷰 논리이름을 물리이름으로 변환한다
        String viewName = mv.getViewName();
        MyView view = viewResolver(viewName);

        view.render(mv.getModel(), request, response);

    }

    private Object getHandler(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return handlerMappingMap.get(requestURI);
    }

    private MyHandlerAdapter getHandlerAdapter(Object handler) {
        for (MyHandlerAdapter adapter : handlerAdapters) {
            if(adapter.supports(handler)){
                return adapter;
            }
        }
        throw new IllegalArgumentException("handler adapter를 찾을 수 없습니다. handler=" + handler);
    }

    private MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }

}
```

- #### 핸들러 어댑터는 인터페이스로 생성하여 다형성을 형성한다.

- #### 핸들러 어댑터의 supports() 메소드는 지정한 핸들러가 이 어댑터로 호출 가능한지 여부를 판단한다. 핸들러의 인스턴스가 어떤 인터페이스를 상속하는지로 판단한다(instanceOf 사용). Handle 메소드는 실제 컨트롤러를 호출하고 ModelView 객체를 반환한다(일관된 객체를 반환해야 하므로 Version 4에 대한 어댑터도 ModelView 객체를 생성하여 반환하게 한다).

- #### 핸들러 어댑터의 코드를 보면 Version 5 이전 버전의 프론트 컨트롤러와 유사하다는 것을 느낄 수 있다. 심지어 createParamMap() 메소드도 Version 5의 프론트 컨트롤러에 없고 핸들러 어댑터에 존재한다.

- #### 프론트 컨트롤러의 핸들러 매핑 정보를 담는 HashMap의 값 타입을 Object로 설정하여 Version 3, 4 가 모두 저장될 수 있도록 하였다.

- #### HandlerAdapters 리스트 객체는 각 핸들러 어댑터 객체를 저장하며 getHandlerAdapter() 메소드로 핸들러에 적합한 어댑터인지 확인할 때 사용한다. GetHandlerAdapter() 메소드에서 핸들러에 맞는 어댑터가 없으면 예외 로그를 발생하여 어떤 핸들러가 들어왔는지 출력하여 디버깅을 쉽게 할 수 있게 하였다.

- #### 프론트 컨트롤러의 initHandlerMappingMap(), initHandlerAdapters()도 따로 처리하는 객체를 만들면 프론트 컨트롤러는 OCP를 만족하여 기능을 확장해도 코드 변경이 거의 없어질 수 있다.

- #### 현재 로직은 프론트 컨트롤러가 어댑터에 의존한다.

- #### 어노테이션을 사용해서 컨트롤러를 편리하게 사용하려면 어노테이션을 지원하는 어댑터를 만들면 된다. 사실 스프링 MVC가 어노테이션 스타일 컨트롤러와 어댑터를 사용한다.

- #### 다형성과 어댑터 덕분에 기존 구조를 유지하면서 프레임워크의 기능을 확장할 수 있다. If-else는 직관적이어서 간단한 로직을 구현할 때 좋지만 확장성을 고려하면 인터페이스로 다형성을 고려하여 설계하는 것이 좋다. 다형성을 고려하는 설계가 유지보수에도 좋다.



# About Spring MVC



![spring_mvc구조](/media/mwkang/Klevv/Spring 일지/MVC1/01.09/spring_mvc구조.png)

- #### 스프링 MVC에서는 프론트 컨트롤러가 디스패처 서블릿(Dispatcher Servlet)으로 대체된다.

- #### 디스패처 서블릿도 부모 클래스에서 HttpServlet을 상속받아서 사용하고 서블릿으로 동작한다.

  - #### DispatcherServlet -> FrameworkServlet -> HttpServletBean -> HttpServlet

- #### 디스패처 서블릿을 서블릿으로 등록하면서 내장 톰캣을 띄운다.

- #### 스프링 부트는 디스패처 서블릿으로 모든 경로(urlPatterns="/")에 대해서 매핑한다.

  - #### 더 자세한 경로가 우선순위가 높다. 즉, 기존에 등록한 서블릿도 함께 동작한다.

- #### 디스패처 서블릿은 FrameworkServlet에서 오버라이드한 service()를 시작으로 DispatcherServlet.doDispatch()가 호출된다.

- #### 스프링 MVC의 강점은 디스패처 서블릿 코드의 변경없이 원하는 기능을 변경하거나 확장할 수 있다는 점이다.

- #### 주요 인터페이스 목록

  > 핸들러 매핑: org.springframework.web.servlet.HandlerMapping
  >
  > 핸들러 어댑터: org.springframework.web.servlet.HandlerAdapter
  >
  > 뷰 리졸버: org.springframework.web.servlet.ViewResolver
  >
  > 뷰: org.springframework.web.servlet.View



## HandlerMapping & HandlerAdapter



- #### HandlerMapping

  > 0 = RequestMappingHandlerMapping : 어노테이션 기반의 컨트롤러인 @RequestMapping에서 사용
  >
  > 1 = BeanNameUrlHandlerMapping : 스프링 빈의 이름으로 핸들러를 찾는다.

  - #### HandlerMapping을 순서대로 실행하여 핸들러를 찾는다.

  - #### @RequestMapping으로 매핑된 경로로 찾는 것이 우선 순위가 가장 높으며 두 번째는 빈 이름으로 핸들러를 찾는 것이다.

- #### HandlerAdapter

  > 0 = RequestMappingHandlerAdapter : 어노테이션 기반의 컨트롤러인 @RequestMapping에서 사용
  >
  > 1 = HttpRequestHandlerAdapter : HttpRequestHandler 처리
  >
  > 2 = SimpleControllerHandlerAdapter : Controller 인터페이스(어노테이션X, 과거에 사용) 처리

  - #### HandlerAdapter의 supports()를 순서대로 호출한다.

  - #### HandlerAdapter은 핸들러를 실행하고 결과 디스패처 서블릿에 반환한다.



## ViewResolver



```properties
# application.properties

spring.mvc.view.prefix=/WEB-INF/views/
spring.mvc.view.suffix=.jsp
```

- #### Prefix와 suffix를 설정하여 뷰 리졸버가 로직을 수행하도록 할 수 있다.

- #### ViewResolver

  > 1 = BeanNameViewResolver : 빈 이름으로 뷰를 찾아서 반환한다. (예: 엑셀 파일 생성 기능에 사용)
  >
  > 2 = InternalResourceViewResolver : JSP를 처리할 수 있는 뷰를 반환한다.

  - #### InternalResourceViewResolver은 JSP처럼 forward()를 호출해서 처리할 수 있는 경우에 사용한다(JSTL 라이브러리가 있으면 InternalResourceView를 상속받은 JstlView로 반환한다).

  - #### Thymeleaf 뷰 템플릿의 경우 ThymeleafViewResolver를 등록해야 했지만 최근에는 라이브러리 추가 시 스프링 부트가 모두 자동화로 등록한다.



## Annotation Base Controller



### Version 1 - Basic Style



```java
@Controller
public class SpringMemberListControllerV1 {

    private MemberRepository memberRepository = MemberRepository.getInstance();

    @RequestMapping("/springmvc/v1/members")
    public ModelAndView process() {

        ArrayList<Member> members = memberRepository.findAll();
        ModelAndView mv = new ModelAndView("members");
        mv.addObject("members", members);

        return mv;
    }
}
```

- #### @Controller는 스프링이 자동으로 스프링 빈으로 등록한다. 하지만 스프링 MVC에서는 어노테이션 기반 컨트롤러로 인식한다.

- #### @RequestMapping은 요청 정보를 매핑한다. 지정한 경로 이름이 호출되면 메소드가 실행되며 어노테이션 기반 컨트롤러로 인식한다.

- #### ModelAndView는 모델과 뷰 정보를 담아서 반환한다.



### Version 2 - Controller Integration



```java
@Controller
@RequestMapping("/springmvc/v2/members")
public class SpringMemberControllerV2 {

    private final MemberRepository memberRepository = MemberRepository.getInstance();

    @RequestMapping
    public ModelAndView members() {

        ArrayList<Member> members = memberRepository.findAll();
        ModelAndView mv = new ModelAndView("members");
        mv.addObject("members", members);

        return mv;
    }
}
```

- #### @RequestMapping을 클래스 레벨에 중복되는 경로와 함께 작성하면 내부 메소드에서 중복된 경로를 작성하지 않아도 된다.



### Version 3 - Useful Methods



```java
@Controller
@RequestMapping("/springmvc/v3/members")
public class SpringMemberControllerV3 {

    private final MemberRepository memberRepository = MemberRepository.getInstance();

    @GetMapping
    public String members(Model model) {
        ArrayList<Member> members = memberRepository.findAll();

        model.addAttribute("members",members);

        return "members";
    }

}
```

- #### Model 파라미터로 모델로 값을 전달 받고 값을 넣어서 뷰 템플릿에 넘겨줄 수 있다.

- #### ViewName을 직접 반환하는 것이 가능하다. 즉, 모델과 뷰를 분리한 것이다.

- #### @RequestParam은 request.getParameter("")과 같은 것으로 GET 쿼리 파라미터, POST From 방식을 모두 지원한다.

- #### @GetMapping, @PostMapping 등 HTTP 메소드를 어노테이션 기반으로 표현한다. 이 어노테이션은 모두 @RequestMapping을 상속한다.



# Additional Knowledge



- #### ctrl + o 를 사용하면 사용가능한 메소드가 나오는데 이때 자물쇠 표시는 protected로 설정되어 있다는 뜻이다.

- #### System.out.println()은 리소스를 많이 사용하여 성능저하에 영향을 줄 수 있어서 가능하면 Logger을 사용하는 것이 좋다.



# Keyboard Shortcut



- #### ctrl + alt + m : 메소드를 extract하여 생성 가능하다.

- #### ctrl + alt + b : 인터페이스, 클래스를 상속한 항목을 확인할 수 있다(하위 항목을 보여준다).

- #### shift + F6 : 클래스 이름을 바로 바꾸는 것이 가능하다.

- #### F2 : 화면 내의 바로 다음 오류가 발생한 곳으로 커서를 이동한다.

- #### 리스트명.iter : 해당 리스트로 for-each 문이 자동으로 생성된다.

- #### ctrl + n : 내장된 인터페이스, 클래스를 확인하는 것이 가능하다.



Image 출처 : [김영한 스프링 MVC1](https://www.inflearn.com/course/%EC%8A%A4%ED%94%84%EB%A7%81-mvc-1)

