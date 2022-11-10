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

import java.util.ArrayList;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import net.shonx.serverrestart.api.PlayerServer;
import net.shonx.serverrestart.discord.DiscordPoster;
import net.shonx.serverrestart.discord.EmbedObject;
import net.shonx.serverrestart.messages.Message;
import net.shonx.serverrestart.messages.MessageLoader;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.event.server.ServerStoppingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class Events {
    private ArrayList<Message> messages;

    private final ScheduledThreadPoolExecutor timer = new ScheduledThreadPoolExecutor(1);

    private ServerRestart mod;

    private PlayerServer api;

    public Events(ServerRestart mod) {
        this.mod = mod;
        api = mod.ps;
    }

    @SubscribeEvent
    public void onServerStarted(ServerStartedEvent event) {
        mod.onServerStart();
        final long shutdownIn = mod.cf.getShutdownLength();

        timer.schedule(() -> {
            api.disconnectAllPlayers();
            api.haltServer();
        }, shutdownIn, TimeUnit.SECONDS);

        mod.printLog(shutdownIn);
        messages = MessageLoader.loadMessages();
        messages.forEach(message -> timer.schedule(() -> {
            try {
                api.announceToServer(message);
                if (message.announceToDiscord)
                    DiscordPoster.postEmbed(new EmbedObject(message.message, null));
            } catch (Throwable e) {
                ServerRestart.LOGGER.error("Error announcing message.", e);
            }
        }, shutdownIn - message.time, TimeUnit.SECONDS));
    }

    @SubscribeEvent
    public void onServerStopping(ServerStoppingEvent event) {
        EmbedObject embed = new EmbedObject("The server has shut down!", null);
        DiscordPoster.postEmbed(embed);
        timer.shutdownNow();
    }
}
