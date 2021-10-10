package nextstep.utils;

import racinggame.Message;
import racinggame.RacingCar;
import racinggame.RacingRuleException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author Choi InJoo <rinjyu@naver.com>
 * @version 1.0
 * @since 1.0
 */
public class RacingRule {

    public static final String RACING_CAR_DELIMITER = ",";

    private static final String RACING_ADD_POINT = "-";

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
        throw new RacingRuleException(Message.ERROR_INVALID_RACING_CAR_NAME.getMessage());
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
        throw new RacingRuleException(Message.ERROR_INVALID_NUMBER.getMessage());
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

    /**
     * 각 자동차별 현재 위치
     * @param count 사용자가 입력한 횟수
     * @param location 이전 위치
     * @return 자동차의 현재 위치
     */
    public static String currentLocation(int count, String location) {
        if (!isDataExists(location)) {
            location = "";
        }
        return RacingRule.isForward(count) ? (location + RACING_ADD_POINT) : location;
    }

    /**
     * 각 자동차별 현재 위치 정보
     * @param racingCar 자동차
     * @return 각 자동차별 현재 위치 정보(자동차명 : 현재 위치)
     */
    public static String racingCarCurrentLocation(RacingCar racingCar) {
        return String.format(Message.CURRENT_RACING_CAR_LOCATION.getMessage(), racingCar.getName(), racingCar.getLocation());
    }

    /**
     * 이동거리에 대한 내림차순 정렬
     * @param racingCarList 경주가 끝난 자동차정보
     */
    public static void racingCarsReversedOrder(List<RacingCar> racingCarList) {
        racingCarList.sort(Collections.reverseOrder());
    }

    /**
     * 자동차 경주 우승자의 이동거리
     * @param sortedRacingCarList 이동거리에 대한 내림차순 정렬된 각 자동차별 위치
     * @return 자동차 경주 우승자의 이동거리
     */
    public static int racingCarMaxDistance(List<RacingCar> sortedRacingCarList) {
        return Collections.max(sortedRacingCarList).getLocation().length();
    }

    /**
     * 자동차 경주 우승자 조회
     * @param winner 자동차 경주 우승자
     * @param location 현재 자동차의 이동거리
     * @param maxDistance 자동차 경주 우승자의 이동거리
     * @param racingCarName 현재 자동차의 이름
     */
    public static void getRacingWinner(StringBuilder winner, String location, int maxDistance, String racingCarName) {
        if (location.length() == maxDistance) {
            winner.append(isDataExists(winner.toString()) ? (RACING_CAR_DELIMITER + racingCarName) : racingCarName);
        }
    }

    /**
     * 자동차 경주 우승자 확인
     * @param sortedRacingCarList 이동거리에 대한 내림차순 정렬된 각 자동차별 위치
     * @param maxDistance 자동차 경주 우승자의 이동거리
     * @return 자동차 경주 우승자
     */
    public static String racingWinner(List<RacingCar> sortedRacingCarList, int maxDistance) {
        StringBuilder winner = new StringBuilder();
        for (RacingCar racingCar : sortedRacingCarList) {
            if (racingCar.getLocation().length() < maxDistance) {
                return winner.toString();
            }
            getRacingWinner(winner, racingCar.getLocation(), maxDistance, racingCar.getName());
        }
        return winner.toString();
    }

    /**
     * 자동차 경주 결과 메시지
     * @param racingCarList 자동차 경주 우승자 정보
     * @return 최종 우승자 정보
     */
    public static String racingResultMessage(List<RacingCar> racingCarList) {
        racingCarsReversedOrder(racingCarList);
        String winner = racingWinner(racingCarList, racingCarMaxDistance(racingCarList));
        return String.format(Message.THE_RACING_FINAL_WINNER.getMessage(), winner);
    }
}
