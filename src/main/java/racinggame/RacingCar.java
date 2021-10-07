package racinggame;

import nextstep.utils.RacingRule;

/**
 * @author Choi InJoo <rinjyu@naver.com>
 * @version 1.0
 * @since 1.0
 */
public class RacingCar {

    private String name;
    private int count;
    private String location;

    public RacingCar(String name) {
        this.name = name;
        this.location = RacingRule.currentLocation(count, "");
    }

    public String getName() {
        return name;
    }

    public void setCount(int count) {
        this.count = count;
        this.location = RacingRule.currentLocation(count, location);
    }

    public String getLocation() {
        return location;
    }

    @Override
    public String toString() {
        return "RacingCar{" +
                "name='" + name + '\'' +
                ", count=" + count +
                ", location='" + location + '\'' +
                '}';
    }
}