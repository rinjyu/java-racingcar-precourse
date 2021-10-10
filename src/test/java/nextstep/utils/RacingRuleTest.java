package nextstep.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import racinggame.Message;
import racinggame.RacingCar;
import racinggame.RacingRuleException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class RacingRuleTest {

    private static final String RACING_CAR_DELIMITER = ",";

    private static final String RACING_ADD_POINT = "-";

    @ParameterizedTest
    @DisplayName("데이터의 null or empty string 여부 확인")
    @NullAndEmptySource
    @ValueSource(strings = {"MyCar"})
    void 데이터의_null_or_empty_string_여부_확인(String data) {
        assertTrue(data == null || data.trim().isEmpty(), "입력 데이터 있음");
    }

    @ParameterizedTest
    @DisplayName("자동차명 내의 콤마(,) 포함 여부 확인")
    @ValueSource(strings = {"MyCar1", "MyCar2,MyCar3"})
    void 자동차명_내의_콤마_포함_여부_확인(String racingCars) {
        assertThat(racingCars).contains(RACING_CAR_DELIMITER);
    }

    @ParameterizedTest
    @DisplayName("자동차명의 글자수 확인")
    @ValueSource(strings = {"MyCar", "MyCar1234"})
    void 자동차명의_글자수_확인(String racingCars) {
        assertThat(racingCars.length()).isBetween(1, 5);
    }

    @ParameterizedTest
    @DisplayName("자동차명 유효성 확인 : 유효한 값인 경우")
    @ValueSource(strings = {"Car1,Car2"})
    void 자동차명_유효성_확인_유효한_값인_경우(String racingCars) {
        assertDoesNotThrow(() -> RacingRule.isRacingCarsValidation(racingCars));
    }

    @ParameterizedTest
    @DisplayName("자동차명 유효성 확인 : 유효하지 않은 값인 경우")
    @ValueSource(strings = {"MyCar,MyCar1", "MyCar1,MyCar", "MyCar1,MyCar2"})
    void 자동차명_유효성_확인_유효하지_않은_값인_경우(String racingCars) {
        RacingRuleException thrown = assertThrows(RacingRuleException.class,
                () -> RacingRule.isRacingCarsValidation(racingCars));
        assertThat(thrown.getMessage()).isEqualTo(Message.ERROR_INVALID_RACING_CAR_NAME.getMessage());
    }

    @ParameterizedTest
    @DisplayName("횟수가 유효한 값인지의 여부 확인 : 유효한 값인 경우")
    @ValueSource(strings = {"1", "2", "6", "8"})
    void 횟수가_유효한_값인지의_여부_확인_유효한_값인_경우(String count) {
        assertDoesNotThrow(() -> RacingRule.isCountValidation(count));
    }

    @ParameterizedTest
    @DisplayName("횟수가 유효한 값인지의 여부 확인 : 유효하지 않은 값인 경우")
    @ValueSource(strings = {"abc"})
    void 횟수가_유효한_값인지의_여부_확인_유효하지_않은_값인_경우(String count) {
        RacingRuleException thrown = assertThrows(RacingRuleException.class,
                () -> RacingRule.isCountValidation(count));
        assertThat(thrown.getMessage()).isEqualTo(Message.ERROR_INVALID_NUMBER.getMessage());
    }

    @ParameterizedTest
    @DisplayName("사용자가 입력한 자동차명을 List 형으로 변환")
    @MethodSource("generateRacingCars")
    void 사용자가_입력한_자동차명을_List_형으로_변환(String[] racingCars) {
        List<RacingCar> racingCarList = new ArrayList<>();
        for (String car : racingCars) {
            racingCarList.add(new RacingCar(car));
        }
        assertNotNull(racingCarList);
    }

    @ParameterizedTest
    @DisplayName("전진 조건인지의 여부 확인")
    @ValueSource(ints = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9})
    void 전진_조건인지의_여부_확인(int count) {
        assertThat(count).isGreaterThanOrEqualTo(4);
    }

    @ParameterizedTest
    @DisplayName("각 자동차별 현재 위치 : 경주 시작")
    @CsvSource({"5,", "5,''"})
    void 각_자동차별_현재_위치_경주_시작(int count, String location) {
        if (!RacingRule.isDataExists(location)) {
            location = "";
        }
        location = RacingRule.isForward(count) ? (location + RACING_ADD_POINT) : location;
        assertThat(location).isEqualTo("-");
    }

    @ParameterizedTest
    @DisplayName("각 자동차별 현재 위치 : 전진")
    @CsvSource({"6,'---'"})
    void 각_자동차별_현재_위치_전진(int count, String location) {
        if (!RacingRule.isDataExists(location)) {
            location = "";
        }
        location = RacingRule.isForward(count) ? (location + RACING_ADD_POINT) : location;
        assertThat(location).isEqualTo("----");
    }

    @ParameterizedTest
    @DisplayName("각 자동차별 현재 위치 : 정지")
    @CsvSource({"3,'---'"})
    void 각_자동차별_현재_위치_정지(int count, String location) {
        if (!RacingRule.isDataExists(location)) {
            location = "";
        }
        location = RacingRule.isForward(count) ? (location + RACING_ADD_POINT) : location;
        assertThat(location).isEqualTo("---");
    }

    @ParameterizedTest
    @DisplayName("이동거리에 대한 내림차순 정렬")
    @MethodSource("generateRacingCarList")
    void 이동거리에_대한_내림차순_정렬(List<RacingCar> racingCarList) {
        List<RacingCar> sortedRacingCarList = new ArrayList<>();
        Collections.copy(racingCarList, sortedRacingCarList);
        sortedRacingCarList.sort(Collections.reverseOrder());
        assertAll(
                () -> assertNotNull(sortedRacingCarList),
                () -> assertNotEquals(sortedRacingCarList, racingCarList),
                () -> assertNotSame(sortedRacingCarList, racingCarList)
        );
    }

    @ParameterizedTest
    @DisplayName("자동차 경주 우승자의 이동거리")
    @MethodSource("generateCurrentRacingCars")
    void 자동차_경주_우승자의_이동거리(List<RacingCar> racingCarList, int maxDistance) {
        racingCarList.sort(Collections.reverseOrder());
        assertThat(Collections.max(racingCarList).getLocation().length()).isEqualTo(maxDistance);
    }

    @ParameterizedTest
    @DisplayName("자동차 경주 우승자 조회")
    @MethodSource("getRacingCarWinner")
    void 자동차_경주_우승자_조회(StringBuilder winner, String location, int maxDistance, String racingCarName) {
        if (location.length() == maxDistance) {
            winner.append(RacingRule.isDataExists(winner.toString()) ? (RACING_CAR_DELIMITER + racingCarName) : racingCarName);
        }
        assertAll(
                () -> assertNotNull(winner.toString()),
                () -> assertEquals(winner.toString(), "Car1")
        );
    }

    @ParameterizedTest
    @DisplayName("자동차 경주 우승자 확인")
    @MethodSource("generateCurrentRacingCars")
    void 자동차_경주_우승자_확인(List<RacingCar> racingCarList, int maxDistance) {
        racingCarList.sort(Collections.reverseOrder());
        StringBuilder winner = new StringBuilder();
        for (RacingCar racingCar : racingCarList) {
            if (racingCar.getLocation().length() < maxDistance) {
                break;
            }
            if (racingCar.getLocation().length() == maxDistance) {
                winner.append(RacingRule.isDataExists(winner.toString()) ? (RACING_CAR_DELIMITER + racingCar.getName()) : racingCar.getName());
            }
        }

        assertAll(
                () -> assertNotNull(winner.toString()),
                () -> assertEquals(winner.toString(), "Car1,Car3")
        );
    }

    @ParameterizedTest
    @DisplayName("자동차 경주 결과 메시지")
    @MethodSource("generateRacingCarList")
    void 자동차_경주_결과_메시지(List<RacingCar> racingCarList) {
        racingCarList.sort(Collections.reverseOrder());
        String winner = RacingRule.racingWinner(racingCarList, RacingRule.racingCarMaxDistance(racingCarList));
        assertThat(winner).isEqualTo("Car1,Car3");
    }

    private static Stream<Arguments> generateRacingCars() {
        return Stream.of(
                Arguments.of((Object) new String[]{"Car1", "Car2", "Car3", "Car4"})
        );
    }

    private static Stream<Arguments> generateCurrentRacingCars() {
        List<RacingCar> racingCarList = new ArrayList<>();
        racingCarList.add(new RacingCar("Car1", 5, "---"));
        racingCarList.add(new RacingCar("Car2", 3, ""));
        racingCarList.add(new RacingCar("Car3", 6, "---"));
        racingCarList.add(new RacingCar("Car4", 3, "-"));

        return Stream.of(
                arguments(racingCarList, 4)
        );
    }

    private static Stream<Arguments> getRacingCarWinner() {
        return Stream.of(
                arguments(new StringBuilder(), "----", 4, "Car1"),
                arguments(new StringBuilder(), "---", 4, "Car2")
        );
    }

    private static Stream<Arguments> generateRacingCarList() {
        List<RacingCar> racingCarList = new ArrayList<>();
        racingCarList.add(new RacingCar("Car1", 5, "---"));
        racingCarList.add(new RacingCar("Car2", 3, ""));
        racingCarList.add(new RacingCar("Car3", 6, "---"));
        racingCarList.add(new RacingCar("Car4", 3, "-"));

        return Stream.of(
                arguments(racingCarList)
        );
    }
}