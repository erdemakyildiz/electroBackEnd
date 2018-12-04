package com.electro.repository;

import com.electro.models.Comment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Erdem Akyıldız on 4.12.2018.
 */
@Repository
public interface CommentRepository extends CrudRepository<Comment, Long> {


}
