package rr.np.job.controller.response;

import javax.validation.constraints.NotNull;
import lombok.Value;
import rr.np.job.controller.validator.ErrorCode;

@Value
public class ErrorResponse {

  String errorCode;
  String errorMessage;

  public static ErrorResponse from(@NotNull ErrorCode ec) {
    return new ErrorResponse(ec.code(), ec.message());
  }
}
