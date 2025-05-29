# 🌀 Spring Batch Demo

A simple and scalable Spring Boot project showcasing how to use **Spring Batch** with **JPA** and an **in-memory H2 database**. This demo reads, processes, and writes large volumes of data asynchronously using multi-threaded batch processing.

---

## ✅ Requirements

- **Java:** 21
- **Build Tool:** Maven

---

## 🚀 Getting Started

To run the application locally:

```bash
mvn clean spring-boot:run
``` 
## ⚙ How It Works
This demo sets up a Spring Batch Job with the typical structure:

* Reader: Reads data from the import table (8,000 randomly generated rows)
* Processor: Transforms each record into a Person entity and sleep (100ms to simulate a heavy load)
* Writer: Persists the processed Person records into the person table using a JPA repository

## 👥 Database Tables
* import – Contains test data representing raw people records
* person – Initially empty; gets populated by the batch job

The job uses:

Pagination: 10 items per chunk

Concurrency: 8 threads for parallel processing

## 🔁 Batch Execution
Trigger the batch import job via:

```
GET http://localhost:8080/api/v1/batch/execute
```

## 📥 REST API Endpoints
### Trigger Batch Job

```http
GET /api/v1/batch/execute
```

### Retrieve Raw Import Data
```http 
GET /api/v1/import
```

### Retrieve Processed Person Data
```http 
GET /api/v1/person
```

### Monitor the job
```http 
GET /api/v1/job/{Number:id}     --example 1
```
```json
{
    "jobId": 1,
    "jobName": "import_persons",
    "status": "STARTED",
    "startTime": "2025-05-29T14:49:06.059543",
    "endTime": null,
    "stepExecutions": [
        {
            "stepName": "chunk_import_step",
            "status": "STARTED",
            "startTime": "2025-05-29T14:49:06.289863",
            "endTime": null,
            "readCount": 880,
            "writeCount": 880,
            "commitCount": 88,
            "skipCount": 0,
            "rollbackCount": 0,
            "readSkipCount": 0,
            "processSkipCount": 0,
            "writeSkipCount": 0
        }
    ],
    "jobParameters": {
        "EXECUTION_TIME": "2025-05-29T17:49:06.034+00:00"
    },
    "progress": 11,
    "unitProgress": "%",
    "totalToProcess": 8000,
    "position": 880
}
```

### Get all jobs
```http 
GET /api/v1/jobs
```

### Get last job by name
```http 
GET /api/v1/job/last/{job_name}   --example import_persons
```



## 🛠 Test Data
The import table is preloaded with 8,000 entries via the PrepareSqlDemoScript class. These entries simulate realistic, randomized user data.

## 📚 Technologies Used
* Spring Boot
* Spring Batch
* Spring Data JPA
* H2 In-Memory Database
* Maven

## 📝 License
This project is for educational and demonstration purposes.

