package com.authorization.admin.controller;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.authorization.admin.service.AdminPostService;
import com.authorization.common.messages.BaseResponse;
import com.authorization.common.utils.StatusValue;
import com.authorization.user.model.dto.BlogDTO;



@RestController
@RequestMapping(value = "/admin")
@Validated
public class AdminPostController {

	@Autowired
	private AdminPostService amAdminPostService;
	
	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping(value = "/pending/post")
	public ResponseEntity<List<BlogDTO>> findPendingPostByStatusAndPublish() {
		List<BlogDTO> list = amAdminPostService.findPostByStatusAndPublish(StatusValue.INACTIVE.getValue(), StatusValue.INACTIVE.getValue());
		return new ResponseEntity<List<BlogDTO>>(list, HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PatchMapping(value = "/approve")
	public ResponseEntity<BaseResponse> createUsersAccount(@Valid @RequestBody BlogDTO blogDTO) {
		BaseResponse response = amAdminPostService.approveAndPublishPendingPost(blogDTO);
		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping(value = "/approved/post")
	public ResponseEntity<List<BlogDTO>> findApprovedPostByStatusAndPublish() {
		List<BlogDTO> list = amAdminPostService.findPostByStatusAndPublish(StatusValue.ACTIVE.getValue(), StatusValue.ACTIVE.getValue());
		return new ResponseEntity<List<BlogDTO>>(list, HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping(value = "/delete{blogId}")
	public ResponseEntity<BaseResponse> deleteBlogPostByBlogId(@Valid @NotNull(message = "blogId must not be null") @PathVariable("blogId") Long blogId) {
		BaseResponse response = amAdminPostService.deleteBlogPostByBlogId(blogId);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
