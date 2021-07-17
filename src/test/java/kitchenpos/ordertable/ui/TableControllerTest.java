package kitchenpos.ordertable.ui;

import kitchenpos.ordertable.application.TableService;
import kitchenpos.common.ui.ControllerTest;
import kitchenpos.ordertable.domain.OrderTable;
import kitchenpos.ordertable.dto.OrderTableRequest;
import kitchenpos.ordertable.dto.OrderTableResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static kitchenpos.ordertable.OrderTableTestFixture.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = TableRestController.class)
public class TableControllerTest extends ControllerTest<OrderTableRequest> {

    private static final String BASE_URI = "/api/tables";

    @MockBean
    private TableService tableService;

    @Autowired
    private TableRestController tableRestController;

    @Override
    protected Object controller() {
        return tableRestController;
    }

    @DisplayName("주문테이블 생성요청")
    @Test
    void 주문테이블_생성요청() throws Exception {
        //Given
        when(tableService.create(any())).thenReturn(OrderTableResponse.of(비어있는_테이블));

        //When
        ResultActions 결과 = postRequest(BASE_URI, OrderTableRequest.of(비어있는_테이블));

        //Then
        생성성공(결과);
    }

    @DisplayName("주문테이블 목록 조회요청")
    @Test
    void 주문테이블_목록_조회요청() throws Exception {
        //Given
        when(tableService.list()).thenReturn(Arrays.asList(비어있지_않은_테이블_응답, 비어있는_테이블_응답));

        //When
        ResultActions 결과 = getRequest(BASE_URI);

        //Then
        조회성공(결과);
    }

    @DisplayName("주문테이블을 비어있음으로 수정요청")
    @Test
    void 주문테이블_비어있음_수정요청() throws Exception {
        //Given
        when(tableService.changeEmpty(비어있지_않은_테이블.getId(), OrderTableRequest.of(비어있는_테이블))).thenReturn(OrderTableResponse.of(비어있는_테이블));

        String 수정요청_URI = BASE_URI + "/" + 비어있지_않은_테이블.getId() + "/empty";

        //When
        ResultActions 결과 = putRequest(수정요청_URI, OrderTableRequest.of(비어있지_않은_테이블));

        //Then
        수정성공(결과);
    }

    @DisplayName("주문테이블 손님수 수정요청")
    @Test
    void 주문테이블_손님수_수정요청() throws Exception {
        //Given
        OrderTable 변경될_손님수_정보_가지고있는_테이블 = new OrderTable(99, false);
        when(tableService.changeNumberOfGuests(비어있지_않은_테이블.getId(), OrderTableRequest.of(변경될_손님수_정보_가지고있는_테이블)))
                .thenReturn(OrderTableResponse.of(변경될_손님수_정보_가지고있는_테이블));

        String 수정요청_URI = BASE_URI + "/" + 비어있지_않은_테이블.getId() + "/number-of-guests";

        //When
        ResultActions 결과 = putRequest(수정요청_URI, OrderTableRequest.of(변경될_손님수_정보_가지고있는_테이블));

        //Then
        수정성공(결과);
    }
}
