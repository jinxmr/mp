package com.jxm.jxmsecurity;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;
import lombok.experimental.Accessors;

public class Test {

	public static void main(String[] args) {
		Set<User> userSet = new HashSet<>();
		User user = new User();
		user.setId(1L)
				.setName("张三")
				.setAge(12)
				.setAddr("北京市昌平区")
				.setStatus(true);
		userSet.add(user);

		User user1 = new User();
		user1.setId(1L)
				.setName("张三")
				.setAge(12)
				.setAddr("北京市昌平区")
				.setStatus(true);

		User user2 = new User();
		user2.setId(1L)
				.setName("张三")
				.setAge(12)
				.setAddr("北京市昌平区")
				.setStatus(false);
		userSet.add(user2);
		System.out.println(userSet.size());
		userSet.forEach(u -> System.out.println(u.toString()));
	}
}

@Data
@Accessors(chain = true)
class User {
	private String name;

	private Long id;

	private Integer age;

	private String addr;

	private Boolean status;

	@Override
	public String toString() {
		return "User{" + "name='" + name + '\'' + ", id=" + id + ", age=" + age + ", addr='" + addr + '\'' + ", status=" + status + '}';
	}
}