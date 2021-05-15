package io.github.lvyahui8.sdk.logging.configuration;

/**
 * @author feego lvyahui8@gmail.com
 * @date 2021/5/1
 */
public interface LogConstants {
    interface ReversedKey  {
        String BUSINESS = "biz";
        String TRACE_ID = "tid";
        String MESSAGE = "msg";
        String SUCCESS = "suc";
        String IS_REPLAY = "__isReplay";
        String ACTUAL_TIME = "__logActualTime";
    }

    interface Config {
        String FIELD_SP = "|#|";
    }
}
