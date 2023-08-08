package com.example.example.repository;

import com.example.example.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Optional;

@Repository
public interface ExampleRepository extends JpaRepository<Example, Long> {
    Optional<Example> findByIdx(Long idx);

    /*@Transactional
    @Modifying
    @Query("update tbl_example set car = :car, auto_bike = :auto_bike where idx = :idx")
    Example updateExample(@Param("car") String car, @Param("auto_bike") String auto_bike, @Param("idx") Long idx);*/
}
