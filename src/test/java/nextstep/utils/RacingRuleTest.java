package nextstep.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RacingRuleTest {

    public static final String RACING_CAR_DELIMITER = ",";

    @ParameterizedTest
    @DisplayName("데이터의 null or empty string 여부 확인 : 데이터가 존재하는 경우")
    @ValueSource(strings = {"MyCar"})
    void 데이터가_존재하는_경우(String data) {
        assertTrue(!(data == null || data.trim().isEmpty()), "입력 데이터 없음");
    }

    @ParameterizedTest
    @DisplayName("데이터의 null or empty string 여부 확인 : 데이터가 존재하지 않는 경우")
    @NullAndEmptySource
    void 데이터가_존재하지않는_경우(String data) {
        assertFalse(!(data == null || data.trim().isEmpty()), "입력 데이터 있음");
    }

    @ParameterizedTest
    @DisplayName("자동차명 내의 콤마(,) 포함 여부 확인")
    @ValueSource(strings = {"MyCar1", "MyCar2,MyCar3"})
    void 자동차명_내의_콤마_포함_여부_확인(String racingCars) {
        assertTrue(racingCars.contains(RACING_CAR_DELIMITER), "자동차명 내의 콤마(,)가 포함되지 않음");
    }
}