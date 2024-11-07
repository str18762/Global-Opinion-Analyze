/**
 * 功能：
 * 作者：
 * 日期：2024/11/5 上午8:42
 */
package com.example.studyspringboot.entity;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;

import java.io.Serializable;

@Data
@Node(labels = "TestPerson")
public class TestPerson implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Property
    private String name;
}