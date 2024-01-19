package co.polarpublishing.common.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class URL {

  private static Set<Character> RESERVED_CHARS = Stream.of('!', '*', '\'', '(', ')', ';', ':', '@',
      '&', '=', '+', '$', ',', '/', '?', '%', '#', '[', ']', '.').collect(Collectors.toSet());
  public static Set<String> PUBLIC_SUFFIXES = Stream.of("com", "co.uk", "ca", "com.mx", "de", "fr",
      "it", "es", "co.jp", "sg", "ae", "com.br", "com.au", "in", "nl", "sa", "com.tr", "se", "pl")
      .collect(Collectors.toSet());
  private static Set<String> DELIMITERS = Stream.of("://", ".", "/", "?", "=", "&")
      .collect(Collectors.toSet());

  private String protocol;
  private Map<String, String> queryParams;
  private String path;
  private String publicSuffix;
  private String domainName;
  private String subDomainName;

  public URL(String url) {
    this.parseUrl(url);
  }

  private void parseUrl(String url) {
    if (url == null || url.trim().isEmpty()) {
      throw new IllegalArgumentException("URL can't be null/empty.");
    }

    url = url.trim();
    if (url.length() <= 2 || URL.RESERVED_CHARS.contains(url.charAt(0))) {
      throw new IllegalArgumentException(String.format("Given URL string %s is invalid.", url));
    }

    Stack<String> urlStack = this.buildUrlStack(url);

    String token = urlStack.pop();
    String previousToken = token;
    String publicSuffix = "";
    while (token != null) {
      switch (token) {
        case "=": {
          this.queryParams = this.queryParams == null ? new HashMap<>() : this.queryParams;
          this.queryParams.put(urlStack.pop(), previousToken);
          break;
        }
        case "/": {
          this.path = this.path == null ? "" : this.path;
          this.path = token + previousToken + this.path;
          break;
        }
        case "://": {
          this.protocol = urlStack.pop();
          break;
        }
        case ".": {
          if (this.publicSuffix == null) {
            publicSuffix = previousToken + publicSuffix;
            if (URL.PUBLIC_SUFFIXES.contains(publicSuffix)) {
              this.publicSuffix = publicSuffix;
              this.domainName = urlStack.pop();
            } else {
              publicSuffix = token + publicSuffix;
            }
          } else {
            if (this.subDomainName == null) {
              this.subDomainName = urlStack.pop();
            } else {
              this.subDomainName = urlStack.pop() + token + this.subDomainName;
            }
          }
          break;
        }
        default: {
          previousToken = token;
        }
      }

      if (urlStack.isEmpty()) {
        token = null;
      } else {
        token = urlStack.pop();
      }
    }

    if (this.domainName == null || this.publicSuffix == null) {
      throw new IllegalArgumentException(String.format("Given URL string %s is invalid.", url));
    }
  }

  private Stack<String> buildUrlStack(String url) {

    Stack<String> urlStack = new Stack<>();
    StringBuilder stringBuilder = new StringBuilder();
    for (int index = 0; index < url.length(); index++) {
      char curChar = url.charAt(index);
      String delimiter = curChar + "";

      if (curChar == ':') {
        if (url.charAt(index + 1) == '/' && url.charAt(index + 2) == '/') {
          delimiter = "://";
          index += 2;
        } else {
          throw new IllegalArgumentException(String.format("Given URL string %s is invalid.", url));
        }
      }

      if (URL.DELIMITERS.contains(delimiter)) {
        urlStack.push(stringBuilder.toString());
        urlStack.push(delimiter);
        stringBuilder = new StringBuilder();
      } else {
        stringBuilder.append(curChar);
      }
    }

    if (stringBuilder.length() > 0) {
      urlStack.push(stringBuilder.toString());
    }

    return urlStack;
  }

}
