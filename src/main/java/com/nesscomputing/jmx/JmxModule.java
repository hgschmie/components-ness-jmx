/**
 * Copyright (C) 2012 Ness Computing, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.nesscomputing.jmx;

import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;

import com.google.inject.AbstractModule;
import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.google.inject.Scopes;
import com.google.inject.Singleton;
import com.google.inject.util.Modules;

import org.weakref.jmx.MBeanExporter;
import org.weakref.jmx.guice.MBeanModule;

/**
 * Sets up JMX bindings.  Specifically, binds an {@link MBeanExporter}
 * that records all bindings made and will automagically unbind them at
 * {@link Lifecycle} STOP_STAGE.
 */

public class JmxModule extends AbstractModule
{
    @Override
    protected void configure()
    {
        install(Modules.override(new MBeanModule()).with(new Module() {
            @Override
            public void configure(final Binder binder) {
                binder.bind(MBeanExporter.class).to(LifecycledMBeanExporter.class).in(Scopes.SINGLETON);
            }
        }));
    }

    @Provides
    @Singleton
    public MBeanServer getPlatformMBeanServer()
    {
        return ManagementFactory.getPlatformMBeanServer();
    }
}
