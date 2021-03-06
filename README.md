# Microservices-With-Docker-Compose-Precticee
This is all abou the docker compose with wicroservices architacture 


step 1 :													
		create Applications with given dependancy											
													
		1: EurekaServerApplication											
			dependancy : EurekaSreve,web Stater										
			stater Class:   _@EnableEurekaServer										
			properties: 										
						server.port=8761							
						spring.application.name=Eureka-Server							
						eureka.client.register-with-eureka=false							
						eureka.client.fetch-registry=false							
													
													
		2: ConfigServerApplication											
			dependancy: Config Server , web Stater										
			stater Class:   _@EnableConfigServer										
			properties:  										
						server.port=8888							
						spring.application.name=Config-Server							
						spring.cloud.config.server.git.uri=https://github.com/prasunpatidar94/MicroServiceCloudConfigTest.git							
						spring.cloud.config.server.git.username=<username>							
						spring.cloud.config.server.git.password=<password>							
						spring.cloud.config.server.git.default-label=<Branch name >							
													
													
													
		3: EurekaClientCompanyApplication											
			dependancy: Eureka Discovery Client,Config Client , Web Stater,openfeign(if you want intra communication)										
			and 										
			<dependency>										
				<groupId>org.springframework.cloud</groupId>									
				<artifactId>spring-cloud-starter-bootstrap</artifactId>									
			</dependency>										
			stater Class:   _@EnableEurekaClient 										
			properties: 										
						server.port=9001							
						spring.application.name=Company-Application							
						eureka.client.service-url.defaultZone=http://localhost:8761/eureka							
						eureka.instance.instance-id=${spring.application.name}: ${random.value}							
			controller: 										
						package in.pp.sun.controller;							
						import org.springframework.beans.factory.annotation.Value;							
						import org.springframework.web.bind.annotation.GetMapping;							
						import org.springframework.web.bind.annotation.RestController;							
													
						=  _@RestController							
						public class CompanyRest {							
						//							
							=  _@VALUE("${my.application.name}")						
							private String name;						
													
							=  _@GetMapping("name")						
							public String getName() {						
								return "name uis tehjb"+name;					
							}						
													
						}							
													
		Git: 											
			1: create one repository on git hub in public 										
			2: create one new file named "application.properties"										
			3: give key value in it which is same form all microservices										
													
		Execution Order:											
						1: Eureka Server							
						2: Config Server							
						3: All Microservices (one by one )							
													
													
													
		Problems: 											
				1: if we are modified key vale in config server account then changes will not reflect in our application									
					so for that we have to solution 								
					Solution:								
							1: Restart Config-Server and all microservices :						
								* it is recomended when more number of key values are modifies in Config-Server Account					
							2: Refresh Scope (Actuator)						
								changes: 					
									1: add new dependency "Actuator" in pom .xml of client application 				
									2: add new propertie in cliend aplication.properties file 				
										pro: management.endpoints.web.exposure.include=* (for all feature enable of the acutator)			
												management.endpoints.web.exposure.include=*	
									3:  puth   _@RefreshScope anotation in the client conroller class				
									4: then if you want to change key=value dynamacly so for that 				
										trigger one inbuild end point on actuator (repearted on changes)			
										endpoint url is :			
												http://localhost:9001/actuator/refresh	
													
									note: use when less nuber of properties are modified 				
													
													
		example code:											
													
					1: Eureka Server								
							stater class:						
								package in.pp.sun;					
													
								import org.springframework.boot.SpringApplication;					
								import org.springframework.boot.autoconfigure.SpringBootApplication;					
								import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;					
													
								=  _@SpringBootApplication					
								=  _@EnableEurekaServer					
								public class EurekaServerApplication {					
													
									public static void main(String[] args) {				
										SpringApplication.run(EurekaServerApplication.class, args);			
									}				
													
								}					
													
							properties:						
								server.port=8761					
								spring.application.name=Eureka-Server					
													
								eureka.client.register-with-eureka=false					
								eureka.client.fetch-registry=false					
													
													
					2: Config Server								
							Stater class:						
								package in.pp.sun;					
								import org.springframework.boot.SpringApplication;					
								import org.springframework.boot.autoconfigure.SpringBootApplication;					
								import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;					
													
								=  _@SpringBootApplication					
								=  _@EnableEurekaServer					
								public class EurekaServerApplication {					
													
									public static void main(String[] args) {				
										SpringApplication.run(EurekaServerApplication.class, args);			
									}				
													
								}					
													
							properties:						
								server.port=8888					
								spring.application.name=Config-Server					
								spring.cloud.config.server.git.uri=https://github.com/prasunpatidar94/MicroServiceCloudConfigTest.git					
								spring.cloud.config.server.git.username=prasunpatidar94  _@gmail.com					
								spring.cloud.config.server.git.password=P  _@$$w0rd.2021					
								spring.cloud.config.server.git.default-label=master					
													
													
													
													
					3: client Microservices 								
						stater class							
							package in.pp.sun;						
							import org.springframework.boot.SpringApplication;						
							import org.springframework.boot.autoconfigure.SpringBootApplication;						
							import org.springframework.cloud.netflix.eureka.EnableEurekaClient;						
													
							=  _@SpringBootApplication						
							=  _@EnableEurekaClient						
							public class CompanyClientApplication {						
													
								public static void main(String[] args) {					
									SpringApplication.run(CompanyClientApplication.class, args);				
								}					
													
							}						
													
													
							properties:						
								server.port=9001					
								spring.application.name=Company-Application					
								eureka.client.service-url.defaultZone=http://localhost:8761/eureka					
								# Active Actuator Refresh mode 					
								#management.endpoints.web.exposure.include=*					
								management.endpoints.web.exposure.include=*					
													
							controller:						
								package in.pp.sun.controller;					
								import org.springframework.beans.factory.annotation.Value;					
								import org.springframework.cloud.context.config.annotation.RefreshScope;					
								import org.springframework.web.bind.annotation.GetMapping;					
								import org.springframework.web.bind.annotation.RestController;					
													
								=  _@RestController					
								=  _@RefreshScope					
								public class CompanyRest {					
								//					
									=  _@VALUE("${my.application.name}")				
									private String name;				
													
									=  _@GetMapping("name")				
									public String getName() {				
										return "name uis tehjb" + name;			
									}				
													
								}					
													
													
=======================Change the defalt url of config server===========================													
													
										 			
													
	1. cahnge port number of config server application 												
		spring.server.port=9999  (by defalut 8888)											
													
	2 create bootstrap.properties and put the one propertie 												
		spring.cloud.config.uri=http://localhost:9999											
													
													
	conflution:												
				we know the spring application have to project one is maven perent project and one child project 									
				and according to the java concept if you want to gchange any method in perent the you need to override									
				the perent method.									
													
				same concept is here if you want top  chanange any propereties in spring boot the we need to create one propertie file in 									
				child project with same name *(bootstrap.propereties) and then give it same properties name 									
													
													
													
==========to enable the actuator============================================================================													
													
												to enable the actuator 	
													
		 add depenbecy :											
		 											
					Actoator								
													
													
		 enable actoator all services:											
		 											
						#to enable the actuator							
						management.endpoints.web.exposure.include=*  							
						#to enable the custom base path							
						#management.endpoints.web.base-path=/myComp							
													
						#to see app details of health							
						#management.endpoint.health.show-details=WHEN-AUTHORISED							
						#management.endpoint.health.show-details=NEVER							
						management.endpoint.health.show-details=ALWAYS							
													
						#TO enable or disable perticulat health endpoind is							
						management.endpoint.health.enabled=true							
						#management.endpoint.health.enabled=false							
						#management.endpoint.<endpoints name >.enabled=true/false							
													
						#management.endpoint.<endpoints name >.roles=ADMIN,CEO							
													
													
						#TO give the metadata of project							
						info.my.app.name=company application							
						info.my.app.developer=Prasun Patidar							
						info.my.app.client.name=SUNPRASUN							
						info.my.app.version=1.0							
													
											
													
=============Admin server for monitering												
													
	* WE know that if we want to check all application health and exra detailes the we need to go with actoator but if we are woring with microservices												
	  the we need to monitor all microserves at same time in thios case we need to go with another tool of spring.												
	* that name is Admin Server ->												
						in this we are using one new sepetare project   and monitor all microserves by ushing that project							
													
	-> creation steps : 												
					1. create AdminServer application 								
						Stater class: 							
									=  _@EnableAdminServer				
						depenbecy:							
									<dependency>				
										<groupId>de.codecentric</groupId>			
										<artifactId>spring-boot-admin-starter-server</artifactId>			
									</dependency>				
									<dependency>				
										<groupId>org.springframework.boot</groupId>			
										<artifactId>spring-boot-starter-actuator</artifactId>			
									</dependency>				
						propereties : 							
									server.port=9999				
									spring.application.name=Admin-Server-Application				
													
													
					2. Create one client Application = "CompanyApplicaton"								
													
						Stater class: 							
						depenbecy:							
									<dependency>				
										<groupId>de.codecentric</groupId>			
										<artifactId>spring-boot-admin-starter-client</artifactId>			
										<version>2.1.0</version>			
									</dependency>				
									<dependency>				
										<groupId>org.springframework.boot</groupId>			
										<artifactId>spring-boot-starter-actuator</artifactId>			
									</dependency>				
													
						propereties : 							
									server.port=9001				
									spring.application.name=Company-Application				
									eureka.instance.instance-id=${spring.application.name}: ${random.value}				
									#to enable the actuator				
									management.endpoints.web.exposure.include=*  				
									#to enable the custom base path				
									#management.endpoints.web.base-path=/myComp				
									#to see app details of health				
									#management.endpoint.health.show-details=WHEN-AUTHORISED				
									#management.endpoint.health.show-details=NEVER				
									management.endpoint.health.show-details=ALWAYS				
									#TO enable or disable perticulat health endpoind is				
									management.endpoint.health.enabled=true				
									#management.endpoint.health.enabled=false				
									#management.endpoint.<endpoints name >.enabled=true/false				
									#management.endpoint.<endpoints name >.roles=ADMIN,CEO				
									#TO give the metadata of project				
									info.my.app.name=company application				
									info.my.app.developer=Prasun Patidar				
									info.my.app.client.name=SUNPRASUN				
									info.my.app.version=1.0				
									#admin server registration				
									spring.boot.admin.client.url=http://localhost:9999				
													
													
					-> Execution oders: 								
								1. admin application					
								2. client Application (Company-Application)					
								3. browser url "http://localhost:9999"					
													
													
=========Sleuth And Zenkin tracking system=================================================================================													
													
Hear we are createing 3 application 													
		1. microservices-slueth-Zipking-1											
					* dependencys:								
							Sleuth,Zenkin client						
					*Application.properties:								
													
						server.port=8585							
						spring.application.name =Application-1							
						logging.file.name= C:/MyWorkSpace/Prectice_WorkSpace/Microservices/Sleuth-And-Zipkig-traking-log/ApplicationLog1.log							
													
					* configutarion class:								
										package sun.pp.in;			
													
										import brave.sampler.Sampler;			
										import org.springframework.context.annotation.Bean;			
										import org.springframework.context.annotation.Configuration;			
										import org.springframework.web.client.RestTemplate;			
													
										=  _@Configuration			
										public class AppConfig {			
													
											=  _@Bean		
											public RestTemplate tr() {		
												return new RestTemplate();	
											}		
													
											//collection trecs information from sleuth and sent to zkipkin server		
											=  _@Bean		
											public Sampler sampleObject() {		
												return Sampler.ALWAYS_SAMPLE;	
										//        return Sampler.NEVER_SAMPLE;			
											}		
													
										}			
													
													
		2. microservices-slueth-Zipking-2											
					* dependencys:								
							Sleuth,Zenkin client						
					Application.properties:								
													
						server.port=8586							
						spring.application.name =Application-2							
						logging.file.name= C:/MyWorkSpace/Prectice_WorkSpace/Microservices/Sleuth-And-Zipkig-traking-log/ApplicationLog2.log							
													
													
					* configutarion class:								
										package sun.pp.in;			
													
										import brave.sampler.Sampler;			
										import org.springframework.context.annotation.Bean;			
										import org.springframework.context.annotation.Configuration;			
										import org.springframework.web.client.RestTemplate;			
													
										=  _@Configuration			
										public class AppConfig {			
													
											=  _@Bean		
											public RestTemplate tr() {		
												return new RestTemplate();	
											}		
													
											//collection trecs information from sleuth and sent to zkipkin server		
											=  _@Bean		
											public Sampler sampleObject() {		
												return Sampler.ALWAYS_SAMPLE;	
										//        return Sampler.NEVER_SAMPLE;			
											}		
													
										}			
													
		3. microservices-slueth-Zipking-3											
					* dependencys:								
							Sleuth,Zenkin client						
					*Application.properties:								
													
						server.port=8587							
						spring.application.name =Application-3							
						logging.file.name= C:/MyWorkSpace/Prectice_WorkSpace/Microservices/Sleuth-And-Zipkig-traking-log/ApplicationLog1.log							
													
													
					* configutarion class:								
										package sun.pp.in;			
													
										import brave.sampler.Sampler;			
										import org.springframework.context.annotation.Bean;			
										import org.springframework.context.annotation.Configuration;			
													
										=  _@Configuration			
										public class AppConfig {			
													
													
													
											//collection trecs information from sleuth and sent to zkipkin server		
											=  _@Bean		
											public Sampler sampleObject() {		
												return Sampler.ALWAYS_SAMPLE;	
										//        return Sampler.NEVER_SAMPLE;			
											}		
													
										}			
													
													
		->required the zenkin server software:											
					zipkin-server-2.23.15-exec.jar								
					-> run cmd : java jar- <jar name with extencestion>								
													
													
		->start step:											
				1 run zipkin server jar 									
				2 run all micrrvices in order to microservicase architacture 									
				3 ping services by browser or web client									
													
													
========API GETWAY WITH SPRING CLOUD================================================================													
													

create 3 application to expplaiun the funcnality 

1=> eureka-application

	Stater Class :
				@EnableEurekaServer
				
	dependancy :
				eureka-server, web
	
	propereties : 
				server.port= 8761
				spring.application.name= eureka-server-application
				eureka.client.register-with-eureka= false
				eureka.client.fetch-registry= false

2=> microservices- application 

	Stater Class :
				@EnableDiscoveryClient

				
	dependancy :
				eureka-client, web, openfeign(if you waft to intracommunication with another microservices )
	
	propereties : 
				server.port=8585
				spring.application.name=company-application
				eureka.client.service-url.defaultZone=http://${HOST:localhost}:8761/eureka/
				#eureka.client.service-url.defaultZone=http://localhost:8761/eureka/

				#database
				spring.jpa.hibernate.ddl-auto=update
				spring.datasource.url=jdbc:mysql://${MYSQL_DATABASE:localhost}:3306/test
				#spring.datasource.url=jdbc:mysql://db:3306/test
				spring.datasource.username=prasun
				spring.datasource.password=test
				spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
				spring.jpa.show-sql:true

	for intracomminication  then insode aouther microservice  :
				package com.pp.in.vendorapplication.intracommunication;

				import com.pp.in.vendorapplication.entity.Product;
				import org.springframework.cloud.openfeign.FeignClient;
				import org.springframework.http.ResponseEntity;
				import org.springframework.web.bind.annotation.PathVariable;
				import org.springframework.web.bind.annotation.RequestBody;
				import org.springframework.web.bind.annotation.RequestMapping;
				import org.springframework.web.bind.annotation.RequestMethod;

				import java.util.Map;


				@FeignClient("company-application")
				public interface CompanyService {

					@RequestMapping(value = "/company/hi", method = RequestMethod.GET)
					String hi();

					@RequestMapping(value = "/company/save/product",method = RequestMethod.POST)
					 ResponseEntity<Map<String,Object>> saveProduct (@RequestBody Product product);

					@RequestMapping(value = "/company/get/product/{productId}",method = RequestMethod.GET)
					  ResponseEntity<Map<String,Object>> getProduct (@PathVariable("productId") String productId);

					@RequestMapping(value = "/company/get/products",method = RequestMethod.GET)
					ResponseEntity<Map<String,Object>> getProducts ();


				}


3=> Getway- application (zuul or spring cloud getway) hear i am using GETWAY OF SPRING CLOUDE

	Stater Class :
				@EnableDiscoveryClient
				
	dependancy :
				eureka-client, spring-cloud-starter-gateway
	
	propereties : 
				
				server.port=8080
				spring.application.name=zuul-application
				eureka.client.service-url.defaultZone=http://${HOST:localhost}:8761/eureka/
				#eureka.client.service-url.defaultZone=http://localhost:8761/eureka/

				#
				## zuul properties
				#zuul.routes.vendor.path=/ven/**
				#zuul.routes.vendor.service-id=vendor-application
				#zuul.routes.company.path=/com/**
				#zuul.routes.company.service-id=company-application
				
				# gateway confriguration
				spring.cloud.gateway.routes[0].id=vendor-application
				spring.cloud.gateway.routes[0].uri=lb://vendor-application
				spring.cloud.gateway.routes[0].predicates[0]=Path=/vendor/**


				spring.cloud.gateway.routes[1].id=company-application
				spring.cloud.gateway.routes[1].uri=lb://company-application
				spring.cloud.gateway.routes[1].predicates[0]=Path=/company/**

	Dockerfile.txt( this is all of the application to create docker images in jar or war )
	
		#FROM tomcat:9-jre11
		#EXPOSE 8686
		#RUN rm -rf /usr/local/tomcat/webapps/*
		#COPY target/*.war /usr/local/tomcat/webapps/ROOT.war
		#CMD ["catalina.sh","run"]

		FROM adoptopenjdk/openjdk11
		EXPOSE 8686
		ADD target/*.jar app.jar
		ENTRYPOINT ["sh","-c","java -jar app.jar"]

	docker-compose.yml  (this is for to  start the all application one by one on singale command )
		
		version: '3'

		services:
			db:
				image: mysql:8
				ports:
				  - "3306:3306"
				restart: always
				environment:
				  MYSQL_DATABASE: test
				  MYSQL_USER: prasun
				  MYSQL_PASSWORD: test
				  MYSQL_ROOT_PASSWORD: root
				volumes:
				  - db-data:/var/lib/mysql
				networks:
				  - two-boot-docker-compose
			eureka:
			   # image: spring-docker-compose:v0.1
				build:
					context: eureka-server-application
					dockerfile: Dockerfile.txt
				ports:
					- "8761:8761"
				restart: always
				networks:
					- two-boot-docker-compose
			company:
				#image: spring-docker-compose-second:v0.1
				build:
					context: company-application
					dockerfile: Dockerfile.txt
				depends_on:
					- eureka
					- db
				ports:
					- "8585:8585"
				restart: always
				environment:
					HOST: eureka
					MYSQL_DATABASE: db
				networks:
					- two-boot-docker-compose
			vendor:
				#image: spring-docker-compose-second:v0.1
				build:
					context: vendor-application
					dockerfile: Dockerfile.txt
				depends_on:
					- company
				ports:
					- "8686:8686"
				restart: always
				environment:
					HOST: eureka
				networks:
					- two-boot-docker-compose
			zuul:
				#image: spring-docker-compose-second:v0.1
				build:
					context: zuul-application
					dockerfile: Dockerfile.txt
				depends_on:
					- vendor
				ports:
					- "8080:8080"
				restart: always
				environment:
					HOST: eureka
				networks:
					- two-boot-docker-compose
		volumes:
			db-data:
			
		networks:
			two-boot-docker-compose:


==========Zipkin-with-slueth=

1> properties:
		spring.zipkin.base-url= http://${ZIPKIN_HOST:localhost}:9411/
		
2> Dependency :
		  
			<dependency>
			  <groupId>org.springframework.cloud</groupId>
			  <artifactId>spring-cloud-sleuth-zipkin</artifactId>
			</dependency>
			<dependency>
			  <groupId>org.springframework.cloud</groupId>
			  <artifactId>spring-cloud-starter-sleuth</artifactId>
			</dependency>

3 > Run Zipkin server on docker or local by docker images or jar file  

			on docker: docker run -d -p 9411:9411 openzipkin/zipkin   (*I am using docker image)
	