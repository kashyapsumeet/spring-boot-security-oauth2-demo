package com.sumkash.secapp.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
@SuppressWarnings("serial")
public class User implements Serializable {

	@GeneratedValue
	@Id
	private Long id;

	private String email;

	private String name;

	private String twitter;

	private String github;

	private String avatarUrl;

	@Column
	@Lob
	private String bio;
	
	public User() {
	}

	public User(String email, String name) {
		this.email = email;
		this.name = name;
	}

	public Long getId() {
		return this.id;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTwitter() {
		return this.twitter;
	}

	public void setTwitter(String twitter) {
		this.twitter = twitter;
	}

	public String getGithub() {
		return this.github;
	}

	public void setGithub(String github) {
		this.github = github;
	}

	public String getAvatarUrl() {
		return avatarUrl;
	}

	public void setAvatarUrl(String avatarUrl) {
		this.avatarUrl = avatarUrl;
	}

	public String getBio() {
		return this.bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", name=" + name + ", twitter=" + twitter + ", github=" + github
				+ ", avatarUrl=" + avatarUrl + ", bio=" + bio + "]";
	}
}
