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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.shonx.serverrestart.api.ConfigValues;
import net.shonx.serverrestart.api.ModLoading;
import net.shonx.serverrestart.api.PlayerServer;
import net.shonx.serverrestart.discord.DiscordPoster;
import net.shonx.serverrestart.discord.EmbedObject;
import net.shonx.serverrestart.version_specific.ConfigHelper_16;
import net.shonx.serverrestart.version_specific.ModLoading_16;
import net.shonx.serverrestart.version_specific.PlayerServer_16;

import net.minecraftforge.fml.common.Mod;

@Mod(ServerRestart.MOD_ID)
public class ServerRestart {
    public static final Logger LOGGER = LogManager.getLogger();
    public static final String MOD_ID = "serverrestart";

    public static final void onServerCrash() {
        try {
            EmbedObject embed = new EmbedObject(":boom: Oh no! The server has crashed! :boom:", null);
            embed.color = 16711680;
            DiscordPoster.postEmbed(embed);
        } catch (Throwable ignored) {
            // Server is already crashing... don't make it worse
        }
    }

    public final ModLoading ml = new ModLoading_16(this);
    public final ConfigValues cf = new ConfigHelper_16();
    public final PlayerServer ps = new PlayerServer_16();

    public ServerRestart() {
        ml.loadConfig();
        ml.setServerSideOnly();
        ml.registerEventHandler();
    }

    public void printLog(long shutdownIn) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        format.setTimeZone(TimeZone.getDefault());
        long shutdownAtInMillis = System.currentTimeMillis() + shutdownIn * 1000L;
        LOGGER.warn(String.format("Server will restart at %s.", format.format(new Date(shutdownAtInMillis))));

        String startupMessage = cf.getStartupMessage();
        EmbedObject embed = new EmbedObject(String.format("Hey everyone! The server is up. It will restart at <t:%d:T>", shutdownAtInMillis / 1000L), "null".equals(startupMessage) ? null : startupMessage);
        embed.color = 65280;
        DiscordPoster.postEmbed(embed);
    }

    public void onServerStart() {
        DiscordPoster.setWebhookURL(cf.getWebhookURL());
        EmbedObject.loadConfig(cf);
    }
}
