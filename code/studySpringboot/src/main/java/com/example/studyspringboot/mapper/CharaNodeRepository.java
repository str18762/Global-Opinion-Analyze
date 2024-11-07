package com.example.studyspringboot.mapper;

import com.example.studyspringboot.entity.CharaNode;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface CharaNodeRepository extends Neo4jRepository<CharaNode, Long> {

    CharaNode findByName(String name);
}
