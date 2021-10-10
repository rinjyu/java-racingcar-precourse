package nextstep.utils;

import racinggame.common.constant.RacingConstant;
import racinggame.common.enums.Message;
import racinggame.common.validation.RacingValidation;
import racinggame.domain.RacingCar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Choi InJoo <rinjyu@naver.com>
 * @version 1.0
 * @since 1.0
 */
public class RacingRule {

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
        if (!RacingValidation.isDataExists(location)) {
            location = "";
        }
        return RacingRule.isForward(count) ? (location + RacingConstant.RACING_ADD_POINT) : location;
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
            winner.append(RacingValidation.isDataExists(winner.toString()) ? (RacingConstant.RACING_CAR_DELIMITER + racingCarName) : racingCarName);
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
