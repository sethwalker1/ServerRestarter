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

import net.shonx.serverrestart.api.PlayerServer;
import net.shonx.serverrestart.messages.Message;

import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Util;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.Color;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;

import net.minecraftforge.fml.server.ServerLifecycleHooks;

public class PlayerServer_16 implements PlayerServer {

    @Override
    public void announceToServer(Message message) {
        ServerLifecycleHooks.getCurrentServer().getPlayerList().broadcastMessage(new StringTextComponent(message.message).withStyle(Style.EMPTY.withColor(Color.fromRgb(0xFF00FF)).withFont(Style.DEFAULT_FONT)), ChatType.SYSTEM, Util.NIL_UUID);

    }

    @Override
    public void disconnectAllPlayers() {
        MinecraftServer server = ServerLifecycleHooks.getCurrentServer();
        StringTextComponent message = new StringTextComponent("Server is restarting!");
        for (ServerPlayerEntity player : new ArrayList<ServerPlayerEntity>(server.getPlayerList().getPlayers()))
            player.connection.disconnect(message);
    }

    @Override
    public void haltServer() {
        ServerLifecycleHooks.getCurrentServer().halt(false);

    }

}
