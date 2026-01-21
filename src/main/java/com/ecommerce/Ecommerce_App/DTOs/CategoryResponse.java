package com.ecommerce.Ecommerce_App.DTOs;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CategoryResponse {
   private List<CategoryDTO> content;
}
