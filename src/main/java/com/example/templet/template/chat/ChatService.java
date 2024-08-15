package com.example.templet.template.chat;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import lombok.AllArgsConstructor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.apache.coyote.BadRequestException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ChatService {

  private static final String apiKey = System.getenv("OPENAI_API_KEY");
  private static final String API_URL = "https://api.openai.com/v1/chat/completions";

  public String chat(
      String userContent, String messageChatFormatEntitiesString, String systemContent)
      throws IOException {
    OkHttpClient client = new OkHttpClient();

    String jsonBody =
        String.format(
                """
                            {
                                "model": "gpt-4o-mini",
                                "messages": [
                                  {
                                    "role": "system",
                                    "content": "%s"
                                  },
                                  """,
                systemContent)
            + messageChatFormatEntitiesString
            + "\n"
            + String.format(
                """
                                  {
                                    "role": "user",
                                    "content": "%s"
                                  }
                                ]
                              }""",
                "(now)" + userContent);

    RequestBody body =
        RequestBody.create(jsonBody, MediaType.get("application/json; charset=utf-8"));
    Request request =
        new Request.Builder()
            .url(API_URL)
            .header("Authorization", "Bearer " + apiKey)
            .header("Content-Type", "application/json")
            .post(body)
            .build();

    try (Response response = client.newCall(request).execute()) {
      assert response.body() != null;
      if (!response.isSuccessful()) throw new BadRequestException("Unexpected code " + response);

      String jsonResponse = response.body().string();
      JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
      return jsonObject
          .get("choices")
          .getAsJsonArray()
          .get(0)
          .getAsJsonObject()
          .get("message")
          .getAsJsonObject()
          .get("content")
          .getAsString();
    } catch (Exception e) {
      e.printStackTrace();
      throw new BadRequestException("Unexpected code " + e);
    }
  }
}
