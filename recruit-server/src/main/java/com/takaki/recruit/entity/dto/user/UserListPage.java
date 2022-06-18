package com.takaki.recruit.entity.dto.user;

import com.takaki.recruit.common.TransferPage;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author Takaki
 * @date 2022/6/17
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserListPage extends TransferPage {
    private String username;
    private String name;
}
