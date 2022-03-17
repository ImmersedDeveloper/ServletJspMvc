### 🙇 Reference
[Inflearn : 스프링 MVC 1편 - 백엔드 웹 개발 핵심 기술(김영한님)](https://www.inflearn.com/course/%EC%8A%A4%ED%94%84%EB%A7%81-mvc-1/dashboard)
[MDN Web Docs](https://developer.mozilla.org/en-US/)
[스프링 공식](https://spring.io/)

***
### 1. 웹 애플리케이션 이해

#### HTTP 시대

웹은 전부 HTTP 기반으로 통신하게 됩니다.
웹 브라우저에서 URL을 입력하면,
인터넷을 통해 서버에 접근하고
서버에선 html 생성해서 클라이언트에게 전달해줍니다.

클라이언트에서 요청, 서버에서 응답할 때
__모든 것이 HTTP__ 기반에서 동작합니다.

HTTP 메시지에 전송되는 데이터의 형태는 다음과 같습니다.

- HTML, TEXT
- IMAGE, 음성, 영상, 파일
- JSON, XML (API)
거의 모든 형태의 데이터를 전송하는 것이 가능 합니다.
서버간에 데이터를 주고 받을 때도 대부분 HTTP를 사용합니다.
지금은 html같은 하이퍼미디어 문서 뿐만 아니라 거의 모든 것을 다 전송할 수 있는 HTTP의 시대라고 합니다. 바야흐로 HTTP의 시대라고 할 수 있습니다.

#### 웹 서버(Web Server)
- HTTP 기반으로 동작합니다.
- 정적 리소스와 기타 부가기능을 제공합니다.
  - 정적(파일) HTML, CSS, JS, 이미지, 영상
  - 예) NGINX, APACHE
  
#### 웹 애플리케이션 서버(WAS - Web Application Server)
- HTTP 기반으로 동작합니다.
- 웹 서버의 기능들을 포함하고 있습니다. (정적 리소스 제공)
- 프로그램 코드를 실행해서 애플리케이션 로직을 수행합니다.
  - 동적 HTML, HTTP API(JSON)
  - 서블릿, JSP, 스프링 MVC
    - 예) 톰캣() Jetty, Undertow
    
#### 웹 서버, 웹 애플리케이션 서버(WAS) 차이
- __웹 서버는 정적 리소스(파일) 제공, WAS는 애플리케이션 로직까지 수행합니다.__
- 웹 서버도 프로그램을 실행하는 기능을 포함하기도 합니다.
- WAS도 웹 서버의 기능을 제공합니다.(용어, 경계가 모호합니다.)
- 자바는 서블릿 컨테이너 기능을 제공하면 WAS
  - 서블릿 없이 자바코드를 실행하는 서버 프레임워크도 있습니다.
- __WAS는 애플리케이션 코드를 실행하는데 더 특화되어 있다는 차이가 있습니다.__


#### 웹 시스템 구성 - WAS, DB

WAS는 정적 리소스, 애플리케이션 로직 모두 제공 가능하기 때문에 WAS, DB 만으로 시스템 구성 가능합니다.
하지만 다음과 같은 문제가 있습니다.

- WAS가 너무 많은 역할을 담당해서 서버 과부하가 우려됩니디.
- 가장 비싼 애플리케이션 로직이 정적 리소스 때문에 수행이 어려울 수 있습니다.
- WAS 장애시 접근 불가이기 때문에 오류 화면도 노출이 불가능합니다.
![WAS](https://images.velog.io/images/urtimeislimited/post/22b65a67-dc40-4aee-a270-5fb6d90a4f6c/image.png)


그래서 일반적으로 다음과 같이 웹 시스템을 구성합니다.

- 정적 리소스는 웹 서버가 처리합니다.
- 웹 서버는 애플리케이션 로직같은 동적인 처리가 필요할 떄 WAS에 요청을 위임합니다.
- WAS는 중요한 애플리케이션 로직만을 처리하는 것을 전담합니다.
![webWasDb](https://images.velog.io/images/urtimeislimited/post/6f3717eb-e9eb-4abd-aa07-c82c125df53f/image.png)
- 또한 리소스를 효율적으로 관리할 수 있습니다.
  - 정적 리소스가 많이 사용되면 Web 서버를 증설하면 됩니다.
  - 애플리케이션 리소스가 많이 사용되면 WAS 증설하면 됩니다.
![wwd](https://images.velog.io/images/urtimeislimited/post/2c481c52-4d0f-4fde-9c16-fb43060a5633/image.png)

#### 웹 시스템 구성(Web, Was, Db) 장점
- 정적 리소스만 제공하는 웹 서버는 잘 죽지 않습니다.
- 애플리케이션 로직이 동작하는 WAS 서버는 잘 죽습니다.
- WAS, DB 장애시 WEB 서버가 오류 화면을 제공하는 것이 가능합니다.
![Web, Was, Db](https://images.velog.io/images/urtimeislimited/post/403cc7fd-6457-404c-adc6-c18385c1ba0a/image.png)

#### 서블릿

![form](https://images.velog.io/images/urtimeislimited/post/732ce5d0-ca07-41ad-bfe1-eee5515723e1/image.png)
html form 태그를 작성해서 전송을 동작시키면
웹브라우저는 다음과 같은 HTTP 메시지를 생성하여 요청합니다.

![post](https://images.velog.io/images/urtimeislimited/post/7ba9ed63-ea42-4066-a2af-fc06d5b8f84b/image.png)

웹 애플리케이션 서버를 직접 구현해야 한다면 의미있는 비즈니스 로직 외에 해야할 작업이 굉장히 복잡하고 많아집니다.

![was](https://images.velog.io/images/urtimeislimited/post/bf116eda-4a45-4f5e-bb5d-d4d8f6381d44/image.png)

이 때 서블릿이 등장합니다. 
서블릿을 지원하는 WAS를 사용하면 서블릿은 WAS가 수행해야 하는 의미있는 비즈니스 로직을 제외한 모든 작업을 전부 자동화해줍니다.
![servlet](https://images.velog.io/images/urtimeislimited/post/5dba3a80-8ff6-482f-9683-1de8b8180c18/image.png)

서블릿의 특징은 다음과 같습니다.

```
@WebServlet(name = "helloServlet", urlPatterns = "/hello")
public class HelloServlet extends HttpServlet {
 @Override
 protected void service(HttpServletRequest request, HttpServletResponse response){
 //애플리케이션 로직
 }
}
```
- urlPatterns(/hello)의 URL이 호출되면 서블릿 코드가 실행됩니다.
- HTTP 요청 메시지 정보를 편리하게 사용할 수 있는 HttpServletRequest
- HTTP 응답 메시지 정보를 편리하게 제공할 수 있는 HttpServletResponse
- 서블릿을 통해 개발자는 HTTP 스펙을 매우 편리하게 사용할 수 있습니다.
  - 효과적으로 사용하려면 HTTP 스펙을 어느정도 인지하고 있어야 한다고 합니다.

#### 서블릿 - HTTP 요청, 응답흐름
서블릿을 통한 HTTP 요청, 응답흐름은 다음과 같습니다.
![servletRequesetResponse](https://images.velog.io/images/urtimeislimited/post/4b89f591-6e0a-4c4d-805a-65338e700c49/image.png)
1. 웹 브라우저가 localhost:8080/parameter를 입력합니다.
2. HTTP 요청 메시지를 기반으로 request, response 객체를 생성합니다.
3. 생성한 객체를 서블릿 컨테이너로 전달하여 helloServlet을 실행합니다.
4. helloServlet을 종료해서 response객체 내부에 return값을 반환하면 response객체 내용을 기반으로 응답할 HTTP 응답 메시지를 생성하여 전달합니다.



 HTTP 요청시
- WAS는 Request, Response 객체를 새로 만들어서 서블릿 객체 호출합니다.
- 개발자는 Request 객체에서 HTTP 요청 정보를 편리하게 꺼내서 사용합니다.
- 개발자는 Response 객체에 HTTP 응답 정보를 편리하게 입력할 수 있습니다.
- WAS는 Response 객체에 담겨있는 내용으로 HTTP 응답 정보를 생성하여 웹 브라우저에게 전달합니다.

#### 서블릿 컨테이너

- 톰캣처럼 서블릿을 지원하는 WAS를 서블릿 컨테이너라고 합니다.
- 서블릿 컨테이너는 서블릿 객체를 생성, 초기화, 호출, 종료하는 생명주기를 관리합니다.
- 서블릿 객체는 __싱글톤(단일 객체 공유, 재사용)으로 관리__합니다.
- 서로 다른 고객의 요청 데이터가 올 때 마다 계속 객체를 생성하는 것은 비효율적이기 때문에
- 최초 로딩 시점에 서블릿 객체를 미리 만들어두고 재활용합니다.
- 모든 고객 요청은 동일한 서블릿 객체 인스턴스에 접근하도록 합니다.
- __공유 변수 사용에 주의해야 합니다.__
  - 여러 클라이언트가 하나의 같은 객체 인스턴스를 공유하기 때문에 싱글톤 객체는 상태를 유지(stateful)하게 설계하면 안되고, 무상태(stateless)로 설계해야 합니다.
    - 특정 클라이언트에 의존적인 필드가 있으면 안됩니다.
    - 특정 클라이언트가 값을 변경할 수 있는 필드가 있으면 안됩니다.
    - 가급적 읽기만 가능해야 합니다.
    - 필드 대신 자바에서 공유되지 않는 지역변수, 파라미터, ThreadLocal 등을 사용해야 합니다.
- 서블릿 컨테이너 종료시 함께 종료됩니다.
- JSP도 서블릿으로 변환 되어서 사용하게 됩니다.
- __동시 요청을 위한 멀티 쓰레드 처리를 지원합니다.__
  - WAS의 아주 큰 특징으로써 브라우저에서 많은 요청이 와도 개발자 대신 멀티 쓰레드 처리를 자동화해줍니다.
  
#### 동시 요청 - 멀티 쓰레드

일반적으로 웹 브라우저 요청시 WAS에 TCP/IP 커넥션이 연결되어 서블릿을 호출합니다. 

![request](https://images.velog.io/images/urtimeislimited/post/0a42826e-8d2e-43da-99a7-6f103a7ccaf6/image.png)


이 상황에서 서블릿을 호출하는 것은 쓰레드 입니다.
> 쓰레드
- 애플리케이션 코드를 하나하나 순차적으로 실행하는 것입니다.(프로세스는 프로그램 시행, 쓰레드는 그 내부에서 다양하게 나뉩니다.)
- 예) 자바 메인 메서드를 처음 실행하면 main이라는 이름의 쓰레드가 실행됩니다.
- 쓰레드가 없다면 자바 애플리케이션 실행이 불가능합니다.
- 쓰레드는 한번에 하나의 코드 라인만 수행합니다.
- 동시 처리가 필요하면 쓰레드를 추가로 생성해줘야 합니다.

예) 단일 요청 - 쓰레드 하나 사용

![singleThread](https://images.velog.io/images/urtimeislimited/post/26de2a84-5b67-434d-ae30-46da7f5a97ed/image.png)
![singleThread2](https://images.velog.io/images/urtimeislimited/post/9d418226-c8fd-440b-9219-011c99e9992b/image.png)
![singleThread3](https://images.velog.io/images/urtimeislimited/post/2e13ed33-04a0-4e80-8cc0-5c25be87da89/image.png)

만약 쓰레드는 하나인데 요청 저리가 지연되는 상황에서
다중 요청이 발생할 경우 기존 요청과 함께 장애가 발생합니다.

![manyRequest](https://images.velog.io/images/urtimeislimited/post/2ac15e1c-d6ea-42b0-8e96-fa10ada73f71/image.png)

요청1 쓰레드도 지연되면서 동시에 요청2의 쓰레드도 처리되지 않기 때문입니다.


단순하게 요청마다 쓰레드를 생성한다면 다음과 같은 장단점이 있습니다.
![requestByThread](https://images.velog.io/images/urtimeislimited/post/fae81e04-3d3e-4ffa-9e10-a91faa51174e/image.png)
- 장점
  - 동시 요청을 처리할 수 있습니다.
  - 리소스(CPU, 메모리)가 허용할 때까지 처리 가능합니다.
  - 하나의 쓰레드가 지연 되어도, 나머지 쓰레드는 정상으로 동작합니다.
- 단점
  - 쓰레드는 생성 비용이 매우 비쌉니다.
    - 따라서 고객 요청마다 쓰레드르르 생성하면 응답 속도가 늦어집니다.
  - 쓰레드는 컨텍스트 스위칭 비용이 발생합니다.
  - 쓰레드 생성에 제한이 없습니다.
    - 따라서 고객 요청이 너무 많이 오면 CPU, 메모리 임계점을 넘어서 서버가 죽을 수도 있습니다.

이러한 장단점을 해결하기 위해 쓰레드 풀(미리 생성한 쓰레드)이 존재합니다.
 - 쓰레드 풀이 없는 상황에서 요청이 오면 쓰레드를 대기하거나 거절할 수 있습니다.
 
 ![threadPool](https://images.velog.io/images/urtimeislimited/post/c84fa659-29c6-4731-b275-6ed715a8adb4/image.png)
 
 #### 쓰레드 풀 (요청 마다 쓰레드 생성의 단점 보완)
 - 특징
   - 필요한 쓰레드를 쓰레드 풀에 보관하고 관리합니다.
   - 쓰레드 풀에 생성 가능한 쓰레드의 최대치를 관리합니다. 톰캣은 최대 200개 기본 설정(변경 가능)
 - 사용
   - 쓰레드가 필요하면, 이미 생성되어 있는 쓰레드를 쓰레드 풀에서 꺼내서 사용합니다.
   - 사용 종료시 쓰레드 풀에 해당 쓰레드를 반납합니다.
   - 최대 쓰레드가 모두 사용중이어서 풀에 쓰레드가 없을 경우
     - 기다리는 요청은 거절하거나 특정 숫자만큼만 대기하도록 설정할 수 있습니다.
 - 장점
   - 쓰레드가 미리 생성되어 있으므로, 쓰레드를 생성하고 종료하는 비용(CPU)이 절약되고, 응답 시간이 빠릅니다.
   - 생성 가능한 쓰레드의 최대치가 설정되어 있으므로 너무 많은 요청이 들어와도 기존 요청은 안전하게 처리할 수 있습니다.
   
#### TIP
- WAS의 주요 튜닝 포인트는 최대 쓰레드(max thread) 수입니다.
- 이 값을 너무 낮게 설정하면
  - 동시 요청이 많을 때 서버 리소스는 여유롭지만, 클라이언트는 응답 지연 상태가 됩니다.
  ![threadPoolLow](https://images.velog.io/images/urtimeislimited/post/786a29fd-9d27-4c25-b79f-69bfeeb9e419/image.png)
- 이 값을 너무 높게 설정하면
  - 동시 요청이 많을 때 CPU, 메모리 리소스 임계점 초과로 서버가 다운됩니다.
- 장애 발생시
  - 클라우드면 일단 서버부터 늘리고, 이후에 튜닝합니다.
  - 클라우드가 아니면 열심히 튜닝합니다.

#### 쓰레드 풀의 적정 숫자
- 애플리케이션 로직의 복잡도, CPU, 메모리, IO 리소스 상황에 따라 모두 다르기 때문에 누구라도 최적의 해를 내긴 어렵다고 합니다. 다만 대략적인 감을 잡습니다. 그래서 성능테스트를 해봐야 한다고 합니다.
- 성능 테스트
  - 최대한 실제 서비스와 유사하게 성능 테스트를 시도합니다.
  - 툴: 아파치 ab, 제이미터, nGrinder
  
> 핵심
__WAS는 멀티쓰레드를 지원합니다.__
- 멀티 쓰레드에 대한 부분은 WAS가 처리합니다.
- __개발자가 멀티 쓰레드 관련 코드를 신경쓰지 않아도 됩니다.__
- 따라서 개발자는 마치 __싱글 쓰레드 프로그래밍을 하듯이 편리하게 소스 코드를 개발__할 수 있습니다.
- 멀티 쓰레드 환경이므로 싱글톤 객체(서블릿, 스프링 빈)는 주의해서 사용해야 합니다.

#### HTML, HTTP API, CSR, SSR
1. 정적 리소스
- 고정된 HTML 파일, CSS, JS, 이미지, 영상 등을 제공합니다.
- 주로 웹 브라우저에서 요청이 옵니다.
![staticResource](https://images.velog.io/images/urtimeislimited/post/8a7602c4-094f-4de2-a285-082d0e9cb9d2/image.png)
2. HTML 페이지
- 동적으로 필요한 HTML 페이지를 요청할 경우 
- WAS에서 DB에 접근 하여 조회한 정보를 기반으로 동적인 HTML을 생성하여 전달합니다. 예) JSP, Thymeleaf
![nonStaticHtml](https://images.velog.io/images/urtimeislimited/post/cb3a996c-7838-404a-a493-7e6427287c47/image.png)
3. HTTP API
- HTML이 아닌 데이터를 제공합니다.
- 주로 JSON 형식을 사용합니다.
![httpAPI](https://images.velog.io/images/urtimeislimited/post/630ca1b7-14f7-4cdd-b961-2c2a92ffece2/image.png)
- 다양한 시스템에서 호출할 수 있습니다.
  - 데이터만 주고 받습니다. 
    - UI 화면이 필요하면 클라이언트가 별도로 처리합니다.
  - 앱 : 아이폰, 안드로이드 같은 앱 클라이언트에서 UI와 별도로 데이터만 주고 받는데에 사용됩니다.
  - 웹 클라이언트 : HTML에서 Form 전송 대신 자바 스크립트를 통한 통신에 사용(AJAX)됩니다. 예) React, VueJs 같은 웹 클라이언트와 함께할 때 API 통신을 많이 사용한다고 합니다.
  - 서버 to 서버 : 주로 백엔드 시스템 통신에 사용됩니다. (HTML이 전혀 없습니다.)
  ![httpAPI2](https://images.velog.io/images/urtimeislimited/post/d507e9e2-088d-43fa-b393-3e0f14aeba3f/image.png)
  - 다양한 시스템 연동
    - 주로 JSON 형태로 데이터 통신
    - UI 클라이언트 접점
      - 앱 클라이언트(아이폰, 안드로이드, PC 앱)
      - 웹 브라우저에서 자바스크립트를 통한 HTTP API 호출
      - React, Vue.js 같은 웹 클라이언트
    - 서버 to 서버
      - 주문 서버 -> 결제 서버
      - 기업간 데이터 통신
  
  백엔드 개발자는 정적 리소스, HTML 페이지, HTTP API를 어떻게 제공할지에 대해 고민해야 합니다.
  
#### 서버사이드 렌더링, 클라이언트 사이드 렌더링
- __SSR - 서버 사이드 렌더링__
  - 서버에서 최종 HTML을 생성해서 클라이언트에 전달하는 것을 의미합니다.
    1. 서버에서 DB에 접근에 정보를 조회합니다.
    2. 1을 기반으로 동적 HTML을 생성하여 최종 HTML을 응답합니다.
  - 즉, HTML 최종 결과까지 서버에서 만들어서 웹 브라우저에 전달합니다.
  - 주로 정적인 화면에 사용됩니다.
  - 관련기술: JSP, 타임리프 -> __백엔드 개발자__
  ![ssr](https://images.velog.io/images/urtimeislimited/post/8ca6bb69-ed59-4222-9d62-6d8713d81f21/image.png)
- __CSR - 클라이언트 사이드 렌더링__
  - HTML 결과를 자바스크립트를 사용해 웹 브라우저에서 동적으로 생성해서 적용합니다.
    1. HTML을 요청합니다. (내용 없이 자바스크립트 링크만 전달합니다.)
    2. 클라이언트 로직과 HTML 렌더링 정보를 포함한 자바스크립트를 요청합니다.
    3. HTTP API 데이터를 요청하여 서버를 통해 DB에 저장된 데이터를 JSON과 같은 형식으로 응답받습니다.
    4. 웹 브라우저에서 2와 3을 조합하여 최종 HTML 결과를 렌더링합니다.
  - 주로 동적인 화면에 사용하며 웹 환경을 마치 앱처럼 필요한 부분마다 변경할 수 있습니다.
  - 예) 구글 지도, Gmail, 구글 캘린더
  - 관련기술: React, Vue.js -> __웹 프론트엔드 개발자__
![csr](https://images.velog.io/images/urtimeislimited/post/d1deb42f-98cb-4897-8582-9ce8915a0767/image.png)
- 참고
  - React, Vue.js를 CSR + SSR 동시에 지원하는 웹 프레임워크도 있습니다.
  - SSR을 사용하더라도, 자바스크립트를 사용해서 화면 일부를 동적으로 변경 가능합니다.
  
  #### 그렇다면 백엔드 개발자 입장에서 UI기술을 어디까지 알아야 하나요?
- __백엔드 - 서버 사이드 렌더링 기술__
  - JSP, 타임리프
  - 화면이 정적이고, 복잡하지 않을 때 사용합니다.
  - 백엔드 개발자는 서버 사이드 렌더링 기술 학습 __필수__입니다.
- __웹 프론트엔드 - 클라이언트 사이드 렌더링 기술__
  - React, Vue.js
  - 복잡하고 동적인 UI 사용합니다.
  - 웹 프론트엔드 개발자의 전문 분야
- __선택과 집중__
  - 백엔드 개발자의 웹 프론트엔드 기술 학습은 __옵션__입니다.
  - 백엔드 개발자는 서버, DB, 인프라 등등 수 많은 백엔드 기술을 공부해야 합니다.
  - 웹 프론트엔드도 깊이있게 잘 하려면 숙련에 오랜 시간이 필요하다고 합니다.
  
#### 자바 웹 기술 역사 - 과거 기술
- 서블릿 - 1997
  - 동적 HTML 생성이 어렵다는 단점이 있었습니다.
- JSP - 1999
  - 동적 HTML 생성은 편리하지만, 비즈니스 로직까지 너무 많은 역할 담당하여 유지 보수가 너무 어려웠다고 합니다.
- 서블릿, JSP 조합 MVC 패턴 사용
  - 모델, 뷰 컨트롤러로 역할을 나누어 개발하도록 변경되었습니다.
- MVC 프레임워크 춘추 전국 시대 - 2000년 초 ~ 2010년 초
  - MVC 패턴 자동화, 복잡한 웹 기술을 편리하게 사용할 수 있는 다양한 기능을 지원할수 있게됩니다.
  - 편리함과 다양성 덕분에 여러 프레임워크가 등장하게 됩니다. 예) 스트럿츠, 웹워크, 스프링 MVC(과거 버전)
  
#### 자바 웹 기술 역사 - 현재 사용 기술
- __애노테이션 기반의 스프링 MVC 등장__으로 프레임워크가 통합된다.
  - @Controller
  - MVC 프레임워크의 춘추 전국 시대 마무리
- __스프링 부트의 등장__
  - 스프링 부트는 서버를 내장합니다.
    - 과거에는 서버에 WAS를 직접 설치하고, 소스는 따로 War 파일을 만들어서 설치한 WAS에 배포했습니다. (톰캣 설치, 소스 빌드.... 복잡했습니다.)
  - __스프링 부트는 빌드 결과(Jar)에 WAS 서버 포함__하여 복잡함을 해결했습니다. -> __빌드 배포 단순화__
  
#### 최신 기술 - 스프링 웹 기술의 분화
- Web Servlet - Spring MVC
  - 서블릿 위에서 Spring MVC를 기반으로 동작하는 방식
    - HTTP Request, HTTPResponse사용, WAS에서 멀티 쓰레드 지원하는 장점을 가집니다.
- Web Reactive - Spring WebFlux(자바도 Node.js처럼..)
  - 완전한 비동기 넌 블러킹 처리
  - 최소 쓰레드로 최대 성능 -> 쓰레드 컨텍스트 스위칭 비용 효율화
  - 함수형 스타일로 개발 - 동시처리 코드 효율화
  - 서블릿 기술을 사용하지 않습니다. ([Netty](https://www.baeldung.com/spring-boot-reactor-netty#:~:text=Reactor%20Netty%20is%20an%20asynchronous,and%20UDP%20clients%20and%20servers.&text=Spring%20WebFlux%20is%20a%20part,programming%20support%20for%20web%20applications.))
  - 그런데
    - 웹 플럭스는 기술적 난이도가 매우 높습니다.
    - 아직은 RDB 지원이 부족합니다.
    - 일반 MVC의 쓰레드 모델도 충분히 빠릅니다.
    - 실무에서 아직 많이 사용하지 않는다고 합니다.(전체 1% 이하, 성능 최적화 + 복잡한 API 호출에 사용해야 효과가 있다고 합니다.)
    
#### 자바 뷰 템플릿 역사
백엔드에서 HTML을 동적으로 편리하게 생성하는 기능을 의미합니다.
발전해온 순서는 다음과 같습니다.
1. JSP : 속도 느림, 기능이 부족했습니다.
2. 프리마커(Freemarker), Velocity(벨로시티)
   - 속도 문제 해결, 다양한 기능을 제공하나 코드가 뒤섞여 복잡했습니다.
3. 타임리프(Thymeleaf)
   - 내추럴 템플릿: HTML의 모양을 유지하면서 뷰 템플릿 적용 가능한 템플릿을 의미합니다.
   - __스프링 MVC와 기능 통합__이 강력합니다.
   - 최선의 선택, 단 성능은 프리마커, 벨로시티가 더 빠릅니다.(이슈가 될 정도의 차이는 아니라고 합니다.)

***
### 2. 서블릿
자바 웹 기술의 가장 기초가 되는 서블릿에 대해 다음과 같은 순서로 알아보겠습니다.
1. 서블릿
2. JSP
3. MVC 패턴 
4. MVC 프레임워크 
5. 스프링 MVC 활용

#### 프로젝트 생성
[스프링 부트 스타터](https://start.spring.io/)

- 프로젝트 선택
  - Project: Gradle Project
  - Language: Java
  - Spring Boot: 2.6.3
- Project Metadata
  -Group: hello
  -Artifact: servlet
  -Name: servlet
  -Package name: hello.servlet
-Packaging: War
  - JSP를 실행하기 위해 Jar가 아닌 War를 선택합니다.
-Java: 11
Dependencies: Spring Web, Lombok

build.gradle
```
plugins {
	id 'org.springframework.boot' version '2.6.3'
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

tasks.named('test') {
	useJUnitPlatform()
}

```
참고 
- Settings | File | Settings | Build, Execution, Deployment | Build Tools | Gradle
-> Build and run using, Run test using : IntelliJ IDEA 설정시 실행 속도가 향상됩니다.
- Lombok 라이브러리 추가
  - File | Settings | Plugins | Marketplace 에서 lombok 추가
- File | Settings | Build, Execution, Deployment | Compiler | Annotation Processors
  - Enable annotaition processing 체크 해야 롬복이 동작합니다.
  
동작 확인 - main() 실행
```
Tomcat started on port(s): 8080 (http) with context path ''
```
![localhost:8080](https://images.velog.io/images/urtimeislimited/post/4e7a7094-07c0-411c-bcf6-c02949f3c3cb/image.png)

[POSTMAN 설치](https://www.postman.com/downloads/)
API 호출을 위해 설치합니다.

#### Hello 서블릿
서블릿은 톰캣 같은 웹 애플리케이션 서버를 직접 설치하고,그 위에 서블릿 코드를 클래스 파일로 빌드해서 올린 다음, 톰캣 서버를 실행하면 됩니다. 하지만 이 과정은 매우 번거롭기 때문에 톰캣 서버를 내장하고, 톰캣 서버 설치 없이 편리하게 서블릿 코드를 실행할 수 있는 스프링 부트 환경에서 서블릿을 등록하고 사용해보겠습니다.

```java
@WebServlet(name = "helloServlet", urlPatterns = "/hello")
public class HelloServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("HelloServlet.service");
        System.out.println("request = " + request);
        System.out.println("response = " + response);

        String username = request.getParameter("username");
        System.out.println("username = " + username);

        response.setContentType("text/plain");
        response.setCharacterEncoding("utf-8"); // 권장
        response.getWriter().write("hello " + username); 
    }
}
```
- @ServletComponentScan : 스프링이 해당 패키지의 하위 패키지를 자동 탐색해서 서블릿 등록을 지원해주는 어노테이션
- HttpServlet : HttpServlet을 상속받습니다.
- @WebServlet(서블릿 어노테이션) : urlPatterns로 전달받는 파라미터와 일치하는 클래스를 실행해줍니다.
  - name: 서블릿 이름
  - urlPatterns: URL 매핑
  - 중복되면 안 됩니다.
- setContentType : HTTP 헤더에 들어가는 정보
- setCharacterEncoding : HTTP 바디에 들어가는 정보
>- http://localhost:8080/hello 콘솔 실행결과
HelloServlet.service
request = org.apache.catalina.connector.RequestFacade@51d0e482
response = org.apache.catalina.connector.ResponseFacade@65964004
>- http://localhost:8080/hello?username=kim 콘솔 실행결과
username = kim
>- 브라우저 화면 
![hellokim](https://images.velog.io/images/urtimeislimited/post/f76914e8-e514-4937-b90c-d4171026230e/image.png)
![content-type](https://images.velog.io/images/urtimeislimited/post/b9f0cf59-8815-4054-9ef7-67a48788cbef/image.png)

HttpServletRequest, HttpServletResponse은 인터페이스입니다.
톰캣 등등 여러 WAS 서버의 표준 스펙을 구현한 결과를 호출하게 됩니다.
만약 HTTP 메시지를 일일히 파싱해서 읽으려면 굉장히 번거롭습니다.
서블릿은 이러한 번거로움을 편리하게 사용하도록 도와줍니다.

HTTP 요청이 오면, 매핑된 URL이 호출되면서 서블릿 컨테이너는 해당 메서드를 실행합니다.

#### HTTP 요청 메시지 확인하기

application.properties에 다음 설정을 추가하면 요청 메시지가 어떻게 왔는지 확인할 수 있습니다.

요청 메시지 정보
![요청 메시지 정보](https://images.velog.io/images/urtimeislimited/post/30b163ed-484d-40e2-ab0a-7b24267ce65a/image.png)
헤더 정보..
![헤더 정보](https://images.velog.io/images/urtimeislimited/post/18292fec-41fc-464e-8deb-ea17891e3329/image.png)
```java
logging.level.org.apache.coyote.http11=debug
```

- 참고 : 운영서버에 모든 요청 정보를 남기는 것은 성능 저하가 발생할 수 있기 때문에 고려해야합니다. 개발 단계에서만 적용할 것을 권장합니다.

#### 서블릿 컨테이너 동작 방식
1. 스프링 부트를 통해 내장 톰캣 서버를 동작시킵니다.
2. 서블릿 컨테이너를 포함시킨 톰캣 서버의 내부는 helloServlet이라는 서블릿을 생성합니다.
![내장 톰캣 서버 생성](https://images.velog.io/images/urtimeislimited/post/b68d2744-ef81-4039-bba9-317f967437f3/image.png)
3. 웹 브라우저가 HTTP 요청 메시지를 생성하여 전달합니다.
4. 서버는 전달받은 HTTP 요청 메시지를 기반으로 request, response 객체를 생성해서 싱글톤 형태의 helloServlet을 호출해줍니다.
5. 파라미터가 일치하는 해당 메서드에 HTTP 메타데이터가 포함되어있는 request, response 객체를 전달하면 내부 작업을 처리합니다.
6. 작업이 종료되면 WAS 서버가 response 객체 정보를 기반으로 HTTP 응답 메시지를 생성하고 반환합니다.
7. 응답 받은 결과를 웹 브라우저에서 확인할 수 있습니다.
![HTTP 요청, HTTP 응답 메시지](https://images.velog.io/images/urtimeislimited/post/7faea610-081c-4fbf-b281-9cba5d5018ce/image.png)


- 참고 : HTTP 응답에서 서 Content-Length같은 부가 정보들은 WAS가 자동으로 생성해줍니다.

welcomPage (localhost:8080 호출시 기본 페이지)
main/webapp/index.html
```html
<!DOCTYPE html>
<html>
<head>
 <meta charset="UTF-8">
 <title>Title</title>
</head>
<body>
<ul>
 <li><a href="basic.html">서블릿 basic</a></li>
</ul>
</body>
</html>
```
![index.html](https://images.velog.io/images/urtimeislimited/post/f67e6e0e-209d-4606-b506-0717be4bfcf1/image.png)
main/webapp/basic.html
```html
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
<ul>
    <li>hello 서블릿
        <ul>
            <li><a href="/hello?username=servlet">hello 서블릿 호출</a></li>
        </ul>
    </li>
    <li>HttpServletRequest
        <ul>
            <li><a href="/request-header">기본 사용법, Header 조회</a></li>
            <li>HTTP 요청 메시지 바디 조회
                <ul>
                    <li><a href="/request-param?username=hello&age=20">GET -
                        쿼리 파라미터</a></li>
                    <li><a href="/basic/hello-form.html">POST - HTML Form</a></li>
                    <li>HTTP API - MessageBody -> Postman 테스트</li>
                </ul>
            </li>
        </ul>
    </li>
    <li>HttpServletResponse
        <ul>
            <li><a href="/response-header">기본 사용법, Header 조회</a></li>
            <li>HTTP 응답 메시지 바디 조회
                <ul>
                    <li><a href="/response-html">HTML 응답</a></li>
                    <li><a href="/response-json">HTTP API JSON 응답</a></li>
                </ul>
            </li>
        </ul>
    </li>
</ul>
</body>
</html>
```
![basic.html](https://images.velog.io/images/urtimeislimited/post/fab9af34-a50a-48dd-ad28-f666306dc82e/image.png)

#### HttpServletRequest 개요

__HttpServletRequest 역할__
HTTP 요청 메시지를 개발자가 직접 파싱해서 사용해도 되지만, 매우 불편할 것입니다. 서블릿은 개발자가 HTTP 요청 메시지를 편리하게 사용할 수 있도록 개발자 대신에 HTTP 요청 메시지를 파싱하고 그 결과를 HttpServletRequest 객체에 담아서 제공합니다.

HttpServletRequest를 사용하면 다음과 같은 HTTP 요청 메시지를 편리하게 조회할 수 있습니다.
![HTTP 요청 메시지](https://images.velog.io/images/urtimeislimited/post/562c88be-9e0c-42e8-b27b-0b4044d0b9ca/image.png)

- START LINE
  - HTTP 메소드
  - URL
  - 쿼리 스트링
  - 스키마, 프로토콜
- 헤더
  - 헤더 조회
- 바디
  - form 파라미터 형식 조회
  - message body 데이터 직접 조회
  
HttpServletRequest 객체는 추가로 여러가지 부가기능도 함께 제공합니다.

임시 저장소 기능
- 해당 HTTP 요청 생명주기 동안(시작부터 끝날 때 까지) 유지되는 임시 저장소 기능
  - 저장: request.setAttribute(name, value)
  - 조회: request.getAttribute(name)
세션 관리 기능
- request.getSession(create: true)
> 중요
> HttpServletRequest, HttpServletResponse를 사용할 때 가장 중요한 점은 이 __객체들이 HTTP 요청
메시지, HTTP 응답 메시지를 편리하게 사용하도록 도와주는 객체라는 점__입니다. 따라서 이 기능에 대해서
깊이있는 이해를 하려면 __HTTP 스펙이 제공하는 요청, 응답 메시지 자체를 이해__해야 합니다.

#### HttpServletRequest - 기본 사용법

```java
@WebServlet(name = "requestHeaderServlet", urlPatterns = "/request-header")
public class RequestHeaderServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        printStartLine(request);
    }

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
        System.out.println("request.isSecure() = " + request.isSecure()); //https사용 유무
        System.out.println("--- REQUEST-LINE - end ---");
        System.out.println();
    }
}
```
- 참고 : Ctrl+Alt+M(Windows/Linux) -> ExtractMethod 메서드 추출

> 콘솔 실행 결과
--- REQUEST-LINE - start ---
request.getMethod() = GET
request.getProtocal() = HTTP/1.1
request.getScheme() = http
request.getRequestURL() = http://localhost:8080/request-header
request.getRequestURI() = /request-header
request.getQueryString() = null
request.isSecure() = false // https or http 여부
--- REQUEST-LINE - end ---

헤더 정보 불러오는 과거 방법
``` java
//Header 모든 정보
    private void printHeaders(HttpServletRequest request) {
        System.out.println("--- Headers - start ---");

         Enumeration<String> headerNames = request.getHeaderNames(); // 방법1
         while (headerNames.hasMoreElements()) {
         String headerName = headerNames.nextElement();
         System.out.println(headerName + ": " + request.getHeader(headerName));
         }

        System.out.println("--- Headers - end ---");
        System.out.println();
    }
```
getHeaderNames를 Enumeration으로 while문으로
요소가 있으면 다음 요소 반환하는 반복문의 형식입니다.
```java
printHeaders(request);
```
> 콘솔 실행 결과
--- Headers - start ---
host: localhost:8080
connection: keep-alive
cache-control: max-age=0
sec-ch-ua: " Not A;Brand";v="99", "Chromium";v="98", "Google Chrome";v="98"
sec-ch-ua-mobile: ?0
sec-ch-ua-platform: "Windows"
upgrade-insecure-requests: 1
user-agent: Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/98.0.4758.102 Safari/537.36
accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9
sec-fetch-site: none
sec-fetch-mode: navigate
sec-fetch-user: ?1
sec-fetch-dest: document
accept-encoding: gzip, deflate, br
accept-language: ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7,ja;q=0.6
--- Headers - end ---

간결한 문법으로 리팩토링해도 결과는 같습니다.
```java
request.getHeaderNames().asIterator()
                .forEachRemaining(headerName -> System.out.println(headerName + ": " + request.getHeader(headerName)));
                //request.getHeader("헤더명"); // 특정 헤더만 조회
                        System.out.println("--- Headers - end ---");
        System.out.println();
```

좀 더 편리하게 조회해보겠습니다.
```java
//Header 편리한 조회
    private void printHeaderUtils(HttpServletRequest request) {
        System.out.println("--- Header 편의 조회 start ---");
        System.out.println("[Host 편의 조회]");
        System.out.println("request.getServerName() = " +
                request.getServerName()); //Host 헤더
        System.out.println("request.getServerPort() = " +
                request.getServerPort()); //Host 헤더
        System.out.println();
        System.out.println("[Accept-Language 편의 조회]");
        request.getLocales().asIterator()
                .forEachRemaining(locale -> System.out.println("locale = " +
                        locale));
        System.out.println("request.getLocale() = " + request.getLocale());
        System.out.println();
        System.out.println("[cookie 편의 조회]");
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                System.out.println(cookie.getName() + ": " + cookie.getValue());
            }
        }
        System.out.println();
        System.out.println("[Content 편의 조회]");
        System.out.println("request.getContentType() = " +
                request.getContentType());
        System.out.println("request.getContentLength() = " +
                request.getContentLength());
        System.out.println("request.getCharacterEncoding() = " +
                request.getCharacterEncoding());
        System.out.println("--- Header 편의 조회 end ---");
        System.out.println();
    }
```
> 콘솔 실행 결과
--- Header 편의 조회 start ---
[Host 편의 조회]
request.getServerName() = localhost
request.getServerPort() = 8080
>[Accept-Language 편의 조회]
locale = ko_KR
locale = ko
locale = en_US
locale = en
locale = ja
request.getLocale() = ko_KR
>[cookie 편의 조회]
>[Content 편의 조회]
request.getContentType() = null
request.getContentLength() = -1
request.getCharacterEncoding() = UTF-8
--- Header 편의 조회 end ---

- getServerName : 서버 정보 조회
- getServerPort : 포트 정보 조회
예) Host: localhost:8080
- getLocale : 가장 우선순위로 지원하는 언어를 호출합니다.
예) Accept-Language: ko-KR,ko;q=0.9,en-US;q=0.8,en;q=0.7,ja;q=0.6
- getCookies : 쿠키 정보를 조회해서 헤더에 포함합니다.
- getContentType, getContentLength, getCharacterEncoding
  - get이기 때문에 null 출력, POSTMAN으로 간단한 데이터(hello!)를 POST해보면
  >
request.getContentType() = application/json
request.getContentLength() = 6
request.getCharacterEncoding() = UTF-8

기타 정보 조회
```java
private void printEtc(HttpServletRequest request) {
        System.out.println("--- 기타 조회 start ---");
        System.out.println("[Remote 정보]");
        System.out.println("request.getRemoteHost() = " +
                request.getRemoteHost()); //
        System.out.println("request.getRemoteAddr() = " +
                request.getRemoteAddr()); //
        System.out.println("request.getRemotePort() = " +
                request.getRemotePort()); //
        System.out.println();
        System.out.println("[Local 정보]");
        System.out.println("request.getLocalName() = " +
                request.getLocalName()); //
        System.out.println("request.getLocalAddr() = " +
                request.getLocalAddr()); //
        System.out.println("request.getLocalPort() = " +
                request.getLocalPort()); //
        System.out.println("--- 기타 조회 end ---");
        System.out.println();
    }
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        printEtc(request);
    }
```
> 콘솔 실행 결과
--- 기타 조회 start ---
[Remote 정보] // 요청에 대한 정보
request.getRemoteHost() = 0:0:0:0:0:0:0:1
request.getRemoteAddr() = 0:0:0:0:0:0:0:1
request.getRemotePort() = 9322
[Local 정보] // 현재 나의 서버 정보
request.getLocalName() = 0:0:0:0:0:0:0:1
request.getLocalAddr() = 0:0:0:0:0:0:0:1
request.getLocalPort() = 8080
--- 기타 조회 end ---

- 참고
  - 로컬에서 테스트하면 IPv6 정보가 나오는데, IPv4 정보를 보고 싶으면 다음 옵션을 VM options에 넣어주면 됩니다.
``` -Djava.net.preferIPv4Stack=true ```

지금까지 HttpServletRequest를 통해서 HTTP 메시지의 start-line, header 정보 조회 방법을
이해했습니다. 이제 본격적으로 HTTP 요청 데이터를 어떻게 조회하는지 알아보겠습니다.

#### HTTP 요청 데이터
HTTP 요청 메시지를 통해 클라이언트에서 서버로 데이터를 전달하는 방법은 주로 다음 3가지 방법을 사용합니다.

- __GET - 쿼리 파라미터__
  - /url**?username=hello&age=20**
  - 메시지 바디 없이, URL의 쿼리 파라미터에 데이터를 포함해서 전달합니다.
  - 예) 검색, 필터, 페이징등에서 많이 사용하는 방식
- __POST - HTML Form__
  - content-type: application/x-www-form-urlencoded
  - 메시지 바디에 쿼리 파리미터 형식으로 전달합니다.
    - username=hello&age=20같은 형식과 호환됩니다.
  - 예) 회원 가입, 상품 주문, HTML Form 사용
  ![POST - HTML Form](https://images.velog.io/images/urtimeislimited/post/8b0ee59e-64e2-48e1-b398-c3c51aa16d27/image.png)
- __HTTP message body__에 데이터를 직접 담아서 요청
  -HTTP API에서 주로 사용, JSON, XML, TEXT
- 데이터 형식은 주로 JSON 사용
  - POST, PUT, PATCH
  
#### HTTP 요청 데이터 - GET 쿼리 파라미터
다음 데이터를 클라이언트에서 서버로 전송해보겠습니다.
메시지 바디 없이, URL의 __쿼리 파라미터__를 사용해서 데이터를 전달하겠습니다. 
예) 검색, 필터, 페이징 등에서 많이 사용하는 방식입니다.

- 전달 데이터
  - username=hello
  - age=20
- URL
  - 쿼리 파라미터는 URL에 다음과 같이 ? 를 시작으로 보낼 수 있으며 추가 파라미터는 & 로 구분하면 됩니다.
  - http://localhost:8080/request-param?username=hello&age=20
  
서버에서는 HttpServletRequest 가 제공하는 다음 메서드를 통해 쿼리 파라미터를 편리하게 조회할 수있습니다.
``` java
String username = request.getParameter("username"); //단일 파라미터 조회
Enumeration<String> parameterNames = request.getParameterNames(); //파라미터 이름들 모두 조회
Map<String, String[]> parameterMap = request.getParameterMap(); //파라미터를 Map 으로 조회
String[] usernames = request.getParameterValues("username"); //복수 파라미터 조회
```

``` java
/**
 * 1. 파라미터 전송 기능
 * http://localhost:8080/request-param?username=hello&age=20
 * <p>
 * 2. 동일한 파라미터 전송 가능
 * http://localhost:8080/request-param?username=hello&username=kim&age=20
 */
@WebServlet(name = "requesetParamServlet", urlPatterns = "/request-param")
public class RequesetParamServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("RequesetParamServlet.service");
        System.out.println("[전체 파라미터] - start");
        request.getParameterNames().asIterator()
                .forEachRemaining(paramName -> System.out.println(paramName+ " =" + request.getParameter(paramName)));
        System.out.println("[전체 파라미터] - end");
        System.out.println();

        System.out.println("[단일 파라미터 조회]");
        String username = request.getParameter("username");
        System.out.println("username = " + username);
        String age = request.getParameter("age");
        System.out.println("age = " + age);

        System.out.println("[이름이 같은 복수 파라미터 조회]");
        String[] usernames = request.getParameterValues("username");
        for (String name : usernames) {
            System.out.println("username = " + name);
        }
        response.getWriter().write("ok");
    }
}
```

**복수 파라미터에서 단일 파라미터 조회**
username=hello&username=kim 과 같이 파라미터 이름은 하나인데 값이 중복이라면?

request.getParameter() 는 하나의 파라미터 이름에 대해서 단 하나의 값만 있을 때 사용해야 하기 때문에 지금처럼 중복일 때는 request.getParameterValues() 를 사용해야 합니다.

참고로 이렇게 중복일 때 request.getParameter() 를 사용하면 request.getParameterValues() 의 첫 번째 값을 반환합니다.
실무에서는 **request.getParameter()**를 가장 많이 사용한다고 합니다.

#### HTTP 요청 데이터 - POST HTML Form

HTML의 Form을 사용해서 클라이언트에서 서버로 데이터를 전송해보겠습니다. 주로 회원 가입, 상품 주문 등에서 사용하는 방식입니다.
- 특징
  - content-type: application/x-www-form-urlencoded
  - 메시지 바디에 쿼리 파리미터 형식으로 데이터를 전달합니다.     
    - username=hello&age=20
    
src/main/webapp/basic/hello-form.html 생성
```html
<!DOCTYPE html>
<html>
<head>
 <meta charset="UTF-8">
 <title>Title</title>
</head>
<body>
<form action="/request-param" method="post">
 username: <input type="text" name="username" />
 age: <input type="text" name="age" />
 <button type="submit">전송</button>
</form>
</body>
</html>
```

http://localhost:8080/basic/hello-form.html
![hello-form.html](https://images.velog.io/images/urtimeislimited/post/93598eab-17ca-4bca-ad64-78ac1283a126/image.png)
POST의 HTML Form을 전송하면 웹 브라우저는 다음 형식으로 HTTP 메시지를 만듭니다. (웹 브라우저 개발자 모드 확인)
- 요청 URL: http://localhost:8080/request-param
- content-type: application/x-www-form-urlencoded
- message body: username=hello&age=20

application/x-www-form-urlencoded 형식은 앞서 **GET에서 살펴본 쿼리 파라미터 형식과 같습니다.** 
따라서 **쿼리 파라미터 조회 메서드를 그대로 사용**하면 됩니다.
클라이언트(웹 브라우저) 입장에서는 두 방식에 차이가 있지만, 서버 입장에서는 둘의 형식이 동일하므로, **request.getParameter() 로 편리하게 구분없이 조회**할 수 있습니다.

즉, **request.getParameter() 는 GET URL 쿼리 파라미터 형식도 지원하고, POST HTML Form 형식도 둘 다 지원합니다.**

- 참고 : content-type은 HTTP 메시지 바디의 데이터 형식을 지정합니다. GET URL 쿼리 파라미터 형식으로 클라이언트에서 서버로 데이터를 전달할 때는 HTTP 메시지 바디를 사용하지 않기 때문에 content-type이 없습니다.
 POST HTML Form 형식으로 데이터를 전달하면 HTTP 메시지 바디에 해당 데이터를 포함해서 보내기 때문에 바디에 포함된 데이터가 어떤 형식인지 content-type을 꼭 지정해야 합니다. 이렇게 폼으로 데이터를
전송하는 형식을 application/x-www-form-urlencoded 라 합니다.

이런 간단한 테스트에 HTML form을 만들기는 귀찮다면 Postman을 사용하면 됩니다.

- Postman 테스트 주의사항
  - POST 전송시
    - Body x-www-form-urlencoded 선택
    - Headers에서 content-type: application/x-www-form-urlencoded 로 지정된 부분 꼭 확인
    
![Postman](https://images.velog.io/images/urtimeislimited/post/faa09bf0-0c5c-495c-bc87-f1031c72b249/image.png)

#### HTTP 요청 데이터 - API 메시지 바디 - 단순 텍스트
- HTTP message body에 데이터를 직접 담아서 요청하는 방식입니다.
  - HTTP API에서 주로 JSON, XML, TEXT 사용합니다.
  - 데이터 형식은 주로 JSON 사용합니다.
  - POST, PUT, PATCH
  
HTTP 메시지 바디의 데이터를 InputStream을 사용해서 직접 읽을 수 있습니다. 먼저 가장 단순한 텍스트 메시지를 HTTP 메시지 바디에 담아서 전송하고, 읽어보겠습니다.

```java
@WebServlet(name = "requestBodyStringServlet", urlPatterns = "/request-body-string")
public class RequestBodyStringServlet extends HttpServlet{
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletInputStream inputStream = request.getInputStream(); // 메시지 바디의 내용을 바이트코드로 바로 얻을 수 있음
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);// StreamUtils: 바이트코드를 문자형으로 변환
        System.out.println("messageBody = " + messageBody);
        response.getWriter().write("ok");

    }
}
```
- inputStream은 byte 코드를 반환합니다. byte 코드를 우리가 읽을 수 있는 문자(String)로 보려면 문자표 (Charset)를 지정해주어야 합니다. 일반적인 UTF_8 Charset을 지정해주었습니다.
![requesetString](https://images.velog.io/images/urtimeislimited/post/4d509f3f-4a70-4cc4-9395-2835f0d09b74/image.png)
> 콘솔 실행결과
```messageBody = hello!```

#### HTTP 요청 데이터 - API 메시지 바디 - JSON
 HTTP API에서 주로 사용하는 JSON 형식으로 데이터를 전달해보겠습니다.

JSON 형식 전송
- POST http://localhost:8080/request-body-json
- content-type: application/json
- message body: {"username": "hello", "age": 20}
- 결과: messageBody = {"username": "hello", "age": 20}

JSON 데이터는 보통 그대로 사용하지 않고 객체로 변환하여 사용합니다. JSON 형식으로 파싱할 수 있도록 객체를 먼저 생성합니다.

hello.servlet.basic;
```java
@Getter @Setter
public class HelloData {
    private String username;
    private int age;
}
```
- 참고: lombok이 제공하는 @Getter , @Setter 덕분에 다음 코드가 자동으로 추가됩니다.(눈에 보이지는 않습니다.)
```java
 //==== lombok 생성 코드 ====//
 public String getUsername() {
 return username;
 }
 public void setUsername(String username) {
 this.username = username;
 }
 public int getAge() {
 return age;
 }
 public void setAge(int age) {
 this.age = age;
 }
```
```java
@WebServlet(name = "requestBodyJsonServlet", urlPatterns = "/request-body-json")
public class RequestBodyJsonServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        System.out.println("messageBody = " + messageBody);
    }
}
```
POSTMAN : Body - raw - JSON
```{"username": "hello!", "age": 20}```
![headerConvertoJson](https://images.velog.io/images/urtimeislimited/post/4efc82fb-f626-43ce-9a07-8465d20f5776/image.png)

> 콘솔 실행결과
```messageBody = {username: "hello!", age: 20}```

스프링이 가지고있는 jackson 라이브러리를 통해 JSON 데이터를 객체로 변환할 수도 있습니다.
![jackson](https://images.velog.io/images/urtimeislimited/post/d1856892-273d-4e20-aede-78a81600b812/image.png)
```java
	    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        System.out.println("messageBody = " + messageBody);
        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);

        System.out.println("helloData.getUsername() = " + helloData.getUsername());
        System.out.println("helloData.getAge() = " + helloData.getAge());

        response.getWriter().write("ok");
    }
```

> 콘솔 실행 결과
```
helloData.getUsername() = hello!
helloData.getAge() = 20
```

- JSON 결과를 파싱해서 사용할 수 있는 자바 객체로 변환하려면 Jackson, Gson 같은 JSON 변환 라이브러리를 추가해서 사용해야 합니다. 스프링 부트로 Spring MVC를 선택하면 기본으로 Jackson 
라이브러리( ObjectMapper )를 함께 제공합니다.

- HTML form 데이터도 HTTP 스펙의 요청 메시지 바디를 통해 전송되므로 직접 읽을 수 있습니다. 하지만 편리한 파리미터 조회 기능 (request.getParameter(...) )을 이미 제공하기 때문에 파라미터 조회 기능을 사용하면 된다고 합니다.

#### HttpServletResponse - 기본 사용법
 HttpServletResponse의 역할은 다음과 같습니다.
- HTTP 응답 메시지 생성
  - HTTP 응답코드 지정
  - 헤더 생성
  - 바디 생성
- 편의 기능 제공
  - Content-Type, 쿠키, Redirect

``` java
@WebServlet(name = "responseHeaderServlet", urlPatterns = "/response-header")
public class ResponseHeaderServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //[status-line]
        response.setStatus(HttpServletResponse.SC_OK); //200 매직 넘버가 아니기 때문에 권장

        //[response-headers]
        response.setHeader("Content-Type","text/plain;char-set=UTF-8");
        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // 캐시 완전 부여
        response.setHeader("Pragma","no-cache"); // 캐시 무효화
        response.setHeader("my-header","hello"); // 임의의 헤더
        //[message Body]
        PrintWriter writer = response.getWriter();
        writer.println("ok");
    }
}
```

![responseHeader](https://images.velog.io/images/urtimeislimited/post/811e948a-e3dc-4689-9e22-896e0aa935a1/image.png)
간결한 코드를 위한 편의 메서드도 사용할 수 있습니다.
```java
		//[Header 편의 메서드]
        content(response);
        
    private void content(HttpServletResponse response) {
        //Content-Type: text/plain;charset=utf-8
        //Content-Length: 2
        //response.setHeader("Content-Type", "text/plain;charset=utf-8");
        response.setContentType("text/plain");
        response.setCharacterEncoding("utf-8");
        //response.setContentLength(2); //(생략시 자동 생성)
    }
```
쿠키 편의 메서드
```java
	cookie(response);
    
    private void cookie(HttpServletResponse response){
        //Set-Cookie: myCookie=good; Max-Age=600;
        //response.setHeader("Set-Cookie", "myCookie=good; Max-Age=600");
        Cookie cookie = new Cookie("myCookie", "good"); // 쿠키 객체 생성 생략가능
        cookie.setMaxAge(600); //600초
        response.addCookie(cookie);
    }
```
![cookie()](https://images.velog.io/images/urtimeislimited/post/1d729374-a1b0-4da6-bfb7-54d2d9096dbe/image.png)

리다이렉트 메서드
```java

redirect(response);

private void redirect(HttpServletResponse response) throws IOException{
        //Status Code 302
        //Location: /basic/hello-form.html

        //response.setStatus(HttpServletResponse.SC_FOUND); //302
        //response.setHeader("Location", "/basic/hello-form.html");

        response.sendRedirect("/basic/hello-form.html");
    }
```

![redirection](https://images.velog.io/images/urtimeislimited/post/63fdedcf-fde0-438e-a829-df8679767d31/image.png)
![302](https://images.velog.io/images/urtimeislimited/post/6bc29aee-46fd-4c95-b9d4-45c399c44f1b/image.png)
- [304](https://developer.mozilla.org/en-US/docs/Web/HTTP/Status/304) : HTTP 304 Not Modified 클라이언트 리디렉션 응답 코드는 요청된 리소스를 재전송할 필요가 없음을 나타냅니다. 캐시된 리소스에 대한 암시적 리디렉션입니다.

#### HTTP 응답 데이터 - 단순 텍스트, HTML

HTTP 응답 메시지는 주로 다음 내용을 담아서 전달합니다.
- 단순 텍스트 응답
  - 앞에서 살펴본 ( writer.println("ok"); )
- HTML 응답
- HTTP API - MessageBody JSON 응답

```java
@WebServlet(name = "responseHtmlServlet", urlPatterns = "/response-html")
public class ResponseHtmlServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse
            response)
            throws ServletException, IOException {
        //Content-Type: text/html;charset=utf-8
        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");

        PrintWriter writer = response.getWriter();
        writer.println("<html>");
        writer.println("<body>");
        writer.println(" <div>안녕?</div>");
        writer.println("</body>");
        writer.println("</html>");
    }
}
```
![responseHtml](https://images.velog.io/images/urtimeislimited/post/c104f391-7fd0-4743-bb0d-1cc386119fcd/image.png)
![Content-Type](https://images.velog.io/images/urtimeislimited/post/b37e0422-717d-4dee-8fed-97047a46e672/image.png)
>소스보기
```html
<html>
<body>
 <div>안녕?</div>
</body>
</html>
```
- HTTP 응답으로 HTML을 반환할 때는 content-type을 text/html 로 지정해야 합니다.
- 페이지 소스보기를 사용하면 결과 HTML을 확인할 수 있습니다.

#### HTTP 응답 데이터 - API JSON
메시지 바디에 포함할 HTTP 응답 데이터를 만들 때 사용하는 JSON 형식을 알아보겠습니다.

```java
@WebServlet(name = "responseJsonServlet", urlPatterns = "/response-json")
public class ResponseJsonServlet extends HttpServlet {
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Content-Type: application/json
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");

        HelloData helloData = new HelloData();
        helloData.setUsername("kim");
        helloData.setAge(20);

        //{"username":"kim","age":20}
        String result = objectMapper.writeValueAsString(helloData);
        response.getWriter().write(result);

    }
}
```
> http://localhost:8080/response-json
![responseJson](https://images.velog.io/images/urtimeislimited/post/4f59fe98-b858-4419-86d1-3d3c235ae02c/image.png)
![responseJson2](https://images.velog.io/images/urtimeislimited/post/ebf4c1b5-1488-4db9-a495-c786e30acecb/image.png)

- HTTP 응답으로 JSON을 반환할 때는 content-type을application/json 로 지정해야 합니다.
- Jackson 라이브러리가 제공하는 objectMapper.writeValueAsString() 를 사용하면 객체를 JSON 문자로 변경할 수 있습니다.
- 참고 : application/json 은 스펙상 utf-8 형식을 사용하도록 정의되어 있습니다. 그래서 스펙에서 charset=utf-8 과 같은 추가 파라미터를 지원하지 않습니다. 따라서 application/json 이라고만 사용해야지 application/json;charset=utf-8 이라고 전달하는 것은 의미 없는 파라미터를 추가한 것이 됩니다.
- response.getWriter()를 사용하면 추가 파라미터를 자동으로 추가해버립니다. 이때는 response.getOutputStream()으로 변환해서 출력하면 그런 문제가 없습니다.

> 서블릿 정리
- HttpServlet 객체는 HTTP 스펙대로 요청 메시지 파싱을 최소화하여 편리하게 사용할 수 있도록 조회하는 방법입니다.
- HttpServletRequest가 제공하는 기본 기능
  - printStartLine(request); //스타터 라인 정보
  - printHeaders(request); //Header 모든 정보
  - printHeaderUtils(request); //Header 편의 조회 
  - printEtc(request); //기타 정보
- HTTP 요청 메시지를 통해 클라이언트에서 서버로 데이터를 전달하는 방법
  - GET : 쿼리 파라미터
    - 메시지 바디 없이, URL의 쿼리 파라미터에 데이터를 포함해서 전달 (검색, 필터, 페이징 등에서 많이 사용)
  - POST : HTML Form
    - 웹 브라우저가 content-type: application/x-www-form-urlencoded 형식의 HTTP 메시지 생성
    - 메시지 바디에 쿼리 파라미터 형식으로 전달(회원 가입, 상품 주문, HTML Form 사용)
    - getParameter() 는 GET URL 쿼리 파라미터 형식도 지원하고, POST HTML Form 형식 둘 다 지원
    - HTTP 스펙상 HTML Form 사용시 POST만 허용
  - HTTP message body
    - HTTP API에서 주로 사용, JSON, XML, TEXT
    - 데이터 형식은 주로 JSON 사용
    - POST, PUT, PATCH
- 응답
  - 데이터는 메시지, HTML, API JSON 형태로 응답 가능
  - 헤더는 setStatus()로 상태 지정, setHeader(), 편의 메서드 등 사용 가능
  
***

### 3. 서블릿, JSP, MVC 패턴
서블릿 - 불편함 개선 JSP - 불편함 개선 MVC 패턴 적용
순서대로 간단한 회원 관리 웹 애플리케이션을 만들어 보겠습니다.

#### 요구사항
- 회원 정보
  - 이름: username
  - 나이: age
- 기능 요구사항
  - 회원 저장
  - 회원 목록 조회

도메인 모델을 설계해보겠습니다.
hello.servlet.domain.member;
회원 도메인
```java
@Getter @Setter
public class Member {

    private Long id; // 회원 저장소 FK
    private String username;
    private int age;

    public Member(){}
    public Member(Long id, String username, int age) {
        this.id = id;
        this.username = username;
        this.age = age;
    }
}
```
- id 는 Member 를 회원 저장소에 저장하면 회원 저장소가 할당합니다.

회원 저장소
```java
public class MemberRepository {

    /**
     * 동시성 문제가 고려되어 있지 않음, 실무에서는 ConcurrentHashMap, AtomicLong 사용 고려
     */
    // {key: id, value: member}
    private static Map<Long, Member> store = new HashMap<>();
    private static long sequence = 0L; // id 하나씩 증가하는 객체

    //싱글톤 패턴 위해 static 사용
    private static final MemberRepository instance = new MemberRepository();

    public static MemberRepository getInstance(){
        return instance;
    }

    private MemberRepository() {}

    public Member save(Member member){
        member.setId(++sequence);
        store.put(member.getId(), member);
        return member;
    }
    public Member findById(Long id){
        return store.get(id);
    }
    public List<Member> findAll(){
        return new ArrayList<>(store.values()); //store의 모든 값 새로운 ArrayList에 저장
    }

    public void clearStore(){
        store.clear(); // 테스트에 사용
    }
}
```
- 회원 저장소는 싱글톤 패턴을 적용했습니다. 스프링을 사용하면 스프링 빈으로 등록하면 되지만, 지금은 최대한 스프링 없이 순수 서블릿 만으로 구현하는 것이 목적입니다.
싱글톤 패턴은 객체를 단 하나만 생생해서 공유해야 하므로 생성자를 private 접근자로 막아둡니다.

회원 저장소 테스트
```java
class MemberRepositoryTest {

    MemberRepository memberRepository = MemberRepository.getInstance(); // 싱글톤은 new사용 필요 x

    @AfterEach // 테스트 종료시 초기화, 데이터 저장 순서 보장을 위해 사용
    void afterEach(){
        memberRepository.clearStore();
    }

    @Test
    void save() {
        //given
        Member member = new Member("hello", 20);
        //when
        Member savedMember = memberRepository.save(member);
        //then
        Member findMember = memberRepository.findById(savedMember.getId());
        assertThat(findMember).isEqualTo(savedMember);
    }

    @Test
    void findAll(){
        //given
        Member member1 = new Member("member1", 20);
        Member member2 = new Member("member2", 30);

        memberRepository.save(member1);
        memberRepository.save(member2);
        //when
        List<Member> result = memberRepository.findAll();

        //then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result).contains(member1, member2);
    }
}
```
> Test Passed

- 회원을 저장하고, 목록을 조회하는 테스트를 작성했습니다.
각 테스트가 끝날 때, 다음 테스트에 영향을 주지 않도록 각 테스트의 저장소를 clearStore() 를 호출해서 초기화했습니다.

#### 서블릿으로 회원 관리 웹 애플리케이션 만들기
본격적으로 서블릿 회원 관리 웹 애플리케이션을 만들기 위해
가장 먼저 서블릿으로 회원 등록 HTML 폼을 제공해보겠습니다.

회원 등록 폼
```java
@WebServlet(name = "memberFormServlet", urlPatterns = "/servlet/members/new-form")
public class MemberFormServlet extends HttpServlet {

    private MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        // HTTP 응답 Content 바디
        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");


        PrintWriter w = response.getWriter();
        // 보통 일이 아니네..
        w.write("<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                " <meta charset=\"UTF-8\">\n" +
                " <title>Title</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<form action=\"/servlet/members/save\" method=\"post\">\n" +
                " username: <input type=\"text\" name=\"username\" />\n" +
                " age: <input type=\"text\" name=\"age\" />\n" +
                " <button type=\"submit\">전송</button>\n" +
                "</form>\n" +
                "</body>\n" +
                "</html>\n");
    }
}
```
> http://localhost:8080/servlet/members/new-form
![/servlet/members/new-form](https://images.velog.io/images/urtimeislimited/post/ecef2521-346d-4a21-a256-ff19d43abd37/image.png)
>페이지 소스
![/servlet/members/new-form2](https://images.velog.io/images/urtimeislimited/post/b73e20af-4ea0-4330-9e11-dc39e80211fb/image.png)

회원 저장
```java
@WebServlet(name = "memberSaveServlet", urlPatterns = "/servlet/members/save")
public class MemberSaveServlet extends HttpServlet {
    private MemberRepository memberRepository = MemberRepository.getInstance();
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse
            response)
            throws ServletException, IOException {
        System.out.println("MemberSaveServlet.service");

        //GET 쿼리 파라미터, HTML Form 둘다 getParameter() 사용 가능
        // Form submit 결과 getParameter()로 조회
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));

        //저장
        Member member = new Member(username, age);
        memberRepository.save(member);

        //결과 응답
        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");
        PrintWriter w = response.getWriter();
        w.write("<html>\n" +
                "<head>\n" +
                " <meta charset=\"UTF-8\">\n" +
                "</head>\n" +
                "<body>\n" +
                "성공\n" +
                "<ul>\n" +
                " <li>id="+member.getId()+"</li>\n" +
                " <li>username="+member.getUsername()+"</li>\n" +
                " <li>age="+member.getAge()+"</li>\n" +
                "</ul>\n" +
                "<a href=\"/index.html\">메인</a>\n" +
                "</body>\n" +
                "</html>");
    }
}
```
> http://localhost:8080/servlet/members/new-form 에서 submin 결과
![save](https://images.velog.io/images/urtimeislimited/post/7124f534-f648-49c0-8281-497bbd5341fe/image.png)

회원 목록

```java
@WebServlet(name = "memberListServlet", urlPatterns = "/servlet/members")
public class MemberListServlet extends HttpServlet {
    private MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Member> members = memberRepository.findAll();

        response.setContentType("text/html");
        response.setCharacterEncoding("utf-8");

        PrintWriter w = response.getWriter();

        w.write("<html>");
        w.write("<head>");
        w.write(" <meta charset=\"UTF-8\">");
        w.write(" <title>Title</title>");
        w.write("</head>");
        w.write("<body>");
        w.write("<a href=\"/index.html\">메인</a>");
        w.write("<table>");
        w.write(" <thead>");
        w.write(" <th>id</th>");
        w.write(" <th>username</th>");
        w.write(" <th>age</th>");
        w.write(" </thead>");
        w.write(" <tbody>");
/*
 w.write(" <tr>");
 w.write(" <td>1</td>");
 w.write(" <td>userA</td>");
 w.write(" <td>10</td>");
 w.write(" </tr>");
*/
        for (Member member : members) {
            w.write(" <tr>");
            w.write(" <td>" + member.getId() + "</td>");
            w.write(" <td>" + member.getUsername() + "</td>");
            w.write(" <td>" + member.getAge() + "</td>");
            w.write(" </tr>");
        }
        w.write(" </tbody>");
        w.write("</table>");
        w.write("</body>");
        w.write("</html>");
    }
}
```
![memberList](https://images.velog.io/images/urtimeislimited/post/1eb3a7d0-ce5d-4d12-8dde-175397809e90/image.png)

MemberListServlet 은 다음 순서로 동작합니다.
1. memberRepository.findAll() 을 통해 모든 회원을 조회한다.
2. 회원 목록 HTML을 for 루프를 통해서 회원 수 만큼 동적으로 생성하고 응답합니다.

>템플릿 엔진으로
지금까지 서블릿과 자바 코드만으로 HTML을 만들어보았습니다. 
서블릿 덕분에 동적으로 원하는 HTML을 마음껏 만들 수 있지만 
정적인 HTML 문서라면 화면이 계속 달라지는 회원의 저장 결과라던가, 회원 목록 같은 동적인 HTML을 만드는 일은 불가능 할 것입니다.
그런데, 코드에서 보듯이 이것은 매우 복잡하고 비효율 적입니다.
자바 코드로 HTML을 만들어 내는 것 보다
차라리 HTML 문서에 동적으로 변경해야 하는 부분만 자바 코드를 넣을 수 있다면 더 편리할 것 같습니다.
이것이 바로 템플릿 엔진이 나온 이유입니다. 
템플릿 엔진을 사용하면 HTML 문서에서 필요한 곳만 코드를 적용해서 동적으로 변경할 수 있습니다.
템플릿 엔진에는 JSP, Thymeleaf, Freemarker, Velocity등이 있습니다.

 - 참고
  - JSP는 성능과 기능면에서 다른 템플릿 엔진과의 경쟁에서 밀리면서, 점점 사장되어 가는 추세입니다. 템플릿 엔진들은 각각 장단점이 있는데, JSP는 잠깐 다루고, 스프링과 잘 통합되는 Thymeleaf를 사용하겠습니다.
  


#### JSP로 회원 관리 웹 애플리케이션 만들기
JSP를 사용하려면 먼저 다음 라이브러리를 추가해야 합니다.

```java
//JSP 추가 시작
implementation 'org.apache.tomcat.embed:tomcat-embed-jasper'
implementation 'javax.servlet:jstl'
//JSP 추가 끝
```
회원 등록 폼 JSP
```jsp
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="/jsp/members/save.jsp" method="post">
    username: <input type="text" name="username" />
    age:      <input type="text" name="age" />
    <button type="submit">전송</button>
</form>
</body>
</html>
```
![/jsp/members/save.jsp](https://images.velog.io/images/urtimeislimited/post/3e898bf0-f209-476d-a335-2ec7208ae8b0/image.png)

회원 저장 JSP
```jsp
<%@ page import="hello.servlet.domain.member.Member" %>
<%@ page import="hello.servlet.domain.member.MemberRepository" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%--자바 로직 태그--%>
<%
    // request, response은 그냥 사용 가능
    MemberRepository memberRepository = MemberRepository.getInstance();
    String username = request.getParameter("username");
    int age = Integer.parseInt(request.getParameter("age"));

    Member member = new Member(username, age);
    memberRepository.save(member);
%>
<html>
<head>
    <title>Title</title>
</head>
<body>
성공
<ul>
    <li>id=<%=member.getId()%></li>
    <li>username=<%=member.getUsername()%></li>
    <li>age=<%=member.getAge()%></li>
</ul>
<a href="/index.html">메인</a>
</body>
</html>
```
>http://localhost:8080/jsp/members/new-form.jsp 저장 결과
![save.jsp](https://images.velog.io/images/urtimeislimited/post/7bcadb28-3ff6-4ea4-80e2-a92cc052afbf/image.png)

JSP는 자바 코드를 그대로 다 사용할 수 있습니다.
- <%@ page import="hello.servlet.domain.member.MemberRepository" %>
  - 자바의 import 문과 같다.
- <% ~~ %>
  - 이 부분에는 자바 코드를 입력할 수 있다.
- <%= ~~ %>
  - 이 부분에는 자바 코드를 출력할 수 있다.
회원 저장 JSP를 보면, 회원 저장 서블릿 코드와 같습니다. 
다른 점이 있다면, HTML을 중심으로 하고, 자바 코드를 부분부분 입력해주었습니다. <% ~ %> 를 사용해서 HTML 중간에 자바 코드를 출력하고 있습니다.

회원 목록 JSP
``` jsp
<%
    MemberRepository memberRepository = MemberRepository.getInstance();
    List<Member> members = memberRepository.findAll();
%>
<html>
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
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
            out.write(" <tr>");
            out.write(" <td>" + member.getId() + "</td>");
            out.write(" <td>" + member.getUsername() + "</td>");
            out.write(" <td>" + member.getAge() + "</td>");
            out.write(" </tr>");
        }
    %>
    </tbody>
</table>
</body>
</html>
```

회원 리포지토리를 먼저 조회하고, 결과 List를 사용해서 중간에 <tr><td> HTML 태그를 반복해서 출력하고 있습니다.
  
#### 서블릿과 JSP의 한계
  
서블릿으로 개발할 때는 뷰(View)화면을 위한 HTML을 만드는 작업이 자바 코드에 섞여서 지저분하고 복잡했습니다.
JSP를 사용한 덕분에 뷰를 생성하는 HTML 작업을 깔끔하게 가져가고, 중간중간 동적으로 변경이 필요한 부분에만 자바 코드를 적용했지만 해결되지 않는 몇가지 고민이 남습니다.
회원 저장 JSP를 보면, 코드의 상위 절반은 회원을 저장하기 위한 비즈니스 로직이고, 나머지 하위 절반만 결과를 HTML로 보여주기 위한 뷰 영역입니다. 
회원 목록의 경우에도 마찬가지입니다.
코드를 잘 보면, JAVA 코드, 데이터를 조회하는 리포지토리 등등 다양한 코드가 모두 JSP에 노출되어 있다. 
JSP가 너무 많은 역할을 한다. 이렇게 작은 프로젝트도 벌써 머리가 아파오는데, 수백 수천줄이 넘어가는 JSP를 떠올려보면 정말 지옥과 같을 것입니다. 
  
#### MVC 패턴 - 개요
__너무 많은 역할__
하나의 서블릿이나 JSP만으로 비즈니스 로직과 뷰 렌더링까지 모두 처리하게 되면, 너무 많은 역할을 하게되고, 결과적으로 유지보수가 어려워집니다. 
비즈니스 로직을 호출하는 부분에 변경이 발생해도 해당 코드를 손대야 하고, UI를 변경할 일이 있어도 비즈니스 로직이 함께 있는 해당 파일을 수정해야 합니다. 
HTML 코드 하나 수정해야 하는데, 수백줄의 자바 코드가 함께 있다고 상상해보면.. 또는 비즈니스 로직을 하나 수정해야 하는데 수백 수천줄의 HTML 코드가 함께 있다고 상상해보면 끔찍합니다!!

__변경의 라이프 사이클__
사실 이게 정말 중요한데, 진짜 문제는 둘 사이에 **변경의 라이프 사이클이 다르다**는 점입니다. 예를 들어서 UI를 일부 수정하는 일과 비즈니스 로직을 수정하는 일은 각각 다르게 발생할 가능성이 매우 높고 대부분 서로에게 영향을 주지 않습니다. 
이렇게 변경의 라이프 사이클이 다른 부분을 하나의 코드로 관리하는 것은 유지보수하기 좋지 않습니다. (물론 UI가 많이 변하면 함께 변경될 가능성도 있습니다.)
  
__기능 특화__
특히 JSP 같은 뷰 템플릿은 화면을 렌더링 하는데 최적화 되어 있기 때문에 이 부분의 업무만 담당하는 것이 가장 효과적입니다.
  
__Model View Controller__
MVC 패턴은 지금까지 학습한 것 처럼 하나의 서블릿이나, JSP로 처리하던 것을, 
컨트롤러(Controller)와 뷰(View)라는 영역으로 서로 역할을 나눈 것을 말합니다.  웹 애플리케이션은 보통 이 MVC 패턴을 사용합니다.
  
__컨트롤러__: HTTP 요청을 받아서 파라미터를 검증하고, 비즈니스 로직을 실행합니다. 그리고 뷰에 전달할 결과 데이터를 조회해서 모델에 저장합니다.
__모델__: 뷰에 출력할 데이터를 저장합니다. 뷰가 필요한 데이터를 모두 모델에 담아서 전달해주는 덕분에 뷰는 비즈니스 로직이나 데이터 접근을 몰라도 되고, 화면을 렌더링 하는 일에 집중할 수 있습니다.
__뷰__: 모델에 저장된 데이터를 사용해서 화면을 그리는 일에 집중합니다. (HTML을 생성하는 부분을 말합니다.)

MVC 패턴 이전
![beforeMVC](https://images.velog.io/images/urtimeislimited/post/0591a4d0-ad6c-4bd9-b05a-6cca1a9dc8f8/image.png)

MVC 패턴 동작 순서
  1. 고객 요청 시 컨트롤러에서 비즈니스 로직을 수행합니다.
  2. 결과 데이터를 모델에 저장합니다.
  3. 모델을 통해 데이터를 전달받은 뷰는 제어권을 넘겨받고 뷰 로직을 수행합니다.
    - 모델 덕분에 컨트롤과 뷰의 의존 관계가 간소화됩니다.
  4. 뷰 로직 실행 결과를 고객에게 응답합니다.
![mvc](https://images.velog.io/images/urtimeislimited/post/378d2958-c5d0-4b12-a341-7128b9316d0d/image.png)
	

***

### 4. MVC 프레임워크 만들기

MVC 패턴은 "공통 처리의 어려움"이라는 한계가 있었습니다. 프론트 컨트롤러 패턴을 도입시켜 점진적으로 개선시켜보겠습니다.

####  프론트 컨트롤러 패턴 소개

프론터 컨트롤러를 도입하기 전에는
클라이언트가 매번 공통 로직과 컨트롤러 로직을 각각 수행했어야 했습니다. 
![fc](https://images.velog.io/images/urtimeislimited/post/83b4e885-16f7-4f12-868f-05913be92231/image.png)
프론트 컨트롤러를 도입 하게 되면 먼저 공통 로직을 한 곳에서 처리하고 각 컨트롤러에게 로직 처리를 넘겨줍니다. 공통의 관심사를 우선적으로 먼저 처리해주는 문지기 역할이라고 할 수 있습니다.
![afterFc](https://images.velog.io/images/urtimeislimited/post/68b70845-5536-4f9d-81a2-896786950d2f/image.png)

FrontController의 특징은 다음과 같습니다.

- 프론트 컨트롤러 서블릿 하나로 클라이언트의 요청을 받습니다.
- 프론트 컨트롤러가 요청에 맞는 컨트롤러를 찾아서 호출합니다.
- 입구를 하나로! 최소화합니다.
- **공통 처리 가능**
- 프론트 컨트롤러를 제외한 나머지 컨트롤러는 서블릿을 사용하지 않아도 됩니다.
  - 요청 매핑에 사용되는 서블릿은 프론트 컨트롤러로 충분합니다.

스프링 웹 MVC의 핵심은 바로 **FrontController**에 있습니다. 스프링 웹 MVC의 __DispatcherServlet__이 FrontController 패턴으로 구현되어 있기 때문입니다.
- [DispatcherServlet](https://www.java67.com/2017/06/what-is-use-of-dispatcherservlet-in-spring-mvc.html#:~:text=The%20DispatcherServlet%20is%20one%20of,acts%20as%20a%20Front%20Controller.&text=The%20DispatcherServlet%20is%20a%20front,Spring%20MVC%20controllers%20for%20processing.) : DispatcherServlet은 Spring MVC 웹 애플리케이션에 대한 클라이언트 요청에 대한 단일 진입점을 제공하고 처리를 위해 요청을 Spring MVC 컨트롤러에 전달하는 것과 같은 FrontController입니다.

#### 프론트 컨트롤러 도입 - v1

프론트 컨트롤러를 단계적으로 도입해보겠습니다.
기존 코드를 최대한 유지하면서, 프론트 컨트롤러 도입하는 것을 목표로 먼저 구조를 설정한 후 점진적 리팩터링 하겠습니다.
- v1 구조
1. 클라이언트가 HTTP 요청 시 FrontController라는 서블릿이 요청을 전달받습니다.
   - 요청 메시지에서 URL 매핑 정보를 기반으로 필요한 컨트롤러를 조회합니다.
2. 해당 컨트롤러를 호출합니다.
3. 컨트롤러가 자신의 로직을 수행, 
view로forward -> JSP(해당 서블릿) 렌더링하여 응답을 반환합니다.

![v1](https://images.velog.io/images/urtimeislimited/post/ab4fe8e4-6dbd-4dd0-ac06-fe1724724200/image.png)

- 서블릿과 비슷한 모양의 컨트롤러 인터페이스를 도입합니다. 
```java
public interface ControllerV1 {
        void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}
```
- 각 컨트롤러들은 이 인터페이스를 구현하면
됩니다. 
- 프론트 컨트롤러는 이 인터페이스를 호출해서 구현과 관계없이 로직의 일관성을 가져갈 수 있습니다.
- 기존 로직을 최대한 유지하면서 인터페이스를 구현한 컨트롤러를 만들어보겠습니다. 

**회원 등록 컨트롤러**
 ```java
 public class MemberFormControllerV1 implements ControllerV1{
    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String viewPATH = "/WEB-INF/views/new-form.jsp";
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPATH);
        dispatcher.forward(request, response);
    }
}
 ```
__회원 저장 컨트롤러__
```java
public class MemberSaveControllerV1 implements ControllerV1 {
    private MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));

        Member member = new Member(username, age);
        memberRepository.save(member);
        
        request.setAttribute("member", member); //Model에 데이터 보관

        String viewPATH = "/WEB-INF/views/save-result.jsp";
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPATH);
        dispatcher.forward(request, response);
    }
}
```

**회원 목록 컨트롤러**
```java
public class MemberListControllerV1 implements ControllerV1{

    private MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Member> members = memberRepository.findAll();

        request.setAttribute("members", members);

        String viewPath = "/WEB-INF/views/members.jsp";
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
        dispatcher.forward(request, response);
    }
}
```

**프론트 컨트롤러**
```java
@WebServlet(name = "frontControllerServeltV1", urlPatterns = "/front-controller/v1/*") // *에 어떤 URL이 들어와도 해당 서블릿 무조건 호출되어서 매핑되는 원리
public class FrontControllerServletV1 extends HttpServlet {

    // 매핑 정보
    private Map<String, ControllerV1> controllerMap = new HashMap<>();

    // 서블릿 생성시 매핑 정보 객체 미리 저장, 해당 데이터 조회해서 사용 가능
    public FrontControllerServletV1() {
        controllerMap.put("/front-controller/v1/members/new-form", new MemberFormControllerV1());
        controllerMap.put("/front-controller/v1/members/save", new MemberSaveControllerV1());
        controllerMap.put("/front-controller/v1/members/members", new MemberListControllerV1());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
         System.out.println("FrontControllerServletV1.service"); // test

        String requestURI = request.getRequestURI();
        ControllerV1 controller = controllerMap.get(requestURI);

        if (controller == null){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        controller.process(request, response);
    }
}
```

- urlPatterns
  - urlPatterns = "/front-controller/v1/*" : /front-controller/v1 를 포함한 하위 모든 요청은 이 서블릿에서 받아들입니다.
  - 예) /front-controller/v1 , /front-controller/v1/a , /front-controller/v1/a/b
- controllerMap
  - key: 매핑 URL
  - value: 호출될 컨트롤러
- service()
  - 먼저 requestURI 를 조회해서 실제 호출할 컨트롤러를 controllerMap 에서 찾습니다. 만약 없다면 404(SC_NOT_FOUND) 상태 코드를 반환합니다.
  - 컨트롤러를 찾고 controller.process(request, response); 을 호출해서 해당 컨트롤러를 실행합니다.


#### View 분리 - v2
모든 컨트롤러에서 이동하는 부분에 중복이 있고 깔끔하지 않습니다.
```java
String viewPath = "/WEB-INF/views/members.jsp";
RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
dispatcher.forward(request, response);
```
이 부분을 깔끔하게 분리하기 위해 별도로 뷰를 처리하는 객체를 만들겠습니다.
구조는 다음과 같이 변경됩니다.

1. 클라이언트가 HTTP 요청 시 FrontController라는 서블릿이 요청을 전달받습니다.
   - 요청 메시지에서 URL 매핑 정보를 기반으로 필요한 컨트롤러를 조회합니다.
2. 해당 컨트롤러를 호출합니다.
3. 컨트롤러가 자신의 로직(forward)을 직접 처리하지 않습니다. 대신 view 역할을 하는 객체를 반환합니다.
4. 프론트 컨트롤러가 해당 객체의 render() 메서드를 호출하면 JSP로 전달(forward)합니다.

![v2](https://images.velog.io/images/urtimeislimited/post/8eeed517-f449-4405-be2d-8070455bdb19/image.png)

다른 곳에서 사용될 뷰 객체는
```java
public class MyView {
    private String viewPath;

    public MyView(String viewPath) {
        this.viewPath = viewPath;
    }

    public void render(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
        dispatcher.forward(request, response);
    }
}
```

컨트롤러 인터페이스를 통해 점직적으로 구현됩니다.
```java
public interface ControllerV2 {
     MyView process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException;
}
```
회원등록
```java
public class MemberFormControllerV2 implements ControllerV2 {
    @Override
    public MyView process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /**
        String viewPATH = "/WEB-INF/views/new-form.jsp";
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPATH);
        dispatcher.forward(request, response);
        */

        /**
        MyView myView = new MyView("/WEB-INF/views/new-form.jsp");
        return myView;
         */

        // Ctrl Alt n (inline method)
        return new MyView("/WEB-INF/views/new-form.jsp");
    }
}
```
이제 복잡한 dispatcher.forward 생성 호출같은 로직이 필요없어졌습니다. 단순히 MyView 객체를 생성하고 거기에 뷰 이름만 지정하여 반환받으면 됩니다.

ControllerV1에 비해 ControllerV2를 구현한 클래스는 이러한 중복이 확실히 제거되었음을 확인할 수 있습니다.

```java
public class MemberSaveControllerV2 implements ControllerV2 {
    private MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    public MyView process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));

        Member member = new Member(username, age);
        memberRepository.save(member);

        request.setAttribute("member", member); //Model에 데이터 보관

        /**
         String viewPATH = "/WEB-INF/views/save-result.jsp";
         RequestDispatcher dispatcher = request.getRequestDispatcher(viewPATH);
         dispatcher.forward(request, response);
         */

        return new MyView("/WEB-INF/views/save-result.jsp");
    }
}
```
회원 목록
```java
public class MemberListControllerV2 implements ControllerV2 {
    private MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    public MyView process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<Member> members = memberRepository.findAll();
        request.setAttribute("members", members);
        return new MyView("/WEB-INF/views/members.jsp");

        /**
         String viewPath = "/WEB-INF/views/members.jsp";
         RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
         dispatcher.forward(request, response);
         */
    }
}

```

프론트 컨트롤러
```java
@WebServlet(name = "frontControllerServeltV2", urlPatterns = "/front-controller/v5/*") // *에 어떤 URL이 들어와도 해당 서블릿 무조건 호출되어서 매핑되는 원리
public class FrontControllerServletV2 extends HttpServlet {

    // 매핑 정보
    private Map<String, ControllerV2> controllerMap = new HashMap<>();

    // 서블릿 생성시 매핑 정보 객체 미리 저장, 해당 데이터 조회해서 사용 가능
    public FrontControllerServletV2() {
        controllerMap.put("/front-controller/v2/members/new-form", new MemberFormControllerV2());
        controllerMap.put("/front-controller/v2/members/save", new MemberSaveControllerV2());
        controllerMap.put("/front-controller/v2/members/members", new MemberListControllerV2());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("FrontControllerServletV2.service"); // test

        String requestURI = request.getRequestURI();
        ControllerV2 controller = controllerMap.get(requestURI);

        if (controller == null){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        
        MyView view = controller.process(request, response);
        view.render(request, response);
    }
}
```
ControllerV2의 반환 타입이 MyView 이므로 프론트 컨트롤러는 컨트롤러의 호출 결과로 MyView를 받습니다. 
- view.reinder()를 호출하면 forward 로직을 수행해서 JSP가 실행됩니다.

>프론트 컨트롤러의 도입으로 MyView 객체의 render() 호출 역할을 모두 일관되게 처리할 수 있습니다. 각각의 컨트롤러는 오직 MyView 객체를 생성, 반환하면 됩니다.

#### Model 추가 - v3

- 서블릿 종속성 제거
  - 컨트롤러 입장에서 HttpServletRequest, HttpServletResponse가 꼭 필요할지 고민해봐야 합니다.
  - 요청 파라미터 정보는 자바의 Map으로 대신 넘기도록 하면 지금 구조에서는 **컨트롤러가 서블릿 기술을 몰라도 동작할 수 있습니다.**
  - request 객체를 Model로 사용하는 대신 별도의 Model 객체를 만들어서 반환하면 됩니다.
  - **구현하는 컨트롤러가 서블릿 기술을 전혀 사용하지 않도록 변경하면 구현 코드도 매우 단순해지고, 테스트 코드 작성이 쉽습니다.**
  
- 뷰 이름 중복 제거
  - 컨트롤러에서 지정하는 뷰 이름에 중복이 있는 것을 확인할 수 있습니다.
  - 컨트롤러는 "뷰의 논리 이름"을 변환하고, 실제 물리 위치의 이름은 프론트 컨트롤러에서 처리하도록 단순화합니다.
  - 이렇게 해두면 향후 **뷰의 폴더 위치경로가 함께 변경되더라도 프론트 컨트롤러만 고치면 됩니다.** 즉, 변경 지점이 통일되어 대응할 수 있는 좋은 설계입니다.

>
- "/WEB-INF/views/new-form.jsp" -> **new-form**
- "/WEB-INF/views/save-result.jsp" -> **save-result**
- "/WEB-INF/views/members.jsp" ->** members**

변경된 구조는 다음과 같습니다.
1. 클라이언트의 HTTP 요청 -> 매핑 정보 컨트롤러 조회
2. 컨트롤러 호출
3. 기존에 view 객체만 반환했던 것과 달리 model과 view가 함께 포함되어있는 ModelView를 반환합니다.
4. "뷰의 물리 위치"를 관리하는 컨트롤러에서 "뷰의 논리 이름"을 반환하여 프론트 컨트롤러에서 단순하게 처리합니다.

![v3 구조](https://images.velog.io/images/urtimeislimited/post/27e21e1f-58f3-46ab-86b8-eb18db87e655/image.png)

ModelView
지금까지 컨트롤러에서 서블릿에 종속적인 HttpServletRequest를 사용했습니다. 그리고 Model도 request.setAttribute()를 통해 데이터를 저장하고 뷰에 전달했습니다. 서블릿의 종속성을 제거하기 위해 Model을 직접 만들고, 추가로 View 이름까지 전달하는 객체를 만들어 보겠습니다.

```java
public class ModelView {
    private String viewName;
    private Map<String, Object> model = new HashMap<>();

    public ModelView(String viewName) {
        this.viewName = viewName;
    }

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    public Map<String, Object> getModel() {
        return model;
    }

    public void setModel(Map<String, Object> model) {
        this.model = model;
    }
}
```
뷰의 이름과 뷰를 렌더링할 때 필요한 model 객체를 가지고 있습니다. model은 단순히 map으로 구성되어 있으므로 컨트롤러에서 뷰에 필요한 데이터를 key, value로 넣어주면 됩니다.

ControllerV3
```java
public interface ControllerV3 {
    ModelView process(Map<String, String> paramMap); // 서블릿 종속성 제거
}
```
이 컨트롤러는 서블릿 기술을 전혀 사용하지 않기 때문에 구현이 매우 단순해지고, 테스트 코드 작성시 테스트하기 쉽습니다.
HttpServletRequest가 제공하는 파라미터는 프론트 컨트롤러가 paramMap에 담아서 호출해주면 됩니다. 응답 결과로 뷰 이름과 뷰에 전달할 Model 데이터를 포함하는 ModelView객체를 반환하면 됩니다.

회원 등록 폼
```java
@WebServlet(name = "mvcMemberFormServlet", urlPatterns = "/servlet-mvc/members/new-form")
public class MvcMemberFormServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String viewPath = "/WEB-INF/views/new-form.jsp";
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
        dispatcher.forward(request, response);
    }
}
```
ModelView를 생성할 때 new-form이라는 view의 논리적인 이름을 지정합니다.
실제 물리적인 이름은 프론트 컨트롤러에서 처리합니다.

회원저장
```java
@WebServlet(name = "mvcMemberSaveServlet", urlPatterns = "/servlet-mvc/members/save")
public class MvcMemberSaveServlet extends HttpServlet {

    private MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));

        Member member = new Member(username, age);
        memberRepository.save(member);

        //Model에 데이터를 보관
        request.setAttribute("member", member);

        String viewPath = "/WEB-INF/views/save-result.jsp";
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
        dispatcher.forward(request, response);
    }
}
```
- paramMap.get("username");
파라미터 정보는 map에 담겨있습니다. map에서 필요한 요청 파라미터를 조회하면 됩니다.
- mv.getModel().put("member", member);
모델은 단순한 map이므로 모델에 뷰에서 필요한 member 객체를 담고 반환합니다.

FrontControllerServletV3
```java
@WebServlet(name = "frontControllerServeltV3", urlPatterns = "/front-controller/v3/*") // *에 어떤 URL이 들어와도 해당 서블릿 무조건 호출되어서 매핑되는 원리
public class FrontControllerServletV3 extends HttpServlet {

    // 매핑 정보
    private Map<String, ControllerV3> controllerMap = new HashMap<>();

    // 서블릿 생성시 매핑 정보 객체 미리 저장, 해당 데이터 조회해서 사용 가능
    public FrontControllerServletV3() {
        controllerMap.put("/front-controller/v3/members/new-form", new MemberFormControllerV3());
        controllerMap.put("/front-controller/v3/members/save", new MemberSaveControllerV3());
        controllerMap.put("/front-controller/v3/members/members", new MemberListControllerV3());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("FrontControlControllerV3.service"); // test

        String requestURI = request.getRequestURI();
        ControllerV3 controller = controllerMap.get(requestURI);

        if (controller == null){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        //paramMap
        /**
         * Map<String, String> paramMap = new HashMap<>();
         *         request.getParameterNames().asIterator()
         *                 .forEachRemaining(paramName -> paramMap.put(paramName, request.getParameter(paramName)));
         */

        Map<String, String> paramMap = createParamMap(request);

        ModelView mv = controller.process(paramMap);
        String viewName = mv.getViewName();// 논리 이름 new-form
        /**
         // /WEB-INF/views/new-form.jsp
         MyView view = new MyView("/WEB-INF/views/" + viewName + ".jsp");
         view.render(request, response);
         */
        // /WEB-INF/views/new-form.jsp
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
- createParamMap()
HttpServletRequest에서 파라미터 정보를 꺼내서 Map으로 변환합니다. 그리고 해당 Map( paramMap )을 컨트롤러에 전달하면서 호출합니다.
- view.render(mv.getModel(), request, response) 코드에서 컴파일 오류가 발생할 것입니다. 다음 코드를 참고해서 MyView 객체에 필요한 메서드를 추가합니다.

뷰 리졸버

```MyView view = viewResolver(viewName)```
- 컨트롤러가 반환한 논리 뷰 이름을 실제 물리 뷰 경로로 변경하고, 실제 물리 경로가 있는 MyView 
객체를 반환합니다.
- 논리 뷰 이름: members
- 물리 뷰 경로: /WEB-INF/views/members.jsp
view.render(mv.getModel(), request, response)
- 뷰 객체를 통해서 HTML 화면을 렌더링 합니다.
- 뷰 객체의 render() 는 모델 정보도 함께 받습니다.
- JSP는 request.getAttribute() 로 데이터를 조회하기 때문에, 모델의 데이터를 꺼내서
- request.setAttribute() 로 담아둡니다.
- JSP로 포워드 해서 JSP를 렌더링 합니다.

MyView
```java
public class MyView {
    private String viewPath;

    public MyView(String viewPath) {
        this.viewPath = viewPath;
    }

    public void render(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
        dispatcher.forward(request, response);
    }

    public void render(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException{
        modelToRequesetAttribute(model, request);
        RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
        dispatcher.forward(request, response);
    }

    private void modelToRequesetAttribute(Map<String, Object> model, HttpServletRequest request) {
        model.forEach((key, value) -> request.setAttribute(key, value));
    }
}
```

#### 단순하고 실용적인 컨트롤러 - v4
앞서 만든 v3 컨트롤러는 서블릿 종속성을 제거하고 뷰 경로의 중복을 제거하는 등, 잘 설계된 컨트롤러입니다. 그런데 실제 컨트롤러 인터페이스를 구현하는 개발자 입장에서 보면, 항상 ModelView 객체를 생성하고 반환해야 하는 부분이 조금은 번거롭습니다.
좋은 프레임워크는 아키텍쳐도 중요하지만, 그와 더불어 실제 개발하는 개발자가 단순하고 편리하게 사용할 수 있어야 합니다. 소위 실용성이 있어야 합니다.

v3를 조금 변경해서 실제 구현하는 개발자들이 매우 편리하게 개발할 수 있는 v4 버전을 개발해보겠습니다.

![v4](https://images.velog.io/images/urtimeislimited/post/51b02f1c-3a05-4405-b00b-d5f8be21d563/image.png)
- 기본적인 구조는 v3와 같습니다. 
대신 컨트롤러가 ModelView를 반환하지 않고, ViewName만 반환합니다.

ControllerV4
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
- 인터페이스에 ModelView가 없습니다.
- model 객체는 파라미터로 전달되기 때문에 그냥 사용하면 되고, 결과로 뷰의 이름만 반환해주면 됩니다. 실제 구현 코드를 보면

MemberFormControllerV4
```java
public class MemberFormControllerV4 implements ControllerV4 {
    @Override
    public String process(Map<String, String> paramMap, Map<String, Object> model) {
        return "new-form";
    }
}
```
- 정말 단순하게 new-form이라는 뷰의 논리 이름만 반환하면 됩니다.

MemberSaveControllerV4
```java
public class MemberSaveControllerV4 implements ControllerV4 {
    private MemberRepository memberRepository = MemberRepository.getInstance();
    
    @Override
    public String process(Map<String, String> paramMap, Map<String, Object> model) {
        String username = paramMap.get("username");
        int age = Integer.parseInt(paramMap.get("age"));

        Member member = new Member(username, age);
        memberRepository.save(member);

        model.put("member", member);
        return "save-result";
    }
}
```
- 모델이 파라미터로 전달됩니다. 모델을 직접 생성하지 않아도 됩니다.

MemberListControllerV4
```java
public class MemberListControllerV4 implements ControllerV4 {

    private MemberRepository memberRepository = MemberRepository.getInstance();

    @Override
    public String process(Map<String, String> paramMap, Map<String, Object> model) {
        List<Member> members = memberRepository.findAll();

        model.put("members", members);
        return "members";
        /**
        ModelView mv = new ModelView("members");
        mv.getModel().put("members", members);

        return mv;
         */
    }
}
```
FrontControllerServletV4
```java
@WebServlet(name = "frontControllerServeltV4", urlPatterns = "/front-controller/v4/*") // *에 어떤 URL이 들어와도 해당 서블릿 무조건 호출되어서 매핑되는 원리
public class FrontControllerServletV4 extends HttpServlet {

    // 매핑 정보
    private Map<String, ControllerV4> controllerMap = new HashMap<>();

    // 서블릿 생성시 매핑 정보 객체 미리 저장, 해당 데이터 조회해서 사용 가능
    public FrontControllerServletV4() {
        controllerMap.put("/front-controller/v4/members/new-form", new MemberFormControllerV4());
        controllerMap.put("/front-controller/v4/members/save", new MemberSaveControllerV4());
        controllerMap.put("/front-controller/v4/members/members",
                new MemberListControllerV4());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("FrontControlControllerV4.service"); // test

        String requestURI = request.getRequestURI();
        ControllerV4 controller = controllerMap.get(requestURI);

        if (controller == null){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }


        Map<String, String> paramMap = createParamMap(request);
        /**
        * 추가
        */
        Map<String, Object> model = new HashMap<>(); 
        String viewName = controller.process(paramMap, model);


        // /WEB-INF/views/new-form.jsp
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
- **모델 객체 전달** : Map<String, Object> model = new HashMap<>(); 추가
- 모델 객체를 프론트 컨트롤러에서 생성해서 넘겨줍니다. 컨트롤러에서 모델 객체에 값을 담으면 여기에 그대로 담겨있게 됩니다.
- **뷰의 논리 이름을 직접 반환** : 컨트롤러가 직접 뷰의 논리 이름을 반환하므로 이 값을 사용해서 실제 물리 뷰를 찾을 수 있습니다.
```java
String viewName = controller.process(paramMap, model);
MyView view = viewResolver(viewName);
```
컨트롤러가 직접 뷰의 논리 이름을 반환하므로 이 값을 사용해서 실제 물리 뷰를 찾을 수 있습니다.

이번 버전의 컨트롤러는 **매우 단순하고 실용적**입니다. **"기존 구조에서 모델을 파라미터로 넘기고, 뷰의 논리 이름을 반환한다"**라는 작은 아이디어를 적용했을 뿐인데, 컨트롤러를 구현하는 입장에서 군더더기 없는 코드를 작성할 수 있습니다.
또한 여기까지 한 번에 온 것이 아니라 프레임워크가 점진적으로 발전하는 과정 속에서 이런 방법을 찾을 수 있었습니다.
**프레임워크나 공통 기능이 수고로워야 사용하는 개발자가 편리**해진다고 합니다.

#### 유연한 컨트롤러1 - v5
만약 ControllerV3 방식으로 개발하고 싶은 사람과, ControllerV4 방식으로 개발하고 싶은 사람이 있다면 어떻게 해야 할까요? 하나의 프로젝트 내에서 다양한 컨트롤러를 사용하고 싶다면?
```java
public interface ControllerV3 {
 ModelView process(Map<String, String> paramMap);
}
```
```java
public interface ControllerV4 {
 String process(Map<String, String> paramMap, Map<String, Object> model);
}
```
**어댑터 패턴**을 통해 위와 같은 문제를 해결할 수 있습니다.

- **어댑터 패턴**
지금까지 개발한 프론트 컨트롤러는 한가지 방식의 컨트롤러 인터페이스만 사용할 수 있었습니다.
  - ControllerV3, ControllerV4는 완전히 다른 인터페이스입니다. 따라서 호환이 불가능합니다.
  - 예를 들어 110v, 220v 전기 콘센트를 공통으로 호환하기 위해 한가지 어댑터를 사용하는 것처럼
  - 어댑터 패턴을 사용해서 프톤트 컨트롤러가 다양한 방식의 컨트롤러를 처리할 수 있도록 변경할 수 있습니다.
  
![v5](https://images.velog.io/images/urtimeislimited/post/be2b60f0-f69c-4073-94c9-4b0782ca1aa4/image.png)

내부 과정의 순서는 다음과 같습니다.
1. 클라이언트가 HTTP 요청시 프론터 컨트롤러가 핸들러(컨트롤러) 매핑 정보에서 핸들러를 조회합니다.
2. **핸들러 어탭터 목록에서 핸들러를 처리할 수 있는 핸들러 어댑터를 조회합니다.**
3. 기존 프론트 컨트롤러에서 핸들러를 바로 호출했던 것과 달리, 유연함을 위해 중간에서 핸들러 어댑터를 통해 핸들러를 호출합니다.
  - 프론트 컨트롤러에서 파라미터를 어댑터에게 전달합니다.
4. 어댑터에서 전달받은 파라미터에 해당하는 핸들러를 호출합니다.
5. 핸들러에게 반환받은 결과값을 기반으로 프론트 컨트롤러에게 ModelView를 반환해줍니다.
6. ModelView를 받은 프론트 컨트롤러가 뷰의 논리이름을 전달하여 viewResolver를 호출합니다.
7. "뷰의 물리 위치"를 관리하는 viewResolver에서 "뷰의 논리 이름"을 프론트 컨트롤러에게 반환합니다.
8. 프론트 컨트롤러는 전달받은 "뷰의 논리 이름"을 간단하게 처리하여 결과를 응답합니다.

- **핸들러** : 컨트롤러의 이름을 더 넓은 범위인 핸들러로 변경한 이유는 어댑터가 있기 때문에 꼭 컨트롤러의 개념 뿐만 아니라 어떠한 것이든 해당하는 종류의 어댑터만 있으면 다 처리할 수 있기 때문입니다.
- **핸들러 어댑터** : 프론터 컨트롤러와 핸들러 중간에 추가된 역할을 의미합니다. 어댑터 역할 덕분에 다양한 종류의 컨트롤러를 호출할 수 있습니다.

어댑터용 인터페이스를 구현해보겠습니다.
MyHandlerAdapter
```java
package hello.servlet.web.frontcontroller.v5;

public interface MyHandlerAdapter {
    boolean supports(Object handler);
    ModelView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException;
}
```
- boolean supports(Object handler)
  - handler는 컨트롤러를 말합니다.
  - 어댑터가 해당 컨트롤러를 처리할 수 있는지 판단하는 메서드 입니다.
- ModelView handle(HttpServletRequest request, HttpServletResponse response, Object handler)
  - 어댑터는 실제 컨트롤러를 호출하고, 그 결과로 ModelView를 반환해야 합니다.
  - 실제 컨트롤러가 ModelView를 반환하지 못하면, 어댑터가 ModelView를 직접 생성해서라도 반환해야 합니다.
  - 이전에는 프론트 컨트롤러가 실제 컨트롤러를 호출했지만 이제 이 어댑터를 통해서 실제 컨트롤러가 호출됩니다.
  
실제 어댑터 구현을 위해 먼저 ControllerV3를 지원하는 어댑터를 하나씩 구현해보겠습니다.
``` java
public boolean supports(Object handler) {
 return (handler instanceof ControllerV3);
}
```
- ControllerV3를 처리할 수 있는 어댑터입니다.
```java
        ControllerV3 controller = (ControllerV3) handler; // 핸들러를 controllerV3로 캐스팅
        Map<String, String> paramMap = createParamMap(request);
        ModelView mv = controller.process(paramMap);
        return mv;
```
- handler를 컨트롤러 V3로 변환한 다음 V3 형식에 맞도록 호출합니다.
- supports()를 통해 ControllerV3만 지원하기 때문에 타입 변환 걱정없이 실행해도 됩니다.
- ControllerV3는 ModelView를 반환하므로 그대로 ModelView를 반환하면 됩니다.


```java
public class ControllerV3HandlerAdapter implements MyHandlerAdapter {
    @Override
    public boolean supports(Object handler) {
        return (handler instanceof ControllerV3); // 핸들러는 V3의 인스턴스
    }
    @Override
    public ModelView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException {
        ControllerV3 controller = (ControllerV3) handler; // 핸들러를 controllerV3로 캐스팅
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

**FrontControllerServletV5**
```java
@WebServlet(name = "frontControllerServletV5", urlPatterns = "/front-controller/v5/*")
public class FrontControllerServletV5 extends HttpServlet {
    // 모든 컨트롤러 지원 위해 Object 사용
    private final Map<String, Object> handlerMappingMap = new HashMap<>();
    private final List<MyHandlerAdapter> handlerAdapters = new ArrayList<>();

    public FrontControllerServletV5() {
        initHandlerMappingMap();
        initHandlerAdapters();
    }

    private void initHandlerAdapters() {
        handlerAdapters.add(new ControllerV3HandlerAdapter());
    }

    private void initHandlerMappingMap() {
        handlerMappingMap.put("/front-controller/v5/v3/members/new-form", new MemberFormControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members/save", new MemberSaveControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members/members", new MemberListControllerV3());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Object handler = getHandler(request);
        if (handler == null){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        MyHandlerAdapter adapter = getHandlerAdapter(handler);
        ModelView mv = adapter.handle(request, response, handler);

        MyView view = viewResolver(mv.getViewName());
        view.render(mv.getModel(), request, response);
    }

    private Object getHandler(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return handlerMappingMap.get(requestURI);
    }

    private MyHandlerAdapter getHandlerAdapter(Object handler) {
        for (MyHandlerAdapter adapter : handlerAdapters) {
            if (adapter.supports(handler)) {
                return adapter;
            }
        }
        throw new IllegalArgumentException("handler adapter를 찾을 수 없습니다. handler= " + handler);
    }

    private MyView viewResolver(String viewName){
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }
}
```
- **컨트롤러(Controller)  -> 핸들러(Handler)**
이전에 컨트롤러를 직접 매핑해서 사용했지만 어댑터를 사용하게 되면 컨트롤러 뿐만 아니라 어댑터가 지원하는 어떤 것이라도 URL에 매핑해서 사용할 수 있습니다. 이름을 컨트롤러에서 더 넓은 범위의 핸들러로 변경한 이유입니다.

생성자
```java
public FrontControllerServletV5() {
        initHandlerMappingMap(); // 핸들러 매핑 초기화
        initHandlerAdapters(); // 어댑터 초기화
    }
```
 - 생성자는 핸들러 매핑과 어댑터를 초기화(등록)합니다.

매핑 정보
```private final Map<String, Object> handlerMappingMap = new HashMap<>();```
- 매핑 정보의 값이 ControllerV3 , ControllerV4 같은 인터페이스에서 아무 값이나 받을 수 있는 Object 로 변경되었습니다.

핸들러 매핑
```Object handler = getHandler(request); // Extract Method```
```java
    private Object getHandler(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return handlerMappingMap.get(requestURI);
    }
```
- 핸들러 매핑 정보인 handlerMappingMap에서 URL에 매핑된 핸들러(컨트롤러) 객체를 찾아서 반환합니다.

핸들러를 처리할 수 있는 어댑터 조회
```MyHandlerAdapter adapter = getHandlerAdapter(handler); // Extract Method```
```java
for (MyHandlerAdapter adapter : handlerAdapters) {
            if (adapter.supports(handler)) {
                return adapter;
            }
        }
```
- handler를 처리할 수 있는 어댑터를 adapter.supports(handler)를 통해서 찾습니다.
- handler가 ControllerV3 인터페이스를 구현했다면, ControllerV3HandlerAdapter 객체가 반환됩니다.

어댑터 호출
```ModelView mv = adapter.handle(request, response, handler); // Extract Method```

어댑터의 handle(request, response, handler) 메서드를 통해 실제 어댑터가 호출됩니다.
어댑터는 handler(컨트롤러)를 호출하고 그 결과를 어댑터에 맞추어 반환합니다.
ControllerV3HandlerAdapter의 경우 어댑터의 모양과 컨트롤러의 모양이 유사해서 변환 로직이 단순합니다.

지금은 V3 컨트롤러를 사용할 수 있는 어댑터와 ControlelrV3만 들어 있으니 ControlelrV4를 사용할 수 있도록 기능을 추가해보겠습니다.

#### 유연한 컨트롤러2 - v5
FrontControllerServletV5에 ControlelrV4 기능을 하나씩 추가해보겠습니다.
```java
    @Override
    public boolean supports(Object handler) {
        return (handler instanceof ControllerV4);
    }
```
- handler가 ControlelrV4의 객체인 경우에만 처리하는 어댑터입니다.

실행 로직
```java
        ControllerV4 controller = (ControllerV4) handler;

        Map<String, String> paramMap = createParamMap(request);
        Map<String, Object> model = new HashMap<>();

        String viewName = controller.process(paramMap, model);
```
- handler를 ControlelrV4로 캐스팅 합니다. - - paramMap, model을 만들어서 해당 컨트롤러를 호출합니다.
- viewName을 반환 받습니다.

어댑터 변환
```java
ModelView mv = new ModelView(viewName);
mv.setModel(model);
return mv;
```
- 어댑터가 호출하는 ControlelrV4는 뷰의 이름을 반환합니다.
- 어댑터는 뷰의 이름이 아니라 ModelView를 만들어서 반환해야 합니다.
- 어댑터가 꼭 필요한 이유
  - ControllerV4 는 뷰의 이름을 반환했지만, 어댑터는 이것을 ModelView로 만들어서 형식을 맞추어 반환합니다. 마치 110v 전기 콘센트를 220v 전기 콘센트로 변경하듯이!
- **어댑터에서 이 부분은 단순하지만 중요한 부분입니다.**
  
어댑터와 ControllerV4 차이
```java
public interface ControllerV4 {
 String process(Map<String, String> paramMap, Map<String, Object> model);
}
public interface MyHandlerAdapter {
 ModelView handle(HttpServletRequest request, HttpServletResponse response,
Object handler) throws ServletException, IOException;
}
```

최종 FrontControllerV5

```java
@WebServlet(name = "frontControllerServletV5", urlPatterns = "/front-controller/v5/*")
public class FrontControllerServletV5 extends HttpServlet {
    // 모든 컨트롤러 지원 위해 Object 사용
    private final Map<String, Object> handlerMappingMap = new HashMap<>();
    private final List<MyHandlerAdapter> handlerAdapters = new ArrayList<>();

    public FrontControllerServletV5() {
        initHandlerMappingMap(); // 핸들러 매핑 초기화
        initHandlerAdapters(); // 어댑터 초기화
    }

    private void initHandlerAdapters() {

        handlerAdapters.add(new ControllerV3HandlerAdapter());
        handlerAdapters.add(new ControllerV4HandlerAdapter()); // V4 추가
    }

    private void initHandlerMappingMap() {
        handlerMappingMap.put("/front-controller/v5/v3/members/new-form", new MemberFormControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members/save", new MemberSaveControllerV3());
        handlerMappingMap.put("/front-controller/v5/v3/members/members", new MemberListControllerV3());

        // V4 추가
        handlerMappingMap.put("/front-controller/v5/v4/members/new-form", new MemberFormControllerV4());
        handlerMappingMap.put("/front-controller/v5/v4/members/save", new MemberSaveControllerV4());
        handlerMappingMap.put("/front-controller/v5/v4/members/members", new MemberListControllerV4());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Object handler = getHandler(request);
        if (handler == null){
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        MyHandlerAdapter adapter = getHandlerAdapter(handler);
        ModelView mv = adapter.handle(request, response, handler);

        MyView view = viewResolver(mv.getViewName());
        view.render(mv.getModel(), request, response);
    }

    private Object getHandler(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        return handlerMappingMap.get(requestURI);
    }

    private MyHandlerAdapter getHandlerAdapter(Object handler) {
        for (MyHandlerAdapter adapter : handlerAdapters) {
            if (adapter.supports(handler)) {
                return adapter;
            }
        }
        throw new IllegalArgumentException("handler adapter를 찾을 수 없습니다. handler= " + handler);
    }

    private MyView viewResolver(String viewName){
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }
}
```

지금까지 v1 ~ v5로 점진적으로 프레임워크를 발전시켜왔습니다.

1. v1 : 프론트 컨트롤러를 도입
  - 기존 구조를 최대한 유지하면서 프론트 컨트롤러를 도입했습니다.
2. v2 : View 분류
  - 단순 반복되는 뷰 로직을 분리했습니다.
3. v3 : Model 추가
  - 서블릿 종속성을 제거했습니다.
  - 뷰 이름의 중복을 제거했습니다.
4. v4: 단순하고 실용적인 컨트롤러
  - v3와 거의 비슷하지만
  구현 입장에서 ModelView를 직접 생성해서 반환하지 않도록 편리한 인터페이스를 제공할 수 있습니다.
5. v5: 유연한 컨트롤러
  - 어댑터 도입
  - 어댑터를 추가해서 프레임워크를 유연하고 확장성 있게 설계했습니다.
  
여기에 어노테이션을 사용해서 컨트롤러를 더 편리하게 발전시킬 수도 있습니다. 만약 어노테이션을 사용해서 컨트롤러를 편리하게 사용할 수 있게 하려면 어노테이션을 지원하는 어댑터를 추가하면 됩니다.

다형성과 어댑터 덕분에 기존 구조를 유지하면서, 프레임워크의 기능을 확장할 수 있습니다.

스프링 MVC
지금까지 작성한 코드는 스프링 MVC 프레임워크의 핵심 코드의 축약 버전이고, 구조도
거의 같다고 합니다.
스프링 MVC에는 지금까지의 내용과 거의 같은 구조를 가지고 있습니다.
***

### 스프링 MVC - 구조 이해

#### 스프링 MVC 전체 구조
직접 만든 MVC 프레임워크와 스프링 MVC를 비교해보겠습니다. 

직접 만든 프레임워크와 스프링 MVC를 비교한 차이는 다음과 같습니다.

- FrontController -> DispatcherServlet
- handlerMappingMap -> HandlerMapping
- MyHandlerAdapter -> HandlerAdapter
- ModelView -> ModelAndView
- viewResolver -> ViewResolver
- MyView -> View 

![mvc](https://images.velog.io/images/urtimeislimited/post/db098793-d37a-44c0-bfb1-eafcdf343593/image.png)
	
![mvc](https://images.velog.io/images/urtimeislimited/post/db098793-d37a-44c0-bfb1-eafcdf343593/image.png)

#### DispatcherServlet 구조 살펴보기

```org.springframework.web.servlet.DispatcherServlet```
스프링 MVC도 프론트 컨트롤러 패턴으로 구현되어 있습니다.
스프링 MVC의 프론트 컨트롤러가 바로 디스패처 서블릿(DispatcherServlet)입니다.
그리고 이 디스패처 서블릿이 바로 스프링 MVC의 핵심입니다.

#### DispacherServlet 서블릿 등록
DispacherServlet 도 부모 클래스에서 HttpServlet 을 상속 받아서 사용하고, 
서블릿으로 동작합니다.
- DispatcherServlet -> FrameworkServlet -> HttpServletBean -> HttpServlet
- 스프링 부트는 DispacherServlet 을 서블릿으로 자동으로 등록하면서 모든 경로(urlPatterns="/" )에 대해서 매핑합니다.
- 참고: 더 자세한 경로가 우선순위가 높기 때문에 기존에 등록한 서블릿도 함께 동작합니다.

#### 요청 흐름

