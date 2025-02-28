package net.p1nero.ss.gameassets;

import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.collider.MultiOBBCollider;

public class SwordSoaringColliders {
    public static final Collider VATANSEVER = new MultiOBBCollider(1, 0.4, 1.7, 0.4, 0.0, 0.5, 0);
    public static final Collider SCAN_SCALE = new MultiOBBCollider(2, 8, 8, 15, 0, 1, -15);

}
