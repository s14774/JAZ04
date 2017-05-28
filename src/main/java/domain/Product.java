package domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
@Entity
@NamedQueries({
	@NamedQuery(name="product.all",query = "SELECT p FROM Product p"),
	@NamedQuery(name="product.id", query = "FROM Product p WHERE p.id=:productId"),
	@NamedQuery(name="product.price", query = "FROM Product p WHERE p.price <= :productMaxPrice AND p.price >= :productMinPrice")
})
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private Float price;
	
	private List<Comment> comments = new ArrayList<>();	
	
	@ManyToOne
	private Category Category;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public Category getCategory() {
		return Category;
	}
	public void setCategory(Category Category) {
		this.Category = Category;
	}
	
	@XmlTransient
	@OneToMany(mappedBy = "product")
	public List<Comment> getComments() {
		return comments;
	}
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	
}
