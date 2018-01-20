/*
 * Copyright 2017 flow.ci
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.flow.plugins.dingtalk;

import com.flow.plugins.dingtalk.util.CommonUtil;
import com.flow.plugins.dingtalk.util.HttpClient;
import com.flow.plugins.dingtalk.util.HttpResponse;
import com.flow.plugins.dingtalk.util.Strings;
import java.io.UnsupportedEncodingException;
import org.apache.http.entity.ContentType;

/**
 * @author yh@firim
 */
public class App {


    public static void main(String[] args) {

        System.out.println(CommonUtil.showJfigletMessage("DingTalk Start"));

        String dingApi = System.getenv("PLUGIN_DINGTALK_API");

        String message = System.getenv("PLUGIN_DINGTALK_MESSAGE");

        if (Strings.isNullOrEmpty(dingApi)) {
            System.out.println("PLUGIN_DINGTALK_API is required");
            exit();
        }

        // message not blank
        if (!Strings.isNullOrEmpty(message)) {
            sendMessage(dingApi, message);
        }

        System.out.println(CommonUtil.showJfigletMessage("DingTalk Finish"));
    }

    private static void sendMessage(String url, String message) {

        String body = "{ \"msgtype\": \"text\", \"text\": {\"content\": \"" + message + "\"}}";

        try {
            HttpResponse<String> response = HttpClient.build(url)
                .post(body)
                .withContentType(ContentType.APPLICATION_JSON)
                .bodyAsString();

            if (response.hasSuccess()) {
                System.out.println("Send Message Success");
            } else {
                System.out.println("Send Message Exception " + response.getBody());
                exit();
            }


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    private static void exit() {
        System.exit(1);
    }
}
