package org.happinessmeta.last.common.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Result {
    private boolean success = false;
    private int code = 200;
    private String message;
}
