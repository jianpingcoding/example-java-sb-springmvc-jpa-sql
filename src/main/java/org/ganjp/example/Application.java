package org.ganjp.example;

import lombok.extern.slf4j.Slf4j;
import org.ganjp.example.bm.entity.Address;
import org.ganjp.example.bm.entity.Profile;
import org.ganjp.example.bm.entity.Role;
import org.ganjp.example.bm.entity.User;
import org.ganjp.example.bm.repository.UserRepository;
import org.ganjp.example.cm.entity.Comment;
import org.ganjp.example.cm.entity.Post;
import org.ganjp.example.cm.entity.Tag;
import org.ganjp.example.cm.repository.CommentRepository;
import org.ganjp.example.cm.repository.PostRepository;
import org.ganjp.example.common.enumeration.Gender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Slf4j
@SpringBootApplication
@EnableJpaAuditing
public class Application implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) {
		try {
			// Clean up database tables
//			profileRepository.deleteAllInBatch();
//			userRepository.deleteAllInBatch();

			//------------------------------ Init User data ------------------------------
			User user01 = new User();
			user01.setName("ganjianping");
			user01.setEmail("ganjpcoding@gmail.com");
			user01.setPassword("123434");

			Address address0101 = new Address();
			address0101.setCity("Singapore");
			address0101.setUser(user01);
			user01.getAddresses().add(address0101);

			Address address0102 = new Address();
			address0102.setCity("HongKong");
			address0102.setUser(user01);
			user01.getAddresses().add(address0102);

			Role role01 = new Role();
			role01.setName("admin");
			role01.getUsers().add(user01);
			user01.getRoles().add(role01);

			Profile profile01 = new Profile();
			Calendar dateOfBirth = Calendar.getInstance();
			dateOfBirth.set(1990, 2, 30);
			profile01.setGender(Gender.male);
			profile01.setBirthdate(dateOfBirth.getTime());
			profile01.setDescription("hello");

			File file = new File("jacky.jpg");
			byte[] avatar = Files.readAllBytes(file.toPath());
			profile01.setAvatar(avatar);

			profile01.setUser(user01);
			user01.setProfile(profile01);

			userRepository.save(user01);


			//------------------------------ Init Post data ------------------------------
			List<Post> posts = new ArrayList<Post>();
			List<Comment> comments = new ArrayList<Comment>();
			Tag tag1 = new Tag("Spring Boot");
			Tag tag2 = new Tag("Hibernate");
			for (int i=1; i<20; i++) {
				Post post = new Post();
				post.setTitle("Post " + i);
				post.setDescription("Description " + i);
				post.setContent("Content " + i);
				post.getTags().add(tag1);
				post.getTags().add(tag2);

				tag1.getPosts().add(post);
				tag2.getPosts().add(post);

				posts.add(post);

				for (int x=1; x<5; x++) {
					Comment comment = new Comment();
					comment.setPost(post);
					comment.setText("Text" + i + x);
					comments.add(comment);
				}
			}
			postRepository.saveAll(posts);
			commentRepository.saveAll(comments);
		} catch (Exception ex) {
			System.out.println(ex);
		}
	}


	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private CommentRepository commentRepository;
}
