package com.cdac.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="categories")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Category extends BaseEntity {

	@Column(name="category_name",length=25)
	private String categoryName;
	
	@OneToMany(mappedBy = "category",cascade = CascadeType.ALL, orphanRemoval = true )
	private List<Book> books=new ArrayList<>();
}
