/*
 * MIT License
 *
 * Copyright (c) 2021 shroomdog27
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package net.shonx.serverrestart.version_specific;

import java.util.ArrayList;
import java.util.Objects;

import net.shonx.serverrestart.Config;
import net.shonx.serverrestart.api.ConfigValues;

public class ConfigHelper_16 implements ConfigValues {

    @Override
    public String getAvatarURL() {
        return Objects.requireNonNull(Config.SERVER.d_avatarUrl.get());

    }

    @Override
    public Integer getEmbedColor() {
        return Objects.requireNonNull(Config.SERVER.d_embed_color.get());
    }

    @Override
    public String getEmbedFooterText() {
        return Objects.requireNonNull(Config.SERVER.d_embed_footer_text.get());
    }

    @Override
    public String getEmbedFooterURL() {
        return Objects.requireNonNull(Config.SERVER.d_embed_footer_url.get());
    }

    @Override
    public String getServerUsername() {
        return Objects.requireNonNull(Config.SERVER.d_serverUserName.get());
    }

    @Override
    public String getStartupMessage() {
        return Objects.requireNonNull(Config.SERVER.d_startupMessage.get());
    }

    @Override
    public Long getShutdownLength() {
        return Objects.requireNonNull(Config.SERVER.s_shutdownLength.get());
    }

    @Override
    public ArrayList<String> getShutdownMessages() {
        return Objects.requireNonNull(Config.SERVER.s_shutdownMessages.get());
    }

    @Override
    public String getWebhookURL() {
        return Objects.requireNonNull(Config.WEBHOOK.d_webhook_url.get());

    }

}
