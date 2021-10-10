package racinggame;

/**
 * @author Choi InJoo <rinjyu@naver.com>
 * @version 1.0
 * @since 1.0
 */
public enum Message {

    ENTER_THE_RACING_CAR_NAME("ENTER_THE_RACING_CAR_NAME", "경주할 자동차 이름을 입력하세요.(이름은 쉼표(,) 기준으로 구분)", MessageType.NORMAL.getType()),

    ENTER_THE_NUMBER_OF_TIMES("ENTER_THE_NUMBER_OF_TIMES", "시도할 회수는 몇회인가요?", MessageType.NORMAL.getType()),

    CURRENT_RACING_CAR_LOCATION("CURRENT_RACING_CAR_LOCATION", "%s : %s", MessageType.NORMAL.getType()),

    THE_RACING_FINAL_WINNER("THE_RACING_FINAL_WINNER", "최종 우승자는 %s 입니다.", MessageType.NORMAL.getType()),

    ERROR_INVALID_RACING_CAR_NAME("ERROR_INVALID_RACING_CAR_NAME", "유효하지 않은 자동차명입니다.\n다시 입력해주세요.", MessageType.ERROR.getType()),

    ERROR_INVALID_NUMBER("ERROR_INVALID_NUMBER", "숫자로 입력해주세요.", MessageType.ERROR.getType());

    final private String key;

    final private String message;

    final private String type;

    Message(String key, String message, String type) {
        this.key = key;
        this.message = message;
        this.type = type;
    }

    public String getKey() {
        return key;
    }

    public String getMessage() {
        return message;
    }

    public String getType() {
        return type;
    }

    public static void printMessage(String messageType, String message) {
        message = (messageType.equals(MessageType.ERROR.getType())) ? ("[" + MessageType.ERROR.getType() + "] " + message) : message;
        System.out.println(message);
    }
}
