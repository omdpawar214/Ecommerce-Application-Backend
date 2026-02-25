package com.ecommerce.Ecommerce_App.DTOs.categoryDTOs;

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
