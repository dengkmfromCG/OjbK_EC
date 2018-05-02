package com.gdut.dkmfromcg.ojbk_ec.generators;


import com.gdut.dkmfromcg.commonlib.wechat.templates.WXPayEntryTemplate;
import com.gdut.dkmfromcg.ec_annotations.PayEntryGenerator;

/**
 * Created by 傅令杰 on 2017/4/22
 */
@SuppressWarnings("unused")
@PayEntryGenerator(
        packageName = "com.gdut.dkmfromcg.ojbk_ec",
        payEntryTemplate = WXPayEntryTemplate.class
)
public interface WeChatPayEntry {
}
