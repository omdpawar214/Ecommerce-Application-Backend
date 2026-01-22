package com.ecommerce.Ecommerce_App.DTOs;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryResponse {
   private List<CategoryDTO> content;

   //including page metadata for frontend
   private Integer pageNumber;
   private Integer pageSize;
   private Long totalElements;
   private Integer totalPages;
   private boolean lastPage;
}
