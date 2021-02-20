package org.iushu.context.beans;

import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.function.Consumer;

/**
 * @author iuShu
 * @since 2/4/21
 */
public class Conductor extends Senator {

    @Autowired
    private Microphone microphone;

    @Resource
    private ScreenRemote screenRemote;

    private ConductorState state;

    public Conductor() {
        setTitle("Senator Conductor");
        setLevel(9);
        state = ConductorState.END;
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

    public ConductorState getState() {
        return state;
    }

    public Microphone getMicrophone() {
        return microphone;
    }

    public void setMicrophone(Microphone microphone) {
        this.microphone = microphone;
    }

    public ScreenRemote getScreenRemote() {
        return screenRemote;
    }

    public void setScreenRemote(ScreenRemote screenRemote) {
        this.screenRemote = screenRemote;
    }

    @Override
    public String toString() {
        return "Conductor{" +
                "title='" + getTitle() + '\'' +
                ", name='" + getName() + '\'' +
                ", level=" + getLevel() +
                ", state=" + state +
                ", microphone=" + microphone +
                ", screenRemote=" + screenRemote +
                "}";
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
