import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @Description TODO .</br>
 * <></>
 * @Author gu
 * @Date 2021/1/23 16:57
 * @Version 1.0.0
 **/
public class Test {
    @org.junit.Test
    public void test(){
        String pattern = "\\d*";
        // 创建 Pattern 对象
        Pattern r = Pattern.compile(pattern);

        Matcher matcher = r.matcher("8899绑定的车辆符合放行条件  离开");
        if (matcher.find()) {
            System.out.println(matcher.group(0));
        }
    }

    @org.junit.Test
    public void testOther(){
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);
        list.add(10);
        System.out.println(list.stream().map(f->String.valueOf(f)).collect(Collectors.joining(",")));
    }
}
