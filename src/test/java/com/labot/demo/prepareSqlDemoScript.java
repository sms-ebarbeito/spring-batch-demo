package com.labot.demo;

import com.labot.demo.entity.Import;
import com.labot.demo.utils.PopulateDemo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class prepareSqlDemoScript {

    @Test
    public void prepareSqlDemoScript() {

        List<String> result = new ArrayList<>();
        long count = 0;
        while (count < 8000) {
            String fullName = PopulateDemo.generateRandomFullName();
            String username = PopulateDemo.generateRandomUsername(fullName);
            String email = PopulateDemo.generateRandomEmail(username);
            String phoneNumber = PopulateDemo.generateRandomPhoneNumber();
            Integer age = PopulateDemo.generateRandomAge();
            Import i = null;
            result.add("INSERT INTO import (id, age, email, last_name, name) VALUES (" + count + ", " + age + ", '" + email + "', '" + fullName.split(" ")[1] + "', '" + fullName.split(" ")[0] + "');");
            count++;
        }
        System.out.println(String.join("\n", result));
    }
}
