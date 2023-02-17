package payroll;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.print.attribute.standard.DateTimeAtCompleted;

import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;



// tag::constructor[]
@RestController
class TestController {

	Logger logger = LogManager.getLogger(TestController.class);
	
	@Value("${nextUri}")
	private String nextUri;
	
	@Value("${lastStop}")
	private boolean lastStop; 
	
	@Value("${apiName}")
	private String apiName; 
	
	@Value("${sleepDurationInSec}")
	private int sleepDurationInSec; 
	
	@GetMapping(produces=MediaType.APPLICATION_JSON_VALUE,path="/hello")
	public ResponseEntity<Object> hello() {
		String message = "Hello World: " +apiName  +" at: " +new Date().getTime();
		return createResponse(message);
	}
	
	
	@GetMapping(path="/fail")
	public ResponseEntity<Object>  fail() {
		String message = "expecting this to fail: " + (1/0) +apiName  +" at: " +new Date().getTime();
		return createResponse(message);
	}
	@GetMapping(path="/loop")
	public ResponseEntity<Object>  loop() throws InterruptedException {
		String message = "Looping: " +apiName  +" at: " +new Date().getTime();
		Thread.sleep(sleepDurationInSec *1000);
		return createResponse(message);
	}
	
	@GetMapping(produces=MediaType.APPLICATION_JSON_VALUE, value="/callNext")
	private ResponseEntity<Object>  getNext(){
		
		String uri = "http://localhost:8080/hello";
		String result =null;
		
		logger.info("property injected for nextUri: " +nextUri);
		
		/*
		String prop = System.getProperty("nextUri");
		System.out.println("property provided for nextUri: " +prop);
		
		String envProp = System.getenv("nextUri");
		if(envProp !=null)
			System.out.println(envProp);
		else
			System.out.println("No env property provided for nextUri");
		*/
		
		//if property file or env variable has a nextUri we will set it, otherwise use local call
		if(nextUri != null && lastStop==false) {
			uri = nextUri;
		
			RestTemplate restTemplate = new RestTemplate();
			result = restTemplate.getForObject(uri, String.class);
		}
		else 
			result = "No more APIs to call: " +apiName  +" at:" +new Date().getTime();
		logger.info(result);
		return createResponse(result);
	}
	
	private ResponseEntity<Object> createResponse(String message) {
		return ResponseEntity.status(HttpStatus.CREATED).body(
	            java.util.Collections.singletonMap("message", message));
	}
}
