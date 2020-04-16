package spring.simpleIOC;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Car {
    private String name;
    private String length;
    private String width;
    private String height;
    private Wheel wheel;

    // 省略其他不重要代码
}