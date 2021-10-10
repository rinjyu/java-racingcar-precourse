package racinggame.common.validation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import racinggame.common.constant.RacingConstant;
import racinggame.common.enums.Message;
import racinggame.common.exception.RacingRuleException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class RacingValidationTest {

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
        assertThat(racingCars).contains(RacingConstant.RACING_CAR_DELIMITER);
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
        assertDoesNotThrow(() -> RacingValidation.isRacingCarsValidation(racingCars));
    }

    @ParameterizedTest
    @DisplayName("자동차명 유효성 확인 : 유효하지 않은 값인 경우")
    @ValueSource(strings = {"MyCar,MyCar1", "MyCar1,MyCar", "MyCar1,MyCar2"})
    void 자동차명_유효성_확인_유효하지_않은_값인_경우(String racingCars) {
        RacingRuleException thrown = assertThrows(RacingRuleException.class,
                () -> RacingValidation.isRacingCarsValidation(racingCars));
        assertThat(thrown.getMessage()).isEqualTo(Message.ERROR_INVALID_RACING_CAR_NAME.getMessage());
    }

    @ParameterizedTest
    @DisplayName("횟수가 유효한 값인지의 여부 확인 : 유효한 값인 경우")
    @ValueSource(strings = {"1", "2", "6", "8"})
    void 횟수가_유효한_값인지의_여부_확인_유효한_값인_경우(String count) {
        assertDoesNotThrow(() -> RacingValidation.isCountValidation(count));
    }

    @ParameterizedTest
    @DisplayName("횟수가 유효한 값인지의 여부 확인 : 유효하지 않은 값인 경우")
    @ValueSource(strings = {"abc"})
    void 횟수가_유효한_값인지의_여부_확인_유효하지_않은_값인_경우(String count) {
        RacingRuleException thrown = assertThrows(RacingRuleException.class,
                () -> RacingValidation.isCountValidation(count));
        assertThat(thrown.getMessage()).isEqualTo(Message.ERROR_INVALID_NUMBER.getMessage());
    }
}