package com.labot.demo.batch.service;


import com.labot.demo.batch.processor.DelegatedItemProcessorService;
import com.labot.demo.batch.reader.DelegatedItemReaderRepository;
import com.labot.demo.batch.writer.DelegatedItemWriterService;
import com.labot.demo.entity.Import;
import com.labot.demo.entity.Person;
import com.labot.demo.repository.jpa.ImportRepository;
import com.labot.demo.repository.jpa.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@StepScope
@Slf4j
public class BatchImportService implements  DelegatedItemReaderRepository<Import, Object>,
                                            DelegatedItemProcessorService<Import, Person>,
                                            DelegatedItemWriterService<Person>,
                                            StepExecutionListener {

    private final ImportRepository importRepository;
    private final PersonRepository personRepository;
    private final StepExecution stepExecution;
    private final Long jobExecutionId;


    public BatchImportService(@Autowired ImportRepository importacionRepository,
                              @Autowired PersonRepository personRepository,
                              // @Value("#{jobParameters['key_name']}") String value_variable,
                              @Value("#{stepExecution}") StepExecution stepExecution) {
        this.importRepository = importacionRepository;
        this.personRepository = personRepository;
        this.stepExecution = stepExecution;
        this.jobExecutionId = stepExecution.getJobExecution().getId();
    }

    /**
     * Item Reader
     * @param filter is not used here
     * @param pageable is the page reference for multi thread batch operation.
     * @return Page with the Section Entity
     * @throws InterruptedException if task was invoked to Stop
     */
    @Override
    public Page<Import> readPage(Object filter, Pageable pageable) throws InterruptedException {
        return importRepository.findAllByOrderByIdAsc(pageable);
    }

    /**
     * Item Processor
     * @param importEntity
     * @return a Person
     * @throws Exception
     */
    @Override
    public Person process(Import importEntity) throws Exception {
        Person person = new Person();
        person.setEmail(importEntity.getEmail());
        person.setFullName(importEntity.getLastName() + ", " + importEntity.getName());
        if (importEntity.getEmail() != null && importEntity.getEmail().contains("@")) {
            person.setUsername(importEntity.getEmail().split("@")[0]);
        }
        person.setBirthYear(LocalDate.now().getYear() -importEntity.getAge());

        try {
            Thread.sleep(100); //SLEEP 100ms to simulate a heavy task
        } catch (InterruptedException ignored) {}

        return person;
    }

    /**
     * Item Writer
     * @param person
     */
    @Override
    public void write(Person person) {
        try {
            personRepository.save(person);
            this.addCounterContext("processed");
        } catch (Exception e) {
            log.error("Error saving Person {}", person.getEmail(), e);
            this.addCounterContext("error");
        }
    }

    private void addCounterContext(String key) {
        ExecutionContext context = stepExecution.getJobExecution().getExecutionContext();
        synchronized (context) {
            context.put(key, context.getInt(key) + 1);
        }
    }

    @Override
    public void write(List<? extends Person> inputList) {
        throw new UnsupportedOperationException("Bulk write Not supported yet.");
    }

    @Override
    public void flushItems() {
        //Nop
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return ExitStatus.COMPLETED;
    }

}
