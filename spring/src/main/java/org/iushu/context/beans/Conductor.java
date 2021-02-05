package org.iushu.context.beans;

import java.util.function.Consumer;

/**
 * @author iuShu
 * @since 2/4/21
 */
public class Conductor extends Senator {

    private ConductorState state;

    public Conductor() {
        setTitle("Senator Conductor");
        setLevel(9);
        state = ConductorState.END;
    }

    public ConductorState getState() {
        return state;
    }

    public void start() {
        state = ConductorState.START;
    }

    public void pause() {
        state = ConductorState.PAUSE;
    }

    public void end() {
        state = ConductorState.END;
    }

    public enum ConductorState {

        START((conductor) -> {
            System.out.println(String.format("[Conductor-%s] start", conductor.getName()));
        }),

        PAUSE((conductor) -> {
            System.out.println(String.format("[Conductor-%s] pause", conductor.getName()));
        }),

        END((conductor) -> {
            System.out.println(String.format("[Conductor-%s] end", conductor.getName()));
        }),

        ;

        private Consumer<Conductor> consumer;

        private ConductorState(Consumer<Conductor> consumer) {
            this.consumer = consumer;
        }

        public void onEvent(Conductor conductor) {
            consumer.accept(conductor);
        }

    }

}
