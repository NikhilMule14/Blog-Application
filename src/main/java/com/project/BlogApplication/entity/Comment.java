package com.project.BlogApplication.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="comments")
@Getter
@Setter
@Component
public class Comment {

	
	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	private int id;
	
	
	private String content;
	
	
	@ManyToOne
	private Post post;
}
