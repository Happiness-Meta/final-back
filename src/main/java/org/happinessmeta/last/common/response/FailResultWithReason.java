package org.happinessmeta.last.common.response;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class FailResultWithReason extends Result {
    List<String> errors = new ArrayList<>();
}
