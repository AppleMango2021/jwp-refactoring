package kitchenpos.menugroup.ui;

import kitchenpos.menugroup.domain.MenuGroupRepository;
import kitchenpos.menugroup.domain.MenuGroup;
import kitchenpos.menugroup.dto.MenuGroupRequest;
import kitchenpos.menugroup.dto.MenuGroupResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class MenuGroupService {
    private final MenuGroupRepository menuGroupRepository;

    public MenuGroupService(final MenuGroupRepository menuGroupRepository) {
        this.menuGroupRepository = menuGroupRepository;
    }

    public MenuGroupResponse create(final MenuGroupRequest menuGroupRequest) {
        return MenuGroupResponse.of(menuGroupRepository.save(menuGroupRequest.toMenuGroup()));
    }


    public List<MenuGroup> list() {
        return menuGroupRepository.findAll();
    }

    public List<MenuGroupResponse> newList() {
        return MenuGroupResponse.ofList(menuGroupRepository.findAll());
    }
}
