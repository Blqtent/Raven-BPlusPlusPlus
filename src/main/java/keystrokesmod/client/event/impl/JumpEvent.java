package keystrokesmod.client.event.impl;


import keystrokesmod.client.event.types.CancellableEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public final class JumpEvent extends CancellableEvent {
    private float yaw;
    private double motion;
}
