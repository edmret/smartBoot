package com.ipsmartboot.demo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

public class FirebaseClient {

	public List<Students> getStudentByCareer(String career) throws InterruptedException {
		
		final Semaphore semaphore = new Semaphore(0);
		
		List<Students> responselist = new ArrayList();

		
		Firebase myFirebaseRef = new Firebase("https://python-sample-6de36.firebaseio.com/Students");
	
		Query query = myFirebaseRef.orderByChild("Carrera").equalTo(career);
		
		query.addValueEventListener(new ValueEventListener() {
			  @Override
			  public void onDataChange(DataSnapshot snapshot) {
				  
				for (DataSnapshot studentSnapshot: snapshot.getChildren()) {
					
					
					if( studentSnapshot.child("Fotos").exists() ) {
					
			            Students student = new Students() {{
			            	Nombre = (String) studentSnapshot.child("Nombre").getValue();
			            	Carrera = (String) studentSnapshot.child("Carrera").getValue();
			            	Matricula = (String) studentSnapshot.child("Matricula").getValue();
			            }};
			            
			            List<String> photos = new ArrayList();
			            
			            //Gets the Photos
			            for (DataSnapshot photoSnapshot: studentSnapshot.child("Fotos").getChildren()) {
			            	photos.add( (String) photoSnapshot.getValue() );
			            }
			            
			            student.Photos = photos;
			            //Students st = studentSnapshot.getValue(Students.class);
			            responselist.add(student);
					}
		        }
				
			    semaphore.release();
			  }

			@Override
			public void onCancelled(FirebaseError error) {
				// TODO Auto-generated method stub
				System.out.println("Having firebase issues");
				semaphore.release();
				
			}
		});
		
		
		// wait until the onDataChange callback has released the semaphore
		semaphore.acquire();
		
		System.out.println("It is working");
		
		
		return responselist;
	}
	
	public List<Students> getStudentByCareers(List<String> careers){
		List<Students> responselist = new ArrayList();
		
		careers.forEach((career)-> {
			try {
				responselist.addAll( getStudentByCareer(career) );
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} );
		
		return responselist;
	}
	
	public List<String> getPhotosByCareers(List<String> careers){
		
		List<Students> students = getStudentByCareers(careers);
		
		List<String> photos = new ArrayList();
		
		students.forEach( (student)-> photos.addAll(student.Photos) );
		
		Slideshow ld = new Slideshow();
		
		try {
			Slideshow.saveImage(photos, "./photos/");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Runtime rt = Runtime.getRuntime();
		try {
			Process pr = rt.exec("ffmpeg -f concat -r .5 -safe 0 -i photos/input.txt -vsync vfr -pix_fmt yuv420p photos/output.mp4");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
			e.printStackTrace();
		}
		
		return photos;
	}
}
