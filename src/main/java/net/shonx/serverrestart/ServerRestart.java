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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.apache.logging.log4j.Logger;

import net.shonx.serverrestart.api.ConfigValues;
import net.shonx.serverrestart.api.ModLoading;
import net.shonx.serverrestart.api.PlayerServer;
import net.shonx.serverrestart.discord.DiscordPoster;
import net.shonx.serverrestart.discord.EmbedObject;
import net.shonx.serverrestart.version_specific.ConfigHelper_12;
import net.shonx.serverrestart.version_specific.ModLoading_12;
import net.shonx.serverrestart.version_specific.PlayerServer_12;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartedEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;

@Mod(
        modid = ServerRestart.MOD_ID,
        name = "Server Restart",
        version = "1",
        serverSideOnly = true,
        acceptableRemoteVersions = "*"
)
public class ServerRestart {
    public static Logger LOGGER = null;
    public static final String MOD_ID = "serverrestart";
    public static File CONFIG_DIR = null;

    public static final void onServerCrash() {
        try {
            EmbedObject embed = new EmbedObject(":boom: Oh no! The server has crashed! :boom:", null);
            embed.color = 16711680;
            DiscordPoster.postEmbed(embed);
        } catch (Throwable ignored) {
            // Server is already crashing... don't make it worse
        }
    }

    public final ModLoading ml = new ModLoading_12();
    public final ConfigValues cf = new ConfigHelper_12();
    public final PlayerServer ps = new PlayerServer_12();
    private Events events;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        LOGGER = event.getModLog();
        CONFIG_DIR = event.getModConfigurationDirectory();
        ml.loadConfig();
        ml.setServerSideOnly();
        ml.registerEventHandler();
        events = new Events(this);
    }

    @EventHandler
    public void onServerStarted(FMLServerStartedEvent event) {
        events.onServerStarted(event);
    }

    @EventHandler
    public void onServerStopping(FMLServerStoppingEvent event) {
        events.onServerStopping(event);
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
