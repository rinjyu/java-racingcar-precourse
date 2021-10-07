package nextstep.utils;

import racinggame.RacingCar;
import racinggame.RacingRuleException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author Choi InJoo <rinjyu@naver.com>
 * @version 1.0
 * @since 1.0
 */
public class RacingRule {

    private static final String RACING_CAR_DELIMITER = ",";

    /**
     * 데이터의 null or empty string 여부 확인
     * @param data 데이터
     * @return 데이터가 null 혹은 empty string 인지의 여부(true / false)
     */
    public static boolean isDataExists(String data) {
        return !(data == null || data.trim().isEmpty());
    }

    /**
     * 자동차명 내의 콤마(,) 포함 여부 확인
     * @param racingCars 사용자가 입력한 자동차명
     * @return 자동차명 내의 콤마(,)가 포함되어 있는 지의 여부(true / false)
     */
    public static boolean isRacingCarsNameContainsComma(String racingCars) {
        return racingCars.contains(RACING_CAR_DELIMITER);
    }

    /**
     * 자동차명의 글자수 확인
     * @param racingCars 사용자가 입력한 자동차명
     * @return 자동차명의 글자수가 1~5사이의 값인지의 여부(true / false)
     */
    public static boolean isEachRacingCarNameLength(String[] racingCars) {
        for (String car : racingCars) {
            if (!(car.length() > 0 && car.length() < 6)) return false;
        }
        return true;
    }

    /**
     * 자동차명 유효성 확인
     * @param racingCars 사용자가 입력한 자동차명
     * @throws RacingRuleException 유효한 조건에 부합하지 않는 경우 발생
     */
    public static void isRacingCarsValidation(String racingCars) {
        if (isDataExists(racingCars) && isRacingCarsNameContainsComma(racingCars) &&
                isEachRacingCarNameLength(racingCars.split(RACING_CAR_DELIMITER))) {
            return;
        }
        throw new RacingRuleException("[ERROR] 유효하지 않은 자동차명입니다.\n다시 입력해주세요.");
    }

    /**
     * 횟수가 유효한 값인지의 여부 확인
     * @param count 횟수
     * @throws RacingRuleException 유효한 조건에 부합하지 않는 경우 발생
     */
    public static void isCountValidation(String count) {
        if (Pattern.matches("^[0-9]*$", count) && Integer.parseInt(count) > 0) {
            return;
        }
        throw new RacingRuleException("[ERROR] 숫자로 입력해주세요.");
    }

    /**
     * 사용자가 입력한 자동차명을 List 형으로 변환
     * @param racingCars 사용자가 입력한 자동차명
     * @return List 형으로 변환한 자동차명
     */
    public static List<RacingCar> racingCarList(String[] racingCars) {
        List<RacingCar> racingCarList = new ArrayList<>();
        for (String car : racingCars) {
            racingCarList.add(new RacingCar(car));
        }
        return racingCarList;
    }

    /**
     * 전진 조건인지의 여부 확인
     * @param count 횟수
     * @return 전진 조건인지의 여부(true / false)
     */
    public static boolean isForward(int count) {
        return count >= 4;
    }
}
