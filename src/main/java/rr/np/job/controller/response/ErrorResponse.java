package rr.np.job.controller.response;

import lombok.Value;

@Value
public class ErrorResponse implements Response {

  String errorCode;
  String errorMessage;

}
