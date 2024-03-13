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

import net.shonx.serverrestart.ServerRestart;
import net.shonx.serverrestart.api.ConfigValues;
import net.shonx.serverrestart.api.PlayerServer;
import net.shonx.serverrestart.messages.Message;

import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.network.chat.contents.LiteralContents;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

import net.minecraftforge.server.ServerLifecycleHooks;

public class PlayerServer_20 implements PlayerServer {

    private ConfigValues config;

    public PlayerServer_20(ServerRestart mod) {
        this.config = mod.cf;
    }

    @Override
    public void announceToServer(Message message) {
        LiteralContents contents = new LiteralContents(message.message);
        MutableComponent component = MutableComponent.create(contents).withStyle(Style.EMPTY.withColor(TextColor.fromRgb(0xFF00FF)).withFont(Style.DEFAULT_FONT));
        ServerLifecycleHooks.getCurrentServer().getPlayerList().broadcastSystemMessage(component, config.bypassSystemChat());

    }

    @Override
    public void disconnectAllPlayers() {
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        MutableComponent message = MutableComponent.create(new LiteralContents("Server is restarting!"));

        // Snapshot of all connected players
        ArrayList<ServerPlayer> serverPlayers = new ArrayList<>(server.getPlayerList().getPlayers());

        if (serverPlayers.isEmpty())
            return;

        ServerRestart.LOGGER.info("Disconnecting all players.");

        // Disconnect all players using the snapshot list
        for (ServerPlayer player : serverPlayers) {
            player.connection.disconnect(message);
        }

        // Stall until the server's player list is empty
        while (!server.getPlayerList().getPlayers().isEmpty()) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                ServerRestart.LOGGER.error("Thread interrupted while awaiting player disconnection", e);
                break;
            }
        }

        ServerRestart.LOGGER.info("All players have been successfully disconnected.");
    }

    @Override
    public void haltServer() {
        ServerLifecycleHooks.getCurrentServer().halt(false);
    }

}
