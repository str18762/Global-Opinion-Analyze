package com.example.studyspringboot.mapper;

import com.example.studyspringboot.entity.TestPerson;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestPersonRepository extends Neo4jRepository<TestPerson, Long> {
}
