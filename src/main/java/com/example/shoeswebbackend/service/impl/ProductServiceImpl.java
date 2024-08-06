package com.example.shoeswebbackend.service.impl;

import com.example.shoeswebbackend.dto.ProductDTO;
import com.example.shoeswebbackend.dto.ProductVariantRequest;
import com.example.shoeswebbackend.dto.response.ProductResponse;
import com.example.shoeswebbackend.entity.Product;
import com.example.shoeswebbackend.entity.ProductImage;
import com.example.shoeswebbackend.entity.ProductVariant;
import com.example.shoeswebbackend.exception.AppException;
import com.example.shoeswebbackend.repository.*;
import com.example.shoeswebbackend.service.FirebaseService;
import com.example.shoeswebbackend.service.ProductService;
import com.example.shoeswebbackend.utils.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductImageRepository productImageRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private BrandRepository brandRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ProductSizeRepository productSizeRepository;

    @Autowired
    private ProductVariantRepository productVariantRepository;

    @Autowired
    private FirebaseService firebaseService;

    @Autowired
    private Mapper mapper;

    @Override
    public List<ProductResponse> getAllProducts() {
        List<ProductResponse> productResponseList = new ArrayList<>();
        productRepository.findAll().forEach(product -> {
            ProductResponse productResponse = mapper.convertProductToProductResponse(product);
            productResponse.setImageUrls(product.getProductImages().stream().map(ProductImage::getImage_url).collect(Collectors.toList()));
            productResponse.setReviews(reviewRepository.getReviewsByProductId(product.getId()).stream().map(mapper::convertReviewToReviewResponse).collect(Collectors.toList()));
            productResponse.setVariants(productVariantRepository.getProductVariantsByProductId(product.getId()));
            productResponseList.add(productResponse);
        });
        return productResponseList;
    }

    @Override
    public List<ProductResponse> getAllProducts(List<String> brands, List<String> categories, List<String> sizes, float minPrice, float maxPrice, String keyword) {
        List<Product> products = productRepository.findAllWithFilters(brands, categories, sizes, minPrice, maxPrice, keyword);

        List<ProductResponse> productResponseList = new ArrayList<>();
        products.forEach(product -> {
            ProductResponse productResponse = mapper.convertProductToProductResponse(product);
            productResponse.setImageUrls(product.getProductImages().stream().map(ProductImage::getImage_url).collect(Collectors.toList()));
            productResponse.setReviews(reviewRepository.getReviewsByProductId(product.getId()).stream().map(mapper::convertReviewToReviewResponse).collect(Collectors.toList()));
            productResponse.setVariants(productVariantRepository.getProductVariantsByProductId(product.getId()));
            productResponseList.add(productResponse);
        });

        return productResponseList;
    }

    @Override
    public List<ProductResponse> getProductsPopular() {
        List<Product> products = productRepository.findTopSellingProducts();

        List<ProductResponse> productResponseList = new ArrayList<>();
        products.forEach(product -> {
            ProductResponse productResponse = mapper.convertProductToProductResponse(product);
            productResponse.setImageUrls(product.getProductImages().stream().map(ProductImage::getImage_url).collect(Collectors.toList()));
            productResponse.setReviews(reviewRepository.getReviewsByProductId(product.getId()).stream().map(mapper::convertReviewToReviewResponse).collect(Collectors.toList()));
            productResponse.setVariants(productVariantRepository.getProductVariantsByProductId(product.getId()));
            productResponseList.add(productResponse);
        });

        return productResponseList;
    }

    @Override
    public ProductResponse getProductById(long id) throws AppException {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) {
            throw new AppException(404, "Product not found");
        }
        ProductResponse productResponse = mapper.convertProductToProductResponse(product);
        productResponse.setImageUrls(product.getProductImages().stream()
                .map(ProductImage::getImage_url)
                .collect(Collectors.toList()));
        productResponse.setReviews(product.getReviews().stream().map(mapper::convertReviewToReviewResponse).collect(Collectors.toList()));
        return productResponse;
    }

    @Override
    public ProductResponse getProductByProductCode(String productCode) throws AppException {
        Product product = productRepository.findByProductCode(productCode);
        if (product == null) {
            throw new AppException(404, "Product not found");
        }
        ProductResponse productResponse = mapper.convertProductToProductResponse(product);
        productResponse.setImageUrls(product.getProductImages().stream()
                .map(ProductImage::getImage_url)
                .collect(Collectors.toList()));
        productResponse.setReviews(reviewRepository.getReviewsByProductId(product.getId()).stream().map(mapper::convertReviewToReviewResponse).collect(Collectors.toList()));
        productResponse.setVariants(productVariantRepository.getProductVariantsByProductId(product.getId()));
        return productResponse;
    }

    @Override
    public void createProduct(ProductDTO productDTO, MultipartFile[] images) throws AppException {
        Product product = mapper.convertDTOToProduct(productDTO);
        if (productRepository.findByProductCode(productDTO.getProductCode()) != null) {
            throw new AppException(409, "Product already exists");
        }
        System.out.println(productDTO);
        productRepository.save(product);
        if(!productDTO.getVariants().isEmpty()) {
            for(ProductVariantRequest variant: productDTO.getVariants()) {
                ProductVariant productVariant = new ProductVariant();
                productVariant.setProduct(product);
                productVariant.setProductSize(productSizeRepository.findById(variant.getSizeId()).orElse(null));
                productVariant.setStock_quantity(variant.getQuantity());
                productVariantRepository.save(productVariant);
            }
        }
        for (MultipartFile image : images) {
            String url = firebaseService.upload(image);
            ProductImage productImage = new ProductImage();
            productImage.setImage_url(url);
            productImage.setProduct(product);
            productImageRepository.save(productImage);
        }
    }

    @Override
    public void updateProduct(ProductDTO productDTO, MultipartFile[] images, String code) throws AppException {
        Product product = productRepository.findByProductCode(code);
        if (product == null) {
            throw new AppException(404, "Product not found");
        }
        if (!productDTO.getProductCode().equals(code) && productRepository.findByProductCode(productDTO.getProductCode()) != null) {
            throw new AppException(409, "Product code already exists");
        }
        System.out.println(productDTO);
        product.setProductCode(productDTO.getProductCode());
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setStock_quantity(productDTO.getStock_quantity());
        product.setSold_quantity(productDTO.getSold_quantity());
        product.setCategory(categoryRepository.findById(productDTO.getCategoryId()).orElse(null));
        product.setBrand(brandRepository.findById(productDTO.getBrandId()).orElse(null));

        List<ProductImage> existingImages = product.getProductImages();
        List<String> existImageUrls = productDTO.getExistImages();

        List<ProductImage> imagesToRemove = existingImages.stream()
                .filter(image -> !existImageUrls.contains(image.getImage_url()))
                .toList();

        existingImages.removeAll(imagesToRemove);

        for (ProductImage imageToRemove : imagesToRemove) {
            productImageRepository.delete(imageToRemove);
            firebaseService.delete(imageToRemove.getImage_url());
        }

        product.setProductImages(existingImages);

        if(!productDTO.getVariants().isEmpty()) {
            for(ProductVariantRequest variant: productDTO.getVariants()) {
                // update
                if(variant.getId() != null) {
                    ProductVariant productVariant = productVariantRepository.findById(variant.getId()).orElse(null);
                    if(productVariant != null) {
                        productVariant.setProductSize(productSizeRepository.findById(variant.getSizeId()).orElse(null));
                        productVariant.setStock_quantity(variant.getQuantity());
                        productVariantRepository.save(productVariant);}
                }else { //create
                    ProductVariant productVariant = new ProductVariant();
                    productVariant.setProduct(product);
                    productVariant.setProductSize(productSizeRepository.findById(variant.getSizeId()).orElse(null));
                    productVariant.setStock_quantity(variant.getQuantity());
                    productVariantRepository.save(productVariant);
                }
            }
        }

        if (images != null) {
            for (MultipartFile image : images) {
                String url = firebaseService.upload(image);
                ProductImage productImage = new ProductImage();
                productImage.setImage_url(url);
                productImage.setProduct(product);
                productImageRepository.save(productImage);
            }
        }

        productRepository.save(product);
    }

    @Override
    public void deleteProduct(long id) {
        productRepository.deleteById(id);
    }

    @Override
    public List<ProductResponse> getProductsByCategory(String name) {
        List<ProductResponse> ds = new ArrayList<>();
        productRepository.findByCategoryName(name).forEach(product -> {
            ProductResponse productResponse = mapper.convertProductToProductResponse(product);
            productResponse.setImageUrls(product.getProductImages().stream()
                    .map(ProductImage::getImage_url)
                    .collect(Collectors.toList()));
            productResponse.setReviews(reviewRepository.getReviewsByProductId(product.getId()).stream().map(mapper::convertReviewToReviewResponse).collect(Collectors.toList()));
            ds.add(productResponse);
        });
        return ds;
    }

    @Override
    public List<ProductResponse> getProductsByBrand(String brand) {
        List<ProductResponse> ds = new ArrayList<>();
        productRepository.findByBrandName(brand).forEach(product -> {
            ProductResponse productResponse = mapper.convertProductToProductResponse(product);
            productResponse.setImageUrls(product.getProductImages().stream()
                    .map(ProductImage::getImage_url)
                    .collect(Collectors.toList()));
            productResponse.setReviews(reviewRepository.getReviewsByProductId(product.getId()).stream().map(mapper::convertReviewToReviewResponse).collect(Collectors.toList()));
            ds.add(productResponse);
        });
        return ds;
    }
}
