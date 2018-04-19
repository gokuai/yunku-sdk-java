package com.gokuai.yunku.demo.compat.v2.file;

import com.gokuai.base.DebugConfig;
import com.gokuai.base.ReturnResult;
import com.gokuai.yunku.demo.compat.v2.helper.EntFileManagerHelper;
import com.gokuai.yunku.demo.helper.DeserializeHelper;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by qp on 2017/3/2.
 *
 * 文件更新数量
 */
public class GetUpdateCounts {

    public static void main(String[] args) {

        DebugConfig.PRINT_LOG = true;
//        DebugConfig.LOG_PATH="LogPath/";

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);//昨天
        Date date = calendar.getTime();

        ReturnResult result = EntFileManagerHelper.getInstance().getUpdateCounts(date.getTime(), System.currentTimeMillis(), false);

        DeserializeHelper.getInstance().deserializeReturn(result);
    }
}
