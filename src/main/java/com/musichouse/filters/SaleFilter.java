package com.musichouse.filters;

import com.musichouse.model.domain.Customer;
import com.musichouse.model.domain.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@NoArgsConstructor@AllArgsConstructor@Data
public class SaleFilter {
    private Optional<String> price;
    private Optional<Date> date;
}
