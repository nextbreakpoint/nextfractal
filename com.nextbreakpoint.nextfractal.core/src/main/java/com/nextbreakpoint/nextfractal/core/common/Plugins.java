/*
 * NextFractal 2.1.2-ea+1
 * https://github.com/nextbreakpoint/nextfractal
 *
 * Copyright 2015-2020 Andrea Medeghini
 *
 * This file is part of NextFractal.
 *
 * NextFractal is an application for creating fractals and other graphics artifacts.
 *
 * NextFractal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * NextFractal is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with NextFractal.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package com.nextbreakpoint.nextfractal.core.common;

import com.nextbreakpoint.Try;
import com.nextbreakpoint.nextfractal.core.encode.Encoder;

import java.util.List;
import java.util.Optional;
import java.util.ServiceLoader;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class Plugins {
    private Plugins() {}

    private static ServiceLoader<CoreFactory> factoryLoader() {
        return ServiceLoader.load(CoreFactory.class);
    }

    private static Stream<? extends CoreFactory> factoryStream() {
        return StreamSupport.stream(factoryLoader().spliterator(), false);
    }

    public static Optional<? extends CoreFactory> findFactory(String pluginId) {
        return factoryStream().filter(plugin -> pluginId.equals(plugin.getId())).findFirst();
    }

    public static Try<? extends CoreFactory, Exception> tryFindFactory(String pluginId) {
        return findFactory(pluginId).map(plugin -> Try.of(() -> plugin)).orElse(Try.failure(new Exception("Factory not found " + pluginId)));
    }

    public static Optional<? extends CoreFactory> findFactoryByGrammar(String grammar) {
        return factoryStream().filter(factory -> factory.getGrammar().equals(grammar)).findFirst();
    }

    public static Try<? extends CoreFactory, Exception> tryFindFactoryByGrammar(String grammar) {
        return findFactoryByGrammar(grammar).map(plugin -> Try.of(() -> plugin)).orElse(Try.failure(new Exception("Factory not found " + grammar)));
    }

    private static ServiceLoader<Encoder> encoderLoader() {
        return ServiceLoader.load(Encoder.class);
    }

    private static Stream<? extends Encoder> encoderStream() {
        return StreamSupport.stream(encoderLoader().spliterator(), false);
    }

    public static Optional<? extends Encoder> findEncoder(String pluginId) {
        return encoderStream().filter(plugin -> pluginId.equals(plugin.getId())).findFirst();
    }

    public static Try<? extends Encoder, Exception> tryFindEncoder(String format) {
        return findEncoder(format).map(plugin -> Try.of(() -> plugin)).orElse(Try.failure(new Exception("Encoder not found " + format)));
    }

    public static List<String> listGrammars() {
        return factoryStream().map(CoreFactory::getGrammar).sorted().collect(Collectors.toList());
    }

    public static Stream<? extends CoreFactory> factories() {
        return factoryStream();
    }
}
