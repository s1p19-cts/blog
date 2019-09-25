package com.cts.blog.model;

import com.cts.blog.utils.JsonUtils;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "blog", schema = "demo")
@Getter
@Setter
@NoArgsConstructor // <--- THIS is it
public class Blog implements Serializable {
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;

	@Column(length = 500)
	private String title;

	@Column(length = 10000)
	private String content;

	public Blog(String title, String content) {
		this.setTitle(title);
		this.setContent(content);
	}

	public Blog(long id, String title, String content) {
		this.setId(id);
		this.setTitle(title);
		this.setContent(content);
	}

	@Override
	public String toString() {
		return "Blog{" +
				       "id=" + id +
				       ", title='" + title + '\'' +
				       ", content='" + content + '\'' +
				       '}';
	}

	public Blog clone() {
		return JsonUtils.fromJson(this.toJson(), Blog.class);
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 79 * hash + Objects.hashCode(this.id);
		hash = 79 * hash + Objects.hashCode(this.title);
		hash = 79 * hash + Objects.hashCode(this.content);
		return hash;
	}

	public String toJson() {
		return JsonUtils.toJson(this, this.getClass());
	}

}
