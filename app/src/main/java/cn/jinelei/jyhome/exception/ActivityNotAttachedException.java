package cn.jinelei.jyhome.exception;

public class ActivityNotAttachedException extends RuntimeException {
    public ActivityNotAttachedException() {
        super("Fragment has disconnected from Activity ! - -.");
    }
}
