package com.jlchn.javaagent;

import lombok.Data;

/**
 * @author jiangli
 * @date 21/11/2018
 */

@Data
public class TraceNode {

    private String traceId;
    private String spanId;
    private String parentId;
}
