/**
 * 功能：
 * 作者：
 * 日期：2024/11/5 上午9:03
 */
package com.example.studyspringboot.entity;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;

import java.util.List;

@Data
@Node(labels = "chara")
public class CharaNode {
    @Id
    @GeneratedValue
    private Long id;

    @Property
    private String name;

    @Property
    private String emotion;

    @Property
    private String followers;

    @Property
    private String chara_type;

    @Property
    private List<String> following;

}
