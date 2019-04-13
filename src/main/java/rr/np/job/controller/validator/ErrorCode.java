package rr.np.job.controller.validator;

public enum ErrorCode {
  ERR_001("ERR_001", "City Code cannot be empty."),
  ERR_002("ERR_002", "City Code must be 3 characters."),
  ERR_003("ERR_003", "Check-In Date wrong format."),
  ERR_004("ERR_004", "Check-Out Date wrong format."),
  ERR_005("ERR_005", "Check-In Date cannot be in the past."),
  ERR_006("ERR_006", "Check-Out Date must be after Check-In Date."),
  ERR_101("ERR_101", "Amadeus API Error.");

  private String code;
  private String message;

  ErrorCode(String code, String message) {
    this.code = code;
    this.message = message;
  }

  public String code() {
    return this.code;
  }

  public String message() {
    return this.message;
  }
}
