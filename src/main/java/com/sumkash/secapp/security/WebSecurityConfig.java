package com.sumkash.secapp.security;

import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.boot.autoconfigure.security.oauth2.resource.AuthoritiesExtractor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;

import com.sumkash.secapp.AppProperties;
import com.sumkash.secapp.github.GithubClient;
import com.sumkash.secapp.github.GithubUser;
import com.sumkash.secapp.models.User;
import com.sumkash.secapp.repositories.UserRepository;

@Configuration
@EnableOAuth2Sso
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Override
    protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
			.antMatchers("/user/**").hasRole("USER")
			.and()
		.formLogin()
	        .loginPage("/login")
	        .defaultSuccessUrl("/user/welcome", true)
	        .permitAll()
	        .and()
        .logout()
        	.logoutUrl("/logout")
        	.permitAll()
        	.and()
        .csrf()
			.ignoringAntMatchers("/admin/h2-console/*")
			.and()
        .headers()
			.frameOptions()
			.sameOrigin();
		
    }
	
	@Bean
	public AuthoritiesExtractor authoritiesExtractor(AppProperties appProperties) {
		return map -> {
			String username = (String) map.get("login");
			if (appProperties.getSecurity().getAdmins().contains(username)) {
				return AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER,ROLE_ADMIN,ROLE_ACTUATOR");
			}
			else {
				return AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER");
			}
		};
	}
	
	@Bean
	public PrincipalExtractor principalExtractor(GithubClient githubClient, UserRepository userRepository) {
		return map -> {
			String githubLogin = (String) map.get("login");
			User user = userRepository.findByGithub(githubLogin);
			
			if (user == null) {
				
				// Get user from github api
				GithubUser githubUser = githubClient.getUser(githubLogin);
				
				user = new User();
				user.setEmail(githubUser.getEmail());
				user.setName(githubUser.getName());
				user.setGithub(githubLogin);
				user.setAvatarUrl(githubUser.getAvatar());
				
				// Save to db
				userRepository.save(user);
			}
			
			return user;
		};
	}
}
