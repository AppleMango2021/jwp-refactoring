package kitchenpos.ordertable.dto;

import kitchenpos.ordertable.domain.OrderTable;

import java.util.List;
import java.util.stream.Collectors;

public class OrderTableRequest {

    private Long id;
    private int numberOfGuests;
    private boolean empty;

    public OrderTableRequest() {

    }

    public OrderTableRequest(int numberOfGuests, boolean empty) {
        this.numberOfGuests = numberOfGuests;
        this.empty = empty;
    }

    public static OrderTableRequest of(OrderTable orderTable) {
        return new OrderTableRequest(orderTable.getNumberOfGuests(), orderTable.isEmpty());
    }

    public static List<OrderTableRequest> ofList(List<OrderTable> orderTables) {
        return orderTables.stream()
                .map(OrderTableRequest::of)
                .collect(Collectors.toList());
    }

    public OrderTable toOrderTable() {
        return new OrderTable(this.numberOfGuests, this.empty);
    }

    public Long getId() {
        return id;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public boolean isEmpty() {
        return empty;
    }
}
