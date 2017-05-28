package rest;

import domain.Category;
import domain.Product;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/categories")
@Stateless
public class CategoryResources {

	@PersistenceContext
	EntityManager em;

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Category> getAll() {
		return em.createNamedQuery("category.all",Category.class).getResultList();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public Response Add(Category category) {
		em.persist(category);
		return Response.ok(category.getId()).build();
	}
	
	@GET
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response get(@PathParam("id") int id){
		Category result = em.createNamedQuery("category.id",Category.class)
				.setParameter("categoryId", id)
				.getSingleResult();
		if(result==null){
			return Response.status(404).build();
		}
		return Response.ok(result).build();
	}
	
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response update(@PathParam("id") int id, Category c){
		Category result = em.createNamedQuery("category.id",Category.class)
				.setParameter("categoryId", id)
				.getSingleResult();
		if(result==null){
			return Response.status(404).build();
		}
		result.setName(c.getName());
		em.persist(result);
		return Response.ok().build();
	}
	
	@DELETE
	@Path("/{id}")
	public Response delete(@PathParam("id") int id){
		Category result = em.createNamedQuery("category.id",Category.class)
				.setParameter("categoryId", id)
				.getSingleResult();
		if(result==null){
			return Response.status(404).build();
		}
		em.remove(result);
		return Response.ok().build();
	}
	
	@GET
	@Path("/{categoryId}/products")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Product> getProducts(@PathParam("categoryId") int categoryId){
		Category result = em.createNamedQuery("category.id",Category.class)
				.setParameter("categoryId", categoryId)
				.getSingleResult();
		if(result==null)
			return null;
		return result.getProducts();
	}
	
	@GET
	@Path("/products")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Product> getAllProducts(){
		return em.createNamedQuery("product.all",Product.class).getResultList();
	}

	@GET
	@Path("/products/price/{min}/{max}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Product> getProductsWithPrice(@PathParam("min") int min, @PathParam("max") int max){
		return em.createNamedQuery("product.price",Product.class)
				.setParameter("productMinPrice", min)
				.setParameter("productMaxPrice", max)
				.getResultList();
	}
	
	@POST
	@Path("/{id}/products")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addProduct(@PathParam("id") int categoryId, Product product){
		Category result = em.createNamedQuery("category.id",Category.class)
				.setParameter("categoryId", categoryId)
				.getSingleResult();
		if(result==null)
			return Response.status(404).build();
		result.getProducts().add(product);
		product.setCategory(result);
		em.persist(product);
		return Response.ok().build();
	}
	
}
