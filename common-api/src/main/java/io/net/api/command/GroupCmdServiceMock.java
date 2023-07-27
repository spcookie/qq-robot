package io.net.api.command;

import io.net.api.GroupCmd;
import io.net.api.MsgResult;

/**
 * @author Augenstern
 * @since 2023/7/27
 */
public class GroupCmdServiceMock implements GroupCmdService {
    @Override
    public MsgResult invoke(GroupCmd request) {
        return MsgResult.newBuilder().setCode(MsgResult.Code.RPC_ANOMALY).setMsg("服务不可以，请稍后再试").build();
    }
}
