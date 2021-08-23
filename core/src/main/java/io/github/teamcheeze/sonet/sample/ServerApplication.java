/*
 * Sonet
 * Copyright (C) 2021 dolphin2410
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package io.github.teamcheeze.sonet.sample;

import io.github.teamcheeze.sonet.Sonet;
import io.github.teamcheeze.sonet.network.PacketRegistry;
import io.github.teamcheeze.sonet.network.component.Server;
import io.github.teamcheeze.sonet.network.handlers.SonetConnectionHandler;
import io.github.teamcheeze.sonet.network.data.SonetPacket;
import io.github.teamcheeze.sonet.network.handlers.SonetPacketHandler;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

public class ServerApplication {
    public static void main(String[] args) {
        Server server = Sonet.createServer(44444);
        System.out.println("Server initialized!");
        SonetConnectionHandler clientHandler = new SonetConnectionHandler() {
            @Override
            public void handle(SocketChannel clientChannel) {
                try {
                    System.out.println("User with IP: " + ((InetSocketAddress) clientChannel.getRemoteAddress()).getAddress().getHostAddress() + " successfully connected.");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        server.addClientHandler(clientHandler);
        SonetPacketHandler packetHandler = new SonetPacketHandler() {
            @Override
            public void handle(SonetPacket<?> packet) {
                if (packet instanceof SamplePacket samplePacket) {
                    System.out.println("PacketId: " + PacketRegistry.getType(SamplePacket.class));
                    System.out.println("UUID: " + samplePacket.getId());
                    System.out.println("Name: " + samplePacket.getName());
                }
            }
        };
        server.addPacketHandler(packetHandler);
        server.start(false);
        System.out.println("Hello guys!!");
    }
}