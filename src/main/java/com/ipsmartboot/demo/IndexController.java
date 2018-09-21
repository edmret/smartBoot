package com.ipsmartboot.demo;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.Query;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

@RestController
@RequestMapping("")
public class IndexController {
	
	@GetMapping(produces = "application/json")
	public List<Students> hi() throws InterruptedException {
		
		final Semaphore semaphore = new Semaphore(0);
		
		List responselist = new ArrayList();
		
		//final String response = "";
		
		Firebase myFirebaseRef = new Firebase("https://python-sample-6de36.firebaseio.com/Students");
		
		
	
		//Query query = myFirebaseRef.child("Carrera");
		//myFirebaseRef.child("Carrera")
		
		
		 myFirebaseRef.addValueEventListener(new ValueEventListener() {
			  @Override
			  public void onDataChange(DataSnapshot snapshot) {
			    System.out.println("----------------Value-------------");
				System.out.println(snapshot.getValue());
				
				for (DataSnapshot messageSnapshot: snapshot.getChildren()) {
		            //String name = (String) messageSnapshot.child("Nombre").getValue();
		            //String car = (String) messageSnapshot.child("Carrera").getValue();		            
		            Students st = messageSnapshot.getValue(Students.class);
		            responselist.add(st);
		        }
				
			    //responselist.add(snapshot.getValue(Students.class));
			    //System.out.println(snapshot.getValue());  //prints "Do you have data? You'll love Firebase."
			    semaphore.release();
			  }
		  
			@Override
			public void onCancelled() {
				// TODO Auto-generated method stub
				System.out.println("Having firebase issues");
				semaphore.release();
			}
		});
		
		/*myFirebaseRef.addChildEventListener(new ChildEventListener() {

			@Override
			public void onCancelled() {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onChildAdded(DataSnapshot snapshot, String prevChildKey) {
				// TODO Auto-generated method stub
				System.out.println("----------------Value-------------");
				System.out.println(snapshot.getValue());
			    //responselist.add(snapshot.getValue(Students.class));
			    //System.out.println(snapshot.getValue());  //prints "Do you have data? You'll love Firebase."
			    semaphore.release();
			}

			@Override
			public void onChildChanged(DataSnapshot arg0, String arg1) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onChildMoved(DataSnapshot arg0, String arg1) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onChildRemoved(DataSnapshot arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});*/
		
		// wait until the onDataChange callback has released the semaphore
		semaphore.acquire();
		
		System.out.println("It is working");
		
		
		return responselist;
	}
}
