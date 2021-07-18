package kitchenpos.menu.application;

import kitchenpos.common.domain.Price;
import kitchenpos.common.domain.Quantity;
import kitchenpos.menu.Menu;
import kitchenpos.menu.MenuProduct;
import kitchenpos.menu.MenuProductRepository;
import kitchenpos.menu.MenuRepository;
import kitchenpos.menu.dto.MenuProductRequest;
import kitchenpos.menu.dto.MenuProductResponse;
import kitchenpos.menu.dto.MenuRequest;
import kitchenpos.menu.dto.MenuResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MenuService {
    private final MenuRepository menuRepository;
    private final MenuProductRepository menuProductRepository;
    private final MenuValidator menuValidator;

    public MenuService(final MenuRepository menuRepository, final MenuProductRepository menuProductRepository, final MenuValidator menuValidator) {
        this.menuRepository = menuRepository;
        this.menuProductRepository = menuProductRepository;
        this.menuValidator = menuValidator;
    }

    public MenuResponse create(final MenuRequest menuRequest) {
        menuValidator.validate(menuRequest);
        Menu savedMenu = menuRepository.save(toMenu(menuRequest));
        List<MenuProduct> menuProducts = toMenuProducts(menuRequest.getMenuProductRequests(), savedMenu);
        menuProductRepository.saveAll(menuProducts);
        return new MenuResponse(savedMenu.getId(),
                savedMenu.getName(),
                savedMenu.getPrice().value(),
                savedMenu.getMenuGroupId(),
                toMenuProductResponses(menuProducts));
    }

    private List<MenuProduct> toMenuProducts(List<MenuProductRequest> menuProductRequests, Menu menu) {
        return menuProductRequests.stream()
                .map(menuProductRequest -> toMenuProduct(menuProductRequest, menu))
                .collect(Collectors.toList());
    }

    private MenuProduct toMenuProduct(MenuProductRequest menuProductRequest, Menu menu) {
        return new MenuProduct(menu, menuProductRequest.getProductId(), Quantity.of(menuProductRequest.getQuantity()));
    }

    private Menu toMenu(MenuRequest menuRequest) {
        return new Menu(menuRequest.getName(),
                Price.valueOf(menuRequest.getPrice()),
                menuRequest.getMenuGroupId());
    }

    @Transactional(readOnly = true)
    public List<MenuResponse> list() {
        final List<Menu> menus = menuRepository.findAll();
        final List<Long> menuIds = menus.stream()
                .map(Menu::getId)
                .collect(Collectors.toList());
        final List<MenuProduct> menuProducts = menuProductRepository.findAllByMenuIdIn(menuIds);
        final List<MenuResponse> menuResponses = new ArrayList<>();
        for (Menu menu : menus) {
            List<MenuProduct> menuProductsOfMenu = menuProducts.stream()
                    .filter(menuProduct -> menuProduct.isMenuProductOf(menu))
                    .collect(Collectors.toList());
            menuResponses.add(new MenuResponse(menu.getId(),
                    menu.getName(),
                    menu.getPrice().value(),
                    menu.getMenuGroupId(),
                    toMenuProductResponses(menuProductsOfMenu)));
        }

        return menuResponses;
    }

    private List<MenuProductResponse> toMenuProductResponses(List<MenuProduct> menuProducts) {
        final List<MenuProductResponse> responses = new ArrayList<>();
        for (MenuProduct menuProduct : menuProducts) {
            responses.add(new MenuProductResponse(menuProduct.getSeq(),
                    menuProduct.getMenu().getId(),
                    menuProduct.getProductId(),
                    menuProduct.getQuantity().value()));
        }
        return responses;
    }
}
