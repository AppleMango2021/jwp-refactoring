package kitchenpos.menu.domain;

import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.util.Collections;
import java.util.List;

@Embeddable
public class MenuProducts {

    @OneToMany(mappedBy = "menu")
    private List<MenuProduct> menuProducts;

    public MenuProducts() {
    }

    public MenuProducts(List<MenuProduct> menuProducts) {
        this.menuProducts = menuProducts;
    }

    public static MenuProducts of(List<MenuProduct> menuProducts) {
        return new MenuProducts(menuProducts);
    }

    public List<MenuProduct> getMenuProducts() {
        return Collections.unmodifiableList(menuProducts);
    }
}
