# Java Crudy Orders

A student that completes this project shows that they can:

* Perform CRUD operations on an RDBMS JPA and Hibernate (Creating and Updating Records)
* Perform CRUD operations on an RDBMS JPA and Hibernate (Updating certain fields in records)
* Perform CRUD operations on an RDBMS JPA and Hibernate (Deleting records)
* Understand and implement @Transactional annotation

## Introduction

This is Part 3 of a 3 Part project.

* Part 1 can be found at [java-orders](https://github.com/LambdaSchool/java-orders.git)
* Part 2 can be found at [java-getorders](https://github.com/LambdaSchool/java-getorders.git)

This is a basic database scheme with customers, orders, and sales agents. This Java Spring REST API application will provide endpoints for clients to manipulate various data sets contained in the application's data.

### Database layout

The table layouts are as follows

![Image of Database Layout](java-orders-db.png)

* AGENTS
  * AGENTCODE primary key, not null Long
  * AGENTNAME string
  * WORKINGAREA string
  * COMMISSION double
  * PHONE string
  * COUNTRY string

* CUSTOMERS
  * CUSTCODE primary key, not null Long
  * CUSTNAME String, not null
  * CUSTCITY String
  * WORKINGAREA String
  * CUSTCOUNTRY String
  * GRADE String
  * OPENINGAMT double
  * RECEIVEAMT double
  * PAYMENTAMT double
  * OUTSTANDINGAMT double
  * PHONE String
  * AGENTCODE Long foreign key (one agent to many customers) not null

* ORDERS
  * ORDNUM primary key, not null Long
  * ORDAMOUNT double
  * ADVANCEAMOUNT double
  * CUSTCODE Long foreign key (one customer to many orders) not null
  * ORDERDESCRIPTION String

* PAYMENTS
  * PAYMENTID primary key, not null long
  * TYPE String not null
  
* ORDERSPAYMENTS (join table)
  * ORDERNUM foreign key to ORDERS
  * PAYMENTID foreign key to PAYMENTS.

* Customers has a foreign key to Agents (AGENTCODE) this means:
  * Customers has a Many to One relationship to Agents and
  * Agents has a One to Many relationship to Customers

* Orders has a foreign key to Customers (CUSTCODE)
  * Orders has a Many to One relationship to Customers and
  * Customers has a One to Many relationship to Orders

* Orders has a many to many relationship with payments
  * multiple orders can use the same payment type and an order can have multiple payment types.
  * For example you can use both gift card and credit card to pay for an order.

Using the provided seed data, a successful application will return the follow data based on the given endpoint. Expand the section of the endpoint to see the data that is returned. The first set of endpoints that read data were developed for the first part of the project. Your task is to add the second set of endpoints which manipulate the data.

### MVP - MANIPULATING DATA new endpoints to add

## Instructions

* [ ] Please fork and clone this repository. Copy your solution from part 2 into this repository. Your solution from part 2 is the starting point for part 3. If your part 2 did not reach MVP, please check with your TL group leader about your options. Regularly commit and push your code as appropriate.

Expose the following endpoints

* [ ]  POST /customers/customer - Adds a new customer including any new orders
* [ ]  PUT /customers/customer/{custcode} - completely replaces the customer record including associated orders with the provided data
* [ ]  PATCH /customers/customer/{custcode} - updates customers with the new data. Only the new data is to be sent from the frontend client.
* [ ]  DELETE /customers/customer/{custcode} - Deletes the given customer including any associated orders

* [ ]  POST /orders/order - adds a new order to an existing customer
* [ ]  PUT /orders/order/{ordernum} - completely replaces the given order record
* [ ]  DELETE /orders/order/{ordernum} - deletes the given order

### Stretch Goal

* [ ] Rename your project and associated packages and files from orders, or what you called your orders, to crudyorders, or what you would like to call your crudyorders project.
* [ ] DELETE /agents/unassigned/{agentcode} - Deletes an agent if they are not assigned to a customer

### MVP Testing

Note that your ids may vary!

<details>
<summary>POST http://localhost:2019/customers/customer</summary>

Given this input

```JSON
    {
        "custname": "John",
        "custcity": "Port Angeles",
        "workingarea": "Washington",
        "custcountry": "USA",
        "grade": "1",
        "openingamt": 70000,
        "receiveamt": 7000,
        "paymentamt": 777,
        "outstandingamt": 0,
        "phone": "5555555555",
        "agent": {
        "agentcode": 8
    },
        "orders": [
        {
            "ordamount": 7777,
                "advanceamount": 777,
                "orderdescription": "SOD",
            "payments" : [
            {
                "paymentid": 4
            }
            ]
        }
    ]
    }
```

Produce this output

```TEXT
No Body Data

Location Header: http://localhost:2019/customers/customer/54
Status 201 Created
```

</details>

<details>
<summary>PUT http://localhost:2019/customers/customer/19</summary>

Given this input

```JSON
    {
        "custname": "Mojo",
        "custcity": "Seattle",
        "workingarea": "Washington",
        "custcountry": "USA",
        "grade": "1",
        "openingamt": 70000,
        "receiveamt": 7000,
        "paymentamt": 777,
        "outstandingamt": 0,
        "phone": "123456789",
        "agent": {
        "agentcode": 8
    },
        "orders": [
        {
            "ordamount": 7777,
                "advanceamount": 777,
                "orderdescription": "SOD",
            "payments" : [
            {
                "paymentid": 4
            }
            ]
        },
        {
            "ordamount": 1234,
                "advanceamount": 52,
                "orderdescription": "ANOTHER ORDER",
            "payments" : [
            {
                "paymentid": 4
            }
            ]
        }
    ]
    }
```

Produce this output

```TEXT
No Body Data

Status OK
```

</details>

<details>
<summary>PATCH http://localhost:2019/customers/customer/19</summary>

Given this input

```JSON
    {
        "custname": "Micheal The Great",
        "custcity": "Austin",
        "workingarea": "TEXAS",
        "custcountry": "TX",
        "agent": {
            "agentcode": 11
        },
        "orders": [
        {
            "ordamount": 7777,
                "advanceamount": 777,
                "orderdescription": "IT WORKD",
            "payments" : [
            {
                "paymentid": 3
            }
            ]
        }
        ]
    }
```

Produce this output

```TEXT
No Body Data

Status OK
```

</details>

<details>
<summary>DELETE http://localhost:2019/customers/customer/54</summary>

Output

```TEXT
No Body Data

Status OK
```

</details>

<details>
<summary>POST http://localhost:2019/orders/order</summary>

Given this input

```JSON
{
   "ordamount" : 3.21,
   "advanceamount" : 1.23,
   "orderdescription" : "My New Order",
   "customer":
   {
       "custcode":30
   },
   "payments": [
   {
       "paymentid": 4
   }
   ]
}
```

Produce this output

```TEXT
No Body Data

Location Header: http://localhost:2019/orders/order/63
Status 201 Created
```

</details>

<details>
<summary>PUT http://localhost:2019/orders/order/63</summary>

Given this input

```JSON
{
    "payments": [
        {
            "paymentid": 1
        }
    ],
    "ordamount": 7.77,
    "advanceamount": 1.23,
    "orderdescription": "My Revised Order",
    "customer": {
        "custcode": 17
    }
}
```

Produce this output

```JSON
No Body Data

Status OK
```

</details>

<details>
<summary>DELETE http://localhost:2019/orders/order/58</summary>

Produce this output

```JSON
```

</details>

### Stretch Goal Testing

<details>
<summary>DELETE http://localhost:2019/agents/unassigned/8</summary>

Output

```TEXT
{
    "timestamp": "2020-03-17T18:28:14.117+0000",
    "status": 500,
    "error": "Internal Server Error",
    "message": "Found A Customer For Agent 8",
    "trace": "javax.persistence.EntityExistsException: Found A Customer For Agent 8\n\tat com.lambdaschool.crudyorders.services.AgentsServiceImpl.deleteUnassigned(AgentsServiceImpl.java:52)\n\tat com.lambdaschool.crudyorders.services.AgentsServiceImpl$$FastClassBySpringCGLIB$$b77ba84b.invoke(<generated>)\n\tat org.springframework.cglib.proxy.MethodProxy.invoke(MethodProxy.java:218)\n\tat org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.invokeJoinpoint(CglibAopProxy.java:769)\n\tat org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:163)\n\tat org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:747)\n\tat org.springframework.transaction.interceptor.TransactionAspectSupport.invokeWithinTransaction(TransactionAspectSupport.java:366)\n\tat org.springframework.transaction.interceptor.TransactionInterceptor.invoke(TransactionInterceptor.java:99)\n\tat org.springframework.aop.framework.ReflectiveMethodInvocation.proceed(ReflectiveMethodInvocation.java:186)\n\tat org.springframework.aop.framework.CglibAopProxy$CglibMethodInvocation.proceed(CglibAopProxy.java:747)\n\tat org.springframework.aop.framework.CglibAopProxy$DynamicAdvisedInterceptor.intercept(CglibAopProxy.java:689)\n\tat com.lambdaschool.crudyorders.services.AgentsServiceImpl$$EnhancerBySpringCGLIB$$a8c05b12.deleteUnassigned(<generated>)\n\tat com.lambdaschool.crudyorders.controllers.AgentController.deleteAgentById(AgentController.java:56)\n\tat java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)\n\tat java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)\n\tat java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)\n\tat java.base/java.lang.reflect.Method.invoke(Method.java:566)\n\tat org.springframework.web.method.support.InvocableHandlerMethod.doInvoke(InvocableHandlerMethod.java:190)\n\tat org.springframework.web.method.support.InvocableHandlerMethod.invokeForRequest(InvocableHandlerMethod.java:138)\n\tat org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod.invokeAndHandle(ServletInvocableHandlerMethod.java:106)\n\tat org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.invokeHandlerMethod(RequestMappingHandlerAdapter.java:888)\n\tat org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter.handleInternal(RequestMappingHandlerAdapter.java:793)\n\tat org.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapter.handle(AbstractHandlerMethodAdapter.java:87)\n\tat org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:1040)\n\tat org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:943)\n\tat org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:1006)\n\tat org.springframework.web.servlet.FrameworkServlet.doDelete(FrameworkServlet.java:931)\n\tat javax.servlet.http.HttpServlet.service(HttpServlet.java:666)\n\tat org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:883)\n\tat javax.servlet.http.HttpServlet.service(HttpServlet.java:741)\n\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:231)\n\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)\n\tat org.apache.tomcat.websocket.server.WsFilter.doFilter(WsFilter.java:53)\n\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)\n\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)\n\tat org.springframework.web.filter.RequestContextFilter.doFilterInternal(RequestContextFilter.java:100)\n\tat org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:119)\n\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)\n\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)\n\tat org.springframework.web.filter.FormContentFilter.doFilterInternal(FormContentFilter.java:93)\n\tat org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:119)\n\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)\n\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)\n\tat org.springframework.web.filter.CharacterEncodingFilter.doFilterInternal(CharacterEncodingFilter.java:201)\n\tat org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:119)\n\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:193)\n\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:166)\n\tat org.apache.catalina.core.StandardWrapperValve.invoke(StandardWrapperValve.java:202)\n\tat org.apache.catalina.core.StandardContextValve.invoke(StandardContextValve.java:96)\n\tat org.apache.catalina.authenticator.AuthenticatorBase.invoke(AuthenticatorBase.java:526)\n\tat org.apache.catalina.core.StandardHostValve.invoke(StandardHostValve.java:139)\n\tat org.apache.catalina.valves.ErrorReportValve.invoke(ErrorReportValve.java:92)\n\tat org.apache.catalina.core.StandardEngineValve.invoke(StandardEngineValve.java:74)\n\tat org.apache.catalina.connector.CoyoteAdapter.service(CoyoteAdapter.java:343)\n\tat org.apache.coyote.http11.Http11Processor.service(Http11Processor.java:367)\n\tat org.apache.coyote.AbstractProcessorLight.process(AbstractProcessorLight.java:65)\n\tat org.apache.coyote.AbstractProtocol$ConnectionHandler.process(AbstractProtocol.java:860)\n\tat org.apache.tomcat.util.net.NioEndpoint$SocketProcessor.doRun(NioEndpoint.java:1591)\n\tat org.apache.tomcat.util.net.SocketProcessorBase.run(SocketProcessorBase.java:49)\n\tat java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1128)\n\tat java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:628)\n\tat org.apache.tomcat.util.threads.TaskThread$WrappingRunnable.run(TaskThread.java:61)\n\tat java.base/java.lang.Thread.run(Thread.java:834)\n",
    "path": "/agents/unassigned/8"
}
```

</details>

<details>
<summary>DELETE http://localhost:2019/agents/unassigned/16</summary>

Output

```TEXT
No Body Data

Status OK
```

</details>
