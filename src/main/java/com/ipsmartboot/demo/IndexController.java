package com.ipsmartboot.demo;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("")
public class IndexController {
	
	@GetMapping(path = "/students/get/{query}", produces = "application/json")
	public List<Students> studentsByCarrer( @PathVariable String query ) throws InterruptedException {
		
		FirebaseClient client = new FirebaseClient();
		
		List<String> careers = Arrays.asList( query.split("::") );
		
		List<Students> result = client.getStudentByCareers(careers);
		
		return result;
	}
	
	@GetMapping(path = "/slider/get/{query}", produces = "application/json")
	public List<String> photosByCarrer( @PathVariable String query ) throws InterruptedException {
		
		FirebaseClient client = new FirebaseClient();
		
		List<String> careers = Arrays.asList( query.split("::") );
		
		return client.getPhotosByCareers(careers);
	}
}
