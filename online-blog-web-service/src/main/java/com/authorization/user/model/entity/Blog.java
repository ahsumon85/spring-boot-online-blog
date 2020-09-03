package com.authorization.user.model.entity;

import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.Max;

@Entity
@Table(name = "blog")
public class Blog {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "blog_id")
	private Long blogId;

//	@NotNull(message = "Content can not be null!")
	@Column(name = "content", columnDefinition = "TEXT"/* , nullable = false */)
	private String content;

//	@Min(value = 0, message = "active status field must be input 0 or 1")
	@Max(value = 1, message = "active status field must be input 0 or 1")
	@Column(name = "active_status"/* , nullable = false, length = 1 */)
	private Integer activeStatus;

//	@Min(value = 0, message = "active status field must be input 0 or 1")
	@Max(value = 1, message = "active status field must be input 0 or 1")
	@Column(name = "publish"/* , nullable = false, length = 1 */)
	private Integer publish;

	@Column(name = "create_date"/* , updatable = false, nullable = false */)
	private Date createDate = new Date();

	@ManyToOne (fetch = FetchType.LAZY) 
	@JoinColumn(name = "id", nullable = false)
	private Users users;

	public Long getBlogId() {
		return blogId;
	}

	public void setBlogId(Long blogId) {
		this.blogId = blogId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getActiveStatus() {
		return activeStatus;
	}

	public void setActiveStatus(Integer activeStatus) {
		this.activeStatus = activeStatus;
	}

	public Integer getPublish() {
		return publish;
	}

	public void setPublish(Integer publish) {
		this.publish = publish;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Users getUsers() {
		return users;
	}

	public void setUsers(Users users) {
		this.users = users;
	}

}
