package com.yunkuent.sdk.upload;

import com.gokuai.base.ReturnResult;

public interface IEntFileManager {
    ReturnResult createFile(String fullpath, String fileHash, long fileSize, boolean overwrite, String opName, int opId);
}
