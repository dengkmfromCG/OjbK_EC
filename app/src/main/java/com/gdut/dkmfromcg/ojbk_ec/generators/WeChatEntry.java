package com.gdut.dkmfromcg.ojbk_ec.generators;



import com.gdut.dkmfromcg.ec_annotations.EntryGenerator;
import com.gdut.dkmfromcg.commonlib.wechat.templates.WXEntryTemplate;

/**
 * Created by 傅令杰 on 2017/4/22
 */

@SuppressWarnings("unused")
@EntryGenerator(
        packageName = "com.gdut.dkmfromcg.ojbk_ec",
        entryTemplate = WXEntryTemplate.class
)
public interface WeChatEntry {
}
