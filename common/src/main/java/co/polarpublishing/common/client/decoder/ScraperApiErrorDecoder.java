package co.polarpublishing.common.client.decoder;

import feign.Response;
import feign.RetryableException;
import feign.codec.ErrorDecoder;
// import lombok.extern.slf4j.Slf4j;

// @Slf4j
public class ScraperApiErrorDecoder implements ErrorDecoder {

  @Override
  public Exception decode(String methodKey, Response response) {
    if (response.status() == 500) {
      throw new RetryableException("Service Unavailable", response.request().httpMethod(), null);
    } else {
      return new RuntimeException("error");
    }
  }

}
