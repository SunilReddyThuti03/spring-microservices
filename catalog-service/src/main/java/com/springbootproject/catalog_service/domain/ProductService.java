package com.springbootproject.catalog_service.domain;

import com.springbootproject.catalog_service.ApplicationProperties;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
@Transactional
public class ProductService {
    private final ProductRepository productRepository;
    private final ApplicationProperties applicationProperties;

    ProductService(ProductRepository productRepository, ApplicationProperties applicationProperties){
        this.productRepository= productRepository;
        this.applicationProperties= applicationProperties;
    }

    public PagedResult<Product> getProducts(int pageNo){
        Sort sort = Sort.by("name").ascending();
        pageNo = pageNo <= 1 ? 0 : pageNo -1;
        Pageable pageable = PageRequest.of(pageNo, applicationProperties.pageSize(), sort);
        Page<Product> productsPage =productRepository.
                                findAll(pageable)
                                .map(ProductMapper::toProduct);

        return new PagedResult<>(
                productsPage.getContent(),
                productsPage.getTotalElements(),
                productsPage.getNumber() + 1,
                productsPage.getTotalPages(),
                productsPage.isFirst(),
                productsPage.isLast(),
                productsPage.hasNext(),
                productsPage.hasPrevious()
        );
    }
}
