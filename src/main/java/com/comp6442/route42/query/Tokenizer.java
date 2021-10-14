package com.comp6442.route42.query;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.comp6442.route42.query.Token.TokenType.*;

public class Tokenizer {
  /**
   * @param query RESTRICTIONS - $and is the default connector between expressions
   *     <p>EXAMPLES "test" becomes {$hashtags: ["#test"]}
   *     <p>"username: xxxx hashtags: #hashtag #android #app" becomes {$and: [ {"username": "xxx"},
   *     {"hashtags": ["#hashtag", "#android", "#app"]}, ] }
   *     <p>"username: xxxx or hashtags: #hashtag #android #app" becomes {$or: [ {$userName: "xxx"},
   *     {$hashtags: ["#hashtag", "#android", "#app"]} ]}
   */
  private final String query;

  private Tokenizer(String query) {
    this.query = query;
  }

  public static List<Token> tokenizeQuery(String text) {
    return new Tokenizer(text).tokenize();
  }

  private List<Token> tokenize() {
    return fixTokens(extractTokens(this.query));
  }

  public List<Token> extractTokens(String text) {
    String regex = "\\s+AND\\s+|\\s+OR\\s+|\\s+and\\s+|\\s+or\\s+|\\(|\\)|\\w+[ ]*:[ ]?";
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(this.query);

    // Check all occurrences
    List<Integer> startIndex = new ArrayList<>();
    while (matcher.find()) {
      startIndex.add(matcher.start());
    }

    // parse all tokens
    List<Token> tokens = new ArrayList<>();
    if (startIndex.size() > 1) {
      // add last index if not included
      if (!startIndex.get(startIndex.size() - 1).equals(this.query.length()))
        startIndex.add(this.query.length());

      for (int i = 0; i < startIndex.size() - 1; i++) {
        String tokenStr = this.query.substring(startIndex.get(i), startIndex.get(i + 1));

        if (tokenStr.trim().startsWith("and") || tokenStr.trim().startsWith("AND")) {
          tokens.add(new Token(AND, "AND"));
        } else if (tokenStr.trim().startsWith("or") || tokenStr.trim().startsWith("OR")) {
          tokens.add(new Token(OR, "OR"));
        } else {
          tokens.add(Token.parseToken(tokenStr));
        }
      }
    } else {
      tokens.add(Token.parseToken(this.query));
    }
    return tokens;
  }

  public List<Token> fixTokens(List<Token> tokens) {
    // insert AND token where needed
    List<Token> finalTokens = new ArrayList<>();
    tokens.forEach(
        token -> {
          if (finalTokens.size() > 0) {
            Token lastItem = finalTokens.get(finalTokens.size() - 1);
            if (lastItem.getType() == RBRA
                && (token.getType() == LBRA || token.getType() == FILTER))
              finalTokens.add(new Token(AND, "AND"));
            else if (lastItem.getType() == FILTER && token.getType() == FILTER)
              finalTokens.add(new Token(AND, "AND"));
          }
          finalTokens.add(token);
        });
    return finalTokens;
  }
}
