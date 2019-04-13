package rr.np.job.controller.response;

import lombok.Value;
import rr.np.job.controller.validator.ErrorCode;

@Value
public class ErrorResponse {

  String errorCode;
  String errorMessage;

  public static ErrorResponse from(ErrorCode ec) {
    return new ErrorResponse(ec.code(), ec.message());
  }
}
