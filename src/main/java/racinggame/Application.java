package racinggame;

import nextstep.utils.Console;
import nextstep.utils.RacingRule;
import nextstep.utils.Randoms;

import java.util.List;
import java.util.ListIterator;

/**
 * @author Choi InJoo <rinjyu@naver.com>
 * @version 1.0
 * @since 1.0
 */
public class Application {

    private static List<RacingCar> racingCars;
    private static String count;

    public static void main(String[] args) {
        racingGameView();
    }

    /**
     * 자동차 경주 화면
     */
    public static void racingGameView() {
        racingCars();
        userNumber();
        duringTheRacing(racingCars, Integer.parseInt(count));
        System.out.println(RacingRule.racingResultMessage(racingCars));
    }

    /**
     * 사용자가에게 경주할 자동차명 입력받기
     */
    public static void racingCars() {
        System.out.println("경주할 자동차 이름을 입력하세요.(이름은 쉼표(,) 기준으로 구분)");
        String userRacingCars = Console.readLine();
        try {
            RacingRule.isRacingCarsValidation(userRacingCars);
            racingCars = RacingRule.racingCarList(userRacingCars.split(","));
        } catch (RacingRuleException e) {
            System.out.println(e.getMessage());
            racingCars();
        }
    }

    /**
     * 사용자에게 횟수 입력받기
     */
    public static void userNumber() {
        System.out.println("시도할 회수는 몇회인가요?");
        count = Console.readLine();
        try {
            RacingRule.isCountValidation(count);
        } catch (RacingRuleException e) {
            System.out.println(e.getMessage());
            userNumber();
        }
    }

    /**
     * 자동차 경주 진행중
     * @param racingCarList 경주 중인 자동차정보
     * @param count 횟수
     */
    public static void duringTheRacing(List<RacingCar> racingCarList, int count) {
        while (count > 0) {
            racingCars = currentRacingResult(racingCarList);
            count--;
        }
    }

    /**
     * 현재 자동차 경주 결과
     * @param racingCarList 경주 중인 자동차정보
     * @return 경주 중인 자동차정보
     */
    public static List<RacingCar> currentRacingResult(List<RacingCar> racingCarList) {
        ListIterator<RacingCar> iterator = racingCarList.listIterator();
        while (iterator.hasNext()) {
            RacingCar racingCar = iterator.next();
            racingCar.setCount(Randoms.pickNumberInRange(0, 9));
            racingCarList.set(iterator.previousIndex(), racingCar);
            System.out.println(RacingRule.racingCarCurrentLocation(racingCar));

            if (!iterator.hasNext()) System.out.println();
        }

        return racingCarList;
    }
}
