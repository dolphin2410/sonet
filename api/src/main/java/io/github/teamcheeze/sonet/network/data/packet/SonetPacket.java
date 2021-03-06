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

package io.github.teamcheeze.sonet.network.data.packet;

import io.github.teamcheeze.jaw.util.collection.Pair;
import io.github.teamcheeze.sonet.annotations.SonetData;
import io.github.teamcheeze.sonet.network.data.SonetDataType;
import io.github.teamcheeze.sonet.network.data.buffer.SonetBuffer;
import io.github.teamcheeze.sonet.network.data.buffer.StaticSonetBuffer;

import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * An abstract sonetPacket
 */
public interface SonetPacket extends AbstractSonetData {
    default ByteBuffer serialize() {
        SonetBuffer buffer = new SonetBuffer();
        buffer.writeString(getClass().getName());
        List<Pair<Integer, Object>> l = new ArrayList<>();
        for (Field declaredField : getClass().getDeclaredFields()) {
            SonetData sonetData = declaredField.getAnnotation(SonetData.class);
            if (sonetData == null) {
                continue;
            }
            int order = sonetData.value();
            try {
                // Infer the type automatically
                declaredField.setAccessible(true);
                l.add(Pair.of(order, declaredField.get(this)));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        l.sort(Comparator.comparing(Pair::getFirst));

        for (Pair<Integer, Object> iop : l) {
            buffer.write((byte) -0x01, iop.getSecond());
        }
        return buffer.toBuffer();
    }
}
