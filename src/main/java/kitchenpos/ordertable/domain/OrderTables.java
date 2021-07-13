package kitchenpos.ordertable.domain;

import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Embeddable
public class OrderTables {

    @OneToMany(mappedBy = "tableGroup")
    private List<OrderTable> orderTables = new ArrayList<>();

    protected OrderTables() {
    }

    public OrderTables(List<OrderTable> orderTables) {
        this.orderTables = orderTables;
    }

    public void assignTableGroup(TableGroup tableGroup) {
        orderTables.forEach(orderTable -> orderTable.assignToTableGroup(tableGroup));
    }

    public List<OrderTable> getOrderTables() {
        return orderTables;
    }

    public int size() {
        return orderTables.size();
    }
}
