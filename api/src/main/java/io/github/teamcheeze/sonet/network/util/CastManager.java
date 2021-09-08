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

package io.github.teamcheeze.sonet.network.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class CastManager {
    @NotNull
    public static <T> T cast(Object obj, Class<T> clazz) {
        return clazz.cast(obj);
    }

    @Nullable
    public static <T> T safeCast(Object obj, Class<T> clazz) {
        if (obj == null)
            return null;
        return clazz.cast(obj);
    }
}
