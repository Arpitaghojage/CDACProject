package com.cdac.entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="cart")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Cart extends BaseEntity{

	@ManyToOne
	@JoinColumn(name="user_id",nullable=false)
	private User user;
	
	@OneToMany(mappedBy = "cart",cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonIgnore
	private List<CartItem> cartItems = new ArrayList<>(); 
	
}
