Repository just to demonstrate issue with using testcontainers in combination with Spring and RabbitMQ.

When you run the test, containers are started, tests pass. 
The problem is that containers are being stopped before Spring context is closed and as a result there is a bunch of errors in logs:

```
09:19:29.369 [main] INFO  com.example.demo.TestContainersIssueApplicationTests - Starting TestContainersIssueApplicationTests on Maciejs-MacBook-Pro.local with PID 52496 (started by maciej in /Users/maciej/Development/workspaces/battleground/testcontainers-issue)
09:19:29.375 [main] INFO  com.example.demo.TestContainersIssueApplicationTests - No active profile set, falling back to default profiles: default
09:19:29.464 [main] INFO  org.springframework.web.context.support.GenericWebApplicationContext - Refreshing org.springframework.web.context.support.GenericWebApplicationContext@47db5fa5: startup date [Thu Jun 29 09:19:29 CEST 2017]; root of context hierarchy
09:19:30.037 [background-preinit] INFO  org.hibernate.validator.internal.util.Version - HV000001: Hibernate Validator 5.3.5.Final
09:19:31.042 [main] INFO  org.springframework.context.support.PostProcessorRegistrationDelegate$BeanPostProcessorChecker - Bean 'org.springframework.amqp.rabbit.annotation.RabbitBootstrapConfiguration' of type [org.springframework.amqp.rabbit.annotation.RabbitBootstrapConfiguration$$EnhancerBySpringCGLIB$$60411f61] is not eligible for getting processed by all BeanPostProcessors (for example: not eligible for auto-proxying)
09:19:31.887 [main] INFO  org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter - Looking for @ControllerAdvice: org.springframework.web.context.support.GenericWebApplicationContext@47db5fa5: startup date [Thu Jun 29 09:19:29 CEST 2017]; root of context hierarchy
09:19:32.034 [main] INFO  org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping - Mapped "{[/error]}" onto public org.springframework.http.ResponseEntity<java.util.Map<java.lang.String, java.lang.Object>> org.springframework.boot.autoconfigure.web.BasicErrorController.error(javax.servlet.http.HttpServletRequest)
09:19:32.044 [main] INFO  org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping - Mapped "{[/error],produces=[text/html]}" onto public org.springframework.web.servlet.ModelAndView org.springframework.boot.autoconfigure.web.BasicErrorController.errorHtml(javax.servlet.http.HttpServletRequest,javax.servlet.http.HttpServletResponse)
09:19:32.131 [main] INFO  org.springframework.web.servlet.handler.SimpleUrlHandlerMapping - Mapped URL path [/webjars/**] onto handler of type [class org.springframework.web.servlet.resource.ResourceHttpRequestHandler]
09:19:32.133 [main] INFO  org.springframework.web.servlet.handler.SimpleUrlHandlerMapping - Mapped URL path [/**] onto handler of type [class org.springframework.web.servlet.resource.ResourceHttpRequestHandler]
09:19:32.202 [main] INFO  org.springframework.web.servlet.handler.SimpleUrlHandlerMapping - Mapped URL path [/**/favicon.ico] onto handler of type [class org.springframework.web.servlet.resource.ResourceHttpRequestHandler]
09:19:32.719 [main] INFO  org.springframework.context.support.DefaultLifecycleProcessor - Starting beans in phase -2147482648
09:19:32.720 [main] INFO  org.springframework.context.support.DefaultLifecycleProcessor - Starting beans in phase 2147483647
09:19:32.834 [SimpleAsyncTaskExecutor-1] INFO  org.springframework.amqp.rabbit.connection.CachingConnectionFactory - Created new connection: rabbitConnectionFactory#2c3dffff:0/SimpleConnection@29c2155d [delegate=amqp://guest@127.0.0.1:33715/, localPort= 55095]
09:19:32.839 [SimpleAsyncTaskExecutor-1] INFO  org.springframework.amqp.rabbit.core.RabbitAdmin - Auto-declaring a non-durable or auto-delete Exchange (my.exchange) durable:false, auto-delete:false. It will be deleted by the broker if it shuts down, and can be redeclared by closing and reopening the connection.
09:19:32.841 [SimpleAsyncTaskExecutor-1] INFO  org.springframework.amqp.rabbit.core.RabbitAdmin - Auto-declaring a non-durable, auto-delete, or exclusive Queue (my-queue) durable:false, auto-delete:false, exclusive:false. It will be redeclared if the broker stops and is restarted while the connection factory is alive, but all messages will be lost.
09:19:32.921 [main] INFO  com.example.demo.TestContainersIssueApplicationTests - Started TestContainersIssueApplicationTests in 3.902 seconds (JVM running for 17.8)
09:19:33.134 [AMQP Connection 127.0.0.1:33715] ERROR org.springframework.amqp.rabbit.connection.CachingConnectionFactory - Channel shutdown: connection error
09:19:33.916 [SimpleAsyncTaskExecutor-1] INFO  org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer - Restarting Consumer@23a5818e: tags=[{amq.ctag-sEwGgAiLcwmUcRCLfXKUrA=my-queue}], channel=Cached Rabbit Channel: AMQChannel(amqp://guest@127.0.0.1:33715/,1), conn: Proxy@340c41a Shared Rabbit Connection: SimpleConnection@29c2155d [delegate=amqp://guest@127.0.0.1:33715/, localPort= 55095], acknowledgeMode=AUTO local queue size=0
09:19:33.939 [SimpleAsyncTaskExecutor-2] ERROR org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer - Failed to check/redeclare auto-delete queue(s).
org.springframework.amqp.AmqpConnectException: java.net.ConnectException: Connection refused
        at org.springframework.amqp.rabbit.support.RabbitExceptionTranslator.convertRabbitAccessException(RabbitExceptionTranslator.java:62)
        at org.springframework.amqp.rabbit.connection.AbstractConnectionFactory.createBareConnection(AbstractConnectionFactory.java:368)
        at org.springframework.amqp.rabbit.connection.CachingConnectionFactory.createConnection(CachingConnectionFactory.java:565)
        at org.springframework.amqp.rabbit.connection.CachingConnectionFactory.createBareChannel(CachingConnectionFactory.java:518)
        at org.springframework.amqp.rabbit.connection.CachingConnectionFactory.getCachedChannelProxy(CachingConnectionFactory.java:492)
        at org.springframework.amqp.rabbit.connection.CachingConnectionFactory.getChannel(CachingConnectionFactory.java:485)
        at org.springframework.amqp.rabbit.connection.CachingConnectionFactory.access$1400(CachingConnectionFactory.java:98)
        at org.springframework.amqp.rabbit.connection.CachingConnectionFactory$ChannelCachingConnectionProxy.createChannel(CachingConnectionFactory.java:1129)
        at org.springframework.amqp.rabbit.core.RabbitTemplate.doExecute(RabbitTemplate.java:1435)
        at org.springframework.amqp.rabbit.core.RabbitTemplate.execute(RabbitTemplate.java:1411)
        at org.springframework.amqp.rabbit.core.RabbitTemplate.execute(RabbitTemplate.java:1387)
        at org.springframework.amqp.rabbit.core.RabbitAdmin.getQueueProperties(RabbitAdmin.java:336)
        at org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer.redeclareElementsIfNecessary(SimpleMessageListenerContainer.java:1136)
        at org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer$AsyncMessageProcessingConsumer.run(SimpleMessageListenerContainer.java:1387)
        at java.lang.Thread.run(Thread.java:745)
Caused by: java.net.ConnectException: Connection refused
        at java.net.PlainSocketImpl.socketConnect(Native Method)
        at java.net.AbstractPlainSocketImpl.doConnect(AbstractPlainSocketImpl.java:350)
        at java.net.AbstractPlainSocketImpl.connectToAddress(AbstractPlainSocketImpl.java:206)
        at java.net.AbstractPlainSocketImpl.connect(AbstractPlainSocketImpl.java:188)
        at java.net.SocksSocketImpl.connect(SocksSocketImpl.java:392)
        at java.net.Socket.connect(Socket.java:589)
        at com.rabbitmq.client.impl.SocketFrameHandlerFactory.create(SocketFrameHandlerFactory.java:50)
        at com.rabbitmq.client.ConnectionFactory.newConnection(ConnectionFactory.java:907)
        at com.rabbitmq.client.ConnectionFactory.newConnection(ConnectionFactory.java:859)
        at com.rabbitmq.client.ConnectionFactory.newConnection(ConnectionFactory.java:799)
        at org.springframework.amqp.rabbit.connection.AbstractConnectionFactory.createBareConnection(AbstractConnectionFactory.java:352)
        ... 13 common frames omitted
Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 18.054 sec - in com.example.demo.TestContainersIssueApplicationTests
09:19:34.166 [Thread-179] INFO  org.springframework.web.context.support.GenericWebApplicationContext - Closing org.springframework.web.context.support.GenericWebApplicationContext@47db5fa5: startup date [Thu Jun 29 09:19:29 CEST 2017]; root of context hierarchy
09:19:34.169 [Thread-179] INFO  org.springframework.context.support.DefaultLifecycleProcessor - Stopping beans in phase 2147483647
09:19:34.170 [Thread-179] INFO  org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer - Waiting for workers to finish.
09:19:34.171 [Thread-179] INFO  org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer - Successfully waited for workers to finish.
09:19:34.172 [Thread-179] INFO  org.springframework.context.support.DefaultLifecycleProcessor - Stopping beans in phase -2147482648

```

More listeners in the project, more exceptions in logs.