package com.dndserv.brody.gestioncomptable.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AdminDTO {
    private Long id;
    private Long userId;
    private Long enterpriseId;
}
