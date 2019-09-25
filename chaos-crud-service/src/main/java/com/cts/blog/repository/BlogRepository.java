package com.cts.blog.repository;

import com.cts.blog.model.Blog;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BlogRepository extends CrudRepository<Blog, Long> {

	List<Blog> findByTitleContainingOrContentContaining(String text, String textAgain);

}
