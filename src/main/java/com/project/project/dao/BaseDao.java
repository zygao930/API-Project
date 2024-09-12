package com.project.project.dao;

import com.project.project.entity.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@NoRepositoryBean
public interface BaseDao<T extends BaseEntity, I extends Serializable>
        extends JpaRepository<T, I>, JpaSpecificationExecutor<T> {
}
