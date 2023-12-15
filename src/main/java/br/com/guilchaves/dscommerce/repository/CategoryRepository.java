package br.com.guilchaves.dscommerce.repository;

import br.com.guilchaves.dscommerce.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
