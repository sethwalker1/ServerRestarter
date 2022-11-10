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

package net.shonx.serverrestart;

import java.io.File;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent.OnConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class Config {
    public static class DiscordConfig {
        private Configuration config;

        public static String webhookURL = null;

        public DiscordConfig(File file) {
            config = new Configuration(file);

            load();
            sync();

            MinecraftForge.EVENT_BUS.register(this);
        }

        @SubscribeEvent
        public void onConfigChanged(OnConfigChangedEvent event) {
            if (ServerRestart.MOD_ID.equals(event.getModID()))
                sync();
        }

        public void sync() {
            // Get Config
            webhookURL = config.get("discord", "webhook", "null", "The Discord webhook URL. Use \"null\" to disable.").getString();
        }

        public void save() {
            config.save();
        }

        public void load() {
            config.load();
        }
    }

    public static class MainConfig {
        private Configuration config;

        public static Long shutdownLength = null;
        public static String embedFooterUrl = null;
        public static String embedFooterText = null;
        public static Integer embedColor = null;
        public static String avatarUrl = null;
        public static String startupMessage = null;
        public static String serverName = null;

        public MainConfig(File file) {
            config = new Configuration(file);

            load();
            sync();

            MinecraftForge.EVENT_BUS.register(this);
        }

        @SubscribeEvent
        public void onConfigChanged(OnConfigChangedEvent event) {
            if (ServerRestart.MOD_ID.equals(event.getModID()))
                sync();
        }

        public void sync() {
            // Get Config
            shutdownLength = config.get("restart", "shutdownLength", 21600, "Time in seconds before the server will restart.", 60, Integer.MAX_VALUE).getLong();
            embedFooterUrl = config.get("discord", "embedFooterUrl", "null", "The image URL to show in the Discord embed Footer. Use \"null\" to disable.").getString();
            embedFooterText = config.get("discord", "embedFooterText", "null", "The comment to show in the Discord embed Footer. Use \"null\" to disable.").getString();
            embedColor = config.get("discord", "embedColor", 15258703, "The color to use for the Discord embed.", 0, 16777215).getInt();
            avatarUrl = config.get("discord", "avatarUrl", "null", "The URL to use for the webhook avatar. Use \"null\" to disable.").getString();
            startupMessage = config.get("discord", "startupMessage", "null", "The string to send when the server first starts. Mentions are allowed here.").getString();
            serverName = config.get("discord", "serverName", "Server", "The name of the webhook to send to Discord. Use \"null\" to disable.").getString();
        }

        public void save() {
            config.save();
        }

        public void load() {
            config.load();
        }
    }
}
