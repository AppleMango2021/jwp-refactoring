package kitchenpos.menu.ui;

import kitchenpos.menu.dto.MenuGroupRequest;
import kitchenpos.menu.dto.MenuGroupResponse;
import kitchenpos.menu.application.MenuGroupService;
import kitchenpos.menu.domain.MenuGroupRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
class MenuGroupServiceTest {

    @Mock
    private MenuGroupRepository menuGroupRepository;

    @InjectMocks
    private MenuGroupService menuGroupService;

    private MenuGroupRequest 한마리메뉴 = new MenuGroupRequest("한마리메뉴");
    private MenuGroupRequest 두마리메뉴 = new MenuGroupRequest("두마리메뉴");

    @DisplayName("메뉴 그룹을 등록한다")
    @Test
    void 메뉴그룹_등록() {
        //Given
        when(menuGroupRepository.save(any())).thenReturn(한마리메뉴.toMenuGroup());

        //When
        MenuGroupResponse 생성된_메뉴그룹 = menuGroupService.create(한마리메뉴);

        //Then
        verify(menuGroupRepository, times(1)).save(any());
        assertThat(생성된_메뉴그룹.getName()).isEqualTo(한마리메뉴.getName());
    }

    @DisplayName("메뉴 그룹 목록을 조회한다")
    @Test
    void 메뉴그룹_목록_조회() {
        //Given
        List<MenuGroupRequest> 입력한_메뉴그룹_목록 = new ArrayList<>(Arrays.asList(한마리메뉴, 두마리메뉴));
        when(menuGroupRepository.findAll()).thenReturn(MenuGroupRequest.toMenuGroupList(입력한_메뉴그룹_목록));

        //When
        List<MenuGroupResponse> 조회된_메뉴그룹_목록 = menuGroupService.list();

        //Then
        verify(menuGroupRepository, times(1)).findAll();
        assertThat(조회된_메뉴그룹_목록).isNotNull()
                .hasSize(입력한_메뉴그룹_목록.size());
    }
}
