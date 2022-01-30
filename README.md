# Spring-Servlet-MVCPattern-SpringMVC



## Description

본 프로젝트는 MVC 패턴의 탄생 배경과 MVC 패턴을 직접 세부적으로 구현 한다. WAS를 통한 Servlet의 작동 원리를 이해한 후 초기 Servlet Pattern부터 구현 한다. Servlet과 JSP Pattern을 구현한 후 MVC 패턴의 필요성과 발전 과정에 맞춰서 Version 1 ~ Version 5까지 구현 한다. 직접 구현한 MVC 패턴은 Spring MVC에 자동으로 등록된 주요 기능이며  추후에 Spring MVC 구현 및 디버깅 시 빠르게 문제점을 발견하고 해결할 수 있다.



------



## Environment

<img alt="framework" src ="https://img.shields.io/badge/Framework-SpringBoot-green"/><img alt="framework" src ="https://img.shields.io/badge/Language-java-b07219"/> 

Framework: `Spring Boot` 2.6.2

Project: `Gradle`

Packaging: `War` (JSP를 실행 위함)

IDE: `Intellij`

Template Engine: `JSP`

Dependencies: `Spring Web`, `Lombok`



------



## Installation



![Linux](https://img.shields.io/badge/Linux-FCC624?style=for-the-badge&logo=linux&logoColor=black) 

```
./gradlew build
cd build/lib
java -jar hello-spring-0.0.1-SNAPSHOT.jar
```



![Windows](https://img.shields.io/badge/Windows-0078D6?style=for-the-badge&logo=windows&logoColor=white) 

```
gradlew build
cd build/lib
java -jar hello-spring-0.0.1-SNAPSHOT.jar
```



------



## Core Feature



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

        Object handler = getHandler(request);

        if(handler == null){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        MyHandlerAdapter adapter = getHandlerAdapter(handler);

        ModelView mv = adapter.handle(request, response, handler);

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

![v5](/media/mwkang/Klevv/Spring 일지/MVC1/01.09/v5.png)

Spring MVC의 DispatcherServlet을 Serlvet으로 주요 기능인 HandlerMapping, HanderAdapter을 직접 구현한 것이다. 다형성을 이용하여 확장성을 고려하였으며 Spring MVC 패턴의 내부 로직을 정확히 이해하여 응용하는 것이 가능하다.



------



## Demonstration Video



![Spring-Servlet-MVCPattern-SpringMVC](https://user-images.githubusercontent.com/79822924/151713665-9c982d58-45cf-45fe-8498-e7683f2282c4.gif)




------



## More Explanation



[Spring-Servlet-MVCPattern-SpringMVC-Note.md](https://github.com/mwkangit/Spring-Servlet-MVCPattern-SpringMVC/blob/master/Spring-Servlet-MVCPattern-SpringMVC-Note.md)
