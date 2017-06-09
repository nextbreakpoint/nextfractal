package com.nextbreakpoint.nextfractal.verticle.store;

import com.nextbreakpoint.nextfractal.core.Bundle;

import java.util.List;
import java.util.UUID;

public interface BundleStore {
    Bundle getBundle(UUID uuid);

    void saveBundle(UUID uuid, byte[] bytes);

    void deleteBundle(UUID uuid);

    List<String> listBundles();
}
