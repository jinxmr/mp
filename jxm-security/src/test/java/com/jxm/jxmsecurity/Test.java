package com.jxm.jxmsecurity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import lombok.Data;
import lombok.experimental.Accessors;

public class Test {

	public static void main(String[] args) throws Exception {
		String billMonthStr = "2020-06";
		String currentMonthStr = "2020-05";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		Date billMonth = sdf.parse(billMonthStr);
		Date currentMonth = sdf.parse(currentMonthStr);
		boolean flag = billMonth.after(currentMonth);
		System.out.println(flag);
	}
}
@Data
@Accessors(chain = true)
class User {
	private String name;

	private String addr;

	private Integer age;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		User user = (User) o;
		return Objects.equals(name, user.name) && Objects.equals(addr, user.addr) && Objects.equals(age, user.age);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name, addr, age);
	}
}
