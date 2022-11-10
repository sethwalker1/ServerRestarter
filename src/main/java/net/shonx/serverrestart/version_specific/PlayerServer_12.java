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

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;

import net.minecraftforge.fml.server.FMLServerHandler;

public class PlayerServer_12 implements PlayerServer {

    @Override
    public void announceToServer(Message message) {
        FMLServerHandler.instance().getServer().getPlayerList().sendMessage(new TextComponentString(message.message).setStyle(new Style().setColor(TextFormatting.LIGHT_PURPLE)), true);
    }

    @Override
    public void disconnectAllPlayers() {
        MinecraftServer server = FMLServerHandler.instance().getServer();
        TextComponentString message = new TextComponentString("Server is restarting!");
        for (EntityPlayerMP player : new ArrayList<EntityPlayerMP>(server.getPlayerList().getPlayers()))
            player.connection.disconnect(message);
    }

    @Override
    public void haltServer() {
        FMLServerHandler.instance().getServer().initiateShutdown();
    }

}
