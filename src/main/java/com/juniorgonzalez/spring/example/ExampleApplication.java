package com.juniorgonzalez.spring.example;

import com.juniorgonzalez.spring.example.bean.MyBean;
import com.juniorgonzalez.spring.example.bean.MyBeanWithDependency;
import com.juniorgonzalez.spring.example.bean.MyBeanWithProperties;
import com.juniorgonzalez.spring.example.component.ComponentDependency;
import com.juniorgonzalez.spring.example.entity.User;
import com.juniorgonzalez.spring.example.pojo.UserPojo;
import com.juniorgonzalez.spring.example.repository.UserRepository;
import com.juniorgonzalez.spring.example.service.UserService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class ExampleApplication implements CommandLineRunner {

	private final Log LOGGER = LogFactory.getLog(ExampleApplication.class);

	private ComponentDependency componentDependency;
	private MyBean myBean;
	private MyBeanWithDependency myBeanWithDependency;
	private MyBeanWithProperties myBeanWithProperties;
	private UserPojo userPojo;
	private UserRepository userRepository;
	private UserService userService;

	public ExampleApplication(@Qualifier("componentTwoImplement") ComponentDependency componentDependency,
	MyBean myBean, MyBeanWithDependency myBeanWithDependency, MyBeanWithProperties myBeanWithProperties,
							  UserPojo userPojo, UserRepository userRepository, UserService userService) {

		this.componentDependency = componentDependency;
		this.myBean = myBean;
		this.myBeanWithDependency = myBeanWithDependency;
		this.myBeanWithProperties = myBeanWithProperties;
		this.userPojo = userPojo;
		this.userRepository = userRepository;
		this.userService = userService;
	}

	public static void main(String[] args) {
		SpringApplication.run(ExampleApplication.class, args);
	}


	@java.lang.Override
	public void run(java.lang.String... args) throws Exception {
		componentDependency.greet();
		myBean.print();
		myBeanWithDependency.printWithDependency();
		System.out.println(myBeanWithProperties.function());
		System.out.println(userPojo.getEmail()+"-"+userPojo.getPassword());
		LOGGER.error("This is on error");
		saveUserInDataBase();

		getInformationJpqlFromUser();

		saveWithErrorTransactional();
	}

	private void getInformationJpqlFromUser() {
		LOGGER.info("get user from  findByEmail method" +
				userRepository.findByUserEmail("noexiste@domain.com")
						.orElseThrow(() -> new RuntimeException("Unknown user")));

		userRepository.findAndSort("user", Sort.by("id").descending())
				.stream()
				.forEach(user -> LOGGER.info("Get user from sort method"));

		userRepository.findByName("Jhon")
				.stream()
				.forEach(user -> LOGGER.info("Get user from query method " + user));

		LOGGER.info("get user from query method findByEmailAndName : "
				+ userRepository.findByEmailAndName("willy@domain.com", "William")
				.orElseThrow(() -> new RuntimeException("Unknown user")));

		userRepository.findByNameLike("%J%")
				.stream()
				.forEach(user -> LOGGER.info("User findByNamelike " + user));

		userRepository.findByNameOrEmail("user10", null)
				.stream()
				.forEach(user -> LOGGER.info("User findByNameOrEmail " + user));

		userRepository.findByBirthDateBetween(LocalDate.of(2021, 3, 12), LocalDate.of(2021, 3, 12)
				).stream()
				.forEach(user -> LOGGER.info("User between interval " + user));

		userRepository.findByNameContainingOrderByIdDesc("user")
				.stream()
				.forEach(user -> LOGGER.info("User find with like and order " + user));

		LOGGER.info("User from parameter named is:" + userRepository.getAllByBirthDateAndEmail(LocalDate.of(2021,3,12), "jhon@domain.com")
				.orElseThrow(() -> new RuntimeException("Unknowk User")));

	}

	private void saveUserInDataBase() {
		User user1 = new User("John", "jhon@domain.com", LocalDate.of(2022,11,12));
		User user2 = new User("Willian", "willy@domain.com", LocalDate.of(2022,12,1));
		User user3 = new User("Loira", "loira@domain.com", LocalDate.of(2022,12,1));
		User user4 = new User("Dayane", "dayane@domain.com", LocalDate.of(2022,12,2));
		User user5 = new User("Fernanda", "fernanda@domain.com", LocalDate.of(2022,12,3));
		User user6 = new User("Yessica", "yessica@domain.com", LocalDate.of(2022,12,4));
		User user7 = new User("Gabril", "gabriel@domain.com", LocalDate.of(2022,12,4));
		User user8 = new User("Zamu", "zamu@domain.com", LocalDate.of(2022,12,4));
		User user9 = new User("Kaka", "kaka@domain.com", LocalDate.of(2022,12,4));
		User user10 = new User("Antoni", "antoni@domain.com", LocalDate.of(2022,12,4));

		List<User> list = Arrays.asList(user1,user2,user3, user4, user5, user6, user7, user8, user9, user10);

		list.stream().forEach(userRepository::save);

	}

	private void saveWithErrorTransactional() {

		User user1 = new User("Test1Transactional1", "Test1Transactional1@domain.com", LocalDate.of(2022,11,12));
		User user2 = new User("Test2Transactional2", "Test2Transactional2@domain.com", LocalDate.of(2022,12,1));
		User user3 = new User("Test3Transactional3", "Test3Transactional3@domain.com", LocalDate.of(2022,12,1));
		User user4 = new User("Test4Transactional4", "Test4Transactional4@domain.com", LocalDate.of(2022,12,2));

		List<User> users = Arrays.asList(user1,user2,user3, user4);

		try {

			userService.saveTransactional(users);

		} catch (Exception e) {
			LOGGER.error("its a error from transactional method");
		}



		userService.getAllUsers().stream()
				.forEach(user -> LOGGER.info("User from transactional method " + user));

	}

}
