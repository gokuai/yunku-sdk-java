package com.yunkuent.sdk.upload;

import com.gokuai.base.ReturnResult;

public interface IEntFileManager {
    ReturnResult createFile(String fullpath, String fileHash, long fileSize, int opId, String opName, boolean overwrite);
}
