# Spring Batch Demo

This project is a simple demonstration of how to use [Spring Batch](https://spring.io/projects/spring-batch) to process and transform data efficiently in a Java application.

It is designed as a **learning resource** for developers who are new to Spring Batch. The example covers the basic components of a batch job (reader, processor, writer), shows how to use a multithreaded step, and stores everything in an in-memory H2 database.

---

## 🚀 What This Project Does

It simulates a batch process that:
1. Reads a list of randomly generated `Person` entities from a database.
2. Processes each person and transforms them into a `User` object.
3. Writes the processed users to another table.

> The transformation is simple on purpose — the goal is to focus on how Spring Batch works, not on the business logic itself.

---

## 🗂️ Technologies Used

- Java 21
- Spring Boot
- Spring Batch
- Spring Data JPA
- H2 Database (in-memory)
- Maven

---

## 🧩 Key Concepts Demonstrated

| Spring Batch Concept | Where it's Used |
|----------------------|-----------------|
| `ItemReader`         | Reads people from the `people_import` table |
| `ItemProcessor`      | Transforms a `Person` into a `User` |
| `ItemWriter`         | Persists the `User` into the `user` table |
| `Job` and `Step`     | Defined in the batch configuration |
| Multithreading       | Step is configured with a `TaskExecutor` |

---

## 🛠️ How to Run the Project

1. **Clone the repository**
   ```bash
   git clone https://github.com/sms-ebarbeito/spring-batch-demo.git
   cd spring-batch-demo
   ```

2. **Build and run**
   ```bash
   ./mvnw spring-boot:run
   ```

3. **Check the console**
   You will see logs of the batch job reading, processing, and writing data.

4. **Access the H2 Console (optional)**
- URL: `http://localhost:8080/h2-console`
- JDBC URL: `jdbc:h2:mem:testdb`
- Username: `sa`
- Password: *(leave empty)*

You can inspect the tables `people_import` and `user` to verify the processing result.

---

## 📦 Project Structure

```
src
├── main
│   ├─ java
│   │  └ com.kricom.labot.demo
│   │     ├── admin
│   │     │    └── controller/  → Batch Administration
│   │     ├── batch
│   │     │    ├── config/      → Batch job configuration
│   │     │    └── service/     → Support services for the Job and the BatchStarterService
│   │     ├── model/            → Entities: Person, User
│   │     ├── processor/        → PersonToUserProcessor
│   │     ├── repository/       → JPA repositories
│   │     └── SpringBatchDemoApplication.java
│   └── resources
│       ├── application.properties
│       └── data.sql            → Generates 8000 sample people
```

---

## 📌 Educational Tips

- The processor simulates a heavy transformation by sleeping 100ms per item.
- The batch step is executed with a multithreaded executor (4 threads).
- Try changing the chunk size or thread pool to observe the performance difference.

---

## 📚 Want to Go Further?

- Add error handling or skip logic using `SkipPolicy`.
- Write the output to a CSV instead of the database.
- Use `JobParameters` to make the job configurable at runtime.
- Try database paging with `JpaPagingItemReader`.

---

## 🤝 Contributing

This is a learning project — feel free to fork it, suggest improvements, or use it as a base for your own experiments.

---

## 🧑‍💻 Author

**Enrique Barbeito**  
[GitHub Profile](https://github.com/sms-ebarbeito)  
Argentina 🇦🇷

---

## 📝 License

This project is open source and free to use.