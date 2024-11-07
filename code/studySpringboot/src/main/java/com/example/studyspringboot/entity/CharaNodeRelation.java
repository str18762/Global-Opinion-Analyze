/**
 * 功能：
 * 作者：
 * 日期：2024/11/5 上午9:21
 */
package com.example.studyspringboot.entity;

import lombok.Data;
import org.springframework.data.neo4j.core.schema.*;

import java.io.Serializable;
import java.util.List;

@Data
@RelationshipProperties
public class CharaNodeRelation implements Serializable {
    @RelationshipId
    private Long id;

    @TargetNode
    private CharaNode chara;


}
