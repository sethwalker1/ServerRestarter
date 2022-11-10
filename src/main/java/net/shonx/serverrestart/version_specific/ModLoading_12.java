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

import java.io.File;

import net.shonx.serverrestart.Config.DiscordConfig;
import net.shonx.serverrestart.Config.MainConfig;
import net.shonx.serverrestart.ServerRestart;
import net.shonx.serverrestart.api.ModLoading;

public class ModLoading_12 implements ModLoading {

    private MainConfig mc;
    private DiscordConfig dc;

    public ModLoading_12() {
    }

    @Override
    public void loadConfig() {
        mc = new MainConfig(new File(ServerRestart.CONFIG_DIR, String.format("%s/general.cfg", ServerRestart.MOD_ID)));
        mc.save();
        dc = new DiscordConfig(new File(ServerRestart.CONFIG_DIR, String.format("%s/discord.cfg", ServerRestart.MOD_ID)));
        dc.save();

    }

    @Override
    public void setServerSideOnly() {
        // This is handled in the @Mod annotation in 1.12.
    }

    @Override
    public void registerEventHandler() {
        // Because 1.12.2... the events we care about must be made in the main mod
        // class.
    }

}
