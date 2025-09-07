package com.devsuperior.dsmeta.services;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.devsuperior.dsmeta.dto.SellerReportDTO;
import com.devsuperior.dsmeta.dto.SellerSummaryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {

    @Autowired
    private SaleRepository repository;

    public SaleMinDTO findById(Long id) {
        Optional<Sale> result = repository.findById(id);
        Sale entity = result.get();
        return new SaleMinDTO(entity);
    }

    public List<SellerReportDTO> salesReport(String minDate, String maxDate, String name) {

        List<LocalDate> dates = calcDate(minDate, maxDate);

        return repository.salesReport(dates.get(0), dates.get(1), name);

    }

    public List<SellerSummaryDTO> salesSummary(String minDate, String maxDate) {

        List<LocalDate> dates = calcDate(minDate, maxDate);

        return repository.salesSummary(dates.get(0), dates.get(1));

    }

    public List<LocalDate> calcDate(String minDate, String maxDate) {

        List<LocalDate> dates = new ArrayList<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate today = LocalDate.now();

        LocalDate finalDate = (maxDate == null) ? today : LocalDate.parse(maxDate, formatter);
        LocalDate initialDate = (minDate == null) ? finalDate.minusYears(1) : LocalDate.parse(minDate, formatter);

        dates.add(initialDate);
        dates.add(finalDate);

        return dates;

    }

}
