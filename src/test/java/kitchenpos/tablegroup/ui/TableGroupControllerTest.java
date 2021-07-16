package kitchenpos.tablegroup.ui;

import tablegroup.application.TableGroupService;
import kitchenpos.common.ControllerTest;
import tablegroup.domain.TableGroup;
import tablegroup.dto.TableGroupRequest;
import tablegroup.dto.TableGroupResponse;
import tablegroup.ui.TableGroupRestController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.ResultActions;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(controllers = TableGroupRestController.class)
public class TableGroupControllerTest extends ControllerTest<TableGroupRequest> {

    private static final String BASE_URI = "/api/table-groups";

    @MockBean
    private TableGroupService tableGroupService;

    @Autowired
    private TableGroupRestController tableGroupRestController;

    @Override
    protected Object controller() {
        return tableGroupRestController;
    }

    private final boolean 비어있음 = true;
    private TableGroup 테이블그룹 = new TableGroup();

    @DisplayName("단체지정 생성요청")
    @Test
    void 단체지정_생성요청() throws Exception {
        //Given
        when(tableGroupService.create(any())).thenReturn(TableGroupResponse.of(테이블그룹));

        //When
        ResultActions 결과 = postRequest(BASE_URI, TableGroupRequest.of(테이블그룹));

        //Then
        생성성공(결과);
    }

    @DisplayName("단체지정 취소요청")
    @Test
    void 단체지정_취소요청() throws Exception {
        //When
        ResultActions 결과 = deleteRequest(BASE_URI + "/" + 테이블그룹.getId());

        //Then
        삭제성공(결과);
    }
}
