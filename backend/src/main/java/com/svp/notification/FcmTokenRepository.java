package com.svp.notification;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class FcmTokenRepository implements PanacheRepositoryBase<FcmToken, Long> {
}

