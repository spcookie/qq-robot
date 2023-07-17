package io.net.api.command;

import io.net.api.GroupCmd;
import io.net.api.MsgResult;

/**
 * @author Augenstern
 * @since 2023/7/16
 */
public class GroupCmdServiceMock implements GroupCmdService {
    @Override
    public MsgResult invoke(GroupCmd request) {
        return MsgResult.newBuilder().setMsg("服务不可用，请稍后再试").build();
    }
}
