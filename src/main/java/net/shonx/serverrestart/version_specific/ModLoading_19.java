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

import net.shonx.serverrestart.Config;
import net.shonx.serverrestart.Events;
import net.shonx.serverrestart.ServerRestart;
import net.shonx.serverrestart.api.ModLoading;

import net.minecraftforge.common.MinecraftForge;

public class ModLoading_19 implements ModLoading {

    private ServerRestart mod;

    public ModLoading_19(ServerRestart mod) {
        this.mod = mod;
    }

    @Override
    public void loadConfig() {
        Config.load();

    }

    @Override
    public void setServerSideOnly() {
        // This is handled in mods.toml in 1.19.x.
    }

    @Override
    public void registerEventHandler() {
        MinecraftForge.EVENT_BUS.register(new Events(mod));

    }

}
