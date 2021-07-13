package kitchenpos.order.domain;

import kitchenpos.exception.CannotUpdateException;
import kitchenpos.ordertable.domain.OrderTable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static kitchenpos.common.Message.ERROR_ORDER_SHOULD_HAVE_NON_EMPTY_TABLE;
import static kitchenpos.common.Message.ERROR_ORDER_STATUS_CANNOT_BE_CHANGED_WHEN_COMPLETED;
import static kitchenpos.order.domain.OrderStatus.*;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class OrderTest {

    @DisplayName("주문 생성 시, 주문 테이블이 비어있는 상태인 경우 예외발생")
    @Test
    void 비어있는_테이블_주문_생성시_예외발생() {
        int 손님수 = 3;
        boolean 비어있음 = true;
        OrderTable 비어있는_테이블 = new OrderTable(3, 비어있음);

        assertThatThrownBy(() -> new Order(비어있는_테이블, COOKING, new OrderLineItems()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage(ERROR_ORDER_SHOULD_HAVE_NON_EMPTY_TABLE.showText());

    }

    @DisplayName("완료처리된 주문의 상태를 변경하는 경우 예외발생")
    @Test
    void 완료처리된_주문_상태_변경시_예외발생() {
        OrderTable 테이블 = new OrderTable(3, false);
        Order 주문 = new Order(테이블, COMPLETION, new OrderLineItems());

        assertThatThrownBy(() -> 주문.changeOrderStatus(COOKING))
                .isInstanceOf(CannotUpdateException.class)
                .hasMessage(ERROR_ORDER_STATUS_CANNOT_BE_CHANGED_WHEN_COMPLETED.showText());
    }

}
