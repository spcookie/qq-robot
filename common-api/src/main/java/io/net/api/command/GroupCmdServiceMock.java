package io.net.api.command;

import io.net.api.GroupCmd;
import io.net.api.MsgResult;
import io.net.api.MsgResultChain;

/**
 * @author Augenstern
 * @since 2023/7/27
 */
public class GroupCmdServiceMock extends DubboGroupCmdServiceTriple.GroupCmdServiceImplBase {
    @Override
    public MsgResultChain invoke(GroupCmd request) {
        MsgResult msgResult = MsgResult.newBuilder().setMsg("服务不可用，请稍后再试").build();
        return MsgResultChain.newBuilder().setCode(MsgResultChain.Code.RPC_ANOMALY).addMsgResult(msgResult).build();
    }
}
