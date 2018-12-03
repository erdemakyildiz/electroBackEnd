package com.electro.repository;

import com.electro.models.Headline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HeadlineRepository extends JpaRepository<Headline, Long>{

    Headline findFirstByNameEquals(String name);

}
