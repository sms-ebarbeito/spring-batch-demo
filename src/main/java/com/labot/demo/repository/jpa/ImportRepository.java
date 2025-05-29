package com.labot.demo.repository.jpa;

import com.labot.demo.entity.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImportRepository extends JpaRepository<Import, Long> {

    Page<Import> findAllByOrderByIdAsc(Pageable pageable);


}
