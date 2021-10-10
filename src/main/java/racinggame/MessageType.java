package racinggame;

/**
 * @author Choi InJoo <rinjyu@naver.com>
 * @version 1.0
 * @since 1.0
 */
public enum MessageType {

    NORMAL("NORMAL"),
    ERROR("ERROR");

    final private String type;

    MessageType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
