package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.dto.SellerReportDTO;
import com.devsuperior.dsmeta.dto.SellerSummaryDTO;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.dsmeta.entities.Sale;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query("SELECT new com.devsuperior.dsmeta.dto.SellerReportDTO(obj.seller.id, obj.date, SUM(obj.amount), obj.seller.name) " +
            "FROM Sale obj " +
            "WHERE obj.date BETWEEN :minDate AND :maxDate " +
            "AND (:name IS NULL OR UPPER(obj.seller.name) LIKE UPPER(CONCAT('%', :name, '%'))) " +
            "GROUP BY obj.date, obj.seller.name")
    List<SellerReportDTO> salesReport(LocalDate minDate, LocalDate maxDate, String name);

    @Query("SELECT new com.devsuperior.dsmeta.dto.SellerSummaryDTO(obj.seller.name, SUM(obj.amount)) " +
            "FROM Sale obj " +
            "WHERE obj.date BETWEEN :minDate AND :maxDate " +
            "GROUP BY obj.seller.name")
    List<SellerSummaryDTO> salesSummary(LocalDate minDate, LocalDate maxDate);

}
