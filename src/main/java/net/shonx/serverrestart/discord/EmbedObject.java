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

package net.shonx.serverrestart.discord;

import java.util.Objects;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.shonx.serverrestart.api.ConfigValues;

public class EmbedObject {
    private static ConfigValues cf;

    public static void loadConfig(ConfigValues cf) {
        EmbedObject.cf = cf;
    }

    @Nullable
    public String avatarUrl;
    @Nonnull
    public int color;
    @Nullable
    public String content;
    @Nonnull
    public String embed_description;
    @Nullable
    public String footerText;
    @Nullable
    public String footerUrl;
    @Nullable
    public String username;

    public EmbedObject(@Nonnull String message, @Nullable String content) {
        // Username
        String username = cf.getServerUsername();
        if ("null".equals(username))
            this.username = null;
        else
            this.username = username;

        // Avatar URL
        String avatarUrl = cf.getAvatarURL();
        if ("null".equals(avatarUrl))
            this.avatarUrl = null;
        else
            this.avatarUrl = avatarUrl;

        // Content
        this.content = content;

        // Color
        // Cannot be null, it's an int.
        color = cf.getEmbedColor();

        // Footer Text
        String footerText = cf.getEmbedFooterText();
        if ("null".equals(footerText))
            this.footerText = null;
        else
            this.footerText = footerText;

        String footerUrl = cf.getEmbedFooterURL();
        if ("null".equals(footerUrl))
            this.footerUrl = null;
        else
            this.footerUrl = footerUrl;
        embed_description = Objects.requireNonNull(message);
    }
}
