package kitchenpos.menu.application;

import kitchenpos.common.domain.Price;
import kitchenpos.common.exception.CannotFindException;

import kitchenpos.common.exception.Message;
import kitchenpos.menu.dto.MenuProductRequest;
import kitchenpos.menu.dto.MenuRequest;
import kitchenpos.menugroup.MenuGroupRepository;
import kitchenpos.product.Product;
import kitchenpos.product.ProductRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MenuValidator {
    private final MenuGroupRepository menuGroupRepository;
    private final ProductRepository productRepository;

    MenuValidator(MenuGroupRepository menuGroupRepository, ProductRepository productRepository) {
        this.menuGroupRepository = menuGroupRepository;
        this.productRepository = productRepository;
    }

    public void validate(MenuRequest menuRequest) {
        validateMenuGroup(menuRequest);
        validate(menuRequest, getProducts(menuRequest));
    }

    private void validateMenuGroup(MenuRequest menuRequest) {
        menuGroupRepository.findById(menuRequest.getMenuGroupId())
                .orElseThrow(() -> new CannotFindException(Message.ERROR_MENUGROUP_NOT_FOUND));
    }

    private void validate(MenuRequest menuRequest, List<Product> products) {
        if (menuRequest.getMenuProductRequests().size() != products.size()) {
            throw new CannotFindException(Message.ERROR_PRODUCT_NOT_FOUND);
        }

        Price menuPrice = Price.valueOf(menuRequest.getPrice());
        Price productsTotalPrice = getProductsPrice(products);
        if (menuPrice.compareTo(productsTotalPrice) > 0) {
            throw new IllegalArgumentException(Message.ERROR_MENU_PRICE_CANNOT_BE_BIGGER_THAN_MENUPRODUCTS_TOTAL.showText());
        }
    }

    private Price getProductsPrice(List<Product> products) {
        Price sum = Price.valueOf(0);
        for (Product product : products) {
            sum = sum.add(product.getPrice());
        }
        return sum;
    }

    private List<Product> getProducts(MenuRequest menuRequest) {
        List<MenuProductRequest> menuProductRequests = menuRequest.getMenuProductRequests();
        List<Long> productIds = menuProductRequests.stream()
                .map(MenuProductRequest::getProductId)
                .collect(Collectors.toList());
        return productRepository.findAllById(productIds);
    }
}
