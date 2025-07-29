package com.cdac.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


@Entity
@Table(name="users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class User extends BaseEntity implements UserDetails {

	@Column(name="user_name", length=25)
	private String userName;
	
	@Column(name="full_name", length=25)
	private String fullName;
	
	@Column(length=25, unique=true)
	private String email;
	
	@Column(length=25, nullable=false)
	private String password;
	
	@Enumerated(EnumType.STRING)
	@Column(name="user_role")
	private UserRole role;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private List<Order> orders = new ArrayList<>();


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return List.of(new SimpleGrantedAuthority(this.userName));
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.email;
	}

}
