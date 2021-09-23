package com.comp6442.route42;

import com.comp6442.route42.model.Post;
import com.comp6442.route42.repository.PostRepository;
import com.google.cloud.firestore.DocumentReference;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.ExecutionException;

@SpringBootTest
class Route42APITests {

//	@Test
//	void contextLoads() {
//
//		System.out.println("test");
//		DocumentReference ref = PostRepository.getInstance().getOne("0092827e-2961-48ed-8995-50adb9f47781");
//		try {
//			Post post = ref.get().get().toObject(Post.class);
//			System.out.println(post);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		} catch (ExecutionException e) {
//			e.printStackTrace();
//		}
//	}

}
