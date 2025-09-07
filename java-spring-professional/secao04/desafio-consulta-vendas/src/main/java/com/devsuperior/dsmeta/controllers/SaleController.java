package com.devsuperior.dsmeta.controllers;

import com.devsuperior.dsmeta.dto.SellerReportDTO;
import com.devsuperior.dsmeta.dto.SellerSummaryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.services.SaleService;

import java.util.List;

@RestController
@RequestMapping(value = "/sales")
public class SaleController {

    @Autowired
    private SaleService service;

    @GetMapping(value = "/{id}")
    public ResponseEntity<SaleMinDTO> findById(@PathVariable Long id) {
        SaleMinDTO dto = service.findById(id);
        return ResponseEntity.ok(dto);
    }

    @GetMapping(value = "/report")
    public ResponseEntity<List<SellerReportDTO>> getReport(
            @RequestParam(required = false) String minDate,
            @RequestParam(required = false) String maxDate,
            @RequestParam(required = false) String name
    ) {
        List<SellerReportDTO> list = service.salesReport(minDate, maxDate, name);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping(value = "/summary")
    public ResponseEntity<List<SellerSummaryDTO>> getSummary(
            @RequestParam(required = false) String minDate,
            @RequestParam(required = false) String maxDate
    ) {
        List<SellerSummaryDTO> list = service.salesSummary(minDate, maxDate);
        return ResponseEntity.ok().body(list);
    }
}
